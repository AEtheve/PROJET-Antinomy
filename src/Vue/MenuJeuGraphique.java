package Vue;

import javax.swing.*;
import java.awt.*;

public class MenuJeuGraphique extends JComponent{
    InterfaceGraphique ig;
    MenuButton [] bg = new MenuButton[4];
    int ratioXBouton = 949, ratioYBouton = 302;
    int ratioXFond = 4608, ratioYFond = 3072;
    int ratioXTitle = 480, ratioYTitle = 179;
    Image titre, background;


    public MenuJeuGraphique(InterfaceGraphique ig){
        this.ig = ig;
        
        String rep = "res/Images/";

        Runnable local = new Runnable() {
            public void run() {
                ig.switchToGameLocal();
            }
        };

        Runnable ia = new Runnable() {
            public void run() {
                ig.switchToGameIA();
            }
        };

        Runnable online = new Runnable() {
            public void run() {
                ig.switchToGameOnline();
            }
        };

        Runnable retour = new Runnable() {
            public void run() {
                ig.switchJeuToMenuPrincipal();
            }
        };


        bg[0] = new MenuButton(local,"Bouton/VS_joueur.png");
        bg[1] = new MenuButton(ia, "Bouton/VS_IA.png");
        bg[2] = new MenuButton(online,"Bouton/VS_online.png");
        bg[3] = new MenuButton(retour,"Bouton/Fleche_retour_menu.png");

        
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
