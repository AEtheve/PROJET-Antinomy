package Modele;

import java.io.Serializable;

public class Carte implements Serializable {
    // Constantes pour les couleurs
    public static final int TERRE = 1;
    public static final int PSY = 2;
    public static final int EAU = 3;
    public static final int FEU = 4;

    // Constantes pour les symboles
    public static final int PLUME = 1;
    public static final int CLE = 2;
    public static final int CRANE = 3;
    public static final int COURONNE = 4;

    private byte index;
    private byte type;

    /*
    ############################# Constructeurs #############################
    */

    public Carte(int symbole, int couleur_carte, int valeur_carte, int index, boolean retournee) {
        this.index = (byte) index;
        type = 0;
        type += (symbole - 1) << 6;
        type += (couleur_carte - 1) << 4;
        type += (valeur_carte - 1) << 2;
        if (retournee)
            type += 0b1;
        else
            type += 0;
    }

    public Carte(int index, int type) {
        this.index = (byte) index;
        this.type = (byte) type;
    }

    /*
    ############################# Getters #############################
    */

    public byte getType() {
        return type;
    }

    public int getIndex() {
        return index;
    }

    public int getColor() {
        // Renvoie la couleur de la carte
        return ((type & 0b110000) >> 4) + 1;
    }

    public int getSymbol() {
        // Renvoie le symbole de la carte
        return ((type & 0b11000000) >> 6) + 1;
    }

    public int getValue() {
        // Renvoie la valeur de la carte
        return ((type & 0b1100) >> 2) + 1;
    }

    /*
    ############################# Setters #############################
    */

    public void setVisbility(boolean retournee) {
        /* Permet de modifier la visibilité de la carte */
        if (retournee)
            type = (byte) (type | 0b1);
        else
            type = (byte) (type & ~0b1);
    }

    

    public void setIndex(int index) {
        // Permet de modifier l'index de la carte (sur le plateau, la main ou la couleur du codex)
        if (index > 9 || index < 0)
            throw new IllegalArgumentException("Index trop grand");
        this.index = (byte) index;
    }

    /*
    ############################# Méthodes #############################
    */

    public boolean isVisible() {
        /* Verifi si la carte est visible (par le joueur 1),
        renvoie true si elle est visible, false sinon */
        if ((type & 0b1) == 1)
            return true;
        else
            return false;
    }


    /*
    ############################# Méthodes d'affichage #############################
    */

    public String toString() {
        String s = "";
        switch(getColor()) {
            case TERRE:
                s += "\u001B[32m";
                break;
            case PSY:
                s += "\u001B[35m";
                break;
            case EAU:
                s += "\u001B[34m";
                break;
            case FEU:
                s += "\u001B[31m";
                break;
        }
        s += "(" + getValue() + " " + couleurToString(getColor()) + " " + symboleToString(getSymbol()) + ")";
        s += "\u001B[0m";
        return s;
    }

    /*
    ############################# Méthodes statiques #############################
    */
    
    public static String symboleToString(int symbole) {
        // Renvoie le symbole correspondant à l'entier symbole
        switch (symbole) { // \u001B[0m permet de remettre la couleur par défaut
            case PLUME:
                return "plume";
            case CLE:
                return "cle";
            case CRANE:
                return "crane";
            case COURONNE:
                return "couronne";
        }
        return "Erreur";
    }

    public static String couleurToString(int couleur) {
        // Renvoie la couleur correspondant à l'entier couleur
        switch (couleur) { // \u001B[32m, \u001B[35m, \u001B[34m, \u001B[31m
            case TERRE:
                return "terre";
            case PSY:
                return "psy";
            case EAU:
                return "eau";
            case FEU:
                return "feu";
        }
        return "Erreur";
    }
}
