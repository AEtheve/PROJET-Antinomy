package Controleur;

import Modele.*;
import Structures.Sequence;

class JoueurIA extends Joueur {
    IA ia;
    
    public JoueurIA(Jeu j, int num) {
        super(j, num);
        ia = IA.nouvelle(j);
    }

    @Override
    boolean tempsEcoule() {
        Sequence<Coup> coups = ia.elaboreCoups();
        if (coups != null) {
            while(!coups.estVide()){
                j.joue(coups.extraitTete());
            }
            return true;
        }
        return false;
    }



}