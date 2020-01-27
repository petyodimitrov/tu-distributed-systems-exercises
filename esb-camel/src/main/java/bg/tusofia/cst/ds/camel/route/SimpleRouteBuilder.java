package bg.tusofia.cst.ds.camel.route;

import bg.tusofia.cst.ds.camel.model.Lecture;
import bg.tusofia.cst.ds.camel.processor.CreateLectureProcessor;
import bg.tusofia.cst.ds.camel.processor.CreateLectureResponseProcessor;
import bg.tusofia.cst.ds.camel.processor.GetLectureProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;

public class SimpleRouteBuilder extends RouteBuilder {

    private JacksonDataFormat jsonDataFormat = new JacksonDataFormat(Lecture.class);

    @Override
    public void configure() throws Exception {
        // route for REST GET Call
        from("file:./get?noop=true").setHeader(Exchange.HTTP_METHOD, simple("GET"))
                .to("http://localhost:9998/lectures/1").process(new GetLectureProcessor());

        // route for REST POST Call
        from("file:./post?noop=true").process(new CreateLectureProcessor()).marshal(jsonDataFormat)
                .setHeader(Exchange.HTTP_METHOD, simple("POST"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json")).to("http://localhost:9998/lectures")
                .process(new CreateLectureResponseProcessor());
    }

}