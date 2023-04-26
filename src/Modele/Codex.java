package Modele;

public class Codex extends Carte {
    int couleur_interdite;

    public Codex(int couleur_interdite, int symbole, int couleur_carte, int valeur_carte,int index) {
        super(symbole, couleur_carte,  valeur_carte, index, false, false);
        this.couleur_interdite = couleur_interdite;
    }

    public void nextCouleurInterdite(){
        couleur_interdite++;
        couleur_interdite = couleur_interdite % 4;
    }
}
