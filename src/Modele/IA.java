package Modele;

import Global.Configuration;
import Structures.Sequence;

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

    public final Sequence<Coup> elaboreCoups() {
		return joue();
	}

    Sequence<Coup> joue() {
		return null;
	}

}