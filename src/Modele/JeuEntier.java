package Modele;

import Global.Configuration;
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

    public void joue(Coup coup){
        if (historique != null){
            historique.ajouterHistorique(CreerCommande(coup));
        }
        switch (coup.getType()) {
            case Coup.ECHANGE:
            case Coup.ECHANGE_SWAP:
                execEchange(coup);
                Paradoxe();
                break;
            case Coup.SWAP_DROIT:
            case Coup.SWAP_GAUCHE:
                execSwap(coup);
                switchTour();     
                break;
            case Coup.SCEPTRE:
                if (coup.estCoupValide(this)) {
                    execSceptre(coup);
                }
                switchTour();
                break;
            default:
                throw new IllegalArgumentException("Type de coup invalide");
        }

        if (verifDuel() && swap == false && coup.getType() != Coup.SCEPTRE) {
            CLheureDuDuDuDuel();
        }
        metAJour();
         if (interfaceUtilisateur != null) {
            interfaceUtilisateur.animeCoup(coup);
        }
    }


    protected void CLheureDuDuDuDuel() {
        /*
        On fait un duel, le joueur qui a le plus de points gagne
        s'il y a égalité on fait une bataille
        */

        // On récupère les scores
        int scoreJ1 = 0;
        for (Carte c : J1.getMain()) {
            if (c.getColor() != deck.getCodex().getIndex()) {
                scoreJ1 += c.getValue();
            }
        }
        int scoreJ2 = 0;
        for (Carte c : J2.getMain()) {
            if (c.getColor() != deck.getCodex().getIndex()) {
                scoreJ2 += c.getValue();
            }
        }

        // On affiche le gagnant
        if (scoreJ1 > scoreJ2) {
            Compteur.getInstance().Vol(JOUEUR_1);
            deck.prochainCodex();
            Configuration.info("Joueur 1 gagne le duel");
        } else if (scoreJ1 < scoreJ2) {
            Compteur.getInstance().Vol(JOUEUR_2);
            deck.prochainCodex();
            Configuration.info("Joueur 2 gagne le duel");
        } else {
            Configuration.info("Bataille !");
            CLheuredelaBataille();
        }
    }

    protected void CLheuredelaBataille() {
        int score = 0;
        for (int i = 0; i < 3; i++) {
            Carte c1 = J1.getMain()[i];
            Carte c2 = J2.getMain()[i];
            if (c1.getColor() != deck.getCodex().getIndex() && c2.getColor() != deck.getCodex().getIndex()) {
                if (c1.getValue() > c2.getValue()) {
                    score++;
                    break;
                } else if (c1.getValue() < c2.getValue()) {
                    score--;
                    break;
                }
            } else if (c1.getColor() != deck.getCodex().getIndex()) {
                score++;
                break;
            } else if (c2.getColor() != deck.getCodex().getIndex()) {
                score--;
                break;
            }
        }

        if (score > 0) {
            Compteur.getInstance().Vol(JOUEUR_1);
            deck.prochainCodex();
            Configuration.info("Joueur 1 gagne la bataille");
        } else if (score < 0) {
            Compteur.getInstance().Vol(JOUEUR_2);
            deck.prochainCodex();
            Configuration.info("Joueur 2 gagne la bataille");
        } else {
            Configuration.info("Egalité");
        }
    }
}