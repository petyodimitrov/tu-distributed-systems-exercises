package bg.tusofia.cst.ds.rest;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Running REST web service...");
        HttpServer server = HttpServerFactory.create("http://localhost:9998/");
        server.start();
    }

}
