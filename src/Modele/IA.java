package Modele;

import Global.Configuration;
import Structures.Couple;

public abstract class IA {
  	protected JeuEntier jeu;
    public static IA nouvelle(JeuEntier jeu){
        IA ia;
        if (Configuration.difficulteIA == 1) {
            ia = new IAAleatoire();
        } else {
            ia = new IAMinMax();
        }
        ia.jeu = jeu;
        return ia;
    }

    public final Couple<Coup, Coup> elaboreCoups() {
		return joue();
	}

    Couple<Coup, Coup> joue() {
		return null;
	}

}