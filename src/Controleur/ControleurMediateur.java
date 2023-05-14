package Controleur;

import Modele.Carte;
import Modele.Deck;
import Modele.Historique;
import Vue.InterfaceUtilisateur;

public interface ControleurMediateur{
	public static final int STARTGAME = 0; // Début de partie
	public static final int WAITSCEPTRE = 1; 
    public static final int WAITSELECT = 2; // On attend la sélection une carte
    public static final int WAITMOVE = 3; // On attend l'échangeant d'une carte
    public static final int WAITSWAP = 4; // On attend le choix de la direction du swap
    public static final int ENDGAME = 5; // Fin de partie

	public Carte getCarteSelectionne();
	public Carte[] getCartesPossibles();
	public int getSelectedCarteIndex();
	public Historique getHistorique();
	public Deck getInterfaceDeck();
	public Boolean getInterfaceTour();
	public Carte[] getInterfaceMain(Boolean joueur);
	public int getTypeJoueur(int j);
	public int getJoueurCourant() ;
	public void clicSouris(int index, String type);
	public void resetSelection();
	public int getState();
	public void tictac();
	public void changeJoueur(int j, int t);
	public void restartGame();
	public void ajouteInterfaceUtilisateur(InterfaceUtilisateur v);
	public void toucheClavier(Integer touche, String type);
}