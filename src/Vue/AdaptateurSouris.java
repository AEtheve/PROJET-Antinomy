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
        if (!enable)
            return;
        ctrl.clicSouris(carte.getIndex(), type);
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