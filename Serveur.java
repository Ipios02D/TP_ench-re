package dwEnchere;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.Date;
import java.util.jar.Attributes.Name;



public class Serveur {
	
	ArrayList<String> name =new ArrayList<>();
	public ArrayList<String> getName() {
		return name;
	}


	private static int prix;
	private static String currentWinner;
	private static Date date;

	public static void main(String[] args) {
		try {
			prix = 1000;
			currentWinner = "";
			
			LocateRegistry.createRegistry(1099);

			ObjetDistant obj = new ObjetDistant();
			Naming.bind("ServicesObjetDistant", obj);
			
			System.out.println("Service d'Enchere créé ...");

		} catch (MalformedURLException | RemoteException | AlreadyBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
