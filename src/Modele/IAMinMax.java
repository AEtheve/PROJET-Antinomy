package Modele;

import Global.Configuration;
import Structures.Sequence;
import Structures.Couple;
import Structures.Iterateur;
import java.util.Random;

public class IAMinMax extends IA {
	public static IA nouvelle(JeuEntier jeu){
        IA ia = new IAAleatoire();
        ia.jeu = jeu;
        return ia;
    }

	private Random r = new Random();

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

			// Si paradoxe
			if (j.verifParadoxe()) {
				j.scoreJ2++;
				boolean swapGauchePossible = false;
				boolean swapDroitPossible = false;
				int pos_sc = jeu.getDeck().getSceptre(jeu.getTour());
				if (pos_sc - 3 >= 0 && jeu.getTour() == JeuEntier.JOUEUR_1) {
					swapGauchePossible = true;
				}
				if (pos_sc + 3 < jeu.getDeck().getContinuum().length && jeu.getTour() == JeuEntier.JOUEUR_1) {
					swapDroitPossible = true;
				}
				if (pos_sc - 1 >= 0 && jeu.getTour() == JeuEntier.JOUEUR_2) {
					swapGauchePossible = true;
				}
				if (pos_sc + 1 < jeu.getDeck().getContinuum().length && jeu.getTour() == JeuEntier.JOUEUR_2) {
					swapDroitPossible = true;
				}
				if (swapGauchePossible && swapDroitPossible) {
					int random = r.nextInt(2);
					if (random == 0) {
						Coup coup = new Coup(Coup.SWAP_GAUCHE);
						possibles.insereQueue(coup);
					} else {
						Coup coup = new Coup(Coup.SWAP_DROIT);
						possibles.insereQueue(coup);
					}
				} else if (swapGauchePossible) {
					Coup coup = new Coup(Coup.SWAP_GAUCHE);
					possibles.insereQueue(coup);
				} else if (swapDroitPossible) {
					Coup coup = new Coup(Coup.SWAP_DROIT);
					possibles.insereQueue(coup);
				} else {
					System.out.println("Aucun swap possible");
				}
				// TODO: A tester
			} else if (jeu.getDeck().getSceptre(jeu.getTour()) == -1) { // Si premier tour
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
				try {
					config.joue(coup);
				} catch(RuntimeException e) {
					//TODO: Gestion de la bataille (Moyenne pondérée)
					if(config.scoreJ2>0) {
						config.scoreJ2--;
						config.scoreJ1++;
					}
					continue;
				}
				Couple<Integer, Coup> result = MinmaxJoueur(config, n-1);
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
			if (max == null){
				System.out.println("null");
			}
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

			// Si paradoxe
			if (j.verifParadoxe()) {
				j.scoreJ1++;
				boolean swapGauchePossible = false;
				boolean swapDroitPossible = false;
				int pos_sc = jeu.getDeck().getSceptre(jeu.getTour());
				if (pos_sc - 3 >= 0 && jeu.getTour() == JeuEntier.JOUEUR_1) {
					swapGauchePossible = true;
				}
				if (pos_sc + 3 < jeu.getDeck().getContinuum().length && jeu.getTour() == JeuEntier.JOUEUR_1) {
					swapDroitPossible = true;
				}
				if (pos_sc - 1 >= 0 && jeu.getTour() == JeuEntier.JOUEUR_2) {
					swapGauchePossible = true;
				}
				if (pos_sc + 1 < jeu.getDeck().getContinuum().length && jeu.getTour() == JeuEntier.JOUEUR_2) {
					swapDroitPossible = true;
				}
				if (swapGauchePossible && swapDroitPossible) {
					int random = r.nextInt(2);
					if (random == 0) {
						Coup coup = new Coup(Coup.SWAP_GAUCHE);
						possibles.insereQueue(coup);
					} else {
						Coup coup = new Coup(Coup.SWAP_DROIT);
						possibles.insereQueue(coup);
					}
				} else if (swapGauchePossible) {
					Coup coup = new Coup(Coup.SWAP_GAUCHE);
					possibles.insereQueue(coup);
				} else if (swapDroitPossible) {
					Coup coup = new Coup(Coup.SWAP_DROIT);
					possibles.insereQueue(coup);
				} else {
					System.out.println("Aucun swap possible");
				}
				// TODO: A tester
			} else if (jeu.getDeck().getSceptre(jeu.getTour()) == -1) { // Si premier tour
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
			Couple<Integer, Coup> min=null;
			Iterateur<Coup> coup_it = possibles.iterateur();
			while(coup_it.aProchain()) {
				Coup coup = coup_it.prochain();
				JeuCompact config = (JeuCompact) j.clone();
				try {
					config.joue(coup);
				} catch(RuntimeException e) {
					//TODO: Gestion de la bataille (Moyenne pondérée)
					if(config.scoreJ2>0) {
						config.scoreJ2--;
						config.scoreJ1++;
					}
					continue;
				}
				Couple<Integer, Coup> result = MinmaxIA(config, n-1);
				if(min==null) {
					min = result;
					min.second = coup;
				} else if(result.first < min.first) {
					min = result;
					min.second = coup;
				}
			}
			// renvoyer le min des evaluations obtenue
			return min;
		}
	}
}
