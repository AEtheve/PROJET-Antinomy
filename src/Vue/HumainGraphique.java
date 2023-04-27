package Vue;

import javax.swing.JComponent;
import javax.swing.JPanel;

import Modele.Humain;

import java.awt.*;

public class HumainGraphique extends JPanel {
    Humain joueur1, joueur2;
    SceptreGraphique sceptreG1, sceptreG2;
    CarteGraphique [] cartesG1, cartesG2;
    
    public HumainGraphique(Humain joueur1, Humain joueur2){
        // this.setOpaque(false);
        // this.setBackground(new Color(0, 0, 0, 10));

        this.joueur1 = joueur1;
        this.joueur2 = joueur2;

        this.cartesG1 = new CarteGraphique[3];
        this.cartesG2 = new CarteGraphique[3];
        for(int i = 0; i < 3; i++){
            this.cartesG1[i] = new CarteGraphique(joueur1.getCarte(i));
            this.cartesG2[i] = new CarteGraphique(joueur2.getCarte(i));
        }

        sceptreG1 = new SceptreGraphique();
        sceptreG2 = new SceptreGraphique();
    }

    public void miseAJour(){
        repaint();
    }

    public void dessinCartes(Graphics g, int width, int height){

        int tailleY = height / 6;
        int tailleX = width / 13;

        int y = height - tailleY - (int)(0.03 * height);
        int x;

        if(joueur1.getCarte(0).estVisible()){
            for(int i = 0; i < cartesG1.length; i++){
                x = width / 2 + i * tailleX + (tailleX / 9 * i);
                cartesG1[i].dessinImage(x, y, tailleX, tailleY);
                cartesG1[i].paintComponent(g);
            }
            y = - (int)(0.07 * height);
            for(int i = 0; i < cartesG2.length; i++){
                x = 3 * width / 5 + (int)(tailleX / 2.5 * (i + 1));
                cartesG2[i].dessinImage(x, y, tailleX, tailleY);
                cartesG2[i].paintComponent(g);
            }
        } else{
            for(int i = 0; i < cartesG1.length; i++){
                x = width / 2 + i * tailleX + (tailleX / 9 * i);
                cartesG2[i].dessinImage(x, y, tailleX, tailleY);
                cartesG2[i].paintComponent(g);
            }
            y = - (int)(0.07 * height);
            for(int i = 0; i < cartesG2.length; i++){
                x = width / 2 + i * tailleX + (tailleX / 9 * i);
                cartesG1[i].dessinImage(x, y, tailleX, tailleY);
                cartesG1[i].paintComponent(g);
            }
        }
    }

    public void dessinSceptre(Graphics g, int width, int height){
        int tailleY = height / 6;
        int tailleX = width / 13;

        int index = joueur1.getCurseur();
        int y = height / 2 + (int) (0.2 * tailleY);
        int x = tailleX + (index+1) * tailleX + (tailleX / 9 * (index+1));

        sceptreG1.dessinImage(x, y, tailleX, tailleY);
        sceptreG1.paintComponent(g);

        index = joueur2.getCurseur();
        x = tailleX + (index+1) * tailleX + (tailleX / 9 * (index+1));
        y = height / 2 - (int) (2.2 * tailleY);

        sceptreG2.dessinImage(x, y, tailleX, tailleY);
        sceptreG2.paintComponent(g);

    }
    
}
