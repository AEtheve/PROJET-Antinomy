package Vue;

import java.awt.*;

import Global.Configuration;
import Modele.Carte;

public class CarteGraphique {
    Image image;
    Carte carte;

    public CarteGraphique(Carte carte) {
        this.carte = carte;
        image = Configuration.lisImage(AdaptNom(carte.getType()));
    }

    private String AdaptNom(int type){

        String couleur = "erreur";
        switch (carte.getColor()) {
            case 1:
                couleur = "terre";
                break;
            case 2:
                couleur = "psy";
                break;
            case 3:
                couleur = "eau";
                break;
            case 4:
                couleur = "feu";
                break;
        }

        String symbole = "erreur";
        switch (carte.getSymbol()) {
            case 1:
                symbole = "plume";
                break;
            case 2:
                symbole = "cle";
                break;
            case 3:
                symbole = "crane";
                break;
            case 4:
                symbole = "couronne";
                break;
        }
        
        String nom = ""+carte.getValue()+"_"+symbole+"_"+couleur;
        if (Configuration.lisImage(nom) == null) nom = "error";
        return nom;
    }
}
