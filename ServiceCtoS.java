

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServiceCtoS extends Remote{

	public void haveJoined(ServiceStoC sn, String name) throws RemoteException;
	public void proposePrix(ServiceStoC sn, int prix) throws RemoteException;
	
}
