package Vue;

import java.awt.*;
import java.util.HashMap;

import javax.swing.*;

import Controleur.ControleurMediateur;
import Global.Configuration;
import Modele.Carte;

public class Cadran extends JComponent {
    Carte carte;
    int x, y, width, height;
    HashMap<String, Image> imagesCache = new HashMap<String, Image>();
    boolean hover = false;
    boolean selectable = false;
    int tailleY;
    int tailleX;

    int xCarte;
    int yCarte;
    ControleurMediateur ctrl;
    AdaptateurSouris adaptateurSouris;

    public Cadran(ControleurMediateur ctrl, String type, HashMap<String, Image> imagesCache) {
        this.imagesCache = imagesCache;
        this.ctrl = ctrl;
        setLayout(null);
    }

    public void paintComponent(Graphics g) {
        g.drawImage(getImage(), 0, 0, getWidth(), getHeight(), this);
    }

    public void miseAJour() {
        adaptateurSouris.setCarte(carte);
        repaint();
    }

    public Image getImage() {
        return Configuration.lisImage("Cadran_joueur", imagesCache);
    }
}
