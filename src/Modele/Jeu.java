package Modele;

import java.util.stream.IntStream;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import Global.Configuration;

public class Jeu {
    private static Jeu instance;
    final public static Boolean JOUEUR_1 = true;
    final public static Boolean JOUEUR_2 = false;

    public static Jeu getInstance() {
        if (instance == null)
            instance = new Jeu();
        return instance;
    }

    private Deck deck;
    private Carte[] cartes;
    private Main J1, J2;
    private Boolean tour; // true = tour du J1
    private Historique historique;
    Random r = new Random();

    public Jeu() {
        CreerCartes();
        J1 = new Main(creerMain());
        J2 = new Main(creerMain());
        Carte codex = creerCodex();

        Compteur.getInstance();
        tour = JOUEUR_1;

        deck = new Deck(cartes, codex);
        historique = new Historique();
    }

    public Deck getDeck() {
        return this.deck;
    }

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
        Carte result = new Carte(codex.getSymbol(),codex.getIndex(),codex.getValue(),codex.getIndex(),false);
        return result;
    }

    public Carte[] shuffle(Carte[] c) {
        boolean seed = Configuration.getFixedSeed();
        // Mélange le tableau de cartes passé en argument
        Carte[] cartes_res = new Carte[c.length];
        List<Integer> range = IntStream.rangeClosed(0, c.length - 1).boxed().collect(Collectors.toList());
        if (seed){
            r.setSeed(1);
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

    public Boolean getTour() {
        return tour;
    }

    public void switchTour() {
        tour = !tour;
    }

    public Carte[] getMain(Boolean joueur) {
        if (joueur)
            return J1.getMain();
        return J2.getMain();
    }

    public ArrayList<Carte> getCartesPossibles(Carte c) {
        Carte[] continuum = deck.getContinuum();
        ArrayList<Carte> cartesPossibles = new ArrayList<Carte>();
        int j, k;
        for (j = 0; j < continuum.length; j++) {
            if (tour) {
                k = deck.getSceptre(tour);
                if (continuum[j].getIndex() < k) {
                    if (continuum[j].getColor() == c.getColor() || continuum[j].getSymbol() == c.getSymbol()) {
                        cartesPossibles.add(continuum[j]);
                    }
                }
                if (k + c.getValue() == continuum[j].getIndex()) {
                    cartesPossibles.add(continuum[k + c.getValue()]);
                }
            } else {
                k = deck.getSceptre(tour);
                if (continuum[j].getIndex() > k) {
                    if (continuum[j].getColor() == c.getColor() || continuum[j].getSymbol() == c.getSymbol()) {
                        cartesPossibles.add(continuum[j]);
                    }
                }
                if (k - c.getValue() == continuum[j].getIndex()) {
                    cartesPossibles.add(continuum[k - c.getValue()]);
                }
            }

        }
        return cartesPossibles;
    }

    public int[] getIndexCartePossible(Carte[] cartesPossibles) {
        int i = 0;
        for (int j = 0; j < cartesPossibles.length; j++) {
            if (cartesPossibles[j] != null) {
                i++;
            }
        }
        int[] index = new int[i];
        i = 0;
        for (int j = 0; j < cartesPossibles.length; j++) {
            if (cartesPossibles[j] != null) {
                index[i] = cartesPossibles[j].getIndex() + 1;
                i++;
            }
        }
        return index;
    }

    public void execCoup(Coup c) {
        // Coup c_prec = Historique.getInstance().getCoupPrec();
        // if (c_prec != null && c_prec.getType() != Coup.SWAP_DROIT && c_prec.getType()
        // != Coup.SWAP_GAUCHE) {
        // if (!c.estCoupValide(this))
        // throw new IllegalArgumentException("Coup invalide");
        // } // TODO
        switch (c.getType()) {
            case Coup.ECHANGE:
            case Coup.ECHANGE_SWAP:
                execEchange(c);
                break;
            case Coup.SWAP_DROIT:
            case Coup.SWAP_GAUCHE:
                execSwap(c);
                break;
            case Coup.SCEPTRE:
                if (c.estCoupValide(this)) {
                    execSceptre(c);
                }
                break;
            default:
                throw new IllegalArgumentException("Type de coup invalide");
        }
        historique.ajouterCoup(c);
        if (verifDuel()) {
            CLheureDuDuDuDuel();
        }
    }

    public void execCoupHistorique(Coup c) {
        switch (c.getType()) {
            case Coup.ECHANGE:
            case Coup.ECHANGE_SWAP:
                execEchange(c);
                break;
            case Coup.SWAP_DROIT:
            case Coup.SWAP_GAUCHE:
                execSwap(c);
                break;
            case Coup.SCEPTRE:
                execSceptre(c);
                break;
            default:
                throw new IllegalArgumentException("Type de coup invalide");
        }
        if (verifDuel()) {
            CLheureDuDuDuDuel();
        }
    }

    void CLheureDuDuDuDuel() {
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

        if (scoreJ1 > scoreJ2) {
            Compteur.getInstance().Vol(JOUEUR_1);
            System.out.println("Joueur 1 gagne le duel");
        } else if (scoreJ1 < scoreJ2) {
            Compteur.getInstance().Vol(JOUEUR_2);
            System.out.println("Joueur 2 gagne le duel");
        } else {
            System.out.println("Bataille !");
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
                } else if (c1.getValue() < c2.getValue()) {
                    score--;
                }
            } else if (c1.getColor() != deck.getCodex().getIndex()) {
                score++;
            } else if (c2.getColor() != deck.getCodex().getIndex()) {
                score--;
            }
        }

        if (score > 0) {
            Compteur.getInstance().Vol(JOUEUR_1);
            System.out.println("Joueur 1 gagne la bataille");
        } else if (score < 0) {
            Compteur.getInstance().Vol(JOUEUR_2);
            System.out.println("Joueur 2 gagne la bataille");
        } else {
            System.out.println("Egalité");
        }
    }

    public void execEchange(Coup c) {
        int ndx;
        Carte carte = null;
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
        Carte[] continuum = deck.getContinuum();
        int ndx_continuum;
        for (int i = 0; i < continuum.length; i++) {
            if (continuum[i].getIndex() == c.getCarteContinuum()) {
                ndx_continuum = continuum[i].getIndex();
                continuum[i].setIndex(carte.getIndex());
                if (tour) {
                    J1.setCarte(continuum[i], ndx);
                } else {
                    J2.setCarte(continuum[i], ndx);
                }
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
                    if (tour) {
                        coup = new Coup(Coup.ECHANGE_SWAP, J1.getCarte(j).getIndex(), continuum[i].getIndex());
                    } else {
                        coup = new Coup(Coup.ECHANGE_SWAP, J2.getCarte(j).getIndex(), continuum[i].getIndex());
                    }
                    j++;
                    execCoup(coup);
                }
            } else if (c.getType() == Coup.SWAP_GAUCHE) {
                if (continuum[i].getIndex() == pos_sc - 1 || continuum[i].getIndex() == pos_sc - 2
                        || continuum[i].getIndex() == pos_sc - 3) {
                    if (tour) {
                        coup = new Coup(Coup.ECHANGE_SWAP, J1.getCarte(j).getIndex(), continuum[i].getIndex());
                    } else {
                        coup = new Coup(Coup.ECHANGE_SWAP, J2.getCarte(j).getIndex(), continuum[i].getIndex());
                    }
                    j++;
                    execCoup(coup);
                }
            }
        }
    }

    public void execSceptre(Coup c) {
        deck.setSceptre(tour, c.getCarteContinuum());
        switchTour();
    }

    public boolean verifParadoxe() {
        Carte[] main = (tour) ? J1.getMain() : J2.getMain();
        int codexIndex = deck.getCodex().getIndex();
        if (main[0].getColor() == codexIndex || main[1].getColor() == codexIndex || main[2].getColor() == codexIndex) {
            return false;
        }

        if (main[0].getColor() == main[1].getColor() && main[0].getColor() == main[2].getColor()) {
            System.out.println("Paradoxe de couleur");
            return true;
        }
        if (main[0].getSymbol() == main[1].getSymbol() && main[0].getSymbol() == main[2].getSymbol()) {
            System.out.println("Paradoxe de symbole");
            return true;
        }
        if (main[0].getValue() == main[1].getValue() && main[0].getValue() == main[2].getValue()) {
            System.out.println("Paradoxe de valeur");
            return true;
        }
        return false;
    }

    public void prochainCodex() {
        deck.prochainCodex();
    }

    public int[] getSceptrePossibleInit() {
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

    public boolean verifDuel() {
        // System.out.println("Pos sceptre J1 : " + deck.getSceptre(JOUEUR_1) + " Pos
        // sceptre J2 : " + deck.getSceptre(JOUEUR_2));
        return deck.getSceptre(JOUEUR_1) == deck.getSceptre(JOUEUR_2);
    }

    public void rejouer() {
        CreerCartes();
        J1 = new Main(creerMain());
        J2 = new Main(creerMain());
        Carte codex = creerCodex();

        Compteur.getInstance();
        tour = JOUEUR_1;

        deck = new Deck(cartes, codex);
    }

    public Historique getHistorique() {
        return historique;
    }

    public void annulerCoup(Coup c) {
        switch (c.getType()) {
            case Coup.ECHANGE:
                System.out.println("Annuler Echange TODO");
                annulerEchange(c);
                break;
            case Coup.ECHANGE_SWAP:
                // annulerEchangeSwap(c); TODO
                System.out.println("Annuler Echange Swap TODO");
                break;
            case Coup.SWAP_DROIT:
            case Coup.SWAP_GAUCHE:
                // annulerSwap(c); TODO
                System.out.println("Annuler Swap TODO");
                break;
            case Coup.SCEPTRE:
                annulerSceptre(c);
                break;
            default:
                throw new IllegalArgumentException("Type de coup invalide");
        }
    }

    public void annulerSceptre(Coup c) {
        switchTour();
        deck.setSceptre(tour, -1);
    }

    public void annulerEchange(Coup c) {
        switchTour();
        Carte[] continuum = deck.getContinuum();
        int ndx;
        Carte carte = null;
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
        int ndx_continuum;
        for (int i = 0; i < continuum.length; i++) {
            if (continuum[i].getIndex() == c.getCarteContinuum()) {
                ndx_continuum = continuum[i].getIndex();
                continuum[i].setIndex(carte.getIndex());
                if (tour) {
                    J1.setCarte(continuum[i], ndx);
                } else {
                    J2.setCarte(continuum[i], ndx);
                }
                carte.setIndex(ndx_continuum);
                continuum[i] = carte;
                break;
            }
        }

        Coup c_prec = historique.getCoupPrec(2);
        deck.setSceptre(tour, c_prec.getCarteContinuum());
    }

    public void refaireCoup(Coup c) {
        switch (c.getType()) {
            case Coup.ECHANGE:
                // refaireEchange(c); TODO
                System.out.println("Refaire Echange TODO");
                break;
            case Coup.ECHANGE_SWAP:
                // refaireEchangeSwap(c); TODO
                System.out.println("Refaire Echange Swap TODO");
                break;
            case Coup.SWAP_DROIT:
            case Coup.SWAP_GAUCHE:
                // refaireSwap(c); TODO
                System.out.println("Refaire Swap TODO");
                break;
            case Coup.SCEPTRE:
                refaireSceptre(c);
                break;
            default:
                throw new IllegalArgumentException("Type de coup invalide");
        }
    }

    public void refaireSceptre(Coup c) {
        switchTour();
        Coup c_suiv = historique.getCoupSuiv(1);
        deck.setSceptre(tour, c_suiv.getCarteContinuum());
    }

}