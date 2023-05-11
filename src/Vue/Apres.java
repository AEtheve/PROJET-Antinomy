package Vue;

import java.awt.*;
import java.util.HashMap;

import javax.swing.*;

import Controleur.AdaptateurSouris;
import Controleur.ControleurJoueur;
import Global.Configuration;
import Modele.Carte;

public class Apres extends JComponent {
    Carte carte;
    int x, y, width, height;
    HashMap<String, Image> imagesCache = new HashMap<String, Image>();
    boolean hover = false;
    boolean selectable = false;
    int tailleY;
    int tailleX;

    int xCarte;
    int yCarte;
    ControleurJoueur ctrl;
    AdaptateurSouris adaptateurSouris;

    public Apres(ControleurJoueur ctrl, String type, HashMap<String, Image> imagesCache) {
        this.imagesCache = imagesCache;
        this.ctrl = ctrl;
        

        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                if (!isHover()) {
                    setHover(true);
                    repaint();
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            }
        });

        adaptateurSouris = new AdaptateurSouris(this.carte, ctrl, type);
        addMouseListener(adaptateurSouris);
    }

    public void paintComponent(Graphics g) {
        g.drawImage(getImage(), 0, 0, getWidth(), getHeight(), this);
    }

    public void miseAJour() {
        adaptateurSouris.setCarte(carte);
        repaint();
    }

    public Image getImage() {
        return Configuration.lisImage("retour_avant", imagesCache);
    }

    public void setHover(boolean hover) {
        this.hover = hover;
        if (hover) {
            setBounds(xCarte - 10, yCarte - 10, tailleX + 20, tailleY + 20);
        } else {
            setBounds(xCarte, yCarte, tailleX, tailleY);
        }
    }

    public boolean isHover() {
        return hover;
    }
}
