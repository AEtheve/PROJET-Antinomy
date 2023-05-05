package Vue;

import java.awt.*;
import java.util.HashMap;

import javax.swing.*;

import Global.Configuration;
import Modele.Carte;

public class CodexGraphique extends JComponent {
    Image image;
    Carte codex;
    int x, y, width, height;
    HashMap<String, Image> imagesCache = new HashMap<String, Image>();

    public CodexGraphique(Carte codex, int x, int y, int width, int height, HashMap<String, Image> imagesCache) {
        this.codex = codex;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.imagesCache = imagesCache;
        

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

        setBounds(xCarte, yCarte, tailleXCarte, tailleYCarte);
        setPreferredSize(new Dimension(0, 0));
    }

    private String AdaptNom(int type){
        String nom = "codex_"+ type; 
        if (Configuration.lisImage(nom, imagesCache) == null) nom = "error";
        return nom;
    }

    public void paintComponent(Graphics g) {
        g.drawImage(getImage(), 0, 0, getWidth(), getHeight(), null);
    }

    public Image getImage() {
        int codex = this.codex.getIndex();
        String nom = "";
        switch(codex){
            case Carte.EAU:
                nom = "codex_1";
                break;
            case Carte.TERRE:
                nom = "codex_2";
                break;
            case Carte.PSY:
                nom = "codex_3";
                break;
            case Carte.FEU:
                nom = "codex_0";
                break;
        }
        return Configuration.lisImage(nom, imagesCache);
    }
}
