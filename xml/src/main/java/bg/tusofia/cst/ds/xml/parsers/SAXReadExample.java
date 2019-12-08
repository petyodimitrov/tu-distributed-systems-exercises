package bg.tusofia.cst.ds.xml.parsers;

import bg.tusofia.cst.ds.xml.Constants;
import bg.tusofia.cst.ds.xml.Util;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Shows how to read an XML document via a SAX API.
 */
public class SAXReadExample {

    public static void main(String[] args) throws Exception {
        InputStream input = Util.loadFile(Constants.XML_PATH);

        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        SAXParser parser = factory.newSAXParser();

        DefaultHandler handler = new TestHandler();
        parser.parse(input, handler);
    }

    private static class TestHandler extends DefaultHandler {

        private String lengthUnit;

        @Override
        public void characters(char[] ch, int start, int length) {
            if (lengthUnit != null) {
                System.out.print(Arrays.copyOfRange(ch, start, start + length));
                System.out.println(" " + lengthUnit);
            }
        }

        @Override
        public void startElement(String namespaceURI, String lname, String qname, Attributes attrs) {
            lengthUnit = null;
            if (lname.equals("length") && attrs != null) {
                for (int i = 0; i < attrs.getLength(); i++) {
                    String aname = attrs.getLocalName(i);
                    if (aname.equals("unit")) {
                        lengthUnit = attrs.getValue(i);
                    }
                }
            }
        }

        @Override
        public void endElement(String namespaceURI, String lname, String qname) {
            lengthUnit = null;
        }
    }

}