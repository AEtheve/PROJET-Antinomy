package Modele;

import Global.Configuration;
import Structures.Couple;

public abstract class IA {
  	protected JeuEntier jeu;
    protected int profondeurConfig = -1;
    int typeHeuristique = 1;

    public static IA nouvelle(JeuEntier jeu){
        IA ia;
        if (Configuration.difficulteIA == 1) {
            ia = new IAAleatoire();
        } else {
            ia = IAMinMax.nouvelle(jeu);
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