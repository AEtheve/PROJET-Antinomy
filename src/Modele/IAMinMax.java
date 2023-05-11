package Modele;

import Structures.Sequence;

public class IAMinMax extends IA {
	public static IA nouvelle(Jeu jeu){
        IA ia = new IAAleatoire();
        ia.jeu = jeu;
        return ia;
    }

    Sequence<Coup> joue() {
		return null;
	}

	// C'est a l'IA de jouer, on maximise les gains
	int MinmaxIA(JeuCompact j, int n) {
		// Si fin de récursion:
		if(n==0) { return j.evaluation(); }
		// Sinon
		else {
			// recuperer les coups possibles

			// lancer la recursion avec MinmaxJoueur() sur chaque coup

			// renvoyer le max des evaluations obtenue
		}

		return 1;
	}

	// C'est au Joueur de jouer, on minimise les pertes
	int MinmaxJoueur(JeuCompact j, int n) {
		return 1;
	}
}
