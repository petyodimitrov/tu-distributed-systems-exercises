package bg.tusofia.fksu.soa.examples.xml;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XMLValidationExample1 {

	public static void main(String[] args) throws Exception {
		File xmlFile = new File(Constants.XML_PATH);

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		factory.setValidating(true);
		factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage",
				"http://www.w3.org/2001/XMLSchema");
		DocumentBuilder builder = factory.newDocumentBuilder();
		builder.setErrorHandler(new MyErrorHandler());

		builder.parse(xmlFile);
	}
}

class MyErrorHandler implements ErrorHandler {
	public void warning(SAXParseException e) throws SAXException {
		System.out.println("ei: " + e.getMessage());
	}

	public void error(SAXParseException e) throws SAXException {
		System.out.println("opa: " + e.getMessage());
	}

	public void fatalError(SAXParseException e) throws SAXException {
		System.out.println("alo: " + e.getMessage());
	}
}
