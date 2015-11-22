package bg.tusofia.fksu.soa.examples.xml;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

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
