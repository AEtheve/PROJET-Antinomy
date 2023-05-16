package Vue;

import javax.swing.*;
import java.awt.*;

public class MenuOnGameGraphique extends JComponent{
    ContinuumGraphique cg;
    InterfaceGraphique ig;
    MenuButton [] bg = new MenuButton[5];
    MenuButton retourB;
    int ratioXBouton = 1200, ratioYBouton = 400;
    int ratioXFond = 4608, ratioYFond = 3072;
    int ratioXTitle = 380, ratioYTitle = 139;
    Image titre, background;


    public MenuOnGameGraphique(InterfaceGraphique ig, ContinuumGraphique cg){
        this.cg = cg;
        this.ig = ig;
        
        String rep = "res/Images/";

        Runnable rejouer = new Runnable() {
            public void run() {
                ig.rejouer();
                cg.reset();
            }
        };

        Runnable sauvegarde = new Runnable() {
            public void run() {
                ig.sauvegarder();
            }
        };

        Runnable musique = new Runnable() {
            public void run() {
                ig.switchBackgroundSound();
            }
        };

        Runnable sons = new Runnable() {
            public void run() {
                ig.switchSoundEffect();
            }
        };

        Runnable quitter = new Runnable() {
            public void run() {
                // System.exit(0);
                ig.backToMenuPrincipal();
            }
        };

        Runnable quitteMenu = new Runnable() {
            public void run() {
                cg.enleveMenu();
            }
        };

        bg[0] = new MenuButton(rejouer, "Bouton/Rejouer.png");
        bg[1] = new MenuButton(sauvegarde,"Bouton/Sauvegarder.png");
        bg[2] = new MenuButton(musique,  "Bouton/Musique.png");
        bg[3] = new MenuButton(sons,  "Bouton/Sons.png");
        bg[4] = new MenuButton(quitter, "Bouton/Quitter.png");

        retourB = new MenuButton(quitteMenu, "Croix_quitter.png");
        add(retourB);
        
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
        y = height/2 - hauteurBouton/2;

        int tailleEntreBouton = ((int)(0.85 * height) - y) - hauteurBouton*bg.length;
        int espace = tailleEntreBouton / bg.length;

        for(int i = 0; i < bg.length ; i++){
            bg[i].setBounds(x, y + (i*(hauteurBouton + espace)), largeurBouton, hauteurBouton);
        }

        /*
        ####################### DRAW RETURN BUTTON #######################
        */

        int largeurRetour = hauteurBouton;
        int hauteurRetour = hauteurBouton;

        x = (int)(0.85 * width);
        y = (int)(0.12 * height);

        retourB.setBounds(x, y, largeurRetour, hauteurRetour);
    }



}
