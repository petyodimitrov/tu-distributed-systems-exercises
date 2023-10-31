package bg.tusofia.cst.ds.soap.client;

import bg.tusofia.cst.ds.soap.Constants;
import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.net.URL;

public class CinemaClient {

    private static final String NAMESPACE = "http://server.soap.ds.cst.tusofia.bg/";

    private static SOAPMessage createMessage(String title, int year) throws SOAPException {
        SOAPMessage message = MessageFactory.newInstance().createMessage();
        SOAPPart part = message.getSOAPPart();

        SOAPEnvelope envelope = part.getEnvelope();
        SOAPBody body = envelope.getBody();

        QName movieInfo = new QName("http://soap.ds.cst.tusofia.bg/", "getMovieInfo", "e");
        QName movieTitle = new QName("title");
        QName movieYear = new QName("year");

        SOAPBodyElement bodyElement = body.addBodyElement(movieInfo);
        bodyElement.addChildElement(movieTitle).setValue(title);
        bodyElement.addChildElement(movieYear).setValue(String.valueOf(year));
        return message;
    }

    private static void printMessage(SOAPMessage message) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        message.writeTo(baos);

        String xmlString = baos.toString();
        Source xmlInput = new StreamSource(new StringReader(xmlString));
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.transform(xmlInput, new StreamResult(System.out));
    }

    public static void main(String[] args) throws Exception {
        URL wsdl = new URL(Constants.ENDPOINT + "?wsdl");

        QName serviceName = new QName(NAMESPACE, "CinemaServiceImplService");
        Service service = Service.create(wsdl, serviceName);

        QName portName = new QName(NAMESPACE, "CinemaServiceImplPort");
        Dispatch<SOAPMessage> dispatch = service.createDispatch(portName, SOAPMessage.class, Service.Mode.MESSAGE);

        System.out.println("Running SOAP client...");

        System.out.println(">>> request >>>");
        SOAPMessage request = createMessage("The Avengers", 2012);
        printMessage(request);

        SOAPMessage response = dispatch.invoke(request);
        System.out.println("\n<<< response <<<");
        printMessage(response);
    }

}
