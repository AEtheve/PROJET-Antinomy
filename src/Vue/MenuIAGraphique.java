package Vue;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Random;

import Global.Configuration;

public class MenuIAGraphique extends JComponent{
    HashMap<String, Image> imagesCache;
    InterfaceGraphique ig;
    MenuButton [] leftSelect = new MenuButton[3];
    MenuButton [] rightSelect = new MenuButton[4];
    int ratioXBouton = 949, ratioYBouton = 302;
    int ratioXFond = 4608, ratioYFond = 3072;
    int ratioXTitle = 480, ratioYTitle = 179;
    Image titre, background;

    MenuButton retourB, launchB;

    String type = "facile";
    int joueur = 0;


    public MenuIAGraphique(InterfaceGraphique ig, HashMap<String, Image> imagesCache){
        this.ig = ig;

        Runnable joueur1 = new Runnable() {
            public void run() {
                joueur = 0;
                unlockAllLeft();
                leftSelect[0].Lock();
            }
        };

        Runnable joueur2 = new Runnable() {
            public void run() {
                joueur = 1;
                unlockAllLeft();
                leftSelect[1].Lock();
            }
        };

        Runnable aleatoire = new Runnable() {
            public void run() {
                Random r = new Random();
                joueur = r.nextInt(2);
                unlockAllLeft();
                leftSelect[2].Lock();
            }
        };

        Runnable facile2 = new Runnable() {
            public void run() {
                type = "facile";
                unlockAllRight();
                rightSelect[0].Lock();
            }
        };

        Runnable moyen2 = new Runnable() {
            public void run() {
                type = "moyen";
                unlockAllRight();
                rightSelect[1].Lock();
            }
        };

        Runnable difficile2 = new Runnable() {
            public void run() {
                type = "difficile";
                unlockAllRight();
                rightSelect[2].Lock();
            }
        };

        Runnable extreme2 = new Runnable() {
            public void run() {
                type = "extreme";
                unlockAllRight();
                rightSelect[3].Lock();
            }
        };

        Runnable retour = new Runnable() {
            public void run() {
                ig.backIAToMenuJeu();
            }
        };

        Runnable launch = new Runnable() {
            public void run() {
                ig.resetJoueur(joueur);
                if(joueur==1)
                    ig.setIA(type,0);
                else
                    ig.setIA(type,1);
                ig.launchIAGame();
            }
        };


        leftSelect[0] = new MenuButton(joueur1,"Joueur1", false, imagesCache);
        leftSelect[1] = new MenuButton(joueur2, "Joueur2", false, imagesCache);
        leftSelect[2] = new MenuButton(aleatoire,"Aleatoire", false, imagesCache);
        retourB = new MenuButton(retour,"Fleche_retour_menu", false, imagesCache);

        rightSelect[0] = new MenuButton(facile2,"IA_facile", false, imagesCache);
        rightSelect[1] = new MenuButton(moyen2, "IA_moyen", false, imagesCache);
        rightSelect[2] = new MenuButton(difficile2,"IA_difficile", false, imagesCache);
        rightSelect[3] = new MenuButton(extreme2,"IA_extreme", false, imagesCache);

        launchB = new MenuButton(launch,"Jouer", false, imagesCache);
        
        for(int i = 0; i < leftSelect.length; i++){
            add(leftSelect[i]);
        }
        for(int i = 0; i < rightSelect.length; i++){
            add(rightSelect[i]);
        }
        add(retourB);
        add(launchB);

        this.imagesCache = imagesCache;
    }

    private void unlockAllLeft(){
        for(int i = 0; i < leftSelect.length; i++){
            leftSelect[i].unLock();
            leftSelect[i].repaint();
        }
    }

    private void unlockAllRight(){
        for(int i = 0; i < rightSelect.length; i++){
            rightSelect[i].unLock();
            rightSelect[i].repaint();
        }
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
        ####################### DRAW LEFT BUTTONS #######################
        */

        int largeurBouton = width/6;
        int hauteurBouton = height/12;
        
        if (largeurBouton * ratioYBouton > hauteurBouton * ratioXBouton) {
            largeurBouton = hauteurBouton * ratioXBouton / ratioYBouton;
        } else {
            hauteurBouton = largeurBouton * ratioYBouton / ratioXBouton;
        }
        
        
        x = width/3 - largeurBouton/2;
        y = 5*height/8 + hauteurBouton/2;

        for(int i = 0; i < leftSelect.length; i++){
            leftSelect[i].setBounds(x, y + (i-2) * hauteurBouton, largeurBouton, hauteurBouton);
        }

        /*
        ####################### DRAW RIGHT BUTTONS #######################
        */

        x = 2*width/3 - largeurBouton/2;
        y = 5*height/8;

        for(int i = 0; i < rightSelect.length; i++){
            rightSelect[i].setBounds(x, y + (i-2) * hauteurBouton, largeurBouton, hauteurBouton);
        }


        /*
        ####################### DRAW RETURN BUTTON #######################
        */

        x = width/3 - largeurBouton/2;
        retourB.setBounds(x, y + (5 * hauteurBouton / 2), largeurBouton, hauteurBouton);

        /*
        ####################### DRAW LAUNCH BUTTON #######################
        */

        x = 2*width/3 - largeurBouton/2;
        launchB.setBounds(x, y + (5 * hauteurBouton / 2), largeurBouton, hauteurBouton);
    }



}
