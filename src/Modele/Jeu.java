package Modele;

import java.util.stream.IntStream;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import Global.Configuration;
import Vue.InterfaceUtilisateur;


public class Jeu {
    final public static Boolean JOUEUR_1 = true;
    final public static Boolean JOUEUR_2 = false;

    private Deck deck;
    private Main J1, J2;
    private Boolean tour; // true = tour du J1
    private Carte[] cartes;
    private Boolean swap = false;
    Random r = new Random();
    InterfaceUtilisateur interfaceUtilisateur;
    Historique historique;

	public JeuCompact getJeuCompact() {
		JeuCompact jc = new JeuCompact();
		jc.setDeck((Deck)this.deck.clone());
		jc.setCartes(this.cartes);
		jc.setMains((Main)this.J1.clone(), (Main)this.J2.clone());
		jc.setTour(this.tour);
		jc.setScores(Compteur.getInstance().getJ1Points(), Compteur.getInstance().getJ2Points());
		return jc;
	}

    /*
    ############################# Constructeurs #############################
    */

    public Jeu() {
        reset();
    }

    /*
    ############################# Getteurs #############################
    */

    public Deck getDeck() {
        return deck;
    }

    public Boolean getSwap() {
        return swap;
    }

    public Carte[] getMain(Boolean joueur) {
        if (joueur == JOUEUR_1)
            return J1.getMain();
        else
            return J2.getMain();
    }

    public Boolean getTour() {
        return tour;
    }

    public Carte[] getCartesPossibles(Carte c) {
        /*
        Retourne les cartes possibles à jouer en fonction de la carte jouée
        */
        Carte[] continuum = deck.getContinuum();
        Carte [] cartesPossibles = new Carte [continuum.length];
        int j, i = 0, k = deck.getSceptre(tour);
        for (j = 0; j < continuum.length; j++) {
            // Pour chaque carte du continuum
            if (tour) {
                // Si c'est le tour du joueur 1
                if (continuum[j].getIndex() < k) {
                    // Si la carte est avant le sceptre
                    if (continuum[j].getColor() == c.getColor() || continuum[j].getSymbol() == c.getSymbol()) {
                        // Si la carte est de la même couleur ou du même symbole on l'ajoute
                        cartesPossibles[i] = continuum[j];
                        i++;
                    }
                }
                if (k + c.getValue() == continuum[j].getIndex()) {
                    // Si la carte est après le sceptre a une valeur égale à la carte jouée, on l'ajoute
                    cartesPossibles[i] = continuum[j];
                    i++;
                }
            } else {
                // De meme pour le joueur 2
                if (continuum[j].getIndex() > k) {
                    if (continuum[j].getColor() == c.getColor() || continuum[j].getSymbol() == c.getSymbol()) {
                        cartesPossibles[i] = continuum[j];
                        i++;
                    }
                }
                if (k - c.getValue() == continuum[j].getIndex()) {
                    cartesPossibles[i] = continuum[j];
                    i++;
                }
            }
        }
        Carte [] cartesPossibles2 = new Carte[i];
        for (j = 0; j < i; j++) {
            cartesPossibles2[j] = cartesPossibles[j];
        }
        return cartesPossibles2;
    }

    public int[] getIndexCartePossible(Carte[] cartesPossibles) {
        /*
        Retourne les index des cartes possibles à jouer en fonction de la carte jouée
        */
        int[] index = new int[cartesPossibles.length];
        for (int i = 0; i < index.length; i++) {
            index[i] = cartesPossibles[i].getIndex();
        }
        return index;
    }

    public int[] getSceptrePossibleInit() {
        /*
        Retourne les index des positions du septre possibles à jouer en de la couleur du codex
        */
        int codex = deck.getCodex().getIndex();
        Carte[] continuum = deck.getContinuum();
        int[] cartesPossibles = new int[4];
        int i = 0;
        for (int j = 0; j < continuum.length; j++) {
            if (continuum[j].getColor() == codex) {
                cartesPossibles[i] = continuum[j].getIndex();
                i++;
            }
        }
        int[] cartesPossibles2 = new int[i];
        for (int j = 0; j < i; j++) {
            cartesPossibles2[j] = cartesPossibles[j];
        }
        return cartesPossibles2;
    }

