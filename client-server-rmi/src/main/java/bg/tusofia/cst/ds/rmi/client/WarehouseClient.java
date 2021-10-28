package bg.tusofia.cst.ds.rmi.client;

import bg.tusofia.cst.ds.rmi.Warehouse;

import javax.naming.Context;
import javax.naming.InitialContext;

public class WarehouseClient {

    private static final String URL = "rmi://localhost:1099/cw";

    public static void main(String[] args) throws Exception {
        Context context = new InitialContext();
        System.out.println("Connecting to remote bean...");
        Warehouse cw = (Warehouse) context.lookup(URL);

        System.out.println("Calling remote method getPrice()...");
        double price = cw.getPrice("Kindle");
        System.out.println("price is " + price);
    }

}
