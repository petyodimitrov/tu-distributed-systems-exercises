package bg.tusofia.fksu.soa.examples.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Warehouse extends Remote {
	double getPrice(String productName) throws RemoteException;
}
