package Controleur;

import Modele.*;
import Global.Configuration;
import Modele.Carte;
import Modele.Compteur;
import Vue.InterfaceUtilisateur;

public class ControleurMediateurOnline implements ControleurMediateur {

	InterfaceUtilisateur vue;
	Carte [] mainJ1, mainJ2;
	Boolean tour;
	Deck deck;

	int selectedCarteIndex = -1;

	int state = ControleurMediateur.WAITSCEPTRE;
	/*
    ############################# Constructeur #############################
    */

    public ControleurMediateurOnline() {
		Carte carte = new Carte(Carte.PLUME, Carte.TERRE, 1, 0, false);
		mainJ1 = new Carte[] {carte, carte, carte};
		mainJ2 = new Carte[] {carte, carte, carte};

		Carte[] continuum = new Carte[9];
		for (int i = 0; i < 9; i++) {
			continuum[i] = new Carte(Carte.PLUME, Carte.TERRE, 1, i, false);
		}
		Carte codex = new Carte(Carte.PLUME, Carte.TERRE, 1, 0, false);
		
		deck = new Deck(continuum, codex);
		tour = true;

	}

	public void ajouteInterfaceUtilisateur(InterfaceUtilisateur v) {
        vue = v;
    }

	void changeJoueur() {
		throw new UnsupportedOperationException("TODO: changeJoueur pour ControleurMediateurOnline");
	}

	public void changeState(){
		throw new UnsupportedOperationException("TODO: changeState pour ControleurMediateurOnline");
	}

	public void changeJoueur(int j, int t) {
		throw new UnsupportedOperationException("TODO: changeJoueur pour ControleurMediateurOnline");
	}

	/*
	############################# Fonctions de jeu #############################
	*/

	public void clicSouris(int index, String type) {
		throw new UnsupportedOperationException("TODO: clicSouris pour ControleurMediateurOnline");
	}

    public void tictac() {
		// throw new UnsupportedOperationException("TODO: tictac pour ControleurMediateurOnline");
	}

	public void toucheClavier(Integer touche, String type) {
		clicSouris(touche, type);
    }

	public void resetSelection() {
		throw new UnsupportedOperationException("TODO: resetSelection pour ControleurMediateurOnline");
	}

	/*
	############################# Getters #############################
	*/

	public int getState() {
		return state;
	}

	public Carte getCarteSelectionne(){
		// throw new UnsupportedOperationException("TODO: getCarteSelectionne pour ControleurMediateurOnline");
		return null;
	}

	public Carte[] getCartesPossibles(){
		// throw new UnsupportedOperationException("TODO: getCartesPossibles pour ControleurMediateurOnline");
		return null;
	}

	public int getSelectedCarteIndex(){
		return selectedCarteIndex;
	}

	public int getJoueurCourant() {
		throw new UnsupportedOperationException("TODO: getJoueurCourant pour ControleurMediateurOnline");
	}

	public Historique getHistorique() {
		// throw new UnsupportedOperationException("TODO: getHistorique pour ControleurMediateurOnline");
		return null;
    }

    public Deck getInterfaceDeck() {
		return deck;
    }

	public Boolean getInterfaceTour() {
		return tour;
	}

	public Carte[] getInterfaceMain(Boolean joueur) {
		return joueur ? mainJ1 : mainJ2;
	}

	public int getTypeJoueur(int j) {
		return 0; // TODO change pour réseau
	}
	

	/*
	############################# Interaction avec le jeu #############################
	*/

	public void restartGame() {
		throw new UnsupportedOperationException("TODO: restartGame pour ControleurMediateurOnline");
	}

	public void metAJour() {
		if (vue != null) {
			vue.miseAJour();
		}
	}

}