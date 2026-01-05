

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServiceStoC extends Remote {
	
	public void newPrix(String name, String msg) throws RemoteException;
	public void afficheGagnant(String name) throws RemoteException;
	
}
