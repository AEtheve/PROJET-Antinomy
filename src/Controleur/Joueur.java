package Controleur;

import Modele.Jeu;
import Modele.Carte;

abstract class Joueur {
    Jeu j;
    int num;
    Carte carteAJouer;
    Carte [] cartesPossibles;

    public Joueur(Jeu j, int num) {
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
}