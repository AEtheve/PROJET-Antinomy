package Vue;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

import Global.*;

public class MenuOptionsDaltonismeGraphique extends JComponent{
    HashMap<String, Image> imagesCache;
    InterfaceGraphique ig;
    MenuButton [] leftSelect = new MenuButton[3];
    MenuButton [] rightSelect = new MenuButton[3];
    MenuButton retourB;
    int ratioXBouton = 949, ratioYBouton = 302;
    int ratioXFond = 4608, ratioYFond = 3072;
    int ratioXTitle = 480, ratioYTitle = 179;
    Image titre, background;


    public MenuOptionsDaltonismeGraphique(InterfaceGraphique ig, HashMap<String, Image> imagesCache){
        this.ig = ig;

        Runnable deuteranomaly = new Runnable() {
            public void run() {
                if(Configuration.getDaltonisme() == Daltonisme.Type.DEUTERANOMALY){
                    Configuration.setDaltonisme(Daltonisme.Type.NORMAL);
                } else {
                    Configuration.setDaltonisme(Daltonisme.Type.DEUTERANOMALY);
                }
                ig.resetCache();
                ig.refresh();
            }
        };

        Runnable deuteranopia = new Runnable() {
            public void run() {
                if(Configuration.getDaltonisme() == Daltonisme.Type.DEUTERANOPIA){
                    Configuration.setDaltonisme(Daltonisme.Type.NORMAL);
                } else {
                    Configuration.setDaltonisme(Daltonisme.Type.DEUTERANOPIA);
                }
                ig.resetCache();
                ig.refresh();
            }
        };

        Runnable protanomaly = new Runnable() {
            public void run() {
                if(Configuration.getDaltonisme() == Daltonisme.Type.PROTANOMALY){
                    Configuration.setDaltonisme(Daltonisme.Type.NORMAL);
                } else {
                    Configuration.setDaltonisme(Daltonisme.Type.PROTANOMALY);
                }
                ig.resetCache();
                ig.refresh();
            }
        };

        Runnable protanopia = new Runnable() {
            public void run() {
                if(Configuration.getDaltonisme() == Daltonisme.Type.PROTANOPIA){
                    Configuration.setDaltonisme(Daltonisme.Type.NORMAL);
                } else {
                    Configuration.setDaltonisme(Daltonisme.Type.PROTANOPIA);
                }
                ig.resetCache();
                ig.refresh();
            }
        };

        Runnable tritanomaly = new Runnable() {
            public void run() {
                if(Configuration.getDaltonisme() == Daltonisme.Type.TRITANOMALY){
                    Configuration.setDaltonisme(Daltonisme.Type.NORMAL);
                } else {
                    Configuration.setDaltonisme(Daltonisme.Type.TRITANOMALY);
                }
                ig.resetCache();
                ig.refresh();
            }
        };

        Runnable tritanopia = new Runnable() {
            public void run() {
                if(Configuration.getDaltonisme() == Daltonisme.Type.TRITANOPIA){
                    Configuration.setDaltonisme(Daltonisme.Type.NORMAL);
                } else {
                    Configuration.setDaltonisme(Daltonisme.Type.TRITANOPIA);
                }
                ig.resetCache();
                ig.refresh();
            }
        };

        Runnable retour = new Runnable() {
            public void run() {
                ig.backMenuDaltonismeToMenuOptions();
            }
        };


        leftSelect[0] = new MenuButton(deuteranomaly,"Deuteranomaly", false, imagesCache);
        leftSelect[1] = new MenuButton(deuteranopia,  "Deuteranopia", false, imagesCache);
        leftSelect[2] = new MenuButton(protanomaly,  "Protanomaly", false, imagesCache);
        rightSelect[0] = new MenuButton(protanopia, "Protanopia", false, imagesCache);
        rightSelect[1] = new MenuButton(tritanomaly, "Tritanomaly", false, imagesCache);
        rightSelect[2] = new MenuButton(tritanopia, "Tritanopia", false, imagesCache);
        retourB = new MenuButton(retour, "Fleche_retour_menu", false, imagesCache);

        
        for(int i = 0; i < leftSelect.length; i++){
            add(leftSelect[i]);
        }
        for(int i = 0; i < rightSelect.length; i++){
            add(rightSelect[i]);
        }
        add(retourB);

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
        
        
        x = width/3 - largeurBouton/2;
        y = 5*height/8;

        for(int i = 0; i < leftSelect.length; i++){
            leftSelect[i].setBounds(x, y + (i-2) * hauteurBouton, largeurBouton, hauteurBouton);
        }

        /*
        ####################### DRAW RIGHT BUTTONS #######################
        */

        x = 2*width/3 - largeurBouton/2;

        for(int i = 0; i < rightSelect.length; i++){
            rightSelect[i].setBounds(x, y + (i-2) * hauteurBouton, largeurBouton, hauteurBouton);
        }

        retourB.setBounds(x, y + (7 * hauteurBouton / 3), largeurBouton, hauteurBouton);
    }



}
