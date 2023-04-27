package Vue;

import javax.swing.*;

import Modele.Carte;
import Modele.Codex;
import java.awt.*;

public class PlateauGraphique extends JComponent {
    CarteGraphique [] cartesG;
    Carte [] cartes;
    CodexGraphique codexG;
    HumainGraphique h;

    public PlateauGraphique(Carte [] cartes, Codex codex, HumainGraphique h){
        this.setLayout(null);
        this.codexG = new CodexGraphique(codex);
        this.h = h;

        this.cartesG = new CarteGraphique[cartes.length];
        for (int i = 0; i < cartes.length; i++) {
            this.cartesG[i] = new CarteGraphique(cartes[i]);
        }
        this.cartes = cartes;
    }

    public void miseAJour() {
        repaint();
    }

    public void paintComponent(Graphics g) {
        // System.out.println("paintComponent");
        int width = getWidth();
        int height = getHeight();

        int tailleY = height / 6;
        int tailleX = width / 13;

        int y = height / 2 - tailleY;
        int x;

        for(int i = 0; i < cartesG.length; i++) {
            x = tailleX + (i+1) * tailleX + (tailleX / 9 * (i+1));
            cartesG[i].dessinImage(x, y, tailleX, tailleY);
            cartesG[i].paintComponent(g);
        }

        x = tailleX;
        codexG.dessinImage(x, y, tailleX, tailleY);
        codexG.paintComponent(g);
       
        h.dessinCartes(g, width, height);
        h.dessinSceptre(g, width, height);
    }
    
}
