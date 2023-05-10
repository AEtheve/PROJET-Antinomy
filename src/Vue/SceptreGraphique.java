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
<<<<<<< Updated upstream
        
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
=======
>>>>>>> Stashed changes
    }
    
    public void paintComponent(Graphics g) {
        g.drawImage(getImage(), 0, 0, getWidth(), getHeight(), this);
    }

    public Image getImage() {
        return Configuration.lisImage("sceptre", imagesCache);
    }
}
