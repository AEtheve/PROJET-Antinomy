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
        switch (type) {
            case "Continuum":
                ctrl.clicContinuum(carte.getIndex());
                break;
            case "Main":
                ctrl.clicMain(carte.getIndex());
                break;
            default:
                break;
        }
    }
    
    public void setCarte(Carte carte) {
        this.carte = carte;
    }

}