package dwEnchere;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface ServiceStoC extends Remote {
	
	public void afficheGagnant() throws RemoteException;
	public void info(int date, Date d) throws RemoteException;
}
