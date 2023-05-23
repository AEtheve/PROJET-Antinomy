package Vue;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.awt.event.*;

import Global.Configuration;

public class MenuVictoireGraphique extends JComponent {
    HashMap<String, Image> imagesCache;
    Image victoire, background;
    int ratioXFond = 4608, ratioYFond = 3072;
    int ratioXVictoire = 4396, ratioYVictoire = 2614;

    MenuVictoireGraphique(InterfaceGraphique ig, HashMap<String, Image> imagesCache, Boolean gagnant){

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                ig.backVictoireToMenuPrincipal();
            }
        });

        victoire = gagnant ? Configuration.lisImage("Menu/Victoire", imagesCache)
            : Configuration.lisImage("Menu/Défaite", imagesCache);

        this.imagesCache = imagesCache;
    }

    private void getImage(){
        background = Configuration.lisImage("background", imagesCache);
    }

    public void paintComponent(Graphics g){
        Graphics2D drawable = (Graphics2D) g;
        int height = getHeight();
        int width = getWidth();

        getImage();

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

        int largeurTitre = width/2;
        int hauteurTitre = height/2;

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
