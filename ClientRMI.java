public class ClientRMI {

    private PageEnchere fenetreEnchere;

    // Point d'entrée de l'application
    public static void main(String[] args) {
        // On lance le client, qui lance l'accueil
        new ClientRMI();
    }

    public ClientRMI() {
        // On ouvre la fenêtre d'accueil en lui passant 'this' (le contrôleur)
        new Accueil(this);
    }

    // Appelé par Accueil quand on clique sur "Entrer"
    public void connexion(String nomUtilisateur) {
        System.out.println("Simulation connexion pour : " + nomUtilisateur);
        
        // On ouvre la page d'enchère
        fenetreEnchere = new PageEnchere(nomUtilisateur, this);
    }

    
}