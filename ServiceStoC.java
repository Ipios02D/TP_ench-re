package dwEnchere;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface ServiceStoC extends Remote, Serializable {
	
	public void afficheGagnant() throws RemoteException;
	public void info(int prix, Date date, String nom) throws RemoteException;
}
