package Controleur;

import Modele.Jeu;
import Modele.Carte;
import Modele.Compteur;

public class ControleurMediateur {
    Jeu jeu;
	Joueur[][] joueurs;
	int [] typeJoueur;
	int joueurCourant;
	final int lenteurAttente = 50;
	int decompte;
	int state;

	public static final int STARTGAME = 0; // Début de partie
    public static final int WAITSCEPTER = 1; // On attend le placement son sceptre
    public static final int WAITSELECT = 2; // On attend la sélection une carte
    public static final int WAITMOVE = 3; // On attend l'échangeant d'une carte
    public static final int WAITSWAP = 4; // On attend le choix de la direction du swap
    public static final int ENDGAME = 5; // Fin de partie


    public ControleurMediateur(Jeu j) {
		jeu = j;
		joueurs = new Joueur[2][2];
		typeJoueur = new int[2];
		for (int i = 0; i < joueurs.length; i++) {
			joueurs[i][0] = new JoueurHumain(jeu, i);
			joueurs[i][1] = new JoueurIA(jeu, i);
			typeJoueur[i] = 0;
		}

		state = STARTGAME;
	}

	public void clicSouris(Carte c) {
		// Lors d'un clic, on le transmet au joueur courant.
		// Si un coup a effectivement été joué (humain, coup valide), on change de joueur.
		if (joueurs[joueurCourant][typeJoueur[joueurCourant]].jeu(c, state))
			changeJoueur();
	}

	void changeJoueur() {
		joueurCourant = (joueurCourant + 1) % joueurs.length;
		decompte = lenteurAttente;
	}

    public void tictac() {
		if (!Compteur.getInstance().isJ1Gagnant() || !Compteur.getInstance().isJ2Gagnant()) {
			if (decompte == 0) {
				int type = typeJoueur[joueurCourant];
				// Lorsque le temps est écoulé on le transmet au joueur courant.
				// Si un coup a été joué (IA) on change de joueur.
				if (joueurs[joueurCourant][type].tempsEcoule()) {
					changeJoueur();
				} else {
				// Sinon on indique au joueur qui ne réagit pas au temps (humain) qu'on l'attend.
					System.out.println("On vous attend, joueur " + joueurCourant);
					decompte = lenteurAttente;
				}
			} else {
				decompte--;
			}
		}
	}

	public void changeJoueur(int j, int t) {
        String type = "Humain";
        if(t==1){
            type = "IA";
        }
		System.out.println("Nouveau type " + type + " pour le joueur " + j);
		typeJoueur[j] = t;
	}


	public void restartGame() {
		System.out.println("Nouvelle partie");
		jeu.reset();
	}



}
