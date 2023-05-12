package Modele;

import java.io.ObjectInputFilter.Config;

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
		
		return null;
	}

	// C'est a l'IA de jouer, on maximise les gains
	int MinmaxIA(JeuCompact j, int n) {
		// Si fin de récursion:
		if(n==0) { return j.evaluation(); }
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
			
			// renvoyer le max des evaluations obtenue
		}

		return 1;
	}

	// C'est au Joueur de jouer, on minimise les pertes
	int MinmaxJoueur(JeuCompact j, int n) {
		return 1;
	}
}
