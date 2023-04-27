package Vue;

import javax.swing.*;

import Global.Configuration;
import Modele.Carte;
import java.awt.*;

public class CarteGraphique extends JComponent {
    Carte carte;
    Image img;
    int posX, posY;
    int sizeX, sizeY;
    
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

    public void dessinImage(int posX, int posY, int sizeX, int sizeY){
        this.posX = posX;
        this.posY = posY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public void paintComponent(Graphics g){
        // System.out.println("paintComponent Carte");
        Graphics2D drawable = (Graphics2D) g;
        drawable.drawImage(img, posX, posY, sizeX, sizeY, null);
    }
}
