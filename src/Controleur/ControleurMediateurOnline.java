package Controleur;

import Modele.*;

import java.util.HashMap;

import Modele.Carte;
import Serveur.Message;
import Vue.InterfaceUtilisateur;
import Vue.OnlineMenu;

public class ControleurMediateurOnline implements ControleurMediateur {

	InterfaceUtilisateur vue;
	Carte [] mainJ1, mainJ2;
	Boolean tour;
	Deck deck;

	int selectedCarteIndex = -1;

	int state = ControleurMediateur.ONLINEWAITPLAYERS;

	Carte carteAJouer;
	Carte[] cartesPossibles;
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
		Carte codex = new Carte(Carte.PLUME, Carte.TERRE, 1, 1, false);
		
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

	public void changeState(int s){
		state = s;
		tour = !tour;
		vue.miseAJour();
	}

	public void changeJoueur(int j, int t) {
		throw new UnsupportedOperationException("TODO: changeJoueur pour ControleurMediateurOnline");
	}

	/*
	############################# Fonctions de jeu #############################
	*/

	public void clicSouris(int index, String type) {
		// OnlineMenu.postMessage()
		try{
		Message message = new Message();
		
		HashMap <String, Object> clicSouris = new HashMap<String, Object>();
		clicSouris.put("index", index);
		clicSouris.put("typeClic", type);
		message.initDepuisMessage("clicSouris", Message.Serialization(clicSouris));
		OnlineMenu.sendMessage(message);
		}catch(Exception e){
		}
	}

    public void tictac() {
		// throw new UnsupportedOperationException("TODO: tictac pour ControleurMediateurOnline");
	}

	public void toucheClavier(Integer touche, String type) {
		clicSouris(touche, type);
    }

	public void resetSelection() {
		// throw new UnsupportedOperationException("TODO: resetSelection pour ControleurMediateurOnline");
		carteAJouer = null;
		cartesPossibles = null;
		
	}

	/*
	############################# Getters #############################
	*/

	public int getState() {
		return state;
	}

	public Carte getCarteSelectionne(){
		return carteAJouer;
	}

	public Carte[] getCartesPossibles(){
		return cartesPossibles;
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
	 * ############################# Setters #############################
	 */

	 public void setMainJ1(Carte[] mainJ1) {
		 this.mainJ1 = mainJ1;
	 }

	 public void setMainJ2(Carte[] mainJ2) {
		 this.mainJ2 = mainJ2;
	 }

	 public void setTour(Boolean tour) {
		 this.tour = tour;
	 }

	 public void setDeck(Deck deck) {
		 this.deck = deck;
	 }

	 public void setSelectedCarteIndex(int selectedCarteIndex) {
		 this.selectedCarteIndex = selectedCarteIndex;
	 }
	
	 public void setCarteAJouer(Carte carteAJouer) {
		 this.carteAJouer = carteAJouer;
	 }

	 public void setCartesPossibles(Carte[] cartesPossibles) {
		 this.cartesPossibles = cartesPossibles;
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

	public void annulerCoup(){
		throw new UnsupportedOperationException("Annuler coup non disponible en mode online");
	}

	public void refaireCoup(){
		throw new UnsupportedOperationException("Refaire coup non disponible en mode online");
	}
}