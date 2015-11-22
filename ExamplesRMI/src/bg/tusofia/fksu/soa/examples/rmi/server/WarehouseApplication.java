package bg.tusofia.fksu.soa.examples.rmi.server;

import javax.naming.Context;
import javax.naming.InitialContext;

public class WarehouseApplication {

	public static void main(String[] args) throws Exception {
		WarehouseImpl wwf = new WarehouseImpl();
		Context namingCtx = new InitialContext();
		System.out.println("Running RMI bean...");
		namingCtx.rebind("rmi:cw", wwf);
	}

}
