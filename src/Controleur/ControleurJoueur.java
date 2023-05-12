package Controleur;

import Vue.InterfaceUtilisateur;
import Modele.Jeu;
import Modele.Coup;
import Modele.Historique;
import Modele.IA;

import java.util.ArrayList;

import Global.Configuration;
import Modele.Carte;
import Modele.Compteur;
import Modele.Sauvegarde;

public class ControleurJoueur {
    private Jeu j;
    static int state;

    public static final int STARTGAME = 0;
    public static final int WAITPLAYER1SCEPTER = 1; // On attend que le joueur 1 place son sceptre
    public static final int WAITPLAYER2SCEPTER = 2; // On attend que le joueur 2 place son sceptre
    public static final int WAITPLAYER1SELECT = 3; // On attend que le joueur 1 sélectionne une carte
    public static final int WAITPLAYER1MOVE = 4; // On attend que le joueur 1 joue en échangeant une carte
    public static final int WAITPLAYER2SELECT = 5; // On attend que le joueur 2 sélectionne une carte
    public static final int WAITPLAYER2MOVE = 6; // On attend que le joueur 2 joue en échangeant une carte
    public static final int WAITPLAYER1SWAP = 7; // On attend que le joueur 1 choisisse la direction du swap
    public static final int WAITPLAYER2SWAP = 8; // On attend que le joueur 2 choisisse la direction du swap
    public static final int ENDGAME = 9; // On attend que le joueur 1 ou 2 gagne

    InterfaceUtilisateur vue;
    Carte CarteMainAJouer;
    ArrayList<Carte> CartesPossibles;
    boolean IAActive = false;
    IA joueurIA;
    ActionJeuIA actionJeuIA;

    public ControleurJoueur(Jeu j) {
        this.j = j;
        // int starter = (int) Math.round(Math.random());
        // if (starter == 0)
        // state = WAITPLAYER1SCEPTER;
        // else
        // state = WAITPLAYER2SCEPTER;
        state = WAITPLAYER1SCEPTER;
        CarteMainAJouer = null;
    }

    void joue(Coup cp) {
        if (cp != null) {
            if (cp.getSceptreByte() == 1) {
                j.execCoup(cp);
                if (state == WAITPLAYER1SCEPTER)
                    state = WAITPLAYER2SCEPTER;
                else if (state == WAITPLAYER2SCEPTER)
                    state = WAITPLAYER1SELECT;
            } else {
                if (cp.getType() == Coup.SWAP_GAUCHE || cp.getType() == Coup.SWAP_DROIT) {
                    j.execCoup(cp);
                    System.out.println("Swap effectué");
                    if (IAActive) {
                        System.out.println("Swap effectué par l'IA");
                        state = WAITPLAYER1SELECT;
                        j.switchTour();
                    }
                } else {
                    Carte carte_main = j.getMain(j.getTour())[cp.getCarteMain()];
                    Carte carte_continuum = j.getDeck().getContinuum()[cp.getCarteContinuum()];
                    JouerCoup(carte_main, carte_continuum);
                    if (IAActive) {
                        if (state == WAITPLAYER2SELECT) {
                            state = WAITPLAYER1SELECT;
                        }
                    }
                }

            }
        } else {
            Configuration.alerte("Coup null fourni, probablement un bug dans l'IA");
        }
    }

    public void basculeIA() {
        if (joueurIA == null) {
            joueurIA = IA.nouvelle(j);
            actionJeuIA = new ActionJeuIA(joueurIA, this);
        }

        if (joueurIA != null)
            IAActive = !IAActive;
        vue.changeEtatIA(IAActive);
    }

    public void rejouer() {
        j.rejouer();
        state = WAITPLAYER1SCEPTER;
        vue.miseAJour();
    }

    public Historique getHistorique() {
        return j.getHistorique();
    }

    public void annulerCoup() {
        Coup c = j.getHistorique().annuler();
        if (c != null) {
            j.annulerCoup(c);
            if (state == WAITPLAYER2SCEPTER) {
                state = WAITPLAYER1SCEPTER;
            } else if (state == WAITPLAYER1SELECT) {
                state = WAITPLAYER2SCEPTER;
            } else if (state == WAITPLAYER2SELECT) {
                state = WAITPLAYER1SELECT;
            }

            vue.miseAJour();
        }
    }

    public void refaireCoup() {
        Coup c = j.getHistorique().refaire();
        if (c != null) {
            j.execCoupHistorique(c);
            if (state == WAITPLAYER1SCEPTER) {
                state = WAITPLAYER2SCEPTER;
            } else if (state == WAITPLAYER2SCEPTER) {
                state = WAITPLAYER1SELECT;
            }

            vue.miseAJour();
        }
    }

}