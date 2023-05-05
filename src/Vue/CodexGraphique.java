package Vue;

import java.awt.*;

import javax.swing.*;

import Global.Configuration;
import Modele.Carte;

public class CodexGraphique extends JComponent {
    Image image;
    Carte codex;
    int x, y, width, height;

    public CodexGraphique(Carte codex, int x, int y, int width, int height) {
        this.codex = codex;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        image = Configuration.lisImage(AdaptNom(codex.getIndex()-1));
    }

    private String AdaptNom(int type){
        String nom = "codex_"+ type; 
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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }
}
