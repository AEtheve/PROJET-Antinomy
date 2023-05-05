package Vue;

import java.awt.*;
import java.util.HashMap;

import javax.swing.*;

import Global.Configuration;
import Modele.Carte;

public class CarteGraphique extends JComponent {
    Carte carte;
    int x, y, width, height;
    HashMap<String, Image> imagesCache = new HashMap<String, Image>();
    boolean survole = false;

    public CarteGraphique(Carte carte, int x, int y, int width, int height, HashMap<String, Image> imagesCache) {
        this.carte = carte;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.imagesCache = imagesCache;

        int ratioX = 475;
        int ratioY = 703;

        int tailleY = height / 6;
        int tailleX = width / 13;

        int xCarte = x;
        int yCarte = y;

        if (tailleX * ratioY > tailleY * ratioX) {
            tailleX = tailleY * ratioX / ratioY;
            xCarte = x + (tailleX - tailleX) / 2;
        } else {
            tailleY = tailleX * ratioY / ratioX;
            yCarte = y + (tailleY - tailleY) / 2;
        }

        setBounds(xCarte, yCarte, tailleX, tailleY);
        setPreferredSize(new Dimension(0, 0));
    }

    private String AdaptNom(int type) {

        String couleur = "erreur";
        switch (carte.getColor()) {
            case 1:
                couleur = "terre";
                break;
            case 2:
                couleur = "psy";
                break;
            case 3:
                couleur = "eau";
                break;
            case 4:
                couleur = "feu";
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
        if (getSurvole()) {
            g.setColor(new Color(0, 0, 0, 100));
            g.fillRect(0, 0, getWidth(), getHeight());
        }

    }

    public void miseAJour() {
		repaint();
	}
    
    public Image getImage() {
        return Configuration.lisImage(AdaptNom(carte.getType()), imagesCache);
    }

    public void setSurvole(boolean survole) {
        this.survole = survole;
    }

    public boolean getSurvole() {
        return survole;
    }
}
