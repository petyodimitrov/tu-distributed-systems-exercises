package bg.tusofia.cst.ds.soap.server;

import bg.tusofia.cst.ds.soap.Constants;
import javax.xml.ws.Endpoint;

public class CinemaPublisher {

    public static void main(String[] args) {
        System.out.println("Running SOAP web service...");
        Endpoint.publish(Constants.ENDPOINT, new CinemaServiceImpl());
    }

}