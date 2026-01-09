import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Accueil extends Frame {
    private final Client controleur;
    private final TextField nomField;
    private final Button btnEntrer;

    public Accueil(Client controleur) {
        super("Bienvenue");
        this.controleur = controleur;

        setLayout(new BorderLayout());
        
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

        // Actions
        btnEntrer.addActionListener(e -> valider());
        nomField.addActionListener(e -> valider());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });

        setSize(400, 200);
        setLocationRelativeTo(null); // Centrer à l'écran
        setVisible(true);
    }

    private void valider() {
        String nom = nomField.getText().trim();
        if (!nom.isEmpty()) {
            this.setVisible(false);
            controleur.connexion(nom); // Appel vers Client
        }
    }
}