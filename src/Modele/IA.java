package Modele;

import Structures.Sequence;

public abstract class IA {
  protected Jeu jeu;
    public static IA nouvelle(Jeu jeu){
        IA ia = new IAAleatoire();
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
