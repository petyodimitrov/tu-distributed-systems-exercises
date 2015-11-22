package bg.tusofia.fksu.soa.examples.xml;

import java.io.FileReader;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class StAXReadCursorExample {

	public static void main(String[] args) throws Exception {
		XMLInputFactory f = XMLInputFactory.newInstance();
		XMLEventReader reader = f.createXMLEventReader(new FileReader(Constants.XML_PATH));
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
