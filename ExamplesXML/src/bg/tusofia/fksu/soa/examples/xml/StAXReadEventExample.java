package bg.tusofia.fksu.soa.examples.xml;

import java.io.FileReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

public class StAXReadEventExample {

	public static void main(String[] args) throws Exception {
		XMLInputFactory f = XMLInputFactory.newInstance();
		XMLStreamReader reader = f.createXMLStreamReader(new FileReader(Constants.XML_PATH));
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
