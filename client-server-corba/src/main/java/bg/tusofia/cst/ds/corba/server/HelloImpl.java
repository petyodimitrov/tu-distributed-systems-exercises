package bg.tusofia.cst.ds.corba.server;

import bg.tusofia.cst.ds.corba.generated.hello.HelloOperations;
import bg.tusofia.cst.ds.corba.generated.hello.HelloPOA;
import org.omg.CORBA.ORB;

public class HelloImpl extends HelloPOA implements HelloOperations {
    private ORB orb;

    public void setORB(ORB orb_val) {
        orb = orb_val;
    }

    @Override
    public String sayHello() {
        System.out.println("Operation sayHello() triggered");
        return "opa";
    }

    @Override
    public void shutdown() {
        orb.shutdown(true);
    }

}
