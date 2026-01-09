import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.util.Date;
// Auteur : Emilien Cousin et Clément Cano
public class Article implements Serializable {
    private final String description;
    private final Date dateFin;
    private final String infosVendeur;
    private final byte[] imageDonnees;
    private double prixActuel;
    private String nomMeneur;

    public Article(String description, double prixInitial, Date dateFin, String infosVendeur, String nomImage) {
        this.description = description;
        this.prixActuel = prixInitial;
        this.dateFin = dateFin;
        this.infosVendeur = infosVendeur;
        this.nomMeneur = "Personne";
        byte[] imageBuffer = new byte[0];
        try {

        File file = new File(nomImage);
        byte[] buffer = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(buffer);
        fis.close();
        
        // On injecte l'image dans l'objet article
        imageBuffer = buffer;
        
    } catch (Exception e) {
        e.printStackTrace();
    }
        this.imageDonnees = imageBuffer;
    }

    // Getters et Setters
    public String getDescription() { return description; }
    public double getPrixActuel() { return prixActuel; }
    public String getNomMeneur() { return nomMeneur; }
    public Date getDateFin() { return dateFin; }
    public byte[] getImageDonnees() { return imageDonnees; }
    public String getInfosVendeur() { return infosVendeur; }

    public void setPrixActuel(double prix) { this.prixActuel = prix; }
    public void setNomMeneur(String nom) { this.nomMeneur = nom; }
}