package Vue;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.awt.event.*;

import Global.Configuration;

public class MenuVictoireGraphique extends JComponent {
    Image victoire, background;
    int ratioXFond = 4608, ratioYFond = 3072;
    int ratioXVictoire = 2021, ratioYVictoire = 1228;
    MenuButton quitterB;

    MenuVictoireGraphique(InterfaceGraphique ig, HashMap<String, Image> imagesCache, Boolean gagnant){

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                ig.backVictoireToMenuPrincipal();
            }
        });

        Runnable quitter = new Runnable() {
            public void run() {
                ig.backVictoireToMenuPrincipal();
            }
        };

        quitterB = new MenuButton(quitter, "Quitter", false, imagesCache);

        add(quitterB);

        victoire = gagnant ? Configuration.lisImage("Menu/Victoire", imagesCache)
            : Configuration.lisImage("Menu/Défaite", imagesCache);        
        background = Configuration.lisImage("background", imagesCache);
    }

    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        Graphics2D drawable = (Graphics2D) g;
        int height = getHeight();
        int width = getWidth();

        /*
        ####################### DRAW BACKGROUND #######################
        */

        int largeurFond = width;
        int hauteurFond = height;

        if (largeurFond * ratioYFond <= hauteurFond * ratioXFond) {
            largeurFond = hauteurFond * ratioXFond / ratioYFond;
        } else {
            hauteurFond = largeurFond * ratioYFond / ratioXFond;
        }

        int x = width/2 - largeurFond/2;
        int y = height/2 - hauteurFond/2;

        drawable.drawImage(background, x, y, largeurFond, hauteurFond, null);

        /*
        ####################### DRAW TITLE #######################
        */

        int largeurTitre = 4*width/6;
        int hauteurTitre = 2*height/7;

        if (largeurTitre * ratioYVictoire > hauteurTitre * ratioXVictoire) {
            largeurTitre = hauteurTitre * ratioXVictoire / ratioYVictoire;
        } else {
            hauteurTitre = largeurTitre * ratioYVictoire / ratioXVictoire;
        }

        x = width/2 - largeurTitre/2;
        y = height/2 - hauteurTitre/2;


        drawable.drawImage(victoire, x, y, largeurTitre, hauteurTitre, null);
    }
    
}
