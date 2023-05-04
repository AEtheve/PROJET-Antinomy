package Controleur;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Modele.Carte;

public class AdaptateurSouris extends MouseAdapter {
    private Carte carte;
    private ControleurJoueur ctrl;
    
    public AdaptateurSouris(Carte carte, ControleurJoueur c) {
        this.carte = carte;
        this.ctrl = c;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        ctrl.SelectCarte(carte);
    }

}