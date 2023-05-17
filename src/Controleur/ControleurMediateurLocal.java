package Controleur;

import Modele.*;
import Global.Configuration;
import Modele.Carte;
import Modele.Compteur;
import Modele.Sauvegarde;
import Vue.InterfaceUtilisateur;

public class ControleurMediateurLocal implements ControleurMediateur {
	Jeu jeu;
	Joueur[][] joueurs;
	int [] typeJoueur;
	int joueurCourant;
	final int lenteurAttente = 50;
	int decompte;
	int state;
	InterfaceUtilisateur vue;
	int selectedCarteIndex = -1;
	private Historique historique = new Historique();

	/*
    ############################# Constructeur #############################
    */

    public ControleurMediateurLocal() {
		jeu = new Jeu();
		jeu.setHistorique(historique);
		joueurs = new Joueur[2][2];
		typeJoueur = new int[2];
		for (int i = 0; i < joueurs.length; i++) {
			joueurs[i][0] = new JoueurHumain(jeu, i);
			joueurs[i][1] = new JoueurIA(jeu, i);
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
				state = WAITSELECT;
				changeJoueur();
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

	public int loadGame(String filename){
		return Sauvegarde.restaurerSauvegarde(jeu, filename);
	}

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

	public void annulerCoup(){
		if (!historique.peutAnnuler()) {
			Configuration.alerte("Impossible d'annuler le coup");
			return;
		}
		Commande c = historique.annuler();
		historique.affichePasse();
		System.out.println("Annulation du coup " + c.getCoup().getType());
		switch (c.getCoup().getType()){
			case Coup.ECHANGE_SWAP:
				historique.addPasse(c);
				jeu.revertSwap(c);
				changeState(WAITSWAP);
				break;
			case Coup.ECHANGE:
				changeState(WAITMOVE);
				jeu.revertEchange(c,false);
				break;
			case Coup.SCEPTRE:
				if (jeu.getDeck().getSceptre(!(joueurCourant==1)) == -1) {
					changeState(WAITSCEPTRE);
				} else {
					changeState(WAITSELECT);
				}
				jeu.revertSceptre(c);
				break;
		}
		vue.miseAJour();
	}

	public void refaireCoup(){
		jeu.refaireCoup();
		changeState(WAITSELECT);
		vue.miseAJour();
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
        return historique;
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

	public Boolean getSwapDroit(){
		return joueurs[joueurCourant][typeJoueur[joueurCourant]].isSwapDroit();
	}

	public Boolean getSwapGauche(){
		return joueurs[joueurCourant][typeJoueur[joueurCourant]].isSwapGauche();
	}
	
	/*
	############################# Setters #############################
	*/

	public void setMainJ1(Carte[] mainJ1){
		jeu.setMain(mainJ1, Jeu.JOUEUR_1);
	}

	public void setMainJ2(Carte[] mainJ2){
		jeu.setMain(mainJ2, Jeu.JOUEUR_2);
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

	public void rejouer() {
		Configuration.info("Nouvele partie");
		jeu.reset();
		historique = new Historique();
		for(int i = 0; i < 2; i++){
			joueurs[i][typeJoueur[i]].reset();
		}
		state = WAITSCEPTRE;
		joueurCourant = 0;
		metAJour();
	}

	public void sauvegarder(String filename) {
        new Sauvegarde(filename, jeu, this);
    }

	public int restaure() {
        return Sauvegarde.restaurerSauvegarde(jeu, "output.json");
    }

	public void metAJour() {
		if (vue != null) {
			vue.miseAJour();
		}
	}

}