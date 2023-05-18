package Vue;

import javax.swing.*;
import java.awt.*;

public class MenuTuto extends JComponent {
    InterfaceGraphique ig;
    MenuButton quit;
    MenuButton [] bg = new MenuButton[2];
    int ratioXBouton = 949, ratioYBouton = 302;
    int ratioXFond = 4608, ratioYFond = 3072;
    int ratioXTitle = 480, ratioYTitle = 179;
    Image background;
    int pos = 0;
    String [] liste_tuto = {"jeu.png","type.png","echange.png","Objectif.png","paradoxe.png","swap.png","codex.png"};

    public MenuTuto(InterfaceGraphique ig){
        this.ig = ig;

        Runnable Quitter = new Runnable() {
            public void run() {
                ig.switchTutoToMenuPrincipal();
            }
        };

        Runnable Suivant = new Runnable() {
            public void run() {
                if (pos < liste_tuto.length - 1)
                    pos++;
                    background = new ImageIcon("./res/Images/"+liste_tuto[pos]).getImage();
                    repaint();
            }
        };

        Runnable Precedent = new Runnable() {
            public void run() {
                if (pos > 0)
                    pos--;
                    background = new ImageIcon("./res/Images/"+liste_tuto[pos]).getImage();
                    repaint();
            }
        };


        quit = new MenuButton(Quitter, "Croix_quitter");
        bg[0] = new MenuButton(Suivant,  "Suivant", false);
        bg[1] = new MenuButton(Precedent,  "Fleche_retour_menu", false);

        
        for(int i = 0; i < bg.length; i++){
            add(bg[i]);
        }
        add(quit);

        background = new ImageIcon("./res/Images/"+liste_tuto[pos]).getImage();
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
        ####################### DRAW BUTTONS #######################
        */

        int largeurBouton = width/6;
        int hauteurBouton = height/12;
        
        if (largeurBouton * ratioYBouton > hauteurBouton * ratioXBouton) {
            largeurBouton = hauteurBouton * ratioXBouton / ratioYBouton;
        } else {
            hauteurBouton = largeurBouton * ratioYBouton / ratioXBouton;
        }

        int ecart = largeurBouton/4;
        int x1 = width - largeurBouton - ecart;
        int x2 = ecart;
        y = height - hauteurBouton - 10;

        quit.setBounds(width - (hauteurBouton*4/3), hauteurBouton/2, hauteurBouton, hauteurBouton);
        bg[0].setBounds(x1,y, largeurBouton, hauteurBouton);
        bg[1].setBounds(x2, y, largeurBouton, hauteurBouton);
    }

}
