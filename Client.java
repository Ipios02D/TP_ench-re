import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {

	public static void main(String[] args) {
		new Client();
	}

	public Client() {
		try {
			ServiceCtoS sc = (ServiceCtoS) Naming.lookup("ServicesObjetDistant");
			
			System.out.println("Client connecté au ServeurEnchere");

			new Accueil(this);
			
			
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}

	// Appelé par Accueil quand on clique sur "Entrer"
    public void connexion(String nomUtilisateur) {
        System.out.println("Simulation connexion pour : " + nomUtilisateur);
        
        // On ouvre la page d'enchère
        new PageEnchere(nomUtilisateur, this);
    }
	
}
