package Modele;

// Version compacte du modèle du jeu, pour utilisation dans l'arbre Min Max de l'IA
public class JeuCompact extends Jeu{
	public int scoreJ1, scoreJ2;

	@Override
	public Object clone() {
		JeuCompact copie = new JeuCompact();
		copie.setDeck((Deck)this.deck.clone());
		copie.setCartes(this.cartes);
		copie.setMains((Main)this.J1.clone(), (Main)this.J2.clone());
		copie.setTour(this.tour);
		copie.setScores(this.scoreJ1, this.scoreJ2);
        copie.setSwap(this.swap);
		return copie;
	}

    public void reset(){
        CreerCartes();
        J1 = new Main(creerMain());
        J2 = new Main(creerMain());
        Carte codex = creerCodex();

        scoreJ1 = 0;
        scoreJ2 = 0;

        tour = JOUEUR_1;

        deck = new Deck(cartes, codex);
    }

	public void setCartes(Carte[] cartes) {
		this.cartes = new Carte[cartes.length];
		for (int i=0; i<cartes.length; i++) {
			this.cartes[i] = (Carte)cartes[i].clone();
		}
	}

	public void setMains(Main J1, Main J2) {
		this.J1 = J1;
		this.J2 = J2;
	}

	public void setScores(int scoreJ1, int scoreJ2) {
		this.scoreJ1 = scoreJ1;
		this.scoreJ2 = scoreJ2;
	}


    protected void Paradoxe() {
        if(verifParadoxe() && deck.getSceptre(JOUEUR_1) != -1 && deck.getSceptre(JOUEUR_2) != -1){
            setSwap(true);
            if (getTour()) {
                scoreJ1++;
            } else {
                scoreJ2++;
            }
            prochainCodex();
        } else {
            switchTour();
        }
    }
    void CLheureDuDuDuDuel() {
        /*
        On fait un duel, le joueur qui a le plus de points gagne
        s'il y a égalité on fait une bataille
        */
        scoreJ2--;
        // int scoreJ1D = 0;
        // for (Carte c : J1.getMain()) {
        //     if (c.getColor() != deck.getCodex().getIndex()) {
        //         scoreJ1 += c.getValue();
        //     }
        // }
        // int scoreJ2D = 0;
        // for (Carte c : J2.getMain()) {
        //     if (c.getColor() != deck.getCodex().getIndex()) {
        //         scoreJ2 += c.getValue();
        //     }
        // }

        // if (scoreJ1D > scoreJ2D) {
        //     if (scoreJ2 > 0) {
        //         scoreJ2--;
        //         scoreJ1++;
        //     }
        //     deck.prochainCodex();
        // } else if (scoreJ1D < scoreJ2D) {
        //     if (scoreJ1 > 0) {
        //         scoreJ1--;
        //         scoreJ2++;
        //     }
        //     deck.prochainCodex();
        // } else {
        //     CLheuredelaBataille();
        // }
    }

    void CLheuredelaBataille() {
        // TODO: Faire la bataille
        scoreJ2--;
    }

	public int evaluation() {
        if (scoreJ2-scoreJ1 > 0) {
            System.out.println("Score J2-J1 : " + (scoreJ2-scoreJ1));
        }
		return scoreJ2-scoreJ1;
	}
}
