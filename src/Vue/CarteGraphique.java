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
        int tailleX = width / 13;

        int y;
        int x;
        
        
        if (index < 3) {
            y = 10;
            x = width /2 - 3 * tailleX / 2 +index * tailleX + (tailleX / 9 * index);
            drawable.drawImage(img, x, y, tailleX, tailleY, null);
        }
        // 9 cartes au milieu:
        else if (index < 12) {
            y = height / 2 - tailleY / 2;
            x = tailleX + (index - 3) * tailleX + (tailleX / 9 * (index - 3));

            drawable.drawImage(img, x, y, tailleX, tailleY, null);
        }

        // 3 cartes en bas:
        else {
            y = height - tailleY - 10;
            x = width /2 - 3 * tailleX / 2 + (index - 12) * tailleX + (tailleX / 9 * (index - 12));

            drawable.drawImage(img, x, y, tailleX, tailleY, null);
        }
    }
        
}
