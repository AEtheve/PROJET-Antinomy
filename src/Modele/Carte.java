package Modele;

public class Carte {
    byte type;
    byte index;
    boolean survolee = false;
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

    public void setCouleur(int couleur){
        type = (byte) (type & ~ 0b110000);
        type += (couleur-1) << 4;
    }

    public void setSymbole(int symbole){
        type = (byte) (type & ~ 0b11000000);
        type += (symbole-1) << 6;
    }

    public void setValeur(int valeur){
        type = (byte) (type & ~ 0b1100);
        type += (valeur-1) << 2;
    }

    
    public boolean estSurvolee(){
        return survolee;
    }

    public void setSurvolee(boolean survolee){
        this.survolee = survolee;
    }

    public boolean estSelectionnee(){
        return selectionnee;
    }

    public void setSelectionnee(boolean selectionnee){
        this.selectionnee = selectionnee;
    }

    public String getStringCouleur(){
        switch (getCouleur()) {
            case 1:
                return "Terre ";
            case 2:
                return "Psy ";
            case 3:
                return "Eau ";
            case 4:
                return "Feu ";
        }
        return "Non défini";
    }

    public String getStringSymbole(){
        switch (getSymbole()) {
            case 1:
                return "Aigle ";
            case 2:
                return "Epée ";
            case 3:
                return "Tete de mort ";
            case 4:
                return "Couronne ";
        }
        return "Non défini";
    }

    public void description(){
        System.out.print("Carte cliquée: "+getValeur()+" ");
        System.out.print(getStringSymbole());
        System.out.println(getStringCouleur());
    }
    
}
