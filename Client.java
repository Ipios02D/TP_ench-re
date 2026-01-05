package dwEnchere;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {

	public static void main(String[] args) {
		try {
			ServiceCtoS sc = (ServiceCtoS) Naming.lookup("ServicesObjetDistant");
			
			System.out.println("Client connecté au ServeurEnchere");
			
			
			User u1 = new User("Paul");
			
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
