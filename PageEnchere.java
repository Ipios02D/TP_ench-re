import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PageEnchere extends Frame {
    private ClientRMI controleur;
    
    // Données de l'utilisateur
    private String nomAcheteur;

    // Composants graphiques devant être mis à jour
    private Label lblPrixActuel;
    private Label lblDernierAcheteur;
    private Label lblDateCloture;
    private Label lblVendeurInfo; // Caché par défaut, visible à la fin
    private TextField txtProposition;
    private Button btnEncherir;
    private Button plus10;
    private Button plus100;
    private TextArea txtDescription;

    public PageEnchere(String nomAcheteur, ClientRMI controleur) {
        super("Salle d'enchères - Connecté en tant que : " + nomAcheteur);
        this.nomAcheteur = nomAcheteur;
        this.controleur = controleur;
        
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.LIGHT_GRAY);

        // --- HAUT : Titre ---
        Label titre = new Label("Vente aux enchères en direct", Label.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 18));
        add(titre, BorderLayout.NORTH);

        // --- CENTRE : Photo + Infos ---
        Panel centrePanel = new Panel(new GridLayout(1, 2, 10, 10));
        
        // Partie Gauche : Photo (Simulation avec un Canvas)
        PhotoPanel photoPanel = new PhotoPanel();
        centrePanel.add(photoPanel);

        // Partie Droite : Détails
        Panel infoPanel = new Panel(new GridLayout(6, 1));
        
        txtDescription = new TextArea("Description de l'objet...\n(Chargement via RMI...)", 5, 40, TextArea.SCROLLBARS_VERTICAL_ONLY);
        txtDescription.setEditable(false);
        
        lblPrixActuel = new Label("Prix actuel : 0.00 €");
        lblPrixActuel.setFont(new Font("Dialog", Font.BOLD, 14));
        
        lblDernierAcheteur = new Label("Dernier enchérisseur : Aucun");
        
        lblDateCloture = new Label("Clôture : --/--/---- --:--");
        
        lblVendeurInfo = new Label(""); // Vide au début
        lblVendeurInfo.setForeground(Color.RED);

        infoPanel.add(new Label("Description de l'article :"));
        infoPanel.add(txtDescription);
        infoPanel.add(lblPrixActuel);
        infoPanel.add(lblDernierAcheteur);
        infoPanel.add(lblDateCloture);
        infoPanel.add(lblVendeurInfo);

        centrePanel.add(infoPanel);
        add(centrePanel, BorderLayout.CENTER);

        // --- BAS : Zone d'action ---
        Panel basPanel = new Panel(new FlowLayout());
        basPanel.add(new Label("Votre offre (€) :"));
        
        txtProposition = new TextField(10);
        btnEncherir = new Button("Enchérir");
        plus10 = new Button("+10");
        plus100 = new Button("+100");
        
        basPanel.add(txtProposition);
        basPanel.add(btnEncherir);
        basPanel.add(plus10);
        basPanel.add(plus100);
        add(basPanel, BorderLayout.SOUTH);

        // --- Événements ---
        
        // Bouton Enchérir
        btnEncherir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soumettreEnchere();
            }
        });

        // Bouton +10
        plus10.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double currentOffer = Double.parseDouble(txtProposition.getText());
                    txtProposition.setText(String.valueOf(currentOffer + 10));
                    mettreAJourPrix(currentOffer + 10, nomAcheteur);
                } catch (NumberFormatException ex) {
                    txtProposition.setText("10");
                }
            }
        });

        // Bouton +100
        plus100.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double currentOffer = Double.parseDouble(txtProposition.getText());
                    txtProposition.setText(String.valueOf(currentOffer + 100));
                    mettreAJourPrix(currentOffer + 100, nomAcheteur);
                } catch (NumberFormatException ex) {
                    txtProposition.setText("100");
                }
            }
        });

        // Fermeture
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });

        setSize(800, 500);
        setLocationRelativeTo(null);
        setVisible(true);

        // --- Simulation de données (A remplacer plus tard par l'appel RMI) ---
        initialiserDonneesSimulees();
    }

    // Méthode appelée quand l'utilisateur clique sur Enchérir
    private void soumettreEnchere() {
        try {
            double offre = Double.parseDouble(txtProposition.getText());
            System.out.println("Envoi de l'offre via RMI : " + offre + " par " + nomAcheteur);
            
            // ICI : Appeler la méthode du serveur RMI
            // server.proposerPrix(offre, nomAcheteur);
            
            // Pour le test interface uniquement, on met à jour localement
            mettreAJourPrix(offre, nomAcheteur); 
            txtProposition.setText("");
            
        } catch (NumberFormatException ex) {
            System.out.println("Erreur : Veuillez entrer un nombre valide.");
        }
    }

    // --- Méthodes qui seront appelées par le Client RMI pour mettre à jour l'IHM ---

    public void mettreAJourPrix(double nouveauPrix, String nomEncherisseur) {
        lblPrixActuel.setText("Prix actuel : " + nouveauPrix + " €");
        lblDernierAcheteur.setText("Dernier enchérisseur : " + nomEncherisseur);
        
        // changement de couleur si c'est l'utilisateur courant
        if (nomEncherisseur.equals(this.nomAcheteur)) {
            lblPrixActuel.setForeground(Color.BLUE);
        }
        lblPrixActuel.setForeground(Color.BLUE);
    }

    public void afficherVainqueur(String infosVendeur) {
        btnEncherir.setEnabled(false);
        txtProposition.setEnabled(false);
        lblVendeurInfo.setText("FÉLICITATIONS ! Contact vendeur : " + infosVendeur);
        setTitle("Enchère terminée");
    }

    // Simulation de données initiales (Normalement récupérées du serveur)
    private void initialiserDonneesSimulees() {
        txtDescription.setText("Ordinateur Portable Vintage\nAnnée 1995\nTrès bon état de marche.");
        lblPrixActuel.setText("Prix actuel : 150.00 €");
        lblDateCloture.setText("Clôture : " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
    }

    // Classe interne pour dessiner une "Photo" (Placeholder)
    class PhotoPanel extends Canvas {
        public void paint(Graphics g) {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(10, 10, getWidth()-20, getHeight()-20);
            g.setColor(Color.WHITE);
            g.drawString("[PHOTO DE L'ARTICLE]", getWidth()/2 - 60, getHeight()/2);
            g.drawRect(10, 10, getWidth()-20, getHeight()-20);
        }
    }
}