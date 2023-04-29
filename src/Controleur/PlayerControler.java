package Controleur;

import Vue.CollecteurEvenements;
// import Vue.InterfaceUtilisateur;
import Modele.Jeu;
// import Structures.Coords;

public class PlayerControler implements CollecteurEvenements {
    Jeu j;
    /* InterfaceUtilisateur vue; */

    public PlayerControler(Jeu j) {
        this.j = j;
    }

    @Override
    public void onClick(int x, int y) {
        
    }

    @Override
    public void onKeyPress(int keyCode) {
        
    }

    boolean testFin(int x, int y) {
        return false;
    }

    /* public void ajouteInterfaceUtilisateur(InterfaceUtilisateur v) {
		vue = v;
	} */


    
}