package Modele;

import Global.Configuration;
import Structures.Sequence;
import Structures.Couple;
import Structures.Iterateur;
// import java.util.Random;

public class IAMinMax extends IA {
    

	public static IA nouvelle(JeuEntier jeu){
        IA ia = new IAMinMax();
        ia.jeu = jeu;
		ia.profondeurConfig = Configuration.difficulteIA;
        return ia;
    }

	// private Random r = new Random();

    Couple<Coup, Coup> joue() {
		
		// System.out.println("IA joue avec une profondeur de " + profondeurConfig);
		Couple<Coup, Coup> result = MinmaxIA(jeu.getJeuCompact(), profondeurConfig, Integer.MAX_VALUE).second;

		// System.out.println("IA joue");

		return result;
	}

	// C'est a l'IA de jouer, on maximise les gains
	Couple<Integer, Couple<Coup, Coup>> MinmaxIA(JeuCompact j, int n, int previous) {
		if (n == 0 || j.scoreJ1 == Configuration.MAX || j.scoreJ2 == Configuration.MAX) {
			return new Couple<Integer, Couple<Coup, Coup>>(j.evaluation(!jeu.getTour()), null);
		} else{
			Sequence<Couple<Coup, Coup>> coups = j.getCoupsPossibles();
			Iterateur<Couple<Coup, Coup>> it = coups.iterateur();
			Couple<Coup, Coup> best = null;
			int bestScore = Integer.MIN_VALUE;
			while (it.aProchain()) {
				Couple<Coup, Coup> coup = it.prochain();
				JeuCompact j2 = (JeuCompact) j.clone();
				j2.joue(coup.first);
				if (coup.second != null) {
					j2.joue(coup.second);
				}
				int score = MinmaxHumain(j2, n - 1, bestScore).first;

				// Coupe BETA
				if(score >= previous) {
					return new Couple<Integer, Couple<Coup, Coup>>(score, coup);
				}

				if (score > bestScore) {
					bestScore = score;
					best = coup;
				}
			}
			return new Couple<Integer, Couple<Coup, Coup>>(bestScore, best);
		}
	}

	// C'est a l'humain de jouer, on minimise les gains:
	Couple<Integer, Couple<Coup, Coup>> MinmaxHumain(JeuCompact j, int n, int previous) {
		if (n == 0 || j.scoreJ1 == Configuration.MAX || j.scoreJ2 == Configuration.MAX) {
			return new Couple<Integer, Couple<Coup, Coup>>(j.evaluation(jeu.getTour()), null);
		} else {
			Sequence<Couple<Coup, Coup>> coups = j.getCoupsPossibles();
			Iterateur<Couple<Coup, Coup>> it = coups.iterateur();
			Couple<Coup, Coup> best = null;
			int bestScore = Integer.MAX_VALUE;
			while (it.aProchain()) {
				Couple<Coup, Coup> coup = it.prochain();
				JeuCompact j2 = (JeuCompact) j.clone();
				j2.joue(coup.first);
				if (coup.second != null) {
					j2.joue(coup.second);
				}
				int score = MinmaxIA(j2, n - 1, bestScore).first;

				// Coupe ALPHA
				if(score <= previous) {
					return new Couple<Integer, Couple<Coup, Coup>>(score, coup);
				}

				if (score < bestScore) {
					bestScore = score;
					best = coup;
				}
			}
			return new Couple<Integer, Couple<Coup, Coup>>(bestScore, best);
		}
	}

}

