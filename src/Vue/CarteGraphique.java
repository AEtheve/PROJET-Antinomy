package Vue;

import Global.Configuration;
import Modele.Carte;
import java.awt.*;

public class CarteGraphique {
    Carte carte;
    Image img;
    Image verso;
    int posX, posY;
    int sizeX, sizeY;
    
    public CarteGraphique(Carte carte) {
        this.carte = carte;
        String nom = AdaptNom(carte.getType());
        img = Configuration.lisImage(nom);
        verso = Configuration.lisImage("codex");
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

    public void dessinImage(Graphics g, int posX, int posY, int sizeX, int sizeY){
        Graphics2D drawable = (Graphics2D) g;
        if(!carte.estVisible())
            drawable.drawImage(verso, posX, posY+sizeY, sizeX, -sizeY, null);
        else 
            drawable.drawImage(img, posX, posY, sizeX, sizeY, null);
    }
        
}
