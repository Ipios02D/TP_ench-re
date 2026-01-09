import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class Serveur extends UnicastRemoteObject implements InterfaceServeur {
    private Article article;
    private InterfaceCallback[] clients;
    private int compteurClients = 0;

    protected Serveur() throws RemoteException {
        super();
        clients = new InterfaceCallback[50]; // Capacité maximale de 50 clients

        // Initialisation de l'article
        this.article = new Article("Super voiture qui va très vite. \nElle a 4 roues et des portières.", 10000.0, new Date(System.currentTimeMillis() + 30000), "vendeur: rené; tel : 0123456789", "exempleArticle.png");

        // Planification de la fin de l'enchère
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("L'enchère est terminée !");
                for (int i = 0; i < compteurClients; i++) {
                    try {
                        clients[i].finEnchere(article.getNomMeneur(), article.getPrixActuel(), article.getInfosVendeur());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, article.getDateFin());
    }

    //Implémentation des méthodes de l'interface Serveur

    // Engeristrement d'un client
    @Override
    public synchronized void callMeBack(InterfaceCallback obj, String nom) throws RemoteException {
        obj.doCallback();
        clients[compteurClients++] = obj;
        System.err.println(nom + " a été enregistré pour un callback.");

        // Envoi des informations initiales de l'article au client
        obj.nouveauPrix(article.getPrixActuel(), article.getNomMeneur(), article.getDateFin());
    }

    @Override
    public synchronized boolean  Encherire(double montant, String nomAcheteur) throws RemoteException {
        // Vérification si l'enchère est valide
        if (montant > article.getPrixActuel()) {
            // Mise à jour de l'article
            article.setPrixActuel(montant);
            article.setNomMeneur(nomAcheteur);

            System.out.println("Nouvelle enchère de " + montant + " € par " + nomAcheteur);

            // Notification à tous les clients
            for (int i = 0; i < compteurClients; i++) {
                clients[i].nouveauPrix(montant, nomAcheteur, article.getDateFin());
            }
            return true;
        } else {
            System.out.println("Enchère de " + montant + " € par " + nomAcheteur + " rejetée (prix actuel : " + article.getPrixActuel() + " €)");
            return false;
        }
        
    }

    @Override
    public Article getArticle() throws RemoteException {
        return article;
    }

    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099); // Démarrer le registre RMI
            Serveur serveur = new Serveur();
            Naming.rebind("ServicesObjetDistant", serveur);
            System.out.println("Serveur RMI démarré et en attente de connexions...");
        } catch (MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }
    }
}
