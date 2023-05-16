package Vue;

import javax.swing.*;
import java.awt.*;

public class MenuOnGameGraphique extends JComponent{
    ContinuumGraphique ig;
    MenuButton [] bg = new MenuButton[5];
    int ratioXBouton = 949, ratioYBouton = 302;
    int ratioXFond = 4608, ratioYFond = 3072;
    int ratioXTitle = 480, ratioYTitle = 179;
    Image titre, background;


    public MenuOnGameGraphique(ContinuumGraphique ig){
        this.ig = ig;
        
        String rep = "res/Images/";

        Runnable langage = new Runnable() {
            public void run() {
                System.out.println("Langage");
            }
        };

        Runnable musique = new Runnable() {
            public void run() {
                System.out.println("Musique");
            }
        };

        Runnable sons = new Runnable() {
            public void run() {
                System.out.println("Sons");
            }
        };

        Runnable texture = new Runnable() {
            public void run() {
                System.out.println("Texture");
            }
        };

        Runnable retour = new Runnable() {
            public void run() {
                ig.enleveMenu();
            }
        };


        bg[0] = new MenuButton(langage,"Bouton/Langage.png");
        bg[1] = new MenuButton(musique,  "Bouton/Musique.png");
        bg[2] = new MenuButton(sons,  "Bouton/Sons.png");
        bg[3] = new MenuButton(texture, "Bouton/Texture.png");
        bg[4] = new MenuButton(retour, "Bouton/Fleche_retour_menu.png");

        
        for(int i = 0; i < bg.length; i++){
            add(bg[i]);
        }

        titre = new ImageIcon(rep + "Antinomy.png").getImage();
        background = new ImageIcon(rep + "background.png").getImage();
    }



    public void paintComponent(Graphics g) {
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

        if (largeurTitre * ratioYTitle > hauteurTitre * ratioXTitle) {
            largeurTitre = hauteurTitre * ratioXTitle / ratioYTitle;
        } else {
            hauteurTitre = largeurTitre * ratioYTitle / ratioXTitle;
        }

        x = width/2 - largeurTitre/2;
        y = height/7;


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
