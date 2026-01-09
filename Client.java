import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

public class Client extends UnicastRemoteObject implements InterfaceCallback  {
    private PageEnchere pageEnchere;
    private String nom;
    private InterfaceServeur serveur;

    public Client() throws Exception {
        super();
        new Accueil(this); 
    }

    public void connexion(String nom) {
        this.nom = nom;
        try {
            Client obj = this;
            this.serveur = (InterfaceServeur) Naming.lookup("ServicesObjetDistant");
            
            Article articleInitial = serveur.getArticle();
            this.pageEnchere = new PageEnchere(nom, this);
            pageEnchere.chargerArticle(articleInitial);

            serveur.callMeBack(obj, nom);
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }
    }

    // Délégation de l'enchère au serveur RMI
    public void soumettreEnchere(double montant, String nomAcheteur) {
        try {
            if (serveur.Encherire(montant, nomAcheteur)) {
                System.out.println("Enchère de " + montant + " € soumise avec succès.");
            } else {
                System.out.println("Enchère de " + montant + " € rejetée par le serveur.");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doCallback() throws RemoteException {
        System.out.println("Callback reçu du serveur !");
    }

    @Override
    public void nouveauPrix(double prix, String meneur, Date dateFin) throws RemoteException {
        System.out.println("Nouveau prix proposé : " + prix + " par " + meneur);
        pageEnchere.mettreAJourInfos(prix, meneur, dateFin);
    }

    @Override
    public void finEnchere(String gagnant, double prixFinal, String infosVendeur) throws RemoteException {
        System.out.println("L'enchère est terminée ! Gagnant : " + gagnant + " avec un prix final de " + prixFinal);
        if (gagnant.equals(nom)) {
            pageEnchere.afficherEcranGagnant(prixFinal, infosVendeur);
        } else {
            pageEnchere.afficherEcranPerdant();
        }
        
    }

    public static void main(String[] args) {
        try {
            new Client();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
