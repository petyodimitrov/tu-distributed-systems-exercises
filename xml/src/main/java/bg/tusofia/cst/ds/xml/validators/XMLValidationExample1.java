package bg.tusofia.cst.ds.xml.validators;

import bg.tusofia.cst.ds.xml.Constants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Shows how to validate an XML document with embedded XML schema.
 * <p>
 * NOTE: Requires the XML & DTD files to be loaded from the file system, not classpath.
 */
public class XMLValidationExample1 {

    public static void main(String[] args) throws Exception {
        InputStream input = new FileInputStream(Constants.XML_WITH_DTD_PATH);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setValidating(true);

        DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setErrorHandler(new MyErrorHandler());

        System.out.println("Validating document " + Constants.XML_WITH_DTD_PATH);
        builder.parse(input);
        System.out.println("Validated.");
    }

}


