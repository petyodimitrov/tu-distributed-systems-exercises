package bg.tusofia.cst.ds.xml.parsers;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

/**
 * Shows how to write an XML document via a StAX API.
 */
public class StAXWriteExample {

    private static char[] TITLE = "Taxi driver".toCharArray();

    public static void main(String[] args) throws Exception {
        XMLOutputFactory f = XMLOutputFactory.newInstance();

        XMLStreamWriter writer = f.createXMLStreamWriter(System.out);
        writer.writeStartDocument();
        writer.writeStartElement("movie");
        writer.writeDefaultNamespace("some namespace");
        writer.writeAttribute("director", "Scorsese");
        writer.writeCharacters(TITLE, 0, TITLE.length);
        writer.writeEndElement();
        writer.writeEndDocument();
        writer.close();
    }

}
