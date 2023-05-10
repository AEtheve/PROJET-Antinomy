package Vue;

import java.awt.*;
import java.util.HashMap;

import javax.swing.*;

import Controleur.AdaptateurSouris;
import Controleur.ControleurJoueur;
import Global.Configuration;
import Modele.Carte;

public class CarteGraphique extends JComponent {
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

    public CarteGraphique(ControleurJoueur ctrl, Carte carte, String type, int x, int y, int width, int height, HashMap<String, Image> imagesCache) {
        this.carte = carte;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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

        addMouseListener(new AdaptateurSouris(this.carte, ctrl, type));
    }

    private String AdaptNom(int type) {

        String couleur = "erreur";
        switch (carte.getColor()) {
            case Carte.EAU:
                couleur = "eau";
                break;
            case Carte.FEU:
                couleur = "feu";
                break;
            case Carte.TERRE:
                couleur = "terre";
                break;
            case Carte.PSY:
                couleur = "psy";
                break;
        }

        String symbole = "erreur";
        switch (carte.getSymbol()) {
            case 1:
                symbole = "plume";
                break;
            case 2:
                symbole = "cle";
                break;
            case 3:
                symbole = "crane";
                break;
            case 4:
                symbole = "couronne";
                break;
        }

        String nom = "" + carte.getValue() + "_" + symbole + "_" + couleur;
        if (Configuration.lisImage(nom, imagesCache) == null)
            nom = "error";
        return nom;
    }

    public void paintComponent(Graphics g) {
        g.drawImage(getImage(), 0, 0, getWidth(), getHeight(), this);
        if (!isSelectable()){
            g.setColor(new Color(0, 0, 0, 200));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    public void miseAJour() {
        repaint();
    }

    public Image getImage() {
        return Configuration.lisImage(AdaptNom(carte.getType()), imagesCache);
    }

    public void setHover(boolean hover) {
        this.hover = hover;
        if (hover) {
            setBounds(xCarte - (tailleX / 20), yCarte, tailleX + (tailleX / 10), tailleY + (tailleY / 10));
        } else {
            setBounds(xCarte, yCarte, tailleX, tailleY);
        }
    }

    public boolean isHover() {
        return hover;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    public boolean isSelectable() {
        return selectable;
    }
}
