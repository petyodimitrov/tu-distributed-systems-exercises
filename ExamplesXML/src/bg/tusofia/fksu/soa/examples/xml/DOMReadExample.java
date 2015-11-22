package bg.tusofia.fksu.soa.examples.xml;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DOMReadExample {

	public static void main(String[] args) throws Exception {
		File xmlFile = new File(Constants.XML_PATH);

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		factory.setValidating(false);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(xmlFile);

		Element root = doc.getDocumentElement();

		NodeList children = root.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child instanceof Element && child.getChildNodes().getLength() > 0) {
				Node whitespace = child.getFirstChild();
				Node title = whitespace.getNextSibling();
				System.out.println(title.getLocalName() + " - " + title.getTextContent());
			}
		}

		NodeList lengths = doc.getElementsByTagNameNS("http://opa/cool", "length");
		for (int i = 0; i < lengths.getLength(); i++) {
			Node length = lengths.item(i);
			NamedNodeMap attrs = length.getAttributes();
			String unit = attrs.getNamedItem("unit").getNodeValue();
			System.out.println(length.getTextContent() + " " + unit);
		}

	}
}
