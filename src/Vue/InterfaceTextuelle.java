package Vue;

import Modele.Carte;
import Modele.Compteur;
import Modele.Coup;
import Modele.Jeu;
import Modele.Main;

import java.util.Arrays;
import java.util.Scanner;

import Controleur.ControleurMediateur;

public class InterfaceTextuelle implements InterfaceUtilisateur{
    Jeu jeu;
    ControleurMediateur ctrl;

    public InterfaceTextuelle(Jeu jeu, ControleurMediateur ctrl){
        this.jeu = jeu;
        this.ctrl = ctrl;
        boucle();
    }

    public static void demarrer(Jeu jeu, ControleurMediateur ctrl){
		new InterfaceTextuelle(jeu, ctrl);        
	}

    void boucle(){
        miseAJour();
        int entreeInt;
        String type = "";
        while(true){
            switch(ctrl.getState()){
                case ControleurMediateur.STARTGAME:
                case ControleurMediateur.WAITSCEPTRE:{
                    //TODO : a modifer pour les 2 joueurs
                    int [] PositionSceptrePossible = jeu.getSceptrePossibleInit();
                    String str = Arrays.toString(PositionSceptrePossible);
                    System.out.println("J"+ctrl.getJoueurCourant()+": Saisir la position du sceptre (" + str + ")");
                    entreeInt = inputIntFromList(PositionSceptrePossible);
                    break;
                }
                case ControleurMediateur.WAITSELECT:{
                    // TODO : a modifier pour les 2 joueurs
                    System.out.println("J"+ctrl.getJoueurCourant()+": Saisir le numéro d'une carte pour la sélectionner (1, 2, 3)");
                    entreeInt = inputIntFromList(new int[]{1, 2, 3}) - 1;
                    type = "Main";
                    break;
                }
                case ControleurMediateur.WAITMOVE:{
                    // TODO : a modifier pour les 2 joueurs
                    int [] PositionCartePossible = jeu.getIndexCartePossible(ctrl.getCartesPossibles());
                    String str = Arrays.toString(PositionCartePossible);
                    System.out.println("J"+ctrl.getJoueurCourant()+": Saisir le numéro d'une carte dans le continuum (" + str + ")");
                    entreeInt = inputIntFromList(PositionCartePossible);
                    type = "Continuum";
                    break;
                }
                case ControleurMediateur.WAITSWAP:{
                    // TODO : a modifier pour les 2 joueurs
                    System.out.println("J"+ctrl.getJoueurCourant()+": Choisir la direction du swap (1: gauche, 2: droit)");
                    entreeInt = inputIntFromList(new int[]{1, 2}) - 1;
                    type = "Continuum";
                    break;
                }
                default:{
                    System.out.println("Etat non défini : " + ctrl.getState());
                    return;
                }
            }
            ctrl.toucheClavier(entreeInt, type);
            miseAJour();
        }
    }

    private static int inputIntFromList(int[] liste){
        Scanner s = new Scanner(System.in);
        int res;
        while(true){
            while(!s.hasNextInt()){
                System.out.print("Commande > ");
            }
            res = s.nextInt();
            if (liste==null) return res;
            for (int i=0; i<liste.length; i++){
                if (res == liste[i]){
                    return res;
                }
            }
            System.out.print("Erreur entrée invalide\nCommande >");
        }
    }

    private static int inputInt(){
        Scanner s = new Scanner(System.in);
        while(true){
            while(!s.hasNextInt()){
                System.out.print("Commande > ");
            }
            return s.nextInt();
        }
    }



    public void toggleFullscreen(){
        System.out.println("Pas de plein écran en mode textuel");
    }

    @Override
    public void miseAJour(){
        afficheInterface();
    }

    @Override
    public void animeCoup(Coup coup) {
        throw new UnsupportedOperationException("Unimplemented method 'animeCoup'");
    }

    @Override
    public void setCartesPossibles(Carte[] cartesPossibles) {
        throw new UnsupportedOperationException("Unimplemented method 'setCartesPossibles'");
    }

    @Override
    public void setSelectCarteMain1(int index) {
        throw new UnsupportedOperationException("Unimplemented method 'setSelectCarteMain1'");
    }

    @Override
    public void setSelectCarteMain2(int index) {
        throw new UnsupportedOperationException("Unimplemented method 'setSelectCarteMain2'");
    }

    @Override
    public void setGagnant(Boolean gagnant) {
        throw new UnsupportedOperationException("Unimplemented method 'setGagnant'");
    }

    @Override
    public void changeEtatIA(boolean b) {
        throw new UnsupportedOperationException("Unimplemented method 'changeEtatIA'");
    }

    public void afficheInterface(){
        System.out.println("Tour de " + (jeu.getTour() ? "Joueur 1" : "Joueur 2"));
        Main main_joueur1 = new Main(jeu.getMain(Jeu.JOUEUR_1));
        Main main_joueur2 = new Main(jeu.getMain(Jeu.JOUEUR_2));

        System.out.println("Main joueur 1 : " + main_joueur1);
        System.out.println("Main joueur 2 : " + main_joueur2);
        System.out.println("Position sceptre joueur 1 : " + jeu.getDeck().getSceptre(Jeu.JOUEUR_1));
        System.out.println("Position sceptre joueur 2 : " + jeu.getDeck().getSceptre(Jeu.JOUEUR_2));

        Compteur compteur = Compteur.getInstance();
        System.out.println("Score joueur 1 : " + compteur.getJ1Points() + "pts");
        System.out.println("Score joueur 2 : " + compteur.getJ2Points() + "pts");

        System.out.println();
        System.out.println("Continuum :\n" + jeu.getDeck().toString());
        System.out.println();

        Carte codex = jeu.getDeck().getCodex();
        System.out.println("Codex : " + Carte.couleurToString(codex.getIndex()) + "\u001B[0m");
    }
}