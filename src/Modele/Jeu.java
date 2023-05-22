package Modele;

import java.util.stream.IntStream;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import Global.Configuration;
import Structures.Couple;
import Structures.Iterateur;
import Structures.Sequence;
import Vue.InterfaceUtilisateur;


public class Jeu {
    final public static Boolean JOUEUR_1 = true;
    final public static Boolean JOUEUR_2 = false;

    protected Deck deck;
    protected Main J1, J2;
    protected Boolean tour; // true = tour du J1
    protected Carte[] cartes;
    protected Boolean swap = false;
    protected static Boolean initJoueurCommence = JOUEUR_1;
    protected Boolean fini = false;

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

    public Boolean estFini(){
        return fini;
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

    public Sequence<Couple<Coup, Coup>> getCoupsPossibles() {

        if (deck.getSceptre(JOUEUR_2) == -1){
            int[] possibles = getSceptrePossibleInit();
            Sequence<Couple<Coup, Coup>> possibles2 = Configuration.nouvelleSequence();
            for (int i = 0; i < possibles.length; i++) {
                possibles2.insereTete(new Couple<Coup, Coup>(new Coup(Coup.SCEPTRE, possibles[i]), null));
            }
            return possibles2;
        } 
        // Lister les cartes possibles
        Sequence<Couple<Carte, Carte>> echange_possibles = Configuration.nouvelleSequence();
        Carte main[] = getMain(getTour());

		// Sequence<Couple<Carte, Integer>> cartes_possibles_index = Configuration.nouvelleSequence();
		for (Carte carte : main) {
			Carte[] cartes_possibles = getCartesPossibles(carte);
			for (Carte carte_possible : cartes_possibles) {
				if (carte_possible!=null) {
					echange_possibles.insereTete(new Couple<Carte, Carte>(carte, carte_possible));
				}
			}
		}
        // Pour chaque carte, verifier si le coup entraine un paradoxe
        // Si oui, renvoyer les coups ECHANGE & SWAP a droite et a gauche si possible
        
        Sequence<Couple<Coup, Coup>> possibles = Configuration.nouvelleSequence();
        Iterateur<Couple<Carte, Carte>> echange_it = echange_possibles.iterateur();
        while(echange_it.aProchain()) {
            Couple<Carte, Carte> echange = echange_it.prochain();
            if(verifParadoxe(echange)) {
                if(getDeck().getSceptre(getTour())>=3) {
                    possibles.insereTete(
                        new Couple<Coup, Coup>(
                            new Coup(Coup.ECHANGE, echange.first.getIndex(), echange.second.getIndex()),
                            new Coup(Coup.SWAP_GAUCHE)
                        )
                    );
                }
                else if(getDeck().getSceptre(getTour())<=5) {
                    possibles.insereTete(
                        new Couple<Coup, Coup>(
                            new Coup(Coup.ECHANGE, echange.first.getIndex(), echange.second.getIndex()),
                            new Coup(Coup.SWAP_DROIT)
                        )
                    );
                }
            } else {
                possibles.insereTete(
                    new Couple<Coup, Coup>(
                        new Coup(Coup.ECHANGE, echange.first.getIndex(), echange.second.getIndex()),
                        null
                    )
                );
            }
        }
        return possibles;
    }

    public boolean verifParadoxe(Couple<Carte, Carte> coupleEchange) {
        int codexIndex = deck.getCodex().getIndex();
        if (coupleEchange.second.getColor() == codexIndex) {
            return false;
        }

        Carte[] main = (tour) ? J1.getMain().clone() : J2.getMain().clone();

        main[coupleEchange.first.getIndex()] = coupleEchange.second;

        if (main[0].getColor() == codexIndex || main[1].getColor() == codexIndex || main[2].getColor() == codexIndex) {
            return false;
        }

        if (main[0].getColor() == main[1].getColor() && main[0].getColor() == main[2].getColor()) {
            // Configuration.info("Paradoxe de couleur");
            return true;
        }

        if (main[0].getSymbol() == main[1].getSymbol() && main[0].getSymbol() == main[2].getSymbol()) {
            // Configuration.info("Paradoxe de symbole");
            return true;
        }

        if (main[0].getValue() == main[1].getValue() && main[0].getValue() == main[2].getValue()) {
            // Configuration.info("Paradoxe de valeur");
            return true;
        }
        
        return false;
    }

    public static Boolean getInitJoueurCommence() {
        return initJoueurCommence;
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

    public static void setInitJoueurCommence(Boolean joueur) {
        initJoueurCommence = joueur;
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

    public void restaure(Carte[] continuum,Main main1, Main main2,Carte codex, int sceptre1, int sceptre2, Boolean tour, int scoreJ1, int scoreJ2){
        //TODO : A modifier apres MAJ d'Esteban et d'Alexis
        this.cartes = continuum;
        J1 = main1;
        J2 = main2;
        deck.setContinuum(cartes);


        Compteur.getInstance();
        Compteur.getInstance().setScore(JOUEUR_1, scoreJ1);
        Compteur.getInstance().setScore(JOUEUR_2, scoreJ2);

        getDeck().setSceptre(JOUEUR_1, sceptre1);
        getDeck().setSceptre(JOUEUR_2, sceptre2);
        this.tour = tour;
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


    protected void metAJour() {
        if (interfaceUtilisateur != null) {
            interfaceUtilisateur.miseAJour();
        }
    }

    protected void Paradoxe() {
        if(verifParadoxe() && deck.getSceptre(JOUEUR_1) != -1 && deck.getSceptre(JOUEUR_2) != -1){
            setSwap(true);
            int res = Compteur.getInstance().Incremente(getTour());
            // TODO : Resultat
            if (res == 0) {
                Configuration.info("Joueur 1 gagne");
                fini = true;
            } else if (res == 1) {
                Configuration.info("Joueur 2 gagne");
                fini = true;
            }
            prochainCodex();
            // Commande c = historique.getCommandePrec();
            // historique.addPasse(new Commande(c.getCoup(), c.getPosSeptre(), getDeck().getCodex().getIndex(), tour));
        } else {
            switchTour();
        }
    }

    protected void execEchange(Coup c) {
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

    protected void execSwap(Coup c) {
        Carte[] continuum = deck.getContinuum();
        continuum = shuffle(continuum);
        int pos_sc = deck.getSceptre(tour);
        int j = 0;
        Coup coup;
        for (int i = 0; i < continuum.length; i++) {
            if (c.getType() == Coup.SWAP_DROIT) {
                // if (continuum[i].getIndex() == pos_sc + 1 || continuum[i].getIndex() == pos_sc + 2
                //         || continuum[i].getIndex() == pos_sc + 3) {

                // Boolean cond;
                // if (getTour() == JOUEUR_1){
                //     cond = (continuum[i].getIndex() == pos_sc + 1 || continuum[i].getIndex() == pos_sc + 2
                //             || continuum[i].getIndex() == pos_sc + 3);
                // } else {
                //     cond = (continuum[i].getIndex() == pos_sc - 1 || continuum[i].getIndex() == pos_sc - 2
                //             || continuum[i].getIndex() == pos_sc - 3);
                // }
                if (continuum[i].getIndex() == pos_sc + 1 || continuum[i].getIndex() == pos_sc + 2
                            || continuum[i].getIndex() == pos_sc + 3){
                    int ndx = (tour) ? J1.getCarte(j).getIndex() : J2.getCarte(j).getIndex();
                    coup = new Coup(Coup.ECHANGE_SWAP, ndx, continuum[i].getIndex());
                    // System.out.println(coup.toString());
                    // historique.ajouterHistorique(CreerCommande(coup));
                    if (historique != null){
                        historique.ajouterHistorique(CreerCommande(coup));
                    }
                    //historique.affichePasse();
                    j++;
                    execEchange(coup);
                }
            } else if (c.getType() == Coup.SWAP_GAUCHE) {
                // if (continuum[i].getIndex() == pos_sc - 1 || continuum[i].getIndex() == pos_sc - 2
                //         || continuum[i].getIndex() == pos_sc - 3) {
                if ((continuum[i].getIndex() == pos_sc - 1 || continuum[i].getIndex() == pos_sc - 2
                            || continuum[i].getIndex() == pos_sc - 3)){
                    int ndx = (tour) ? J1.getCarte(j).getIndex() : J2.getCarte(j).getIndex();
                    coup = new Coup(Coup.ECHANGE_SWAP, ndx, continuum[i].getIndex());
                    // System.out.println(coup.toString());
                    // historique.ajouterHistorique(CreerCommande(coup));
                    if (historique != null){
                        historique.ajouterHistorique(CreerCommande(coup));
                    }
                    //historique.affichePasse();
                    j++;
                    execEchange(coup);
                }
            }
        }

    }

    protected void execSceptre(Coup c) {
        deck.setSceptre(tour, c.getCarteContinuum());
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
            //System.out.println("REVERT SWAP REUSSI");
            historique.ajouteFutur(echange_swap);
            historique.afficheFutur();
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
        historique.ajouteFutur(c);

    }

    public void revertSceptre(Commande c){
        System.out.println("Revert sceptre");
        switchTour();
        deck.setSceptre(tour, -1);
        historique.ajouteFutur(c);
    }

    public Commande refaireCoup(){
        if (!historique.peutRefaire()) {
            Configuration.alerte("Impossible de refaire le coup");
            return null;
        }
        Commande c = historique.refaire();
        switch(c.getCoup().getType()){
            case Coup.SWAP_DROIT:
            case Coup.SWAP_GAUCHE:
                //historique.addFutur(c);
                historique.ajoutePasse(c);
                execSwap(c.getCoup());
                switchTour();
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
        historique.ajoutePasse(c);

        return c;
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

    public Carte[][] getMainPossibles(Carte[] c) {
        Carte[][] main_possibles = new Carte[6][3];
        for(int i=0; i<6; i++) {
            for(int x=0; x<3; x++) {
                for(int y=0; y<3; y++) {
                    if(y==x) continue;
                    for(int z=0; z<3; z++) {
                        if(z==y || z==x) continue;
                        Carte[] main = {c[x], c[y], c[z]};
                        main_possibles[i] = main;
                    }
                }
            }
        }
        return main_possibles;
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