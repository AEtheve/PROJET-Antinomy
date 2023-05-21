package Vue;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Modele.Carte;

import Controleur.ControleurMediateur;

public class AdaptateurSouris extends MouseAdapter {
    private Carte carte;
    private ControleurMediateur ctrl;
    String type;
    boolean enable = true;

    public AdaptateurSouris(Carte carte, ControleurMediateur c, String type) {
        this.carte = carte;
        this.ctrl = c;
        this.type = type;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (type) {
            case "Main":
            case "Continuum":
            if (!enable) return;
                ctrl.clicSouris(carte.getIndex(), type);
                break;
            case "Background":
                ctrl.resetSelection();
                break;
            case "Retour":
                ctrl.annulerCoup();
                break;
            case "Apres":
                ctrl.refaireCoup();
                break;
            default:
                System.out.println("Type " + type + " non reconnu");
                break;
        }
    }

    public void setCarte(Carte carte) {
        this.carte = carte;
    }

    public void switchEnable() {
        enable = !enable;
    }

    // A Enlever
    public void setEnable(boolean enable) {
        this.enable = enable;
    }

}