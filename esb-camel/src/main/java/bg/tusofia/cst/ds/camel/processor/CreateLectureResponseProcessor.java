package bg.tusofia.cst.ds.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class CreateLectureResponseProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {
        System.out.println(exchange.getIn().getHeader("Location"));
    }

}