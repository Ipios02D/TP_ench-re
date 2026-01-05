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


	static int prix;
	static ServiceStoC currentWinner;
	static Date date;

	public static void main(String[] args) {
		try {
			
			
			prix = 1000;
			currentWinner = null;
			
			LocateRegistry.createRegistry(1099);

			ObjetDistant obj = new ObjetDistant();
			Naming.bind("ServicesObjetDistant", obj);
			
			System.out.println("Service d'Enchere créé ...");

			
			
			Thread t = new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						Date d = new Date();
						Thread.sleep(1000);
						if(date.compareTo(d) == 0) {
							try {
								currentWinner.afficheGagnant();
							} catch (RemoteException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			t.start();
			
		} catch (MalformedURLException | RemoteException | AlreadyBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
