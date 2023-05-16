package Controleur;

import Modele.JeuEntier;
import Modele.Carte;

abstract class Joueur {
    JeuEntier j;
    int num;
    Carte carteAJouer;
    Carte [] cartesPossibles;
    Boolean swap_droit, swap_gauche;

    public Joueur(JeuEntier j, int num) {
        this.j = j;
        this.num = num;
        carteAJouer = null;
        cartesPossibles = null;
    }

    int num() {
        return num;
    }

    boolean tempsEcoule() {
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
}