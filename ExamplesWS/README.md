# XML web services example

1. Run bg.tusofia.fksu.soa.examples.ws.server.CinemaPublisher to start the web service.
2. Connect to the web service by accessing http://localhost:9999/cinema?wsdl in a browser and explore its WSDL and XSD documents.
3. Run bg.tusofia.fksu.soa.examples.ws.client.CinemaClient to connect and use the web service.
4. Import the web service in SOAP UI (http://www.soapui.org/) and interact with it (e.g. get success and fault responses).
5. Enabled WS-Addressing in bg.tusofia.fksu.soa.examples.ws.server.Cinema by uncommenting @javax.xml.ws.soap.Addressing and restart the server.
6. Configure WS-Addressing in SOAP UI and test it again (e.g. send message with addressing info, redirect success reponses to another endpoint, etc.)   
7. Clean up (stop web service publisher).