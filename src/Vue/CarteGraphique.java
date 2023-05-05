package Vue;

import java.awt.*;

import javax.swing.*;

import Global.Configuration;
import Modele.Carte;

public class CarteGraphique extends JComponent {
    Image image;
    Carte carte;
    int x, y, width, height;

    public CarteGraphique(Carte carte, int x, int y, int width, int height) {
        this.carte = carte;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        image = Configuration.lisImage(AdaptNom(carte.getType()));
    }

    private String AdaptNom(int type){

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
        
        String nom = ""+carte.getValue()+"_"+symbole+"_"+couleur;
        if (Configuration.lisImage(nom) == null) nom = "error";
        return nom;
    }

    public void paintComponent(Graphics g) {
        Graphics2D drawable = (Graphics2D) g;

        int ratioX = 475;
        int ratioY = 703;

        int tailleY = height / 6;
        int tailleX = width / 13;

        int tailleXCarte = tailleX;
        int tailleYCarte = tailleY;

        int xCarte = x;
        int yCarte = y;

        if (tailleXCarte * ratioY > tailleYCarte * ratioX) {
            tailleXCarte = tailleYCarte * ratioX / ratioY;
            xCarte = x + (tailleX - tailleXCarte) / 2;
        } else {
            tailleYCarte = tailleXCarte * ratioY / ratioX;
            yCarte = y + (tailleY - tailleYCarte) / 2;
        }

        g.drawImage(image, xCarte, yCarte, tailleXCarte, tailleYCarte, null);
    }
}
