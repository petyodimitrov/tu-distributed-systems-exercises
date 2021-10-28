package bg.tusofia.cst.ds.xml.parsers;

import bg.tusofia.cst.ds.xml.Constants;
import bg.tusofia.cst.ds.xml.Util;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;

/**
 * Shows how to read an XML document via a StAX API.
 */
public class StAXReadEventExample {

    public static void main(String[] args) throws Exception {
        InputStream input = Util.loadFile(Constants.XML_PATH);

        XMLInputFactory f = XMLInputFactory.newInstance();
        XMLStreamReader reader = f.createXMLStreamReader(input);
        while (reader.hasNext()) {
            int eventType = reader.next();
            switch (eventType) {
                case XMLEvent.START_ELEMENT:
                    if (reader.getLocalName().equals("length")) {
                        String lengthUnit = reader.getAttributeValue(0);
                        if (reader.next() == XMLEvent.CHARACTERS) {
                            System.out.println(reader.getText() + " " + lengthUnit);
                        }
                    }
                    break;
            }
        }
    }

}
