package dwEnchere;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Date;

public class Client {

	public String nameClient;
	static int prixCurr = 0;
	static Date datefin = null;
	
	public static void main(String[] args) {
		
		
		
		try {
			ServiceCtoS cs = (ServiceCtoS) Naming.lookup("ServicesObjetDistant");
			
			System.out.println("Client connecté au ServeurEnchere");
			
			ServiceStoC sc = new ServiceStoC() {
				
				@Override
				public void info(int prix, Date date) throws RemoteException {
					prixCurr = prix;
					datefin = date;
				}
				
				@Override
				public void afficheGagnant() throws RemoteException {
					// Affiche gagnat
				}
			};
			
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getName() {
		return nameClient;
	}
	
}
