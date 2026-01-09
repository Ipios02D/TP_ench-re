import java.rmi.Remote;
import java.rmi.RemoteException;
// Auteur : Emilien Cousin et Clément Cano
public interface InterfaceServeur extends Remote {
    public void callMeBack(InterfaceCallback obj, String nom) throws RemoteException;

    public boolean  Encherire(double montant, String nomAcheteur) throws RemoteException;

    Article getArticle() throws RemoteException;
}
