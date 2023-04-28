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
        String couleur = switch (carte.getCouleur()) {
            case 1 -> "feu";
            case 2 -> "eau";
            case 3 -> "terre";
            case 4 -> "psy";
            default -> "erreur";
        };

        String symbole = switch (carte.getSymbole()) {
            case 1 -> "couronne";
            case 2 -> "cle";
            case 3 -> "plume";
            case 4 -> "crane";
            default -> "erreur";
        };
        
        String nom = ""+carte.getValeur()+"_"+symbole+"_"+couleur;
        if (Configuration.lisImage(nom) == null) nom = "error";
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
