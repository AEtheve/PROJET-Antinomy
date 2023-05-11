package Controleur;

import Modele.Jeu;
import Modele.Carte;

class JoueurHumain extends Joueur {
    

    public JoueurHumain(Jeu j, int num) {
        super(j, num);
    }

    @Override
    boolean jeu(Carte c, int state) {
        //TODO
        if(state == ControleurMediateur.STARTGAME){
            
        }

        return false;
    }
}
