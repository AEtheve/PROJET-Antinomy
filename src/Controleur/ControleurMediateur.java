package Controleur;

import Modele.*;
import Global.Configuration;

public class ControleurMediateur {
    public static final int STARTGAME = 0; // Début de partie
	public static final int WAITSCEPTRE = 1; 
    public static final int WAITSELECT = 2; // On attend la sélection une carte
    public static final int WAITMOVE = 3; // On attend l'échangeant d'une carte
    public static final int WAITSWAP = 4; // On attend le choix de la direction du swap
    public static final int ENDGAME = 5; // Fin de partie

	Jeu jeu;
	Joueur[][] joueurs;
	int [] typeJoueur;
	int joueurCourant;
	final int lenteurAttente = 50;
	int decompte;
	int state;

	/*
    ############################# Constructeur #############################
    */

    public ControleurMediateur(Jeu j) {
		jeu = j;
		joueurs = new Joueur[2][2];
		typeJoueur = new int[2];
		for (int i = 0; i < joueurs.length; i++) {
			joueurs[i][0] = new JoueurHumain(jeu, i);
			joueurs[i][1] = new JoueurIA(jeu, i);
			typeJoueur[i] = 0; // 0 si humain, 1 si IA
		}

		state = WAITSCEPTRE;
	}

	/*
	############################# Interaction #############################
	*/

	void changeJoueur() {
		joueurCourant = (joueurCourant + 1) % joueurs.length;
		decompte = lenteurAttente;
	}

	public void changeState(){
		switch (state) {
			case STARTGAME:
				//TODO : initialisation de la partie
				break;
			case WAITSCEPTRE:
				if(joueurCourant == 1){
					state = WAITSELECT;
				}
				changeJoueur();
				break;
			case WAITSELECT:
				state = WAITMOVE;
				break;
			case WAITMOVE:
				if(jeu.getSwap()){
					state = WAITSWAP;
					jeu.setSwap(false);
				} else {
					state = WAITSELECT;
					changeJoueur();
				}
				break;
			case WAITSWAP:
				state = WAITSELECT;
				changeJoueur();
				break;
			default:
				break;
		}
				
	}

	public void changeJoueur(int j, int t) {
        String type = "Humain";
        if(t==1){
            type = "IA";
        }
		Configuration.info("Nouveau type " + type + " pour le joueur " + j);
		typeJoueur[j] = t;
	}

	/*
	############################# Fonctions de jeu #############################
	*/

	public void clicSouris(int index, String type) {
		// Lors d'un clic, on le transmet au joueur courant.
		// Si un coup a effectivement été joué (humain, coup valide), on change de joueur.
		if (joueurs[joueurCourant][typeJoueur[joueurCourant]].jeu(index, state, type))
			changeState();
	}

    public void tictac() {
		if (!Compteur.getInstance().isJ1Gagnant() || !Compteur.getInstance().isJ2Gagnant()) {
			if (decompte == 0) {
				int type = typeJoueur[joueurCourant];
				// Lorsque le temps est écoulé on le transmet au joueur courant.
				// Si un coup a été joué (IA) on change de joueur.
				if (joueurs[joueurCourant][type].tempsEcoule()) {
					changeState();
				} else {
				// Sinon on indique au joueur qui ne réagit pas au temps (humain) qu'on l'attend.
					Configuration.info("On vous attend, joueur " + joueurCourant);
					decompte = lenteurAttente;
				}
			} else {
				decompte--;
			}
		}
	}

	public void toucheClavier(Integer touche, String type) {
		clicSouris(touche, type);
    }

	/*
	############################# Getters #############################
	*/

	public int getState() {
		return state;
	}

	public Carte getCarteSelectionne(){
		return joueurs[joueurCourant][typeJoueur[joueurCourant]].getCarteSelectionne();
	}

	public Carte[] getCartesPossibles(){
		return joueurs[joueurCourant][typeJoueur[joueurCourant]].getCartesPossibles();
	}

	public int getJoueurCourant() {
		return joueurCourant;
	}

	public Historique getHistorique() {
        return jeu.getHistorique();
    }

	/*
	############################# Interaction avec le jeu #############################
	*/

	public void restartGame() {
		Configuration.info("Nouvele partie");
		jeu.reset();
		state = STARTGAME;
	}

	public void sauvegarder() {
        new Sauvegarde("output.json", jeu, this);
    }

	public void restaure() {
        Sauvegarde.restaurerSauvegarde(jeu, "output.json");
    }

}