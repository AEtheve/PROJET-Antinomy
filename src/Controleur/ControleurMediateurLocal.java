package Controleur;

import Modele.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
	final int lenteurAttente = 140;
	int decompte;
	int state;
	InterfaceUtilisateur vue;
	int selectedCarteIndex = -1;
	private Historique historique = new Historique();
	int joueurDebut;
	int GagnantDuel = 0;

	/*
    ############################# Constructeur #############################
    */

    public ControleurMediateurLocal() {
		jeu = new JeuEntier();
		jeu.setHistorique(historique);
		joueurs = new Joueur[2][2];
		typeJoueur = new int[2];
		for (int i = 0; i < joueurs.length; i++) {
			joueurs[i][0] = new JoueurHumain(jeu, i);
			joueurs[i][1] = new JoueurIA(jeu, i);
			typeJoueur[i] = 0; // 0 si humain, 1 si IA, 2 si réseau
		}

		if (Jeu.getInitJoueurCommence() == Jeu.JOUEUR_1) {
			joueurDebut = joueurCourant = 0;
		} else {
			joueurDebut = joueurCourant = 1;
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
		if (Compteur.getInstance().estJ1Gagnant() || Compteur.getInstance().estJ2Gagnant()) {
			Configuration.info("Fin de partie");
			if (vue != null) {
				vue.setGagnant(Compteur.getInstance().estJ1Gagnant());
			}
			metAJour();
			return;
		}
		switch (state) {
			case STARTGAME:
				joueurCourant = joueurDebut;
				break;
			case WAITSCEPTRE:
				if (Jeu.getInitJoueurCommence() == Jeu.JOUEUR_1 && joueurCourant == 1) {
					state = WAITSELECT;
				} else if (Jeu.getInitJoueurCommence() == Jeu.JOUEUR_2 && joueurCourant == 0) {
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
		// System.out.println("Changement d'état: " + state);
	}

	public void changeJoueur(int j, int t) {
        String type = "Humain";
        if(t==1){
            type = "IA";
			type+= " " + Configuration.difficulteIA;
			joueurs[j][t] = new JoueurIA(jeu, j);
        }
		Configuration.alerte("Nouveau type " + type + " pour le joueur " + j);
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
		if (!Compteur.getInstance().estJ1Gagnant() || !Compteur.getInstance().estJ2Gagnant()) {
			if (decompte == 0) {
				int type = typeJoueur[joueurCourant];
				// Lorsque le temps est écoulé on le transmet au joueur courant.
				// Si un coup a été joué (IA) on change de joueur.
				if (joueurs[joueurCourant][type].tempsEcoule(state)) {
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
		System.out.println("Annulation du coup " + c.getCoup().getType());
		switch (c.getCoup().getType()){
			case Coup.ECHANGE_SWAP:
				historique.ajoutePasse(c);
				jeu.revertSwap(c);
				changeJoueur();
				changeState(WAITSWAP);
				break;
			case Coup.ECHANGE:
				changeState(WAITSELECT);
				jeu.revertEchange(c,false);
				changeJoueur();
				break;
			case Coup.SCEPTRE:
				changeState(WAITSCEPTRE);
				jeu.revertSceptre(c);
				changeJoueur();
				break;
		}
		Compteur.getInstance().setScore(Jeu.JOUEUR_1, c.getScoreJ1());
		Compteur.getInstance().setScore(Jeu.JOUEUR_2, c.getScoreJ2());
		Carte codex = jeu.getDeck().getCodex();
		codex.setIndex(c.getCodex());
		jeu.getDeck().setCodex(codex);
		vue.miseAJour();
		System.out.println("Post annuler");
		historique.afficheFutur();
	}

	public void refaireCoup(){
		Commande c = jeu.refaireCoup();
		switch(c.getCoup().getType()){
			case Coup.ECHANGE_SWAP:
				changeJoueur();
				changeState(WAITSWAP);
				break;
			case Coup.ECHANGE:
				changeState(WAITSELECT);
				changeJoueur();
				break;
			case Coup.SCEPTRE:
				if (joueurCourant == 0) {
					state = WAITSCEPTRE;
					changeJoueur();
				} else {
					state = WAITSELECT;
					changeJoueur();
				}
				break;
		}
		System.out.println("Restaure les scores :" + c.getScoreJ1() + " " + c.getScoreJ2());
		Compteur.getInstance().setScore(Jeu.JOUEUR_1, c.getScoreJ1());
		Compteur.getInstance().setScore(Jeu.JOUEUR_2, c.getScoreJ2());
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

	public Boolean getSwap(){
		return jeu.getSwap();
	}

	public int getGagnantDuel() { // 0 si pas de gagnant, 1 si joueur 1, 2 si joueur 2
		return GagnantDuel;
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
		joueurCourant = joueurDebut;
		metAJour();
	}

	public void sauvegarder(String filename) {
        new Sauvegarde(filename, jeu, this, getSwapDroit(), getSwapGauche());
    }

	public void loadGame(String filename){
		JSONObject obj = Sauvegarde.restaurerSauvegarde(jeu, filename);
		if (obj == null) {
			throw new IllegalArgumentException("Impossible de charger la partie");
		}
		Boolean swapDroit = (Boolean) obj.get("swapdroit");
		Boolean swapGauche = (Boolean) obj.get("swapgauche");
		joueurs[joueurCourant][typeJoueur[joueurCourant]].setSwapDroit(swapDroit);
		joueurs[joueurCourant][typeJoueur[joueurCourant]].setSwapGauche(swapGauche);
		state = (int) obj.get("int");

        JSONArray passe = (JSONArray) obj.get("passe");
        for (int i=0; i<passe.size(); i++) {
            JSONObject cmd = (JSONObject) passe.get(i);
            int pos_prev_sceptre = Math.toIntExact((long) cmd.get("pos_prev_sceptre"));
            int scoreJ1_cmd = Math.toIntExact((long) cmd.get("scoreJ1"));
            int scoreJ2_cmd = Math.toIntExact((long) cmd.get("scoreJ2"));
            int coup = Math.toIntExact((long) cmd.get("coup"));
            int codex = Math.toIntExact((long) cmd.get("codex"));
            Boolean tour_cmd = (Boolean) cmd.get("tour");

            Commande commande = new Commande(new Coup(coup), pos_prev_sceptre, codex, tour_cmd);
            this.historique.ajouterHistorique(commande);
        }


		
	}

	public void metAJour() {
		if (vue != null) {
			vue.miseAJour();
		}
	}

	public Boolean estFini(){
		Boolean end;
		if (end = jeu.estFini()){
			state = ENDGAME;
			decompte = -1;
		}
		return end;
	}

	public void stopTime(){
		decompte = -1;
	}

}