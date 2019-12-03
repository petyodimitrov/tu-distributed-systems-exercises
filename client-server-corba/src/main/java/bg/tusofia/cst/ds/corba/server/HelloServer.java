package bg.tusofia.cst.ds.corba.server;

import bg.tusofia.cst.ds.corba.Constants;
import bg.tusofia.cst.ds.corba.generated.hello.Hello;
import bg.tusofia.cst.ds.corba.generated.hello.HelloHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

/**
 * Server application.
 */
public class HelloServer {

    public static void main(String[] args) throws Exception {
        ORB orb = ORB.init(Constants.CONNECTION_PARAMS, null);
        POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
        rootpoa.the_POAManager().activate();

        HelloImpl helloImpl = new HelloImpl();
        helloImpl.setORB(orb);
        org.omg.CORBA.Object ref = rootpoa.servant_to_reference(helloImpl);
        Hello href = HelloHelper.narrow(ref);

        org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
        NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
        NameComponent path[] = ncRef.to_name("Hello");
        ncRef.rebind(path, href);

        System.out.println("Running ORB...");
        orb.run();
    }

}
