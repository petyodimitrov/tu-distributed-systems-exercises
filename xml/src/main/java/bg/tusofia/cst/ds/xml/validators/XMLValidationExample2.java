package bg.tusofia.cst.ds.xml.validators;

import bg.tusofia.cst.ds.xml.Constants;
import bg.tusofia.cst.ds.xml.Util;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.InputStream;

/**
 * Shows how to validate an XML document via DOM API.
 * You can experiment by changing the valid & invalid XML documents.
 */
public class XMLValidationExample2 {

    public static void main(String[] args) throws Exception {
        InputStream input = Util.loadFile(Constants.XML_PATH);
        InputStream inputInvalid = Util.loadFile(Constants.INVALID_XML_PATH);
        InputStream xsd = Util.loadFile(Constants.XSD_PATH);

        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        schemaFactory.setResourceResolver(new ResourceResolver());
        Schema schema = schemaFactory.newSchema(new Source[]{new StreamSource(xsd)});

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setValidating(false);
        factory.setSchema(schema);

        DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setErrorHandler(new MyErrorHandler());

        System.out.println("Validating document " + Constants.XML_PATH);
        builder.parse(input);
        System.out.println("Validated.");

        System.out.println("Validating document " + Constants.INVALID_XML_PATH);
        builder.parse(inputInvalid);
        System.out.println("Validated.");
    }

}
