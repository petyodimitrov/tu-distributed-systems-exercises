package bg.tusofia.fksu.soa.examples.xml;

import java.io.File;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

public class XMLValidationExample3 {

	public static void main(String[] args) throws Exception {
		SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		Schema schema = factory.newSchema(new File(Constants.XSD_PATH));

		Validator validator = schema.newValidator();
		Source src = new StreamSource(new File(Constants.XML_PATH));
		try {
			validator.validate(src);
		} catch (SAXException ex) {
			ex.printStackTrace();
		}

	}
}
