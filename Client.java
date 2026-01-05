package dwEnchere;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Scanner;

public class Client {

	public static String nameClient;
	static int prixCurr = 0;
	static Date datefin = null;
	
	public static void main(String[] args) {
		
		try {
			ServiceCtoS cs = (ServiceCtoS) Naming.lookup("ServicesObjetDistant");
			
			System.out.println("Client connecté au ServeurEnchere");
			
			
			Scanner scan = new Scanner(System.in);
			nameClient = scan.next();
			
			
				
				ServiceStoC sc = new ServiceStoC() {
					
					@Override
					public void info(int prix, Date date, String nom) throws RemoteException {
						prixCurr = prix;
						datefin = date;
						System.out.println("new info: "+prix+" "+date+" "+nom);
					}
					
					@Override
					public void afficheGagnant() throws RemoteException {
						// Affiche gagnant
						System.out.println("Vous êtes le gagnant !");
					}
				};
				cs.haveJoined(sc, nameClient);
				
				do {
					System.out.println("Encherir : ");
					int i = scan.nextInt();
					cs.proposePrix(sc, i, nameClient);
					
				} while(true);
			
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getName() {
		return nameClient;
	}
	
}
