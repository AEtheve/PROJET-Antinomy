package Vue;

import java.awt.*;
import java.util.HashMap;

import javax.swing.*;

import Global.Configuration;

public class SceptreGraphique extends JComponent {
    int x, y, width, height;
    HashMap<String, Image> imagesCache = new HashMap<String, Image>();

    public SceptreGraphique(int x, int y, int width, int height, HashMap<String, Image> imagesCache, boolean rotate) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.imagesCache = imagesCache;
    }
    
    public void paintComponent(Graphics g) {
        g.drawImage(getImage(), 0, 0, getWidth(), getHeight(), this);
    }

    public Image getImage() {
        return Configuration.lisImage("sceptre", imagesCache);
    }
}
