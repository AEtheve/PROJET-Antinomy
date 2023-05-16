package Controleur;

import Modele.*;
import Structures.Sequence;

class JoueurIA extends Joueur {
    IA ia;
    Boolean dejaJoue = false;

    public JoueurIA(JeuEntier j, int num) {
        super(j, num);
        ia = IA.nouvelle(j);
    }

    @Override
    boolean tempsEcoule() {
        if (dejaJoue) {
            dejaJoue = false;
            return true;
        }
        Sequence<Coup> coups = ia.elaboreCoups();
        if (coups != null) {
            while (!coups.estVide()) {
                j.joue(coups.extraitTete());
            }
            dejaJoue = true;
            return true;
        }
        return false;
    }

}