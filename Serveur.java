

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;



public class Serveur {

	public static void main(String[] args) {
		try {
			LocateRegistry.createRegistry(1099);

			System.out.println("Annuaire de Chat créé ...");
			ObjetDistant obj = new ObjetDistant();
			Naming.bind("ServicesObjetDistant", obj);
			
			
			System.out.println("Service du Chat enregistrée");

		} catch (MalformedURLException | RemoteException | AlreadyBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
