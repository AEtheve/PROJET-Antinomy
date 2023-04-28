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

        String couleur = "erreur";
        switch (carte.getCouleur()) {
            case 1:
                couleur = "feu";
                break;
            case 2:
                couleur = "eau";
                break;
            case 3:
                couleur = "terre";
                break;
            case 4:
                couleur = "psy";
                break;
        }

        String symbole = "erreur";
        switch (carte.getSymbole()) {
            case 1:
                symbole = "cle";
                break;
            case 2:
                symbole = "crane";
                break;
            case 3:
                symbole = "plume";
                break;
            case 4:
                symbole = "couronne";
                break;
        }
        
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
