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
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(getImage(), 0, 0, getWidth(), getHeight(), this);
    }

    public Image getImage() {
        String nom = "Cartes/codex_" + Carte.couleurToString(codex.getIndex());
        return Configuration.lisImage(nom, imagesCache);
    }
}
