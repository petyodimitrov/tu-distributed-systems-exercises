package bg.tusofia.fksu.soa.examples.xml;

import java.io.File;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class XSLTExample {

	public static void main(String[] args) throws Exception {
		TransformerFactory factory = TransformerFactory.newInstance();

		Transformer transformer = factory.newTransformer(new StreamSource(new File(Constants.XSLT_PATH)));
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.transform(new StreamSource(new File(Constants.SIMPLE_XML_PATH)), new StreamResult(System.out));
	}
}
