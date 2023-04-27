package Vue;

import javax.swing.JComponent;

import Modele.Humain;

import java.awt.*;

public class HumainGraphique extends JComponent {
    Humain joueur1, joueur2;
    CarteGraphique [] cartesG1, cartesG2;
    
    public HumainGraphique(Humain joueur1, Humain joueur2){
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;

        this.cartesG1 = new CarteGraphique[3];
        this.cartesG2 = new CarteGraphique[3];
        for(int i = 0; i < 3; i++){
            this.cartesG1[i] = new CarteGraphique(joueur1.getCarte(i));
            this.cartesG2[i] = new CarteGraphique(joueur2.getCarte(i));
        }
    }

    public void miseAJour(){
        repaint();
    }

    public void paintComponent(Graphics g){
        int width = getWidth();
        int height = getHeight();

        int tailleY = height / 6;
        int tailleX = width / 13;

        int y = height - tailleY;
        int x;

        if()
    }
    
}
