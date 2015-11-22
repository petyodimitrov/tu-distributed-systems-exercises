package bg.tusofia.fksu.soa.examples.corba.server;

import org.omg.CORBA.ORB;

import bg.tusofia.fksu.soa.examples.corba.HelloOperations;

public class HelloImpl extends HelloPOA implements HelloOperations {
	private ORB orb;

	public void setORB(ORB orb_val) {
		orb = orb_val;
	}

	@Override
	public String sayHello() {
		return "opa";
	}

	@Override
	public void shutdown() {
		orb.shutdown(true);
	}

}
