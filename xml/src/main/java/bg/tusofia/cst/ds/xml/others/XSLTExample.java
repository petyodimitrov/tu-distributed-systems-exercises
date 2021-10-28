package bg.tusofia.cst.ds.xml.others;

import bg.tusofia.cst.ds.xml.Constants;
import bg.tusofia.cst.ds.xml.Util;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;

/**
 * Shows how to transforms an XML document via XSLT.
 */
public class XSLTExample {

    public static void main(String[] args) throws Exception {
        InputStream input = Util.loadFile(Constants.SIMPLE_XML_PATH);
        InputStream xslt = Util.loadFile(Constants.XSLT_PATH);

        TransformerFactory factory = TransformerFactory.newInstance();

        Transformer transformer = factory.newTransformer(new StreamSource(xslt));
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

        transformer.transform(new StreamSource(input), new StreamResult(System.out));
    }
}
