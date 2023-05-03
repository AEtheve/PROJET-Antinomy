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
    AideGraphique AideGraphique;
    ScoreGraphique Score;
    boolean refresh;

    public PlateauGraphique(Carte [] cartes, Codex codex, HumainGraphique h){ // Constructeur
        this.setLayout(null);
        this.codexG = new CodexGraphique(codex);
        this.h = h;

        this.pose = new PoseGraphique();

        this.AideGraphique = new AideGraphique();
        this.Score = new ScoreGraphique();

        this.cartesG = new CarteGraphique[cartes.length];
        for (int i = 0; i < cartes.length; i++) {
            this.cartesG[i] = new CarteGraphique(cartes[i]);
        }
        this.cartes = cartes;
    }

    public void miseAJour() {
        repaint();
    }

    public void paintComponent(Graphics g) {    // Dessin du plateau
        int width = getWidth(); 
        int height = getHeight();

        int tailleY = height / 6;
        int tailleX = width / 13;

        int y = height / 2 - tailleY / 2; // Centre de la fenêtre
        int x;


        for(int i = 0; i < cartesG.length; i++) { // Dessin des cartes
            x = tailleX + (i+1) * tailleX + (tailleX / 9 * (i+1));
            cartesG[i].dessinImage(g, x, y, tailleX, tailleY,false);
        }

        x = tailleX;
        codexG.dessinImage(g, x, y, tailleX, tailleY); // dessin du codex

        int tailleaideX = width / 26;
        int tailleaideY = height / 12;
        int aideX = (int) (width*0.025);
        int aideY = (int) (height*0.88);
        AideGraphique.dessinImage(g, aideX , aideY, tailleaideX, tailleaideY); // dessin du bouton d'aide
        Score.dessinImage(g, (int) (width*0.175) , (int) (height*0.80), (int) (width/5) , (int) (height/6) ); // dessin du score
        Score.dessinImage(g, (int) (width*0.175) , (int) (height*0.025), (int) (width/5) , (int) (height/6) ); // dessin du score
       
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
