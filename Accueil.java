import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Accueil extends Frame {
    private ClientRMI controleur;
    private TextField nomField;
    private Button btnEntrer;

    public Accueil(ClientRMI controleur) {
        super("Bienvenue aux Enchères");
        this.controleur = controleur;

        setLayout(new BorderLayout()); // Utilisation de BorderLayout pour centrer

        // --- Panneau Central ---
        Panel centerPanel = new Panel(new GridLayout(3, 1, 10, 10));
        
        Label lblTitre = new Label("Veuillez saisir votre nom pour participer :", Label.CENTER);
        nomField = new TextField(20);
        btnEntrer = new Button("Entrer dans la salle d'enchères");

        centerPanel.add(lblTitre);
        centerPanel.add(nomField);
        centerPanel.add(btnEntrer);

        // Ajout de marges via des panels vides (astuce AWT)
        add(centerPanel, BorderLayout.CENTER);
        add(new Panel(), BorderLayout.NORTH);
        add(new Panel(), BorderLayout.SOUTH);
        add(new Panel(), BorderLayout.EAST);
        add(new Panel(), BorderLayout.WEST);

        // --- Gestion des événements ---
        
        // Clic sur le bouton
        btnEntrer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lancerEnchere();
            }
        });

        // Appui sur "Entrée" dans le champ de texte
        nomField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lancerEnchere();
            }
        });

        // Fermeture de la fenêtre
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });

        setSize(400, 200);
        setLocationRelativeTo(null); // Centrer à l'écran
        setVisible(true);
    }

    private void lancerEnchere() {
        String nom = nomField.getText().trim();
        if (!nom.isEmpty()) {
            this.setVisible(false);
            // Au lieu de créer PageEnchere ici, on demande au ClientRMI de le faire
            controleur.connexion(nom);
        }
    }
}