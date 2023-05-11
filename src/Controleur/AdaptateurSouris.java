package Controleur;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Modele.Carte;

public class AdaptateurSouris extends MouseAdapter {
    private Carte carte;
    private ControleurJoueur ctrl;
    String type;
    boolean enable = true;
    
    public AdaptateurSouris(Carte carte, ControleurJoueur c, String type) {
        this.carte = carte;
        this.ctrl = c;
        this.type = type;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!enable)
            return;
        switch (type) {
            case "Continuum":
                ctrl.clicContinuum(carte.getIndex());
                break;
            case "Main":
                ctrl.clicMain(carte.getIndex());
                break;
            case "Retour":
                System.out.println("Retour TODO");
                break;
            case "Apres":
                System.out.println("Apres TODO");
                break;
            default:
                break;
        }
    }
    
    public void setCarte(Carte carte) {
        this.carte = carte;
    }

    public void switchEnable() {
        enable = !enable;
    }

    //A Enlever
    public void setEnable(boolean enable) {
        this.enable = enable;
    }

}