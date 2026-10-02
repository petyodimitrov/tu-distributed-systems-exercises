package bg.tusofia.cst.ds.mcp;

import io.modelcontextprotocol.json.McpJsonDefaults;
import io.modelcontextprotocol.json.McpJsonMapper;
import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpSyncServer;
import io.modelcontextprotocol.server.transport.StdioServerTransportProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entry point of the sample Model Context Protocol (MCP) server.
 *
 * <p>MCP is a JSON-RPC 2.0 based protocol through which an AI application (the MCP
 * <em>host</em>, for example Claude Desktop or Claude Code) discovers and uses
 * capabilities offered by external processes (the MCP <em>servers</em>). It is yet
 * another remote invocation style, comparable to the RMI, SOAP, REST and gRPC examples
 * in this repository, but designed for a language model as the caller.
 *
 * <p>The server supports both transports defined by the specification:
 * <ul>
 *     <li><b>STDIO</b> (default) - the host starts this process and exchanges newline
 *     delimited JSON-RPC messages over stdin/stdout. Because stdout belongs to the
 *     protocol, <b>nothing may be printed to it</b>; all diagnostics go to stderr.</li>
 *     <li><b>Streamable HTTP</b> ({@code --http}) - the server listens on a TCP port and
 *     can therefore be called remotely. See {@link ExercisesMcpHttpServer}.</li>
 * </ul>
 *
 * <pre>
 * java -jar services-mcp.jar                  # STDIO
 * java -jar services-mcp.jar --http           # HTTP on 0.0.0.0:8080/mcp
 * java -jar services-mcp.jar --http 9090      # HTTP on 0.0.0.0:9090/mcp
 * </pre>
 */
public final class ExercisesMcpServer {

    private static final Logger log = LoggerFactory.getLogger(ExercisesMcpServer.class);

    public static void main(String[] args) throws Exception {
        if (args.length > 0 && "--http".equals(args[0])) {
            ExercisesMcpHttpServer.main(java.util.Arrays.copyOfRange(args, 1, args.length));
            return;
        }
        if (args.length > 0 && !"--stdio".equals(args[0])) {
            System.err.println("Usage: java -jar services-mcp.jar [--stdio | --http [port]]");
            System.exit(1);
        }
        startStdio();
    }

    /** Starts the server on the STDIO transport and returns. */
    static void startStdio() {
        McpJsonMapper jsonMapper = McpJsonDefaults.getMapper();

        McpSyncServer server = McpServer.sync(new StdioServerTransportProvider(jsonMapper))
                .serverInfo(ExerciseCapabilities.SERVER_NAME, ExerciseCapabilities.SERVER_VERSION)
                .instructions(ExerciseCapabilities.INSTRUCTIONS)
                .capabilities(ExerciseCapabilities.serverCapabilities())
                .tools(ExerciseCapabilities.exerciseInfoTool(jsonMapper))
                .resources(ExerciseCapabilities.catalogResource())
                .build();

        Runtime.getRuntime().addShutdownHook(new Thread(server::closeGracefully, "mcp-shutdown"));

        log.info("MCP server '{}' {} started on the STDIO transport",
                ExerciseCapabilities.SERVER_NAME, ExerciseCapabilities.SERVER_VERSION);
        // The method returns on purpose: the STDIO transport keeps its own non-daemon
        // threads running and the JVM terminates once the host closes stdin.
    }

    private ExercisesMcpServer() {
    }
}
