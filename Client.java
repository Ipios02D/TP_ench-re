import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {

	public String nameClient;
	
	public static void main(String[] args) {
		
		
		try {
			
			
			ServiceCtoS sc = (ServiceCtoS) Naming.lookup("ServicesObjetDistant");
			
			System.out.println("Client connecté au ServeurEnchere");

			new Accueil(this);
			
			
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	public String getName() {
		return nameClient;
	}
	
}
