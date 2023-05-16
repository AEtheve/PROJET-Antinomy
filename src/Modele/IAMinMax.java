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
        return ia;
    }

	// private Random r = new Random();

    Sequence<Coup> joue() {
		Sequence<Coup> out_seq = Configuration.nouvelleSequence();
		
		Couple<Coup, Coup> result = MinmaxIA(jeu.getJeuCompact(), 4).second;
		out_seq.insereTete(result.first);
		if(result.second!=null){
			out_seq.insereTete(result.second);
		}

		System.out.println("IA joue");
		return out_seq;
	}

	// C'est a l'IA de jouer, on maximise les gains
	Couple<Integer, Couple<Coup, Coup>> MinmaxIA(JeuCompact j, int n) {
		// Si fin de récursion:
		if(n==0) {
			return new Couple<Integer, Couple<Coup, Coup>>(
				j.evaluation(),
				new Couple<Coup, Coup>(null, null)
			);
		}
		// Sinon
		else {
			// recuperer les coups possibles
			Sequence<Couple<Coup, Coup>> possibles = j.getCoupsPossibles();
		
			// lancer la recursion avec MinmaxJoueur() sur chaque coup
			Couple<Integer, Couple<Coup, Coup>> max=null;
			Iterateur<Couple<Coup, Coup>> coup_it = possibles.iterateur();
			while(coup_it.aProchain()) {
				Couple<Coup, Coup> coup = coup_it.prochain();

				JeuCompact config = (JeuCompact) j.clone();

				Couple<Integer, Couple<Coup, Coup>> result = null;

				// Si il y a un swap
				if(coup.second!=null) {
					config.joue(coup.first);

					//TODO: Prendre en compte les duels lors d'un paradoxe
					Sequence<JeuCompact> swaps = config.getSwaps(coup.second);

					// TODO: on fait la moyenne des évaluations des swaps
					int scoreMoyen = 0;

					Iterateur<JeuCompact> swap_it = swaps.iterateur();

					while(swap_it.aProchain()){
						JeuCompact swap = swap_it.prochain();
						scoreMoyen += MinmaxJoueur(swap, n-1).first;
					}

					scoreMoyen /= 6;

					result = new Couple<Integer, Couple<Coup, Coup>>(
						scoreMoyen,
						coup
					);
				} else {
					try {
						config.joue(coup.first);
					} catch(RuntimeException e) {
					//TODO: Gestion de la bataille (Moyenne pondérée)
						if(config.scoreJ2>0) {
							config.scoreJ2--;
							config.scoreJ1++;
						}
					}
					result = MinmaxJoueur(config, n-1);
				}
				if (result == null){
					System.out.println("null");
				}

				if(max==null) {
					max = result;
					max.second = coup;
				} else if(result.first > max.first) {
					max = result;
					max.second = coup;
				}
			}
			// renvoyer le max des evaluations obtenue
			if (max == null) {
				System.out.println("null");
			}
			return max;
		}
	}

	// C'est au Joueur de jouer, on minimise les pertes
	Couple<Integer, Couple<Coup, Coup>> MinmaxJoueur(JeuCompact j, int n) {
		// Si fin de récursion:
		if(n==0) {
			return new Couple<Integer, Couple<Coup, Coup>>(
				j.evaluation(),
				new Couple<Coup, Coup>(null, null)
			);
		}
		// Sinon
		else {
			// recuperer les coups possibles
			Sequence<Couple<Coup, Coup>> possibles = j.getCoupsPossibles();
		
			// lancer la recursion avec MinmaxJoueur() sur chaque coup
			Couple<Integer, Couple<Coup, Coup>> min=null;
			if(possibles.estVide()) {
				System.out.println("Pas de coups possibles");
			}
			Iterateur<Couple<Coup, Coup>> coup_it = possibles.iterateur();
			while(coup_it.aProchain()) {
				Couple<Coup, Coup> coup = coup_it.prochain();

				JeuCompact config = (JeuCompact) j.clone();

				Couple<Integer, Couple<Coup, Coup>> result = null;

				// Si il y a un swap
				if(coup.second!=null) {
					config.joue(coup.first);

					//TODO: Prendre en compte les duels lors d'un paradoxe
					Sequence<JeuCompact> swaps = config.getSwaps(coup.second);

					// TODO: on fait la moyenne des évaluations des swaps
					int scoreMoyen = 0;

					Iterateur<JeuCompact> swap_it = swaps.iterateur();

					while(swap_it.aProchain()){
						JeuCompact swap = swap_it.prochain();
						scoreMoyen += MinmaxIA(swap, n-1).first;
					}

					scoreMoyen /= 6;

					result = new Couple<Integer, Couple<Coup, Coup>>(
						scoreMoyen,
						coup
					);
				} else {
					try {
						config.joue(coup.first);
					} catch(RuntimeException e) {
					//TODO: Gestion de la bataille (Moyenne pondérée)
						if(config.scoreJ2>0) {
							config.scoreJ2--;
							config.scoreJ1++;
						}
					}
					result = MinmaxIA(config, n-1);
				}
				if (result == null){
					System.out.println("null@");
				}

				if(min==null) {
					min = result;
					min.second = coup;
				} else if(result.first < min.first) {
					min = result;
					min.second = coup;
				}
			}
			// renvoyer le min des evaluations obtenue
			if (min == null) {
				System.out.println("null !");
			}
			return min;
		}
	}
}
