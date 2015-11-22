package bg.tusofia.fksu.soa.examples.xml;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

public class XMLValidationExample2 {

	public static void main(String[] args) throws Exception {
		File xmlFile = new File(Constants.XML_PATH);

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		factory.setValidating(false);

		SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		factory.setSchema(schemaFactory.newSchema(new Source[] { new StreamSource(new File(Constants.XSD_PATH)) }));

		DocumentBuilder builder = factory.newDocumentBuilder();
		builder.setErrorHandler(new MyErrorHandler());

		builder.parse(xmlFile);
	}
	
}
