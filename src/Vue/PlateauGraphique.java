package Vue;

import javax.swing.*;

import Modele.Carte;
import Modele.Codex;
import java.awt.*;
import java.util.ArrayList;

public class PlateauGraphique extends JComponent {
    CarteGraphique [] cartesG;
    Carte [] cartes;
    CodexGraphique codexG;
    PoseGraphique pose;
    HumainGraphique h;
    boolean refresh;

    public PlateauGraphique(Carte [] cartes, Codex codex, HumainGraphique h){
        this.setLayout(null);
        this.codexG = new CodexGraphique(codex);
        this.h = h;

        this.pose = new PoseGraphique();

        this.cartesG = new CarteGraphique[cartes.length];
        for (int i = 0; i < cartes.length; i++) {
            this.cartesG[i] = new CarteGraphique(cartes[i]);
        }
        this.cartes = cartes;
    }

    public void miseAJour() {
        // System.out.println("miseAJour");
        repaint();
    }

    public void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();

        int tailleY = height / 6;
        int tailleX = width / 13;

        int y = height / 2 - tailleY;
        int x;

        for(int i = 0; i < cartesG.length; i++) {
            x = tailleX + (i+1) * tailleX + (tailleX / 9 * (i+1));
            cartesG[i].dessinImage(g, x, y, tailleX, tailleY,false);
        }

        x = tailleX;
        codexG.dessinImage(g, x, y, tailleX, tailleY);
       
        h.dessinCartes(g, width, height);
        h.dessinSceptre(g, width, height);
        
            if (!refresh && h.getCarteSelectionneeSymbole() != -1) {
                
            ArrayList <Integer> selection = new ArrayList<Integer>();
            for (int i = 0; i < h.joueur1.getCurseur(); i++) {
                // Ajout des déplacements gauche : symboles égaux
                if (cartes[i].getSymbole() == h.getCarteSelectionneeSymbole() || cartes[i].getCouleur() == h.getCarteSelectionneeCouleur()) {
                    selection.add(i);
                }
            }
            if (h.joueur1.getCurseur()+h.getCarteSelectionneeValeur()<cartes.length){
                    selection.add(h.joueur1.getCurseur()+h.getCarteSelectionneeValeur());
            }
            pose.setSelection(selection);
            refresh = true;
        }

        pose.dessin(g, width, height);

    }

    public void reset_refresh(){
        refresh = false;
    }
    
}
