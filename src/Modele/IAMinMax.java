package Modele;

import Global.Configuration;
import Structures.Sequence;
import Structures.Couple;
import Structures.Iterateur;

public class IAMinMax extends IA {
	public static IA nouvelle(Jeu jeu){
        IA ia = new IAAleatoire();
        ia.jeu = jeu;
        return ia;
    }

    Sequence<Coup> joue() {
		Sequence<Coup> out_seq = Configuration.nouvelleSequence();
		
		out_seq.insereTete(MinmaxIA(jeu.getJeuCompact(), 5).second);

		return out_seq;
	}

	// C'est a l'IA de jouer, on maximise les gains
	Couple<Integer, Coup> MinmaxIA(JeuCompact j, int n) {
		// Si fin de récursion:
		if(n==0) { return new Couple<Integer, Coup>(j.evaluation(), null); }
		// Sinon
		else {
			// recuperer les coups possibles
			Sequence<Coup> possibles = Configuration.nouvelleSequence();

			// Si premier tour
			if (j.getDeck().getSceptre(j.getTour()) == -1) {
				int index_possibles[] = j.getSceptrePossibleInit();
				for (int p : index_possibles) {
					possibles.insereTete(new Coup(Coup.SCEPTRE, p));
				}
				
			} else { // Sinon
				Carte main[] = j.getMain(j.getTour());

				Sequence<Couple<Carte, Integer>> cartes_possibles_index = Configuration.nouvelleSequence();
				for (Carte carte : main) {
					Carte[] cartes_possibles = j.getCartesPossibles(carte);
					for (Carte carte_possible : cartes_possibles) {
						if (carte_possible!=null) {
							cartes_possibles_index.insereTete(new Couple<Carte, Integer>(carte, carte_possible.getIndex()));
						}
					}
				}

				Iterateur<Couple<Carte, Integer>> iterateur = cartes_possibles_index.iterateur();
				while(iterateur.aProchain()) {
					Couple<Carte, Integer> couple = iterateur.prochain();
					possibles.insereTete(new Coup(Coup.ECHANGE, couple.first.getIndex(), couple.second));
				}
			}

			
			// lancer la recursion avec MinmaxJoueur() sur chaque coup
			Couple<Integer, Coup> max=null;
			Iterateur<Coup> coup_it = possibles.iterateur();
			while(coup_it.aProchain()) {
				Coup coup = coup_it.prochain();
				JeuCompact config = (JeuCompact) j.clone();
				config.execCoup(coup);
				Couple<Integer, Coup> result = MinmaxJoueur(config, n-1);
				if(max==null) {
					max = result;
				} else if(result.first > max.first) {
					max = result;
				}
			}
			// renvoyer le max des evaluations obtenue
			return max;
		}
	}

	// C'est au Joueur de jouer, on minimise les pertes
	Couple<Integer, Coup> MinmaxJoueur(JeuCompact j, int n) {
		if(n==0) { return new Couple<Integer, Coup>(j.evaluation(), null); }
		// Sinon
		else {
			// recuperer les coups possibles
			Sequence<Coup> possibles = Configuration.nouvelleSequence();

			// Si premier tour
			if (j.getDeck().getSceptre(j.getTour()) == -1) {
				int index_possibles[] = j.getSceptrePossibleInit();
				for (int p : index_possibles) {
					possibles.insereTete(new Coup(Coup.SCEPTRE, p));
				}
				
			} else { // Sinon
				Carte main[] = j.getMain(j.getTour());

				Sequence<Couple<Carte, Integer>> cartes_possibles_index = Configuration.nouvelleSequence();
				for (Carte carte : main) {
					Carte[] cartes_possibles = j.getCartesPossibles(carte);
					for (Carte carte_possible : cartes_possibles) {
						if (carte_possible!=null) {
							cartes_possibles_index.insereTete(new Couple<Carte, Integer>(carte, carte_possible.getIndex()));
						}
					}
				}

				Iterateur<Couple<Carte, Integer>> iterateur = cartes_possibles_index.iterateur();
				while(iterateur.aProchain()) {
					Couple<Carte, Integer> couple = iterateur.prochain();
					possibles.insereTete(new Coup(Coup.ECHANGE, couple.first.getIndex(), couple.second));
				}
			}

			
			// lancer la recursion avec MinmaxJoueur() sur chaque coup
			Couple<Integer, Coup> max=null;
			Iterateur<Coup> coup_it = possibles.iterateur();
			while(coup_it.aProchain()) {
				Coup coup = coup_it.prochain();
				JeuCompact config = (JeuCompact) j.clone();
				config.execCoup(coup);
				Couple<Integer, Coup> result = MinmaxJoueur(config, n-1);
				if(max==null) {
					max = result;
				} else if(result.first < max.first) {
					max = result;
				}
			}
			// renvoyer le max des evaluations obtenue
			return max;
		}
	}
}
