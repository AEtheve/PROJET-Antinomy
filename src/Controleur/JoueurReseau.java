package Controleur;

import Modele.*;

class JoueurReseau extends Joueur {
    IA ia;
    Boolean dejaJoue = false;

    public JoueurReseau(JeuEntier j, int num) {
        super(j, num);
    }

    @Override
    boolean tempsEcoule() {
        return false;
    }

}