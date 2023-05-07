package Controleur;

import Global.Configuration;
import Modele.Coup;
import Modele.IA;
import Structures.Sequence;

public class ActionJeuIA {
    Sequence<Coup> enAttente;
    IA joueurIA;
    ControleurJoueur ctrl;
    ActionJeuIA(IA joueurIA, ControleurJoueur ctrl) {
        this.joueurIA = joueurIA;
        this.ctrl = ctrl;
    }

    public void miseAJour() {
        if ((enAttente == null) || enAttente.estVide()){
			enAttente = joueurIA.elaboreCoups();
        }
		if ((enAttente == null) || enAttente.estVide())
			Configuration.erreur("Bug : l'IA n'a joué aucun coup");
		else
			ctrl.joue(enAttente.extraitTete());
    }

    public void tictac() {
        miseAJour();
	}

}
