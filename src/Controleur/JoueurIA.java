package Controleur;

import Modele.*;
import Structures.Couple;

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
        Couple <Coup, Coup> coups = ia.elaboreCoups();
        if (coups != null) {
            if (coups.first != null) {
                j.joue(coups.first);
            }
            if (coups.second != null) {
                j.joue(coups.second);
            }
            return true;
        }
        return false;
    }

}