package bg.tusofia.cst.ds.xml.parsers;

import bg.tusofia.cst.ds.xml.Constants;
import bg.tusofia.cst.ds.xml.Util;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;

/**
 * Shows how to read an XML document via a StAX API.
 */
public class StAXReadCursorExample {

    public static void main(String[] args) throws Exception {
        InputStream input = Util.loadFile(Constants.XML_PATH);

        XMLInputFactory f = XMLInputFactory.newInstance();
        XMLEventReader reader = f.createXMLEventReader(input);
        while (reader.hasNext()) {
            XMLEvent e = reader.nextEvent();
            if (e.isStartElement()) {
                StartElement se = e.asStartElement();
                if (se.getName().getLocalPart().equals("length")) {
                    String lengthUnit = se.getAttributeByName(new QName("unit")).getValue();
                    e = reader.nextEvent();
                    if (e.isCharacters()) {
                        System.out.println(e.asCharacters().getData() + " " + lengthUnit);
                    }
                }
            }
        }
    }

}
