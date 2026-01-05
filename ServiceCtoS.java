package dwEnchere;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServiceCtoS extends Remote{

	public String haveJoined(ServiceStoC sn, String name) throws RemoteException;
	public String proposePrix(ServiceStoC sn, int prix) throws RemoteException;
	
}
