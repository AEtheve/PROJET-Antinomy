package Vue;

import Global.Configuration;
import Modele.Jeu;
import Modele.Deck;

import java.awt.*;
import javax.swing.*;

public class DeckGraphique extends JComponent {
    Image verso = Configuration.lisImage("verso");
    Jeu j;
    CarteGraphique [] cartes;

    public DeckGraphique(Jeu j) {
        this.j = j;
        
        cartes = new CarteGraphique[16];

        Deck deck = j.getDeck();
        
        for (int i = 0; i < 9; i++) {
            // Cartes du plateau
            cartes[i] = new CarteGraphique(deck.getCarte(i));
        }
        for (int i = 9; i < 12; i++) {
            // Cartes de la main 1
            cartes[i] = new CarteGraphique(deck.joueur_1.getMain()[i-9]);
        }
        for (int i=12; i<15; i++) {
            // Cartes de la main 2
            cartes[i] = new CarteGraphique(deck.joueur_2.getMain()[i-12]);
        }
        // Ajout du codex
        cartes[15] = new CarteGraphique(deck.getCodex());
    }

    
    private void tracer(Graphics2D g, Image i, int x, int y, int l, int h){
        g.drawImage(i, x, y, l, h, null);
    }


    //A modifier carte
    public void dessinImage(Graphics g, CarteGraphique cg, int posX, int posY, int sizeX, int sizeY) {
        Graphics2D drawable = (Graphics2D) g;
        
        if(!cg.carte.isVisible())
            tracer(drawable, verso, posX, posY, sizeX, sizeY);
        else
            tracer(drawable, cg.image, posX, posY, sizeX, sizeY);
    }

    public void miseAJour() {
        repaint();
    }

    public void paintComponent(Graphics g) {
        int width = getWidth(); 
        int height = getHeight();

        int tailleX = width / 13;
        int tailleY = height / 6;
        
        int x;
        int y; // Centre de la fenêtre
        int pos;
        
        for (int i=0; i<16; i++) {
            pos = j.getDeck().getOwner(cartes[i].carte);
            switch(pos){
                case 0:
                    x = tailleX + (i+1) * tailleX + (tailleX / 9 * (i+1));
                    y = height / 2 - tailleY / 2;
                    dessinImage(g, cartes[i], x, y, tailleX, tailleY);
                    break;
                case 1:
                    if (cartes[i].carte.isVisible()) {
                        y = height - tailleY - (int)(0.03 * height); // Centre de la fenêtre
                        x = width / 2  + (i-1) * tailleX + (tailleX / 9 * (i-1));
                        dessinImage(g, cartes[i], x, y, tailleX, tailleY);
                    } else {
                        y = height - tailleY + (int)(0.07 * height);
                        x = 3 * width / 5 + (int)(tailleX / 2.5 * (i + 1));
                        dessinImage(g, cartes[i], x, y, tailleX, tailleY);
                    }
                    break;
                case 2:
                    if (cartes[i].carte.isVisible()){
                        y = (int) (0.03 * height);
                        x = width / 2  + (i-1) * tailleX + (tailleX / 9 * (i-1));
                        dessinImage(g, cartes[i], x, y, tailleX, tailleY);
                    } else {
                        y = - (int)(0.07 * height);
                        x = 3 * width / 5 + (int)(tailleX / 2.5 * (i + 1));
                        dessinImage(g, cartes[i], x, y + tailleY, tailleX, -tailleY);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Position invalide");
            }
        }
    }
    
}
