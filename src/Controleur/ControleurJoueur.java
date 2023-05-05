package Controleur;

import Vue.InterfaceUtilisateur;
import Modele.Jeu;
import Modele.Coup;
import Modele.Carte;
import Modele.Compteur;

import java.lang.Math;

public class ControleurJoueur {
    private Jeu j;
    int state;

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
    Carte[] CartesPossibles;

    public ControleurJoueur(Jeu j) {
        this.j = j;
        //int starter = (int) Math.round(Math.random());
        // if (starter == 0)
        //     state = WAITPLAYER1SCEPTER;
        // else
        //     state = WAITPLAYER2SCEPTER;
        state = WAITPLAYER1SCEPTER;
        CarteMainAJouer = null;
    }

    public void SelectCarte(Carte c) {
        if (CarteMainAJouer != null) {
            for (Carte carte : j.getDeck().getPlateau()) {
                if (carte == c) {
                    SelectCartePlateau(c);
                    return;
                }
            }
        }
        for (Carte carte : j.getMain(j.getTour())) {
            if (carte == c) {
                SelectCarteMain(c);
                return;
            }
        }
        if (c != j.getDeck().getCodex()) {
            throw new IllegalArgumentException("Carte non valide");
        }
        return;
    }

    void SelectCarteMain(Carte c) {
        CarteMainAJouer = c;
        if (state == WAITPLAYER1SELECT)
            state = WAITPLAYER1MOVE;
        else if (state == WAITPLAYER2SELECT)
            state = WAITPLAYER2MOVE;
        CartesPossibles = j.getCartesPossibles(c);
    }

    void SelectCartePlateau(Carte c) {
        if (state != WAITPLAYER1MOVE && state != WAITPLAYER2MOVE)
            throw new IllegalArgumentException("Carte non valide");
        for (Carte carte : CartesPossibles) {
            if (carte == c) {
                JouerCoup(CarteMainAJouer, c);
                // state = (state == WAITPLAYER1MOVE) ? WAITPLAYER2SELECT : WAITPLAYER1SELECT;
                if (state == WAITPLAYER1MOVE)
                    state = WAITPLAYER2SELECT;
                else if (state == WAITPLAYER2MOVE)
                    state = WAITPLAYER1SELECT;
                CarteMainAJouer = null;
                return;
            }
        }
    }

    public void ajouteInterfaceUtilisateur(InterfaceUtilisateur v) {
        vue = v;
    }

    public void JouerCoup(Carte cMain, Carte cPlateau) {
        Coup coup = new Coup(Coup.ECHANGE, cMain.getIndex(), cPlateau.getIndex());
        if (!coup.estCoupValide(j))
            System.out.println("Coup invalide");
        j.execCoup(coup);
        vue.animeCoup(coup);
        if (verifParadoxe()) {
            int res = Compteur.getInstance().Incremente(j.getTour());
            j.prochainCodex();
            if(res == 0){
                System.out.println("Joueur 1 gagne");
                //TODO: Fin de partie
            } else if (res == 1){
                System.out.println("Joueur 2 gagne");
                // TODO: Fin de partie
            }
            if(j.getTour() == Jeu.JOUEUR_1)
                state = WAITPLAYER1SWAP;
            else
                state = WAITPLAYER2SWAP;
        }
        j.switchTour();
    }


    public boolean verifParadoxe() {
        return j.verifParadoxe();
    }

    public void toucheClavier(Integer touche) {
        switch(getState()){
            case WAITPLAYER1SCEPTER:
            case WAITPLAYER2SCEPTER:{
                placeSceptre(touche);
                break;
            }
            case WAITPLAYER1SELECT:
            case WAITPLAYER2SELECT:{
                SelectCarte(j.getMain(j.getTour())[touche]);
                break;
            }
            case WAITPLAYER1MOVE:
            case WAITPLAYER2MOVE:{
                SelectCarte(j.getDeck().getPlateau()[touche]);
                vue.miseAJour();
                break;
            }
            case WAITPLAYER1SWAP:
            case WAITPLAYER2SWAP:{
                choixSwap(touche);
                vue.miseAJour();
                break;
            }
            default:
                System.out.println("Etat non reconnu");
        }
    }

    public void clicPlateau(int index){
        System.out.println("Clic plateau");
        if(state == WAITPLAYER1MOVE || state == WAITPLAYER2MOVE){
            SelectCarte(j.getDeck().getPlateau()[index]);
            vue.miseAJour();
        }
        else if(state == WAITPLAYER1SCEPTER || state == WAITPLAYER2SCEPTER){
            placeSceptre(index);
            vue.miseAJour();
        }
    }
    public void clicMain1(int index){
        System.out.println("Clic main 1");
        if(state == WAITPLAYER1SELECT){
            SelectCarte(j.getMain(j.getTour())[index]);
            vue.miseAJour();
        }
    }
    
    public void clicMain2(int index){
        System.out.println("Clic main 2");
        if(state == WAITPLAYER2SELECT){
            SelectCarte(j.getMain(j.getTour())[index]);
            vue.miseAJour();
        }
    }

    void choixSwap(int choix){
        Coup coup;
        if(choix==1) 
            coup = new Coup(Coup.SWAP_GAUCHE); 
        else 
            coup = new Coup(Coup.SWAP_DROIT);
        j.execCoup(coup);
        vue.animeCoup(coup);

        j.switchTour();
        if(j.getTour() == Jeu.JOUEUR_1)
            state = WAITPLAYER1SELECT;
        else
            state = WAITPLAYER2SELECT;
    }

    public void placeSceptre(int index){
        System.out.println("Sceptre placé en " + index);
        j.getDeck().setSceptre(j.getTour(), index);
        j.switchTour();
        // A modifier
        if (j.getDeck().getSceptre(true)!=-1 && j.getDeck().getSceptre(false)!=-1) state++;
        else if (j.getDeck().getSceptre(true)!=-1) state = WAITPLAYER2SCEPTER;
        else state = WAITPLAYER1SCEPTER;
    }

    public int getState(){
        return state;
    }

    public Carte [] getCartesPossibles(){
        return CartesPossibles;
    }
}