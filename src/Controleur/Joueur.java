package Controleur;

import Modele.Jeu;
import Modele.Carte;

abstract class Joueur {
    Jeu j;
    int num;

    public Joueur(Jeu j, int num) {
        this.j = j;
        this.num = num;
    }

    int num() {
        return num;
    }

    boolean tempsEcoule() {
		return false;
	}
	boolean jeu(Carte c, int state) {
		return false;
	}
}
