package Vue;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

import Global.Configuration;

public class MenuPrincipalGraphique extends JComponent{
    HashMap<String, Image> imagesCache;
    InterfaceGraphique ig;
    MenuButton [] bg = new MenuButton[5];
    int ratioXBouton = 949, ratioYBouton = 302;
    int ratioXFond = 4615, ratioYFond = 3072;
    int ratioXTitle = 480, ratioYTitle = 179;
    Image titre, background;


    public MenuPrincipalGraphique(InterfaceGraphique ig, HashMap<String, Image> imagesCache){
        this.ig = ig;

        Runnable play = new Runnable() {
            public void run() {
                ig.switchToMenuJeu();
            }
        };

        Runnable charger = new Runnable() {
            public void run() {
                ig.switchToGameLoad();
            }
        };

        Runnable tutoriel = new Runnable() {
            public void run() {
                ig.switchToMenuTuto();
            }
        };

        Runnable option = new Runnable() {
            public void run() {
                ig.switchToMenuOptions();
            }
        };

        Runnable quitter = new Runnable() {
            public void run() {
                System.exit(0);
            }
        };


        bg[0] = new MenuButton(play, "Jouer", false, imagesCache);
        bg[1] = new MenuButton(charger, "Charger", false, imagesCache);
        bg[2] = new MenuButton(tutoriel, "Tutoriel", false, imagesCache);
        bg[3] = new MenuButton(option, "Options", false, imagesCache);
        bg[4] = new MenuButton(quitter, "Quitter", false, imagesCache);

        
        for(int i = 0; i < bg.length; i++){
            add(bg[i]);
        }

        this.imagesCache = imagesCache;
    }

    private void getImage(){
        titre = Configuration.lisImage("Antinomy", imagesCache);
        background = Configuration.lisImage("background", imagesCache);
    }


    public void paintComponent(Graphics g) {
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

        int largeurTitre = 4*width/6;
        int hauteurTitre = 2*height/7;

        if (largeurTitre * ratioYTitle > hauteurTitre * ratioXTitle) {
            largeurTitre = hauteurTitre * ratioXTitle / ratioYTitle;
        } else {
            hauteurTitre = largeurTitre * ratioYTitle / ratioXTitle;
        }

        x = width/2 - largeurTitre/2;
        y = 2*height/11;


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
