package bg.tusofia.cst.ds.rmi.server;

import javax.naming.Context;
import javax.naming.InitialContext;

public class WarehouseServer {

    public static void main(String[] args) throws Exception {
        WarehouseImpl wwf = new WarehouseImpl();
        Context namingCtx = new InitialContext();
        System.out.println("Running RMI server bean...");
        namingCtx.rebind("rmi:cw", wwf);
    }

}
