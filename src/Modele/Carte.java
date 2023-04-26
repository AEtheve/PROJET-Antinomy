package Modele;

public class Carte {
    byte type;
    byte index;

    public Carte(int symbole, int couleur_carte, int valeur_carte,int index,boolean retournee1,boolean retournee2){
        this.index = (byte)index;
        type = 0;
        type += symbole << 6;
        type += couleur_carte << 4;
        type += valeur_carte << 2;
        if (retournee1) type += 0b1 << 1;
        else type += 0 << 1;
        if (retournee2) type += 0b1;
        else type += 0;
    }

    public int estVisible(int joueur){
        if(joueur == 1){
            return (type & 0b10) >> 1;
        }
        else{
            return type & 0b1;
        }
    }    
    
    public void setVisibilite(boolean retournee1,boolean retournee2){
        if (retournee1) type += 0b1 << 1;
        else type += 0 << 1;
        if (retournee2) type += 0b1;
        else type += 0;
    }

    public int getType(){
        return (int) type >> 2;
    }

    public int getIndex(){
        return index;
    }

    public int getCouleur(){
        return (type & 0b110000) >> 4;
    }

    public int getSymbole(){
        return (type & 0b11000000) >> 6;
    }

    public int getValeur(){
        return (type & 0b1100) >> 2;
    }
    
}
