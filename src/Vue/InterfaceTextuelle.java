package Vue;

import Modele.Coup;
import Modele.Jeu;
import Modele.Main;
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
        System.out.println("INTERFACE TEXTUELLE");
        Main main_joueur1 = new Main(jeu.getMain(Jeu.JOUEUR_1));
        Main main_joueur2 = new Main(jeu.getMain(Jeu.JOUEUR_2));

        System.out.println("Main joueur 1 : " + main_joueur1);
        System.out.println("Main joueur 2 : " + main_joueur2);

        System.out.println("Plateau :\n" + jeu.getDeck().toString());

        System.out.println("Codex : " + jeu.getDeck().getCodex().getIndex());

    }
}