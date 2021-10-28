package bg.tusofia.cst.ds.rmi.server;

import bg.tusofia.cst.ds.rmi.Warehouse;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

class WarehouseImpl extends UnicastRemoteObject implements Warehouse {

    private static final long serialVersionUID = -5342199459227058710L;

    public WarehouseImpl() throws RemoteException {
    }

    @Override
    public double getPrice(String productName) throws RemoteException {
        return 69.0;
    }

}
