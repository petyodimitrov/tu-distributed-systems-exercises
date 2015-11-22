package bg.tusofia.fksu.soa.examples.rmi.client;

import javax.naming.Context;
import javax.naming.InitialContext;

import bg.tusofia.fksu.soa.examples.rmi.Warehouse;

public class WarehouseClient {

	private static final String URL = "rmi://localhost:1099/cw";

	public static void main(String[] args) throws Exception {
		Context context = new InitialContext();
		Warehouse cw = (Warehouse) context.lookup(URL);
		double price = cw.getPrice("Kindle");
		System.out.println("price is " + price);
	}

}
