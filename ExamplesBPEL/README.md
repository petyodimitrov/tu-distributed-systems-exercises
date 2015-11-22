# Business Process example

1. Add BPEL Designer plugin to Eclipse (https://eclipse.org/bpel/install.php).
2. Download Apache ODE 1.3.6 and extract it (http://ode.apache.org/getting-ode.html).
3. Download Apache Tomcat and extract it (http://tomcat.apache.org/download-80.cgi).
4. Start Apache Tomcat: bin/startup .
5. Deploy Apache ODE war file by copying it to Tomcat's webapps/ directory.
6. Once ODE is deployed, check its admin page at http://localhost:8080/ode/ .
7. Deploy the exmaple process by copyin directory helloworld/ to the processes/ sub-directory of the ode war (i.e. webapps\ode\WEB-INF\processes).
8. Verify the process has been deployed via Apache ODE's admin page by going to tab Processes.
9. Obtain the WSDL of the web service from the Apache ODE admin page by going to Deployment Browser > Process Services > helloWorld > Axis2 WSDL (http://localhost:8080/ode/processes/helloWorld?wsdl).
10. Import the web service in SOAP UI (http://www.soapui.org/) and send a few requests. This would trigger process instances which will handle the request and return a result before completing.
11. Review these process instances in Apache ODE's admin page by going to tab Instances.
12. Clean up (stop Apache Tomcat).