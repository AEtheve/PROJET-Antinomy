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
    ContinuumGraphique continuum;

    public CodexGraphique(ContinuumGraphique continuum, Carte codex, int x, int y, int width, int height, HashMap<String, Image> imagesCache) {
        this.codex = codex;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.imagesCache = imagesCache;
        this.continuum = continuum;
        image = Configuration.lisImage("Cartes/codex_" + Carte.couleurToString(codex.getIndex()), imagesCache);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(getImage(), 0, 0, getWidth(), getHeight(), this);
    }

    public Image getImage() {
        String nom = "Cartes/codex_" + Carte.couleurToString(codex.getIndex());
        if (image != Configuration.lisImage(nom, imagesCache)){
            image = Configuration.lisImage(nom, imagesCache);
            continuum.declencheRoue();
        }
        return image;
    }
}
