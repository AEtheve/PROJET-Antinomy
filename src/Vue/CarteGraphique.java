package Vue;

import java.awt.*;
import java.util.HashMap;

import javax.swing.*;

import Controleur.ControleurMediateur;
import Global.Configuration;
import Modele.Carte;

public class CarteGraphique extends JComponent {
    Carte carte;
    HashMap<String, Image> imagesCache = new HashMap<String, Image>();
    boolean hover = false;
    boolean selectable = false;
    boolean isAnimated = false;

    AdaptateurSouris adaptateurSouris;

    public CarteGraphique(ControleurMediateur ctrl, Carte carte, String type, HashMap<String, Image> imagesCache) {
        this.carte = carte;
        this.imagesCache = imagesCache;
        

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

        String nom = "Cartes/" + carte.getValue() + "_" + symbole + "_" + couleur;
        if (Configuration.lisImage(nom, imagesCache) == null)
            nom = "Cartes/error";
        return nom;
    }

    public void paintComponent(Graphics g) {
        g.drawImage(getImage(), 0, 0, getWidth(), getHeight(), this);
        if (!isSelectable() || isAnimated()){
            g.drawImage(Configuration.lisImage("Cartes/carte_filtre", imagesCache), 0, 0, getWidth(), getHeight(), this);
        }
    }

    public void miseAJour() {
        adaptateurSouris.setCarte(carte);
        repaint();
    }

    public Image getImage() {
        return Configuration.lisImage(AdaptNom(carte.getType()), imagesCache);
    }

    public void setHover(boolean hover) {
        this.hover = hover;
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

    public void setAnimated(boolean animated) {
        isAnimated = animated;
    }

    public boolean isAnimated() {
        return isAnimated;
    }
}
