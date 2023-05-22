package Modele;

import Global.Configuration;
import Structures.Sequence;
import Structures.Couple;
import Structures.Iterateur;

public class IAMinMax extends IA {

	Heuristique heuristique;
	int typeHeuristique;

	public static IA nouvelle(JeuEntier jeu){
        IA ia = new IAMinMax(Configuration.typeHeuristique);
        ia.jeu = jeu;
		ia.profondeurConfig = Configuration.difficulteIA;
        return ia;
    }

	public IAMinMax() {
		this(1);
	}

	public IAMinMax(int typeHeuristique) {
		this.typeHeuristique = typeHeuristique;
		switch(typeHeuristique) {
			case 1:
				this.heuristique = new HeuristiqueScore();
				break;
			case 2:
				this.heuristique = new HeuristiqueScoreMain();
				break;
			default:
				this.heuristique = new HeuristiqueScore();
		}
	}

    Couple<Coup, Coup> joue() {
		
		JeuCompact j = jeu.getJeuCompact();
		j.setHeuristiquePosition(typeHeuristique == 2);

		Couple<Coup, Coup> result = Minmax(j, profondeurConfig, Integer.MAX_VALUE, new Max()).second;
		return result;
	}

	// Minmax Global
	Couple<Integer, Couple<Coup, Coup>> Minmax(JeuCompact j, int n, int previous, IASelection mode) {
		if (n == 0 || j.scoreJ1 >= Configuration.MAX || j.scoreJ2 >= Configuration.MAX) {
			return new Couple<Integer, Couple<Coup, Coup>>(heuristique.heuristique(j, jeu.getTour()), null);
		} else{
			Sequence<Couple<Coup, Coup>> coups = j.getCoupsPossibles();
			Iterateur<Couple<Coup, Coup>> it = coups.iterateur();
			Couple<Coup, Coup> best = null;
			int bestScore = mode.INIT_VALUE;
			while (it.aProchain()) {
				Couple<Coup, Coup> coup = it.prochain();
				JeuCompact j2 = (JeuCompact) j.clone();
				j2.joue(coup.first);
				if (coup.second != null) {
					j2.joue(coup.second);
				}
				int score = Minmax(j2, n - 1, bestScore, mode.next()).first;

				// Coupe ALPHA/BETA
				if(mode.conditionCoupe(previous, score)) {
					return new Couple<Integer, Couple<Coup, Coup>>(score, coup);
				}

				if (mode.selection(bestScore, score)) {
					bestScore = score;
					best = coup;
				}
			}
			return new Couple<Integer, Couple<Coup, Coup>>(bestScore, best);
		}
	}
}

