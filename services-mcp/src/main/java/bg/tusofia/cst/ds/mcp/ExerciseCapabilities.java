package bg.tusofia.cst.ds.mcp;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.modelcontextprotocol.json.McpJsonMapper;
import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.spec.McpSchema;
import io.modelcontextprotocol.spec.McpSchema.CallToolResult;
import io.modelcontextprotocol.spec.McpSchema.ReadResourceResult;
import io.modelcontextprotocol.spec.McpSchema.Resource;
import io.modelcontextprotocol.spec.McpSchema.ServerCapabilities;
import io.modelcontextprotocol.spec.McpSchema.TextResourceContents;
import io.modelcontextprotocol.spec.McpSchema.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The capabilities published by this MCP server, independent of the transport.
 *
 * <p>Exactly the same tool and resource are served over the STDIO transport
 * ({@link ExercisesMcpServer}) and over the Streamable HTTP transport
 * ({@link ExercisesMcpHttpServer}) - in MCP the transport only carries the JSON-RPC
 * messages and does not influence what the server offers.
 */
final class ExerciseCapabilities {

    private static final Logger log = LoggerFactory.getLogger(ExerciseCapabilities.class);

    static final String SERVER_NAME = "ds-exercises";
    static final String SERVER_VERSION = "1.0.0";

    static final String CATALOG_URI = "exercises://catalog";
    static final String TOOL_NAME = "exercise_info";

    static final String INSTRUCTIONS = "Provides information about the demo projects of the course "
            + "\"Programming in a distributed environment\" at the Technical University of Sofia. "
            + "Read the exercises://catalog resource for the list of projects and call the "
            + "exercise_info tool for details about a single one.";

    /** JSON Schema describing the arguments accepted by the tool. */
    private static final String EXERCISE_INFO_INPUT_SCHEMA = """
            {
              "type": "object",
              "properties": {
                "id": {
                  "type": "string",
                  "description": "Identifier of the exercise, equal to its directory name, e.g. services-grpc"
                }
              },
              "required": ["id"],
              "additionalProperties": false
            }
            """;

    private ExerciseCapabilities() {
    }

    static ServerCapabilities serverCapabilities() {
        return ServerCapabilities.builder()
                .tools(true)
                .resources(false, true)
                .logging()
                .build();
    }

    /**
     * The sample tool. A tool is an action the model may invoke; its arguments are
     * described by a JSON Schema and validated by the SDK before the handler is called.
     */
    static McpServerFeatures.SyncToolSpecification exerciseInfoTool(McpJsonMapper jsonMapper) {
        Tool tool = Tool.builder(TOOL_NAME, jsonMapper, EXERCISE_INFO_INPUT_SCHEMA)
                .title("Exercise information")
                .description("Returns the technology and a short description of one demo project "
                        + "of the distributed systems course. Known ids: " + ExerciseCatalog.ids())
                .build();

        return McpServerFeatures.SyncToolSpecification.builder()
                .tool(tool)
                .callHandler((exchange, request) -> {
                    Map<String, Object> arguments = request.arguments();
                    Object id = arguments == null ? null : arguments.get("id");
                    log.info("Tool '{}' called with id={}", TOOL_NAME, id);

                    Optional<ExerciseCatalog.Exercise> exercise =
                            ExerciseCatalog.find(id == null ? null : id.toString());

                    if (exercise.isEmpty()) {
                        // Tool failures are reported as a result with isError=true, so that
                        // the model can see the message and retry with a valid argument.
                        return CallToolResult.builder()
                                .addTextContent("Unknown exercise '" + id + "'. Known ids are: "
                                        + ExerciseCatalog.ids())
                                .isError(true)
                                .build();
                    }

                    return CallToolResult.builder()
                            .addTextContent(ExerciseCatalog.describe(exercise.get()))
                            .isError(false)
                            .build();
                })
                .build();
    }

    /**
     * The sample resource. A resource is context the client may read; it is addressed by
     * a URI and, unlike a tool, is not supposed to have side effects.
     */
    static McpServerFeatures.SyncResourceSpecification catalogResource() {
        Resource resource = Resource.builder(CATALOG_URI, "exercise-catalog")
                .title("Catalog of the course exercises")
                .description("Markdown table of all demo projects in this repository and the "
                        + "distributed systems technology each one demonstrates.")
                .mimeType("text/markdown")
                .build();

        return new McpServerFeatures.SyncResourceSpecification(resource, (exchange, request) -> {
            log.info("Resource '{}' read", request.uri());
            return ReadResourceResult.builder(List.of(
                            TextResourceContents.builder(request.uri(), ExerciseCatalog.asMarkdown())
                                    .mimeType("text/markdown")
                                    .build()))
                    .build();
        });
    }
}
