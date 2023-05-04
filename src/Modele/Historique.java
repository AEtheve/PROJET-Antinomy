package Modele;

import java.util.ArrayList;

public class Historique {
    private static Historique instance;
    ArrayList<Coup> historique;
    int pos;

    public static Historique getInstance() {
        if (instance == null) instance = new Historique();
        return instance;
    }

    public Historique(){
        historique = new ArrayList<Coup>();
        pos=-1;
    }

    public void addCoup(Coup c){
        historique.add(c);
        pos++;
    }

    public Coup getCoupPrec(){
        if (pos>0) return historique.get(pos);
        return null;
    }

}
