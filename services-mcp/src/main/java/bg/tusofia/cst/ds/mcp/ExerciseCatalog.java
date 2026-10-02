package bg.tusofia.cst.ds.mcp;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * In-memory catalog of the demo projects contained in this repository.
 *
 * <p>The same data is published to MCP clients through two different primitives:
 * <ul>
 *     <li>as a <em>resource</em> ({@code exercises://catalog}) - context the client may read,</li>
 *     <li>as a <em>tool</em> ({@code exercise_info}) - an action the model may invoke.</li>
 * </ul>
 */
final class ExerciseCatalog {

    /**
     * A single demo project.
     *
     * @param id         identifier, equal to the directory name in the repository
     * @param title      human readable title
     * @param technology the distributed systems technology being demonstrated
     * @param summary    short description of what the example shows
     */
    record Exercise(String id, String title, String technology, String summary) {
    }

    private static final Map<String, Exercise> EXERCISES = new LinkedHashMap<>();

    private static void add(String id, String title, String technology, String summary) {
        EXERCISES.put(id, new Exercise(id, title, technology, summary));
    }

    static {
        add("client-server-rmi", "Client-server with RMI", "Java RMI",
                "Remote method invocation between a Java client and a Java server using the RMI registry.");
        add("client-server-corba", "Client-server with CORBA", "CORBA / IDL",
                "Language independent remote objects described in IDL and published through an ORB naming service.");
        add("xml", "XML processing", "DOM, SAX, JAXB, XSLT",
                "Parsing, binding, validating and transforming XML documents - the data formats behind SOAP.");
        add("services-soap", "SOAP web service", "JAX-WS / WSDL",
                "Contract first web service described by WSDL and invoked over SOAP envelopes.");
        add("services-rest", "REST web service", "JAX-RS / Spring Web",
                "Resource oriented HTTP service using the standard verbs and JSON representations.");
        add("services-graphql", "GraphQL service", "GraphQL Java",
                "Single endpoint with a typed schema that lets the client select exactly the fields it needs.");
        add("services-grpc", "gRPC service", "gRPC / Protocol Buffers",
                "Binary RPC over HTTP/2 with service contracts and messages generated from .proto files.");
        add("services-mcp", "MCP server", "Model Context Protocol / MCP Java SDK",
                "JSON-RPC 2.0 server that exposes tools and resources to an AI client such as Claude Desktop.");
        add("business-processes-bpel", "Business process orchestration", "WS-BPEL",
                "Orchestration of several web services into one long running executable business process.");
        add("messaging-jms", "Point-to-point and publish-subscribe messaging", "JMS",
                "Asynchronous communication through queues and topics using the Java Message Service API.");
        add("messaging-amqp", "Broker based messaging", "AMQP / RabbitMQ",
                "Exchanges, bindings and queues of the AMQP model demonstrated on RabbitMQ.");
        add("messaging-mqtt", "Lightweight IoT messaging", "MQTT",
                "Publish-subscribe messaging with quality of service levels, suitable for constrained devices.");
        add("messaging-kafka", "Distributed log messaging", "Apache Kafka",
                "Partitioned, replicated commit log with consumer groups for high throughput event streaming.");
        add("esb-camel", "Enterprise integration patterns", "Apache Camel",
                "Routes and mediation between heterogeneous endpoints implemented with the Camel DSL.");
        add("esb-mule", "Enterprise service bus (deprecated)", "Mule ESB",
                "Service bus based integration; kept for reference only, Mule is no longer free to use.");
        add("microservices-jhipster", "Microservice architecture", "JHipster / Spring Cloud",
                "Generated microservices with a gateway, service registry and per-service persistence.");
    }

    private ExerciseCatalog() {
    }

    static Optional<Exercise> find(String id) {
        return Optional.ofNullable(EXERCISES.get(id == null ? "" : id.trim().toLowerCase()));
    }

    static String ids() {
        return String.join(", ", EXERCISES.keySet());
    }

    /** Renders the whole catalog as a Markdown document, used as the resource content. */
    static String asMarkdown() {
        StringBuilder md = new StringBuilder();
        md.append("# Distributed systems exercises\n\n")
          .append("Demo projects of the course \"Programming in a distributed environment\", ")
          .append("Technical University of Sofia.\n\n")
          .append("| Id | Title | Technology |\n")
          .append("| --- | --- | --- |\n");
        for (Exercise exercise : EXERCISES.values()) {
            md.append("| `").append(exercise.id()).append("` | ")
              .append(exercise.title()).append(" | ")
              .append(exercise.technology()).append(" |\n");
        }
        md.append("\nCall the `exercise_info` tool with one of the ids above to get more details.\n");
        return md.toString();
    }

    /** Renders a single exercise as plain text, used as the tool result. */
    static String describe(Exercise exercise) {
        return "Id: " + exercise.id() + "\n"
                + "Title: " + exercise.title() + "\n"
                + "Technology: " + exercise.technology() + "\n"
                + "Summary: " + exercise.summary() + "\n"
                + "Sources: " + exercise.id() + "/ (see " + exercise.id() + "/README.md)";
    }
}
