package Controleur;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Modele.Carte;

public class AdaptateurSouris extends MouseAdapter {
    private Carte carte;
    private ControleurJoueur ctrl;
    String type;
    
    public AdaptateurSouris(Carte carte, ControleurJoueur c, String type) {
        this.carte = carte;
        this.ctrl = c;
        this.type = type;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // ctrl.toucheClavier(carte.getIndex());
        switch (type) {
            case "Plateau":
                ctrl.clicPlateau(carte.getIndex());
                break;
            case "Main1":
                ctrl.clicMain1(carte.getIndex());
                break;
            case "Main2":
                ctrl.clicMain2(carte.getIndex());
                break;
            default:
                break;
        }
    }
    

}