package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloServiceImpl extends UnicastRemoteObject implements HelloService{
	private static final long serialVersionUID = 1566795633779739582L;

	public HelloServiceImpl() throws RemoteException {
    }
    
    @Override
    public String sayHello(String name) throws RemoteException {
        return String.format("Hello %s", name);
    }
}
