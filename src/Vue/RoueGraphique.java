package Vue;

import java.awt.*;
import java.util.HashMap;

import javax.swing.*;

import Global.Configuration;
import Modele.Carte;

public class RoueGraphique extends JComponent {
    Image image;
    Carte codex;
    int x, y, width, height;
    HashMap<String, Image> imagesCache = new HashMap<String, Image>();

    public RoueGraphique(Carte codex, int x, int y, int width, int height, HashMap<String, Image> imagesCache) {
        this.codex = codex;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.imagesCache = imagesCache;

        setBounds(x, y, width, height);

        switch (codex.getIndex()) {
            case Carte.EAU:
                setAngle(0);
                break;
            case Carte.FEU:
                setAngle(Math.PI / 2);
                break;
            case Carte.PSY:
                setAngle(Math.PI);
                break;
            case Carte.TERRE:
                setAngle(3 * Math.PI / 2);
                break;
        }
    }

    double angle = 0;

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getAngle() {
        return angle;
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.rotate(angle, getWidth() / 2, getHeight() / 2);
        g2d.drawImage(getImage(), 0, 0, getWidth(), getHeight(), this);
    }

    public Image getImage() {

        String nom = "roue_codex";

        return Configuration.lisImage(nom, imagesCache);
    }
}
