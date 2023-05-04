package Modele;

public class Deck {
    Carte [] plateau;
    Carte codex;
    int sceptreJ1, sceptreJ2;

    public Deck(Carte [] plateau, Carte codex){
        this.plateau = plateau;
        this.codex = codex;
        sceptreJ1 = sceptreJ2 = -1;
    }

    public Carte[] getPlateau(){
        return plateau;
    }

    public Carte getCodex(){
        return codex;
    }

    public void setSceptre(int joueur, int pos){
        if (joueur==1) sceptreJ1 = pos;
        else sceptreJ2 = pos;
    }

    public int getSceptre(Boolean joueur){
        if (joueur) return sceptreJ1;
        else return sceptreJ2;
    }

    public String toString(){
        String s = "[";
        for (int i = 0; i < plateau.length; i++) {
            s += plateau[i].toString();
            if (i < plateau.length - 1) {
                s += "\n";
            }
        }
        s += "]";
        return s;
    }
}
