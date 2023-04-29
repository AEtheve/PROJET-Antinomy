package Modele;

public class Carte {
    byte type;
    byte index;
    boolean selectionnee = false;

    public static final int BLEU = 1;
    public static final int ROUGE = 2;
    public static final int VERT = 3;
    public static final int VIOLET = 4;

    public static final int TETE_MORT = 1;
    public static final int CLE = 2;
    public static final int PLUME = 3;
    public static final int COURONNE = 4;

    public Carte(int symbole, int couleur_carte, int valeur_carte,int index,boolean retournee){
        this.index = (byte)index;
        type = 0;
        type += (symbole-1) << 6;
        type += (couleur_carte-1) << 4;
        type += (valeur_carte-1) << 2;
        if (retournee) type += 0b1;
        else type += 0;
    }

    public boolean estVisible(){
        if ((type & 0b1)==1) return true;
        else return false;
    }    
    
    public void setVisibilite(boolean retournee){
        if (retournee) type = (byte) (type | 0b1);
        else type = (byte) (type & ~ 0b1);
    }

    public int getType(){
        return (type >> 2) & 0xFF;
    }

    public int getIndex(){
        return index;
    }

    public int getCouleur(){
        return ((type & 0b110000) >> 4)+1;
    }

    public int getSymbole(){
        return ((type & 0b11000000) >> 6)+1;
    }

    public int getValeur(){
        return ((type & 0b1100) >> 2)+1;
    }

    public boolean estSelectionnee(){
        return selectionnee;
    }

    public void setSelectionnee(boolean selectionnee){
        this.selectionnee = selectionnee;
    }
    
}
