package bg.tusofia.cst.ds.corba.client;

import bg.tusofia.cst.ds.corba.Constants;
import bg.tusofia.cst.ds.corba.generated.hello.Hello;
import bg.tusofia.cst.ds.corba.generated.hello.HelloHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

/**
 * Client application.
 */
public class HelloClient {

    public static void main(String[] args) throws Exception {
        ORB orb = ORB.init(Constants.CONNECTION_PARAMS, null);
        org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
        NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

        System.out.println("Connecting to ORB...");
        Hello hello = HelloHelper.narrow(ncRef.resolve_str("Hello"));

        System.out.println("Calling server...");
        String result = hello.sayHello();
        System.out.println("result: " + result);
    }

}
