package bg.tusofia.fksu.soa.examples.rmi.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import bg.tusofia.fksu.soa.examples.rmi.Warehouse;

class WarehouseImpl extends UnicastRemoteObject implements Warehouse {

	private static final long serialVersionUID = -5342199459227058710L;

	public WarehouseImpl() throws RemoteException {
	}

	@Override
	public double getPrice(String productName) throws RemoteException {
		return 69.0;
	}

}
