package Vue;

import Modele.Carte;
import Modele.Coup;
import Modele.Jeu;
import Modele.Main;

import java.util.Scanner;

import Controleur.ControleurJoueur;

public class InterfaceTextuelle implements InterfaceUtilisateur{
    Jeu jeu;
    ControleurJoueur ctrl;

    public InterfaceTextuelle(Jeu jeu, ControleurJoueur ctrl){
        this.jeu = jeu;
        this.ctrl = ctrl;
    }

    public static void demarrer(Jeu j, ControleurJoueur ctrl){
		InterfaceTextuelle vue = new InterfaceTextuelle(j, ctrl);
        ctrl.ajouteInterfaceUtilisateur(vue);
        vue.miseAJour();

        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.println("Entrez une commande :");
            System.out.println(" Saisir le numéro d'une carte pour la sélectionner (1, 2, 3)");
			System.out.print("Commande > ");
			ctrl.toucheClavier("jouer_"+s.next());
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

    public void afficheInterface(){
        System.out.println("Tour de " + (jeu.getTour() ? "Joueur 1" : "Joueur 2"));
        Main main_joueur1 = new Main(jeu.getMain(Jeu.JOUEUR_1));
        Main main_joueur2 = new Main(jeu.getMain(Jeu.JOUEUR_2));

        System.out.println("Main joueur 1 : " + main_joueur1);
        System.out.println("Main joueur 2 : " + main_joueur2);

        System.out.println();
        System.out.println("Plateau :\n" + jeu.getDeck().toString());
        System.out.println();

        Carte codex = jeu.getDeck().getCodex();
        System.out.println("Codex : " + Carte.couleurToString(codex.getIndex()));
    }
}