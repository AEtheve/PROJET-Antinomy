package Vue;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

import Global.Configuration;

public class MenuOptionsGraphique extends JComponent{
    HashMap<String, Image> imagesCache;
    InterfaceGraphique ig;
    MenuButton [] leftSelect = new MenuButton[3];
    MenuButton [] rightSelect = new MenuButton[3];
    MenuButton retourB;
    int ratioXBouton = 949, ratioYBouton = 302;
    int ratioXFond = 4608, ratioYFond = 3072;
    int ratioXTitle = 480, ratioYTitle = 179;
    Image titre, background;


    public MenuOptionsGraphique(InterfaceGraphique ig, HashMap<String, Image> imagesCache){
        this.ig = ig;

        Runnable langage = new Runnable() {
            public void run() {
                System.out.println("Langage");
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

        Runnable daltonisme = new Runnable() {
            public void run() {
                ig.switchToMenuDaltonisme();
            }
        };

        Runnable animation = new Runnable() {
            public void run() {
                Configuration.switchAnimation();
                rightSelect[2].switchAnimation();
            }
        };

        Runnable texture = new Runnable() {
            public void run() {
                System.out.println("Texture");
            }
        };

        Runnable retour = new Runnable() {
            public void run() {
                ig.switchOptionsToMenuPrincipal();
            }
        };


        leftSelect[0] = new MenuButton(langage,"Langage", true, imagesCache);
        leftSelect[1] = new MenuButton(texture, "Textures", true, imagesCache);
        leftSelect[2] = new MenuButton(daltonisme, "Daltonisme", false, imagesCache);
        rightSelect[0] = new MenuButton(musique,  "Musique", ig, imagesCache);
        rightSelect[1] = new MenuButton(sons,  "Effets_sonores", ig, imagesCache);
        rightSelect[2] = new MenuButton(animation, "Animation", ig, imagesCache);
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
        y = 5*height/8 + hauteurBouton/2;

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

        x = width/2 - largeurBouton/2;

        retourB.setBounds(x, y + (5 * hauteurBouton / 3), largeurBouton, hauteurBouton);
    }



}
