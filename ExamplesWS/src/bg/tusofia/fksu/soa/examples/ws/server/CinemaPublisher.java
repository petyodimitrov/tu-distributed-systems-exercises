package bg.tusofia.fksu.soa.examples.ws.server;

import javax.xml.ws.Endpoint;

public class CinemaPublisher {

	public static void main(String[] args) {
		System.out.println("Running XML web service...");
		Endpoint.publish("http://localhost:9999/cinema", new Cinema());
	}

}
