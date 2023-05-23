package Controleur;

import Modele.*;
import Structures.Couple;

class JoueurIA extends Joueur {
    IA ia;
    Boolean dejaJoue = false;
    Couple <Coup, Coup> coups;

    public JoueurIA(JeuEntier j, int num) {
        super(j, num);
        ia = IA.nouvelle(j);
        swap_droit = false;
        swap_gauche = false;
    }

    @Override
    boolean tempsEcoule(int state) {
        if(j.estFini()){
            return false;
        }
        if(state == ControleurMediateur.WAITSCEPTRE){
            coups = ia.elaboreCoups();
            j.joue(coups.first);
            return true;
        } else if (state == ControleurMediateur.WAITSELECT){
            coups = ia.elaboreCoups();
            return true;
        } else if (state == ControleurMediateur.WAITMOVE){
            if(coups!=null){
                if (coups.first != null) {
                    j.joue(coups.first);
                }
                return true;
            }
        } else if (state == ControleurMediateur.WAITSWAP){
            if(coups!=null){
                if (coups.second != null) {
                    j.joue(coups.second);
                }
                return true;
            }
        }
        return false;
    }

}