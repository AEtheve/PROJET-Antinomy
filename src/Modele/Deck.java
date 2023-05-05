package Modele;

public class Deck {
    Carte[] continuum;
    Carte codex;
    int sceptreJ1, sceptreJ2;

    public Deck(Carte[] continuum, Carte codex) {
        this.continuum = continuum;
        this.codex = codex;
        sceptreJ1 = sceptreJ2 = -1;
    }

    public Carte[] getContinuum() {
        return continuum;
    }

    public Carte getCodex() {
        return codex;
    }

    public void setSceptre(Boolean joueur, int pos) {
        if (joueur)
            sceptreJ1 = pos;
        else
            sceptreJ2 = pos;
    }

    public int getSceptre(Boolean joueur) {
        if (joueur)
            return sceptreJ1;
        else
            return sceptreJ2;
    }

    public String toString() {
        Deck deckTriee = new Deck(continuum, codex);
        for (int i = 0; i < deckTriee.continuum.length; i++) {
            for (int j = i + 1; j < deckTriee.continuum.length; j++) {
                if (deckTriee.continuum[i].getIndex() > deckTriee.continuum[j].getIndex()) {
                    Carte tmp = deckTriee.continuum[i];
                    deckTriee.continuum[i] = deckTriee.continuum[j];
                    deckTriee.continuum[j] = tmp;
                }
            }
        }
        String s = "[";
        for (int i = 0; i < deckTriee.continuum.length; i++) {
            s += deckTriee.continuum[i].toString();
            if (i < deckTriee.continuum.length - 1) {
                s += "\n";
            }
        }
        s += "]";
        return s;
    }

    public void prochainCodex() {
        codex.setIndex(((codex.getIndex() + 1) % 5));
        if (codex.getIndex() == 0)
            codex.setIndex(1);
    }
}
