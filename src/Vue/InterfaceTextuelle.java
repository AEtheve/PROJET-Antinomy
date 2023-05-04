package Vue;

import Modele.Carte;
import Modele.Compteur;
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
        System.out.println("J1: Saisir la position du sceptre (1, 2, 3, 4, 5, 6, 7, 8, 9)");
        System.out.print("Commande > ");
        ctrl.toucheClavier("placeSceptre_"+s.next());

        System.out.println("J2: Saisir la position du sceptre (1, 2, 3, 4, 5, 6, 7, 8, 9)");
        System.out.print("Commande > ");
        ctrl.toucheClavier("placeSceptre_"+s.next());
        while (true) {

            System.out.println("Entrez une commande :");
            System.out.println(" Saisir le numéro d'une carte pour la sélectionner (1, 2, 3)");
			System.out.print("Commande > ");
			ctrl.toucheClavier("selectmain_"+s.next());
            
            System.out.println(" Saisir le numéro d'une carte dans le plateau (1, 2, 3, 4, 5, 6, 7, 8, 9)");
            ctrl.toucheClavier("selectplateau_"+s.next());
            
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
        System.out.println("Position sceptre joueur 1 : " + jeu.getDeck().getSceptre(Jeu.JOUEUR_1));
        System.out.println("Position sceptre joueur 2 : " + jeu.getDeck().getSceptre(Jeu.JOUEUR_2));

        Compteur compteur = Compteur.getInstance();
        System.out.println("Score joueur 1 : " + compteur.getJ1Points() + "pts");
        System.out.println("Score joueur 2 : " + compteur.getJ2Points() + "pts");

        System.out.println();
        System.out.println("Plateau :\n" + jeu.getDeck().toString());
        System.out.println();

        Carte codex = jeu.getDeck().getCodex();
        System.out.println("Codex : " + Carte.couleurToString(codex.getIndex()));
    }
}