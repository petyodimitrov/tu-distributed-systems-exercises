package bg.tusofia.fksu.soa.examples.xml;

import java.io.File;
import java.util.Arrays;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXReadExample {

	public static void main(String[] args) throws Exception {
		File xmlFile = new File(Constants.XML_PATH);

		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		SAXParser parser = factory.newSAXParser();

		DefaultHandler handler = new TestHandler();
		parser.parse(xmlFile, handler);
	}

}

class TestHandler extends DefaultHandler {

	private String lengthUnit;

	public void characters(char ch[], int start, int length) throws SAXException {
		if (lengthUnit != null) {
			System.out.print(Arrays.copyOfRange(ch, start, start + length));
			System.out.println(" " + lengthUnit);
		}
	}

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

	public void endElement(String namespaceURI, String lname, String qname) {
		lengthUnit = null;

	}

}
