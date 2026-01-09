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
        clients = new InterfaceCallback[50];
        this.article = new Article("Description de l'article", 100.0, new Date(System.currentTimeMillis() + 30000), "Infos vendeur", "exempleArticle.png");

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

    @Override
    public synchronized void callMeBack(InterfaceCallback obj, String nom) throws RemoteException {
        obj.doCallback();
        clients[compteurClients++] = obj;
        System.err.println(nom + " a été enregistré pour un callback.");
        obj.nouveauPrix(article.getPrixActuel(), article.getNomMeneur(), article.getDateFin());
    }

    @Override
    public synchronized boolean  Encherire(double montant, String nomAcheteur) throws RemoteException {
        if (montant > article.getPrixActuel()) {
            article.setPrixActuel(montant);
            article.setNomMeneur(nomAcheteur);
            System.out.println("Nouvelle enchère de " + montant + " € par " + nomAcheteur);
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
