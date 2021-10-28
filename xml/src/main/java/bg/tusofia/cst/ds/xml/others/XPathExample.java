package bg.tusofia.cst.ds.xml.others;

import bg.tusofia.cst.ds.xml.Constants;
import bg.tusofia.cst.ds.xml.Util;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.InputStream;

/**
 * Shows how to extract data from an XML document via XPath.
 */
public class XPathExample {

    public static void main(String[] args) throws Exception {
        InputStream input = Util.loadFile(Constants.XML_PATH);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(false);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(input);
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();

        XPathExpression expr = xpath.compile("//movie[@rating > 9]/title");
        NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++) {
            System.out.println(nodes.item(i).getTextContent());
        }

    }
}
