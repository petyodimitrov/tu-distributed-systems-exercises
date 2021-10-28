package bg.tusofia.cst.ds.xml.parsers;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Shows how to write an XML document via a DOM API.
 */
public class DOMWriteExample {

    public static void main(String[] args) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        Element newRoot = doc.createElement("root");
        doc.appendChild(newRoot);
        Comment comment = doc.createComment("Just a thought");
        newRoot.appendChild(comment);
        Element child = doc.createElement("child");
        child.setAttribute("name", "value");
        newRoot.appendChild(child);
        Text text = doc.createTextNode("Some text");
        child.appendChild(text);

        // serialize
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(System.out);
        transformer.transform(source, result);
    }

}
