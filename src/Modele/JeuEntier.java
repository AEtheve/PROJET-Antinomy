package Modele;

import java.util.stream.IntStream;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import Global.Configuration;
import Vue.InterfaceUtilisateur;


public class JeuEntier extends Jeu {
    // final public static Boolean JOUEUR_1 = true;
    // final public static Boolean JOUEUR_2 = false;

    // private Deck deck;
    // private Main J1, J2;
    // private Boolean tour; // true = tour du J1
    protected Historique historique;
    // private Carte[] cartes;
    // private Boolean swap = false;
    // Random r = new Random();
    protected InterfaceUtilisateur interfaceUtilisateur;

	public JeuCompact getJeuCompact() {
		JeuCompact jc = new JeuCompact();
		jc.setDeck((Deck)this.deck.clone());
		jc.setCartes(this.cartes);
		jc.setMains((Main)this.J1.clone(), (Main)this.J2.clone());
		jc.setTour(this.tour);
		jc.setScores(Compteur.getInstance().getJ1Points(), Compteur.getInstance().getJ2Points());
        jc.setSwap(this.swap);
		return jc;
	}

    /*
    ############################# Constructeurs #############################
    */

    public JeuEntier() {
        reset();
    }

    /*
    ############################# Getteurs #############################
    */

    // public Deck getDeck() {
    //     return deck;
    // }

    // public Boolean getSwap() {
    //     return swap;
    // }

    // public Carte[] getMain(Boolean joueur) {
    //     if (joueur == JOUEUR_1)
    //         return J1.getMain();
    //     else
    //         return J2.getMain();
    // }

    // public Boolean getTour() {
    //     return tour;
    // }

    // public Carte[] getCartesPossibles(Carte c) {
    //     /*
    //     Retourne les cartes possibles à jouer en fonction de la carte jouée
    //     */
    //     Carte[] continuum = deck.getContinuum();
    //     Carte [] cartesPossibles = new Carte [continuum.length];
    //     int j, i = 0, k = deck.getSceptre(tour);
    //     for (j = 0; j < continuum.length; j++) {
    //         // Pour chaque carte du continuum
    //         if (tour) {
    //             // Si c'est le tour du joueur 1
    //             if (continuum[j].getIndex() < k) {
    //                 // Si la carte est avant le sceptre
    //                 if (continuum[j].getColor() == c.getColor() || continuum[j].getSymbol() == c.getSymbol()) {
    //                     // Si la carte est de la même couleur ou du même symbole on l'ajoute
    //                     cartesPossibles[i] = continuum[j];
    //                     i++;
    //                 }
    //             }
    //             if (k + c.getValue() == continuum[j].getIndex()) {
    //                 // Si la carte est après le sceptre a une valeur égale à la carte jouée, on l'ajoute
    //                 cartesPossibles[i] = continuum[j];
    //                 i++;
    //             }
    //         } else {
    //             // De meme pour le joueur 2
    //             if (continuum[j].getIndex() > k) {
    //                 if (continuum[j].getColor() == c.getColor() || continuum[j].getSymbol() == c.getSymbol()) {
    //                     cartesPossibles[i] = continuum[j];
    //                     i++;
    //                 }
    //             }
    //             if (k - c.getValue() == continuum[j].getIndex()) {
    //                 cartesPossibles[i] = continuum[j];
    //                 i++;
    //             }
    //         }
    //     }
    //     Carte [] cartesPossibles2 = new Carte[i];
    //     for (j = 0; j < i; j++) {
    //         cartesPossibles2[j] = cartesPossibles[j];
    //     }
    //     return cartesPossibles2;
    // }

    // public int[] getIndexCartePossible(Carte[] cartesPossibles) {
    //     /*
    //     Retourne les index des cartes possibles à jouer en fonction de la carte jouée
    //     */
    //     int[] index = new int[cartesPossibles.length];
    //     for (int i = 0; i < index.length; i++) {
    //         index[i] = cartesPossibles[i].getIndex();
    //     }
    //     return index;
    // }

    // public int[] getSceptrePossibleInit() {
    //     /*
    //     Retourne les index des positions du septre possibles à jouer en de la couleur du codex
    //     */
    //     int codex = deck.getCodex().getIndex();
    //     Carte[] continuum = deck.getContinuum();
    //     int[] cartesPossibles = new int[4];
    //     int i = 0;
    //     for (int j = 0; j < continuum.length; j++) {
    //         if (continuum[j].getColor() == codex) {
    //             cartesPossibles[i] = continuum[j].getIndex();
    //             i++;
    //         }
    //     }
    //     int[] cartesPossibles2 = new int[i];
    //     for (int j = 0; j < i; j++) {
    //         cartesPossibles2[j] = cartesPossibles[j];
    //     }
    //     return cartesPossibles2;
    // }

    public Historique getHistorique() {
        return historique;
    }

    /*
    ############################# Setteurs #############################
    */

    // public void setSwap(Boolean swap) {
    //     this.swap = swap;
    // }

    // public void setTour(Boolean tour) {
    //     this.tour = tour;
    // }

    // public void setDeck(Deck d) {
    //     this.deck = d;
    // }

    // public void setMain(Carte[] c, Boolean joueur) {
    //     if (joueur == JOUEUR_1)
    //         this.J1 = new Main(c);
    //     else
    //         this.J2 = new Main(c);
    // }

    public void setInterfaceUtilisateur(InterfaceUtilisateur i) {
        this.interfaceUtilisateur = i;
    }

    /*
    ############################# Methodes d'interaction #############################
    */

    public void reset(){
        CreerCartes();
        J1 = new Main(creerMain());
        J2 = new Main(creerMain());
        Carte codex = creerCodex();

        Compteur.getInstance().reset();
        tour = JOUEUR_1;

        deck = new Deck(cartes, codex);
        historique = new Historique();
    }
}