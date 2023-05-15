package Controleur;

import Modele.*;
import Global.Configuration;
import Modele.Carte;
import Modele.Compteur;
import Modele.Sauvegarde;
import Vue.InterfaceUtilisateur;

public class ControleurMediateurLocal implements ControleurMediateur {
	JeuEntier jeu;
	Joueur[][] joueurs;
	int [] typeJoueur;
	int joueurCourant;
	final int lenteurAttente = 50;
	int decompte;
	int state;
	InterfaceUtilisateur vue;
	int selectedCarteIndex = -1;

	/*
    ############################# Constructeur #############################
    */

    public ControleurMediateurLocal() {
		jeu = new JeuEntier();
		joueurs = new Joueur[2][3];
		typeJoueur = new int[2];
		for (int i = 0; i < joueurs.length; i++) {
			joueurs[i][0] = new JoueurHumain(jeu, i);
			joueurs[i][1] = new JoueurIA(jeu, i);
			joueurs[i][2] = new JoueurReseau(jeu, i);
			typeJoueur[i] = 0; // 0 si humain, 1 si IA, 2 si réseau
		}

		state = WAITSCEPTRE;
	}

	public void ajouteInterfaceUtilisateur(InterfaceUtilisateur v) {
        vue = v;
		jeu.setInterfaceUtilisateur(v);
    }

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
				if (!jeu.verifParadoxe()) {
					state = WAITSELECT;
					changeJoueur();
				}
				break;
			default:
				break;
		}
		metAJour();
	}

	public void changeJoueur(int j, int t) {
        String type = "Humain";
        if(t==1){
            type = "IA";
        }
		else if(t==2){
			type = "Reseau";
		}
		Configuration.info("Nouveau type " + type + " pour le joueur " + j);
		typeJoueur[j] = t;
	}

	/*
	############################# Fonctions de jeu #############################
	*/

	public void clicSouris(int index, String type) {
		if (state == WAITMOVE && type == "Main"){
			state = WAITSELECT;
		}
		// Lors d'un clic, on le transmet au joueur courant.
		// Si un coup a effectivement été joué (humain, coup valide), on change de joueur.
		if (joueurs[joueurCourant][typeJoueur[joueurCourant]].jeu(index, state, type)){
			changeState();
		}
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
					// Configuration.info("On vous attend, joueur " + joueurCourant + ", state: " + state);
					decompte = lenteurAttente;
				}
			} else {
				decompte--;
			}
		}
	}

	public void changeState(int state){
		this.state = state;
	}

	public void toucheClavier(Integer touche, String type) {
		clicSouris(touche, type);
    }

	public void resetSelection() {
		joueurs[joueurCourant][typeJoueur[joueurCourant]].carteAJouer = null;
		joueurs[joueurCourant][typeJoueur[joueurCourant]].cartesPossibles = null;
		metAJour();
	}

	/*
	############################# Getters #############################
	*/

	public int getState() {
		return state;
	}

	public Carte getCarteSelectionne(){
		return joueurs[joueurCourant][typeJoueur[joueurCourant]].getCarte();
	}

	public Carte[] getCartesPossibles(){
		return joueurs[joueurCourant][typeJoueur[joueurCourant]].getCartesPossibles();
	}

	public int getSelectedCarteIndex(){
		Carte carte = getCarteSelectionne();
		if (carte == null) {
			return -1;
		}
		return carte.getIndex();
	}

	public int getJoueurCourant() {
		return joueurCourant;
	}

	public Historique getHistorique() {
        return jeu.getHistorique();
    }

    public Deck getInterfaceDeck() {
        return jeu.getDeck();
    }

	public Boolean getInterfaceTour() {
		return jeu.getTour();
	}

	public Carte[] getInterfaceMain(Boolean joueur) {
		return jeu.getMain(joueur);
	}

	public int getTypeJoueur(int j) {
		return typeJoueur[j];
	}
	
	/*
	############################# Setters #############################
	*/

	public void setMainJ1(Carte[] mainJ1){
		jeu.setMain(mainJ1, JeuEntier.JOUEUR_1);
	}

	public void setMainJ2(Carte[] mainJ2){
		jeu.setMain(mainJ2, JeuEntier.JOUEUR_2);
	}

	public void setTour(Boolean tour){
		jeu.setTour(tour);
	}

	public void setDeck(Deck deck){
		jeu.setDeck(deck);
	}

	public void setSelectedCarteIndex(int selectedCarteIndex){
		this.selectedCarteIndex = selectedCarteIndex;
	}

	public void setCarteAJouer(Carte carteAJouer){
		joueurs[joueurCourant][typeJoueur[joueurCourant]].carteAJouer = carteAJouer;
	}

	public void setCartesPossibles(Carte[] cartesPossibles){
		joueurs[joueurCourant][typeJoueur[joueurCourant]].cartesPossibles = cartesPossibles;
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

	public void metAJour() {
		if (vue != null) {
			vue.miseAJour();
		}
	}

}