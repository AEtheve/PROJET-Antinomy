package Modele;

public class Codex extends Carte {
    int couleur_interdite;

    public Codex(int couleur_interdite, int symbole, int valeur, int type, boolean visible){
        super(symbole, couleur_interdite, valeur, type, visible);
        this.couleur_interdite = couleur_interdite;
    }
    public void nextCouleurInterdite(){
        couleur_interdite = (couleur_interdite+1)%4;
    }

    public int getCouleurInterdite(){
        return couleur_interdite;
    }
}
