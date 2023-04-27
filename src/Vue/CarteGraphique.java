package Vue;

import javax.swing.*;

import Global.Configuration;
import Modele.Carte;
import java.awt.*;
import javax.swing.*;

public class CarteGraphique extends JComponent {
    PlateauGraphique p;
    Carte carte;
    Image img;
    int index;
    
    public CarteGraphique(Carte carte, int index, PlateauGraphique p) {
        this.carte = carte;
        String nom = AdaptNom(carte.getType());
        img = Configuration.lisImage(nom);
        this.index = index;
        this.p = p;
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

        int width = p.getWidth();
        int height = p.getHeight();

        int tailleY = height / 6;
        int tailleX = width / 12;

        int y = height / 2 - tailleY / 2;
        int x = tailleX + index * tailleX + (tailleX / 8 * index);
        
        drawable.drawImage(img, x, y, tailleX, tailleY, null);

        
    }
}
