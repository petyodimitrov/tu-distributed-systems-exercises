package bg.tusofia.fksu.soa.examples.corba.client;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import bg.tusofia.fksu.soa.examples.corba.Constants;
import bg.tusofia.fksu.soa.examples.corba.Hello;
import bg.tusofia.fksu.soa.examples.corba.HelloHelper;

public class HelloClient {

	public static void main(String[] args) throws Exception {
		ORB orb = ORB.init(Constants.CONNECTION_PARAMS, null);
		org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
		NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

		System.out.println("Connecting to ORB...");
		Hello hello = HelloHelper.narrow(ncRef.resolve_str("Hello"));

		System.out.println("result: " + hello.sayHello());
	}

}
