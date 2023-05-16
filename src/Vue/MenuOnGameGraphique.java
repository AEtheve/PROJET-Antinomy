package Vue;

import javax.swing.*;
import java.awt.*;

public class MenuOnGameGraphique extends JComponent{
    ContinuumGraphique ig;
    MenuButton [] bg = new MenuButton[3];
    MenuButton retourB;
    int ratioXBouton = 1200, ratioYBouton = 400;
    int ratioXFond = 4608, ratioYFond = 3072;
    int ratioXTitle = 380, ratioYTitle = 139;
    Image titre, background;


    public MenuOnGameGraphique(ContinuumGraphique ig){
        this.ig = ig;
        
        String rep = "res/Images/";

        Runnable sauvegarde = new Runnable() {
            public void run() {
                System.out.println("Sauvegarde");
            }
        };

        Runnable options = new Runnable() {
            public void run() {
                System.out.println("Options");
            }
        };

        Runnable quitter = new Runnable() {
            public void run() {
                System.exit(0);
            }
        };

        Runnable retour = new Runnable() {
            public void run() {
                ig.enleveMenu();
            }
        };


        bg[0] = new MenuButton(sauvegarde,"Bouton/Sauvegarder.png");
        bg[1] = new MenuButton(options,  "Bouton/Options.png");
        bg[2] = new MenuButton(quitter,  "Bouton/Quitter.png");

        retourB = new MenuButton(retour, "Bouton/Fleche_retour_menu.png");

        
        for(int i = 0; i < bg.length; i++){
            add(bg[i]);
        }

        titre = new ImageIcon(rep + "Antinomy.png").getImage();
        background = new ImageIcon(rep + "Menu/Menu.png").getImage();
    }



    public void paintComponent(Graphics g) {
        Graphics2D drawable = (Graphics2D) g;
        int height = getHeight();
        int width = getWidth();

        /*
        ####################### DRAW BACKGROUND #######################
        */

        int x = (int)(0.1 * width);
        int y = (int)(0.1 * height);

        int largeurFond = width - 2*x;
        int hauteurFond = height - 2*y;

        drawable.drawImage(background, x, y, largeurFond, hauteurFond, null);


        /*
        ####################### DRAW TITLE #######################
        */

        int largeurTitre = 4*width/6;
        int hauteurTitre = 2*height/7;

        if (largeurTitre * ratioYTitle > hauteurTitre * ratioXTitle) {
            largeurTitre = hauteurTitre * ratioXTitle / ratioYTitle;
        } else {
            hauteurTitre = largeurTitre * ratioYTitle / ratioXTitle;
        }

        x = width/2 - largeurTitre/2;
        y = height/2 - hauteurTitre;


        drawable.drawImage(titre, x, y, largeurTitre, hauteurTitre, null);

        /*
        ####################### DRAW BUTTONS #######################
        */

        int largeurBouton = width/6;
        int hauteurBouton = height/12;
        
        if (largeurBouton * ratioYBouton > hauteurBouton * ratioXBouton) {
            largeurBouton = hauteurBouton * ratioXBouton / ratioYBouton;
        } else {
            hauteurBouton = largeurBouton * ratioYBouton / ratioXBouton;
        }
        
        
        x = width/2 - largeurBouton/2;
        y = 5*height/8;

        for(int i = 0; i < bg.length; i++){
            bg[i].setBounds(x, y + (i-2) * hauteurBouton, largeurBouton, hauteurBouton);
        }
    }



}
