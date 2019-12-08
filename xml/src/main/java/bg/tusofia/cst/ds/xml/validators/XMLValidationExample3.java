package bg.tusofia.cst.ds.xml.validators;

import bg.tusofia.cst.ds.xml.Constants;
import bg.tusofia.cst.ds.xml.Util;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.InputStream;

/**
 * Shows how to validate an XML document via Schemas API.
 * You can experiment by changing the valid & invalid XML documents.
 */
public class XMLValidationExample3 {

    public static void main(String[] args) throws Exception {
        InputStream input = Util.loadFile(Constants.XML_PATH);
        InputStream inputInvalid = Util.loadFile(Constants.INVALID_XML_PATH);
        InputStream xsd = Util.loadFile(Constants.XSD_PATH);

        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        factory.setResourceResolver(new ResourceResolver());
        Schema schema = factory.newSchema(new StreamSource(xsd));

        Validator validator = schema.newValidator();

        System.out.println("Validating document " + Constants.XML_PATH);
        validator.validate(new StreamSource(input));
        System.out.println("Validated.");

        System.out.println("Validating document " + Constants.INVALID_XML_PATH);
        validator.validate(new StreamSource(inputInvalid));
        System.out.println("Validated.");
    }
}
