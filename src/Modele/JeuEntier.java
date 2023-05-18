package Modele;

import Vue.InterfaceUtilisateur;


public class JeuEntier extends Jeu {
    protected Historique historique;
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

    public Historique getHistorique() {
        return historique;
    }

    /*
    ############################# Setteurs #############################
    */

    // public void setInterfaceUtilisateur(InterfaceUtilisateur i) {
    //     this.interfaceUtilisateur = i;
    // }

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