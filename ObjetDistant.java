package dwEnchere;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ObjetDistant extends UnicastRemoteObject implements ServiceCtoS{

	protected ObjetDistant() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String haveJoined(ServiceStoC sn, String name) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String proposePrix(ServiceStoC sn, int prix) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	
	

}