    /*
    ############################# Setteurs #############################
    */

    public void setSwap(Boolean swap) {
        this.swap = swap;
    }

    public void setTour(Boolean tour) {
        this.tour = tour;
    }

    public void setDeck(Deck d) {
        this.deck = d;
    }

    public void setMain(Carte[] c, Boolean joueur) {
        if (joueur == JOUEUR_1)
            this.J1 = new Main(c);
        else
            this.J2 = new Main(c);
    }

    public void setInterfaceUtilisateur(InterfaceUtilisateur i) {
        this.interfaceUtilisateur = i;
    }
    
   public void setHistorique(Historique h){
        this.historique = h;
    }

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
    }

    public void restaure(Main main1, Main main2,Carte codex, int sceptre1, int sceptre2, Boolean tour, int scoreJ1, int scoreJ2){
        //TODO : A modifier apres MAJ d'Esteban et d'Alexis
    }

    public void switchTour() {
        tour = !tour;
    }

    public void prochainCodex() {
        deck.prochainCodex();
    }

    public boolean verifDuel() {
        return deck.getSceptre(JOUEUR_1) == deck.getSceptre(JOUEUR_2);
    }

    public boolean verifParadoxe() {
        Carte[] main = (tour) ? J1.getMain() : J2.getMain();
        int codexIndex = deck.getCodex().getIndex();
        if (main[0].getColor() == codexIndex || main[1].getColor() == codexIndex || main[2].getColor() == codexIndex) {
            return false;
        }

        if (main[0].getColor() == main[1].getColor() && main[0].getColor() == main[2].getColor()) {
            Configuration.info("Paradoxe de couleur");
            return true;
        }
        if (main[0].getSymbol() == main[1].getSymbol() && main[0].getSymbol() == main[2].getSymbol()) {
            Configuration.info("Paradoxe de symbole");
            return true;
        }
        if (main[0].getValue() == main[1].getValue() && main[0].getValue() == main[2].getValue()) {
            Configuration.info("Paradoxe de valeur");
            return true;
        }
        return false;
    }

    /*
    ############################# Methodes de jeu #############################
    */

    public void joue(Coup coup){
        historique.ajouterHistorique(CreerCommande(coup));
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
            interfaceUtilisateur.miseAJour();
            interfaceUtilisateur.animeCoup(coup);
        }
    }

    private void metAJour() {
        if (interfaceUtilisateur != null) {
            interfaceUtilisateur.miseAJour();

        }
    }

    private void Paradoxe() {
        if(verifParadoxe() && deck.getSceptre(JOUEUR_1) != -1 && deck.getSceptre(JOUEUR_2) != -1){
            setSwap(true);
            int res = Compteur.getInstance().Incremente(getTour());
            // TODO : Resultat
            if (res == 0) {
                Configuration.info("Joueur 1 gagne");
            } else if (res == 1) {
                Configuration.info("Joueur 2 gagne");
            }
            prochainCodex();
        } else {
            switchTour();
        }
    }

    public void execEchange(Coup c) {
        /*
        Echange la carte de la main avec celle du continuum
        */
        int ndx;
        Carte carte = null;
        // On récupère la carte de la main
        for (ndx = 0; ndx < 3; ndx++) {
            if (tour) {
                if (J1.getMain()[ndx].getIndex() == c.getCarteMain()) {
                    carte = J1.getMain()[ndx];
                    break;
                }
            } else {
                if (J2.getMain()[ndx].getIndex() == c.getCarteMain()) {
                    carte = J2.getMain()[ndx];
                    break;
                }
            }
        }
        // On récupère la carte du continuum et on l'échange
        Carte[] continuum = deck.getContinuum();
        int ndx_continuum;
        for (int i = 0; i < continuum.length; i++) {
            if (continuum[i].getIndex() == c.getCarteContinuum()) {
                ndx_continuum = continuum[i].getIndex();
                Carte carteC = continuum[i];
                if (tour) {
                    J1.setCarte(carteC, ndx);
                } else {
                    J2.setCarte(carteC, ndx);
                }
                carteC.setIndex(carte.getIndex());
                if (c.getType() != Coup.ECHANGE_SWAP)
                    deck.setSceptre(tour, ndx_continuum);
                carte.setIndex(ndx_continuum);
                continuum[i] = carte;
                break;
            }
        }
    }

    public void execSwap(Coup c) {
        Carte[] continuum = deck.getContinuum();
        continuum = shuffle(continuum);
        int pos_sc = deck.getSceptre(tour);
        int j = 0;
        Coup coup;
        for (int i = 0; i < continuum.length; i++) {
            if (c.getType() == Coup.SWAP_DROIT) {
                if (continuum[i].getIndex() == pos_sc + 1 || continuum[i].getIndex() == pos_sc + 2
                        || continuum[i].getIndex() == pos_sc + 3) {
                    int ndx = (tour) ? J1.getCarte(j).getIndex() : J2.getCarte(j).getIndex();
                    coup = new Coup(Coup.ECHANGE_SWAP, ndx, continuum[i].getIndex());
                    System.out.println(coup.toString());
                    historique.ajouterHistorique(CreerCommande(coup));
                    historique.affichePasse();
                    j++;
                    execEchange(coup);
                }
            } else if (c.getType() == Coup.SWAP_GAUCHE) {
                if (continuum[i].getIndex() == pos_sc - 1 || continuum[i].getIndex() == pos_sc - 2
                        || continuum[i].getIndex() == pos_sc - 3) {
                    int ndx = (tour) ? J1.getCarte(j).getIndex() : J2.getCarte(j).getIndex();
                    coup = new Coup(Coup.ECHANGE_SWAP, ndx, continuum[i].getIndex());
                    System.out.println(coup.toString());
                    historique.ajouterHistorique(CreerCommande(coup));
                    historique.affichePasse();
                    j++;
                    execEchange(coup);
                }
            }
        }

    }

    public void execSceptre(Coup c) {
        deck.setSceptre(tour, c.getCarteContinuum());
    }

    void CLheureDuDuDuDuel() {
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

    void CLheuredelaBataille() {
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

    public void revertSwap(Commande c){
        switchTour();
        Commande echange_swap;
        historique.affichePasse();
        for (int i = 0; i < 3; i++) {
            echange_swap = historique.getCommandePrec();
            revertEchange(echange_swap,true);
        }
        echange_swap = historique.getCommandePrec();
        if (echange_swap.getCoup().getType() == Coup.SWAP_DROIT || echange_swap.getCoup().getType() == Coup.SWAP_GAUCHE){
            System.out.println("REVERT SWAP REUSSI");
            return;
        }
        throw new IllegalArgumentException("Erreur de swap");
    }

    public Commande CreerCommande(Coup c){
        return new Commande(c, deck.getSceptre(tour), deck.getCodex().getIndex(), tour);
    }

    public void revertEchange(Commande c, Boolean estSwap){
        // Boolean estSwap à true si on est dans le cas d'un swap : pas de switch tour dans ce cas
        System.out.println("Revert echange");
        if (!estSwap){
            switchTour();
        }
        byte carteContinuumByte = deck.getContinuum()[c.getCoup().getCarteContinuum()].getType();
        byte CarteMainByte = ((tour) ? J1.getMain()[c.getCoup().getCarteMain()] : J2.getMain()[c.getCoup().getCarteMain()]).getType();

        Carte carteContinuum = deck.getContinuum()[c.getCoup().getCarteContinuum()];
        Carte carteMain = ((tour) ? J1.getMain()[c.getCoup().getCarteMain()] : J2.getMain()[c.getCoup().getCarteMain()]);
        carteContinuum.setType(CarteMainByte);
        carteMain.setType(carteContinuumByte);

        deck.setSceptre(tour, c.pos_prev_sceptre);

        // if (estSwap)
        //     switchTour();   
        historique.addFutur(c);

    }

    public void revertSceptre(Commande c){
        System.out.println("Revert sceptre");
        switchTour();
        deck.setSceptre(tour, -1);
        historique.addFutur(c);
    }

    public void refaireCoup(){
        if (!historique.peutRefaire()) {
            Configuration.alerte("Impossible de refaire le coup");
            return;
        }
        Commande c = historique.refaire();
        switch(c.getCoup().getType()){
            case Coup.SWAP_DROIT:
            case Coup.SWAP_GAUCHE:
                execSwap(c.getCoup());
                break;
            case Coup.ECHANGE:
                execEchange(c.getCoup());
                switchTour();
                break;
            case Coup.SCEPTRE:
                execSceptre(c.getCoup());
                switchTour(); 
                break;
        }
        historique.addPasse(c);
    }

    /*
    ############################# Creation des cartes #############################
    */

    Carte creerCodex() {
        // Créer le codex à partir du tableau de cartes passé en argument
        Carte codex = this.cartes[this.cartes.length - 1];

        Carte[] cartes = new Carte[this.cartes.length - 1];
        for (int i = 0; i < this.cartes.length - 1; i++) {
            cartes[i] = this.cartes[i];
            cartes[i].setIndex(i);
        }
        this.cartes = cartes;

        codex.setIndex(cartes[cartes.length - 1].getColor()); 
        Carte result = (Carte) codex.clone();
        return result;
    }

    public Carte[] shuffle(Carte[] c) {
        boolean seed = Configuration.getFixedSeed();
        // Mélange le tableau de cartes passé en argument
        Carte[] cartes_res = new Carte[c.length];
        List<Integer> range = IntStream.rangeClosed(0, c.length - 1).boxed().collect(Collectors.toList());
        if (seed){
            r.setSeed(15);
            Collections.shuffle(range, r);
        }
        else{
            Collections.shuffle(range);
        }
        for (int i = 0; i < c.length; i++) {
            cartes_res[i] = c[range.get(i)];
        }
        return cartes_res;
    }

    void CreerCartes() {
        int couleur = 5;
        int pos = 0;

        Carte[] cartes = new Carte[16];
        for (int symbole = 1; symbole <= 4; symbole++) {
            couleur--;
            for (int valeur = 4; valeur > 0; valeur--) {
                cartes[pos] = new Carte(symbole, couleur, valeur, 0, true);
                // System.out.println(cartes[pos].getType() + " = " + cartes[pos].toString());
                pos++;
                couleur--;
                if (couleur == 0)
                    couleur = 4;
            }
        }

        this.cartes = shuffle(cartes);
    }

    void supprimeDejaUtilisees() {
        // Supprime la carte passée en argument du tableau de cartes
        Carte[] cartes_res = new Carte[cartes.length - 3];
        int pos = 0;
        for (int i = 0; i < cartes.length; i++) {
            if (cartes[i] != null) {
                cartes_res[pos] = cartes[i];
                pos++;
            }
        }
        cartes = cartes_res;
    }

    Carte[] creerMain() {
        // Créer et retourner une main de 3 cartes
        boolean seed = Configuration.getFixedSeed();
        Carte[] main = new Carte[3];
        int index, ndx = 0;
        for (int i = 0; i < 3; i++) {
            if (seed){
                index = r.nextInt(cartes.length);
                while (cartes[index] == null)
                    index = r.nextInt(cartes.length);
            }
            else{
                index = (int) (Math.random() * cartes.length);
                while (cartes[index] == null)
                    index = (int) (Math.random() * cartes.length);
            }
            main[i] = cartes[index];
            main[i].setIndex(ndx);
            ndx++;
            cartes[index] = null;
        }
        supprimeDejaUtilisees();

        return main;
    }
}