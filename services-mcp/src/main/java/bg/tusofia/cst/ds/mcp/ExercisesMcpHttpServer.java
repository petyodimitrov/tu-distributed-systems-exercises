package bg.tusofia.cst.ds.mcp;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import io.modelcontextprotocol.json.McpJsonDefaults;
import io.modelcontextprotocol.json.McpJsonMapper;
import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpSyncServer;
import io.modelcontextprotocol.server.transport.DefaultServerTransportSecurityValidator;
import io.modelcontextprotocol.server.transport.HttpServletStreamableServerTransportProvider;
import io.modelcontextprotocol.server.transport.ServerTransportSecurityValidator;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.ee11.servlet.ServletContextHandler;
import org.eclipse.jetty.ee11.servlet.ServletHolder;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exposes the very same MCP capabilities over HTTP, so that the server can be reached
 * remotely instead of being started as a local subprocess.
 *
 * <p>The transport is the <b>Streamable HTTP</b> transport of the MCP specification. It
 * is a small HTTP API around the same JSON-RPC 2.0 messages used by the STDIO transport:
 * <ul>
 *     <li>{@code POST /mcp} - sends a JSON-RPC request or notification. The answer is
 *     either a single {@code application/json} response or, when the server streams
 *     intermediate messages, a {@code text/event-stream} (SSE) body.</li>
 *     <li>{@code GET /mcp} - opens an SSE stream on which the server may push
 *     notifications and server initiated requests to the client.</li>
 *     <li>{@code DELETE /mcp} - terminates the session.</li>
 *     <li>{@code GET /health} - plain readiness check, not part of MCP.</li>
 * </ul>
 *
 * <p>The session established by {@code initialize} is identified by the
 * {@code Mcp-Session-Id} header, which the client must echo on every subsequent request.
 *
 * <pre>
 * java -jar services-mcp.jar --http            # 0.0.0.0:8080
 * java -jar services-mcp.jar --http 9090       # 0.0.0.0:9090
 * </pre>
 *
 * <p>Configuration through environment variables:
 * <ul>
 *     <li>{@code MCP_HTTP_HOST} - bind address, {@code 0.0.0.0} by default;</li>
 *     <li>{@code MCP_HTTP_PORT} - port, {@code 8080} by default;</li>
 *     <li>{@code MCP_ALLOWED_HOSTS}, {@code MCP_ALLOWED_ORIGINS} - comma separated lists
 *     that enable validation of the {@code Host} and {@code Origin} headers (protection
 *     against DNS rebinding). Disabled by default so that the demo works out of the box.</li>
 * </ul>
 */
public final class ExercisesMcpHttpServer {

    private static final Logger log = LoggerFactory.getLogger(ExercisesMcpHttpServer.class);

    static final String MCP_ENDPOINT = "/mcp";
    static final String HEALTH_ENDPOINT = "/health";

    private static final String DEFAULT_HOST = "0.0.0.0";
    private static final int DEFAULT_PORT = 8080;

    public static void main(String[] args) throws Exception {
        String host = env("MCP_HTTP_HOST", DEFAULT_HOST);
        int port = Integer.parseInt(env("MCP_HTTP_PORT", String.valueOf(DEFAULT_PORT)));
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        Server jetty = start(host, port);
        jetty.join();
    }

    /**
     * Builds the MCP server, publishes it as a servlet and starts Jetty.
     *
     * @return the started Jetty server, so that callers (and tests) can stop or join it
     */
    static Server start(String host, int port) throws Exception {
        McpJsonMapper jsonMapper = McpJsonDefaults.getMapper();

        HttpServletStreamableServerTransportProvider transport =
                HttpServletStreamableServerTransportProvider.builder()
                        .jsonMapper(jsonMapper)
                        .mcpEndpoint(MCP_ENDPOINT)
                        // Periodic ping that keeps idle SSE streams and any proxy in between alive.
                        .keepAliveInterval(Duration.ofSeconds(30))
                        .securityValidator(securityValidator())
                        .build();

        McpSyncServer server = McpServer.sync(transport)
                .serverInfo(ExerciseCapabilities.SERVER_NAME, ExerciseCapabilities.SERVER_VERSION)
                .instructions(ExerciseCapabilities.INSTRUCTIONS)
                .capabilities(ExerciseCapabilities.serverCapabilities())
                .tools(ExerciseCapabilities.exerciseInfoTool(jsonMapper))
                .resources(ExerciseCapabilities.catalogResource())
                .build();

        Server jetty = new Server();
        ServerConnector connector = new ServerConnector(jetty);
        connector.setHost(host);
        connector.setPort(port);
        jetty.addConnector(connector);

        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");

        // The transport provider is itself an HttpServlet. Async support is mandatory:
        // the SSE streams are served from an AsyncContext.
        ServletHolder mcpHolder = new ServletHolder(transport);
        mcpHolder.setAsyncSupported(true);
        context.addServlet(mcpHolder, MCP_ENDPOINT);

        context.addServlet(new ServletHolder(new HealthServlet()), HEALTH_ENDPOINT);

        jetty.setHandler(context);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                server.closeGracefully();
                jetty.stop();
            }
            catch (Exception e) {
                log.warn("Error during shutdown: {}", e.getMessage());
            }
        }, "mcp-http-shutdown"));

        jetty.start();
        log.info("MCP server '{}' {} listening on http://{}:{}{}",
                ExerciseCapabilities.SERVER_NAME, ExerciseCapabilities.SERVER_VERSION, host, port, MCP_ENDPOINT);
        return jetty;
    }

    /**
     * Validation of the {@code Host} and {@code Origin} headers, as recommended by the
     * specification against DNS rebinding attacks. Active only when configured, so that
     * the example remains usable without extra setup.
     */
    private static ServerTransportSecurityValidator securityValidator() {
        List<String> hosts = csv(System.getenv("MCP_ALLOWED_HOSTS"));
        List<String> origins = csv(System.getenv("MCP_ALLOWED_ORIGINS"));

        if (hosts.isEmpty() && origins.isEmpty()) {
            log.warn("Host/Origin validation is disabled. Set MCP_ALLOWED_HOSTS and/or "
                    + "MCP_ALLOWED_ORIGINS before exposing this server outside a trusted network.");
            return ServerTransportSecurityValidator.NOOP;
        }

        log.info("Host/Origin validation enabled - hosts={}, origins={}", hosts, origins);
        return DefaultServerTransportSecurityValidator.builder()
                .allowedHosts(hosts)
                .allowedOrigins(origins)
                .build();
    }

    private static List<String> csv(String value) {
        if (value == null || value.isBlank()) {
            return List.of();
        }
        return Arrays.stream(value.split(",")).map(String::trim).filter(s -> !s.isEmpty()).toList();
    }

    private static String env(String name, String defaultValue) {
        String value = System.getenv(name);
        return value == null || value.isBlank() ? defaultValue : value;
    }

    /** Trivial readiness probe, useful for checking that the server is reachable. */
    private static final class HealthServlet extends HttpServlet {

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.getWriter().write("{\"status\":\"UP\",\"server\":\"" + ExerciseCapabilities.SERVER_NAME
                    + "\",\"version\":\"" + ExerciseCapabilities.SERVER_VERSION
                    + "\",\"mcpEndpoint\":\"" + MCP_ENDPOINT + "\"}");
        }
    }

    private ExercisesMcpHttpServer() {
    }
}
