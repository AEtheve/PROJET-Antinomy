package Modele;

public class Plateau {
    Carte [] cartes;
    Codex codex;

    public Plateau(Carte [] cartes, Codex codex){
        this.cartes = cartes;
        this.codex = codex;
    }

    public void setPlateau(Carte [] cartes, Codex codex){
        this.cartes = cartes;
        this.codex = codex;
    }

}
