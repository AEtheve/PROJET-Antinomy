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
        

        setBounds(x, y, width, height);
    }

    public void paintComponent(Graphics g) {
        g.drawImage(getImage(), 0, 0, getWidth(), getHeight(), this);
    }

    public Image getImage() {
        int codex = this.codex.getIndex();
        String nom = "Cartes/";
        switch(codex){
            case Carte.EAU:
                nom += "codex_1";
                break;
            case Carte.TERRE:
                nom += "codex_2";
                break;
            case Carte.PSY:
                nom += "codex_3";
                break;
            case Carte.FEU:
                nom += "codex_0";
                break;
        }
        return Configuration.lisImage(nom, imagesCache);
    }
}
