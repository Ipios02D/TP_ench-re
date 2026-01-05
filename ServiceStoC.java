

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface ServiceStoC extends Remote {
	
	public void newPrix(String name, String msg) throws RemoteException;
	public void afficheGagnant(String name) throws RemoteException;
	public void info(int date, String name, Date d) throws RemoteException;
}
