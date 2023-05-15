package Controleur;

import Modele.*;

class JoueurReseau extends Joueur {
    IA ia;
    Boolean dejaJoue = false;

    public JoueurReseau(Jeu j, int num) {
        super(j, num);
    }

    @Override
    boolean tempsEcoule() {
        return false;
    }

}