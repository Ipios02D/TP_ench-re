import java.util.Date;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceCallback extends Remote {
    public void doCallback() throws RemoteException;

    public void nouveauPrix(double prix, String meneur, Date dateFin) throws RemoteException;

    public void finEnchere(String gagnant, double prixFinal, String infosVendeur) throws RemoteException;
}