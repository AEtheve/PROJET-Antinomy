package Vue;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

import Global.Configuration;

public class MenuInGameGraphique extends JComponent{
    ContinuumGraphique cg;
    InterfaceGraphique ig;
    MenuButton [] leftSelect = new MenuButton[3];
    MenuButton [] rightSelect = new MenuButton[3];
    MenuButton retourB;
    int ratioXBouton = 1200, ratioYBouton = 400;
    int ratioXFond = 4608, ratioYFond = 3072;
    int ratioXTitle = 380, ratioYTitle = 139;
    Image titre, background;


    public MenuInGameGraphique(InterfaceGraphique ig, ContinuumGraphique cg, HashMap<String, Image> imagesCache){
        this.cg = cg;
        this.ig = ig;

        Runnable rejouer = new Runnable() {
            public void run() {
                ig.rejouer();
                cg.reset();
            }
        };

        Runnable sauvegarde = new Runnable() {
            public void run() {
                JFileChooser choix = new JFileChooser(".");
                choix.showSaveDialog(null);
                try{
                    String path = choix.getSelectedFile().getAbsolutePath();
                    ig.sauvegarder(path);
                }catch(NullPointerException ex){
                    return;
                }
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

        Runnable animation = new Runnable() {
            public void run() {
                Configuration.switchAnimation();
                rightSelect[2].switchAnimation();
            }
        };

        Runnable quitteMenu = new Runnable() {
            public void run() {
                cg.enleveMenu();
            }
        };

        leftSelect[0] = new MenuButton(rejouer, "Rejouer", false, imagesCache);
        leftSelect[1] = new MenuButton(sauvegarde,"Sauvegarder", false, imagesCache);
        leftSelect[2] = new MenuButton(quitter, "Menu_principal", false, imagesCache);

        rightSelect[0] = new MenuButton(musique,  "Musique", ig, imagesCache);
        rightSelect[1] = new MenuButton(sons,  "Effets_sonores", ig, imagesCache);
        rightSelect[2] = new MenuButton(animation, "Animation", ig, imagesCache);

        retourB = new MenuButton(quitteMenu, "Croix_quitter", imagesCache);
        add(retourB);
        
        for(int i = 0; i < leftSelect.length; i++){
            add(leftSelect[i]);
        }

        for(int i = 0; i < rightSelect.length; i++){
            add(rightSelect[i]);
        }

        titre = Configuration.lisImage("Antinomy", imagesCache);
        background = Configuration.lisImage("Menu/Menu", imagesCache);
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
        
        
        x = width/3 - largeurBouton/2;
        y = height/2 - hauteurBouton/2;

        int tailleEntreBouton = ((int)(0.85 * height) - y) - hauteurBouton*leftSelect.length;
        int espace = tailleEntreBouton / leftSelect.length;

        for(int i = 0; i < leftSelect.length ; i++){
            leftSelect[i].setBounds(x, y + (i*(hauteurBouton + espace)), largeurBouton, hauteurBouton);
        }

        x = width*2/3 - largeurBouton/2;

        for(int i = 0; i < rightSelect.length ; i++){
            rightSelect[i].setBounds(x, y + (i*(hauteurBouton + espace)), largeurBouton, hauteurBouton);
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
