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

    public void setSceptre(Boolean joueur, int pos){
        if (joueur) sceptreJ1 = pos;
        else sceptreJ2 = pos;
    }

    public int getSceptre(Boolean joueur){
        if (joueur) return sceptreJ1;
        else return sceptreJ2;
    }

    public String toString(){
        Deck deckTriee = new Deck(plateau, codex);
        for (int i = 0; i < deckTriee.plateau.length; i++) {
            for (int j = i + 1; j < deckTriee.plateau.length; j++) {
                if (deckTriee.plateau[i].getIndex() > deckTriee.plateau[j].getIndex()) {
                    Carte tmp = deckTriee.plateau[i];
                    deckTriee.plateau[i] = deckTriee.plateau[j];
                    deckTriee.plateau[j] = tmp;
                }
            }
        }
        String s = "[";
        for (int i = 0; i < deckTriee.plateau.length; i++) {
            s += deckTriee.plateau[i].toString();
            if (i < deckTriee.plateau.length - 1) {
                s += "\n";
            }
        }
        s += "]";
        return s;
    }
}
