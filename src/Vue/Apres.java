package Vue;

import java.awt.*;
import java.util.HashMap;

import javax.swing.*;

import Global.Configuration;

public class Apres extends JComponent {
    int x, y, width, height;
    HashMap<String, Image> imagesCache = new HashMap<String, Image>();

    public Apres(int x, int y, int width, int height, HashMap<String, Image> imagesCache) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.imagesCache = imagesCache;
        
        int ratioX = 475;
        int ratioY = 475;

        int tailleY = height / 12;
        int tailleX = width / 26;

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
    
    public void paintComponent(Graphics g) {
        g.drawImage(getImage(), 0, 0, getWidth(), getHeight(), this);
    }

    public Image getImage() {
        return Configuration.lisImage("retour_avant", imagesCache);
    }
}
