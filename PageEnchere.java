import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.rmi.RemoteException;

public class PageEnchere extends Frame {
    private final Client controleur;
    private final String nomAcheteur;
    private final PhotoPanel photoPanel;
    private Image currentImage;
    private double prixActuel;

    // Composants
    private final Label lblPrixActuel;
    private final Label lblDernierAcheteur;
    private final Label lblDateCloture;
    private final Label lblStatus; 
    private final TextField txtProposition;
    private final Button btnEncherir;
    private final TextArea txtDescription;
    private final Button plus10;
    private final Button plus100;
    private final Label lbloffre;

    public PageEnchere(String nomAcheteur, Client controleur) {
        super("Enchères - " + nomAcheteur);
        this.nomAcheteur = nomAcheteur;
        this.controleur = controleur;
        
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 240, 240));

        // --- HAUT ---
        Label titre = new Label("Salle de Vente", Label.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 18));
        add(titre, BorderLayout.NORTH);

        // --- CENTRE ---
        Panel centrePanel = new Panel(new GridLayout(1, 2, 10, 10));
        
        // Photo (Canvas)
        photoPanel = new PhotoPanel();
        centrePanel.add(photoPanel);

        // Infos
        Panel infoPanel = new Panel(new GridLayout(6, 1));
        txtDescription = new TextArea("Article de luxe.\nEn attente des infos serveur...", 5, 30, TextArea.SCROLLBARS_VERTICAL_ONLY);
        txtDescription.setEditable(false);
        
        lblPrixActuel = new Label("Prix : Chargement...");
        lblPrixActuel.setFont(new Font("Dialog", Font.BOLD, 14));
        lblDernierAcheteur = new Label("Meneur : -");
        lblDateCloture = new Label("Fin : -");
        lblStatus = new Label(""); 
        lbloffre = new Label("Votre offre :");
        lblStatus.setForeground(Color.RED);

        infoPanel.add(new Label("Description :"));
        infoPanel.add(txtDescription);
        infoPanel.add(lblPrixActuel);
        infoPanel.add(lblDernierAcheteur);
        infoPanel.add(lblDateCloture);
        infoPanel.add(lblStatus);

        centrePanel.add(infoPanel);
        add(centrePanel, BorderLayout.CENTER);

        // --- BAS ---
        Panel basPanel = new Panel(new FlowLayout());
        txtProposition = new TextField(10);
        btnEncherir = new Button("Enchérir");
        plus10 = new Button("+10");
        plus100 = new Button("+100");
        
        basPanel.add(lbloffre);
        basPanel.add(txtProposition);
        basPanel.add(btnEncherir);
        basPanel.add(plus10);
        basPanel.add(plus100);
        add(basPanel, BorderLayout.SOUTH);

        // --- EVENTS ---
        btnEncherir.addActionListener(e -> soumettre());
        txtProposition.addActionListener(e -> soumettre());

        // Bouton +10
        plus10.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    txtProposition.setText(String.valueOf(prixActuel + 10));
                    soumettre();
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
                    txtProposition.setText(String.valueOf(prixActuel + 100));
                    soumettre();
                } catch (NumberFormatException ex) {
                    txtProposition.setText("100");
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });

        

        setSize(700, 450);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    //Appelée par le client pour charger l'article et l'afficher
    public void chargerArticle(Article a) {
        txtDescription.setText(a.getDescription());
        
        // Conversion byte[] -> Image AWT
        if (a.getImageDonnees() != null) {
            // Toolkit convertit les octets en image affichable
            currentImage = Toolkit.getDefaultToolkit().createImage(a.getImageDonnees());
            // On demande au panel de se redessiner
            photoPanel.repaint();
        }
    }

    //Appelée par les boutons et délègue la tache au serveur via le client
    private void soumettre() {
        try {
            // Récupération de l'offre
            double offre = Double.parseDouble(txtProposition.getText());

            controleur.getServeur().Encherire(offre, nomAcheteur);
            txtProposition.setText("");
        } catch (NumberFormatException | RemoteException e) {
            System.out.println("Montant invalide");
        }
    }

    //Méthodes de mise à jour de l'interface appelées par le client
    public void mettreAJourInfos(double prix, String meneur, Date dateFin) {
        lblPrixActuel.setText("Prix actuel : " + prix + " €");
        this.prixActuel = prix;
        lblDernierAcheteur.setText("Meneur : " + meneur);
        
        if (dateFin != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            lblDateCloture.setText("Clôture : " + sdf.format(dateFin));
        }

        if (meneur.equals(this.nomAcheteur)) {
            lblPrixActuel.setForeground(Color.BLUE);
            lblStatus.setText("Vous êtes en tête !");
        } else {
            lblPrixActuel.setForeground(Color.BLACK);
            lblStatus.setText("");
        }
    }

    public void afficherEcranGagnant(double prixFinal, String infosVendeur) {
        lblStatus.setText("FELICITATIONS ! VOUS AVEZ GAGNÉ.");
        lblStatus.setForeground(new Color(0, 100, 0));
        lblPrixActuel.setText("VENTE TERMINÉE.\nPrix final : " + prixFinal+"€");
        lblDernierAcheteur.setText("Contact Vendeur :\n" + infosVendeur);
        
        // On désactive les boutons et les fait disparaitres
        btnEncherir.setEnabled(false);
        btnEncherir.setVisible(false);
        txtProposition.setEnabled(false);
        txtProposition.setVisible(false);
        plus10.setEnabled(false);
        plus10.setVisible(false);
        plus100.setEnabled(false);
        plus100.setVisible(false);
        lbloffre.setVisible(false);
    }

    public void afficherEcranPerdant() {
        lblStatus.setText("L'ENCHÈRE EST TERMINÉE. VOUS AVEZ PERDU.");
        
        // On désactive les boutons et les fait disparaitres
        btnEncherir.setEnabled(false);
        btnEncherir.setVisible(false);
        txtProposition.setEnabled(false);
        txtProposition.setVisible(false);
        plus10.setEnabled(false);
        plus10.setVisible(false);
        plus100.setEnabled(false);
        plus100.setVisible(false);
        lbloffre.setVisible(false);
    }

    // Classe interne pour le dessin
    class PhotoPanel extends Canvas {
        @Override
        public void paint(Graphics g) {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());
            
            if (currentImage != null) {
                // Si on a une image, on la dessine en l'adaptant à la taille du panel
                g.drawImage(currentImage, 0, 0, getWidth(), getHeight(), this);
            } else {
                // Sinon on dessine le texte par défaut
                g.setColor(Color.BLACK);
                g.drawRect(0, 0, getWidth()-1, getHeight()-1);
                g.drawString("PAS D'IMAGE", getWidth()/2 - 40, getHeight()/2);
            }
        }
    }
}