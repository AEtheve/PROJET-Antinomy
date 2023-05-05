package Controleur;

import Vue.InterfaceUtilisateur;
import Modele.Jeu;
import Modele.Coup;
import Modele.Carte;
import Modele.Compteur;

public class ControleurJoueur {
    private Jeu j;
    int state;

    public static final int STARTGAME = 0; // TODO: FAIRE PILE OU FACE
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
                state = (state == WAITPLAYER1MOVE) ? WAITPLAYER2SELECT : WAITPLAYER1SELECT;
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
            throw new IllegalArgumentException("Coup invalide");
        j.execCoup(coup);
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
            // TODO: Choisir la direction du swap
            coup = new Coup(Coup.SWAP_DROIT);
            j.execCoup(coup);
            
        }
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
            default:
                System.out.println("Etat non reconnu");
        }
    }

    public void placeSceptre(int index){
        j.getDeck().setSceptre(j.getTour(), index);
        j.switchTour();
        state++;
    }

    public int getState(){
        return state;
    }
}