package bg.tusofia.fksu.soa.examples.corba.server;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import bg.tusofia.fksu.soa.examples.corba.Constants;
import bg.tusofia.fksu.soa.examples.corba.Hello;
import bg.tusofia.fksu.soa.examples.corba.HelloHelper;

public class HelloApplication {

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
