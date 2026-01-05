

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ObjetDistant extends UnicastRemoteObject implements ServiceCtoS{

	Serveur serv = new Serveur();
	
	protected ObjetDistant() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void haveJoined(ServiceStoC sn,String name) throws RemoteException {
		
		serv.name.add(name);
	}

	@Override
	public void proposePrix(ServiceStoC sn, int prix) throws RemoteException {
		
	}

	
	

}
