package Controleur;

import Modele.Jeu;
import Modele.Carte;

abstract class Joueur {
    Jeu j;
    int num;
    Carte carteAJouer;
    Carte [] cartesPossibles;
    Boolean swap_droit, swap_gauche;

    public Joueur(Jeu j, int num) {
        this.j = j;
        this.num = num;
        carteAJouer = null;
        cartesPossibles = null;
    }

    int num() {
        return num;
    }

    boolean tempsEcoule(int state) {
		return false;
	}
	boolean jeu(int index, int state, String type) {
		return false;
	}

    public Carte getCarte() {
        return carteAJouer;
    }

    public Carte[] getCartesPossibles() {
        return cartesPossibles;
    }

    public Boolean isSwapDroit(){
        return swap_droit;
    }

    public Boolean isSwapGauche(){
        return swap_gauche;
    }

    public void setSwapDroit(Boolean b){
        swap_droit = b;
    }

    public void setSwapGauche(Boolean b){
        swap_gauche = b;
    }

    public void reset(){
        carteAJouer = null;
        cartesPossibles = null;
        swap_droit = false; swap_gauche = false;
    }
}