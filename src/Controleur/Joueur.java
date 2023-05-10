package Controleur;

import Modele.Jeu;

abstract class Joueur {
    Jeu j;

    public Joueur(Jeu j) {
        this.j = j;
    }

    boolean tempsEcoule() {
		return false;
	}
	boolean jeu(int i, int j) {
		return false;
	}
}
