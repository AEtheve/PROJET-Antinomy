package Vue;

import javax.swing.*;

import Global.Configuration;
import Modele.Carte;
import java.awt.*;

public class CarteGraphique extends JComponent {
    Carte carte;
    Image img;
    
    public CarteGraphique(Carte carte) {
        this.carte = carte;
        String nom = AdaptNom(carte.getType());
        img = Configuration.lisImage(nom);
    }

    private String AdaptNom(int type){
        // A vérifier
        String nom = "" + Integer.toBinaryString(type); 
        if (nom.length() < 6){
            int longueur = nom.length();
            for (int i = 0; i < 6 - longueur; i++) {
                nom = "0" + nom;
            }
        }
        return nom;
    }

    public void miseAJour(){
        repaint();
    }
    

    public void paintComponent(Graphics g) {
        Graphics2D drawable = (Graphics2D) g;
        drawable.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
