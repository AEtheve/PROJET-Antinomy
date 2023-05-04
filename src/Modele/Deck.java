package Modele;

public class Deck {
    Carte [] plateau;
    Carte codex;

    public Deck(Carte [] plateau, Carte codex){
        this.plateau = plateau;
        this.codex = codex;
    }

    public Carte[] getPlateau(){
        return plateau;
    }

    public Carte getCodex(){
        return codex;
    }

}
