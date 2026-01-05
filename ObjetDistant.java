package dwEnchere;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

public class ObjetDistant extends UnicastRemoteObject implements ServiceCtoS{

	Serveur serv = new Serveur();
	
	protected ObjetDistant() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void haveJoined(ServiceStoC sn,String name) throws RemoteException {
		
		serv.name.add(name);
		sn.info(serv.prix, serv.date, serv.nom);
	}

	@Override
	public void proposePrix(ServiceStoC sn, int prixPropose, String nomPropose) throws RemoteException {
		Date currdate = new Date();
		if(currdate.compareTo(serv.date) < 1) {
			if(prixPropose > serv.prix) {
				serv.prix = prixPropose;
				serv.currentWinner = sn;
				serv.nom = nomPropose;
				sn.info(prixPropose, serv.date, serv.nom);
			}
		}
	}

	
	

}
