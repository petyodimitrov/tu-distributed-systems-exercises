package bg.tusofia.cst.ds.camel.processor;

import bg.tusofia.cst.ds.camel.model.Lecture;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import java.util.Date;

public class CreateLectureProcessor implements Processor {

    public void process(Exchange exchange) {
        Lecture lecture = new Lecture("ESB", 30, new Date());
        exchange.getIn().setBody(lecture);
    }

}