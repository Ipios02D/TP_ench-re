import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

public class Client extends UnicastRemoteObject implements ServiceStoC {

    private PageEnchere fenetreEnchere;
    private ServiceCtoS serveur; // Référence vers le serveur distant
    private String nomClient;

    public static void main(String[] args) {
        try {
            new Client();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    // Le constructeur doit lancer une RemoteException car on hérite de UnicastRemoteObject
    public Client() throws RemoteException {
        super(); 
        
        // 1. Connexion RMI au démarrage
        try {
            serveur = (ServiceCtoS) Naming.lookup("rmi://localhost/ServicesObjetDistant");
            System.out.println("Connexion RMI réussie.");
        } catch (MalformedURLException | NotBoundException e) {
            System.err.println("Erreur de connexion au serveur : " + e.getMessage());
            System.exit(1);
        }

        // 2. Lancement de l'accueil
        new Accueil(this);
    }

    // --- Méthodes appelées par l'Interface Graphique (Front -> Back) ---

    // Appelée par Accueil quand on clique sur "Entrer"
    public void connexion(String nomUtilisateur) {
        this.nomClient = nomUtilisateur;
        System.out.println("Connexion du client : " + nomUtilisateur);
        
        try {
            // On s'enregistre auprès du serveur en envoyant 'this' (le callback)
            serveur.haveJoined(this, nomUtilisateur);
            
            // On ouvre la fenêtre d'enchère (les infos viendront via la méthode info())
            fenetreEnchere = new PageEnchere(nomUtilisateur, this);
            
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    // Appelée par PageEnchere quand on clique sur "Enchérir"
    public void soumettreEnchere(double montant) {
        try {
            // On envoie le prix au serveur (cast en int selon votre interface)
            serveur.proposePrix(this, (int) montant);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    // --- Méthodes appelées par le Serveur (Back -> Front) ---

    @Override
    public void info(int prix, String description, Date date) throws RemoteException {
        // Mise à jour initiale de la fenêtre
        if (fenetreEnchere != null) {
            // On convertit pour l'affichage si besoin, ou on adapte PageEnchere
            fenetreEnchere.mettreAJourPrix(prix, "Actuel");
            // Idéalement ajoutez une méthode setInfos(desc, date) dans PageEnchere
        }
    }

    @Override
    public void newPrix(String prix, String nomGagnant) throws RemoteException {
        if (fenetreEnchere != null) {
            fenetreEnchere.mettreAJourPrix(Integer.parseInt(prix), nomGagnant);
        }
    }

    @Override
    public void afficheGagnant(String nomGagnant) throws RemoteException {
        if (fenetreEnchere != null) {
            fenetreEnchere.afficherVainqueur(nomGagnant);
        }
    }
}