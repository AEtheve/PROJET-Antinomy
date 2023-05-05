package Vue;


import javax.imageio.ImageIO;
import javax.swing.*;

import Modele.Carte;
import Modele.Deck;
import Modele.Jeu;
import Modele.Main;

import java.awt.*;
import java.io.InputStream;

public class PlateauGraphique extends JComponent {
    Jeu jeu;
    PlateauGraphique(Jeu jeu){
        this.jeu = jeu;
    }
	public void miseAJour() {
		repaint();
	}
    
    public void paintComponent(Graphics g) {
        Graphics2D drawable = (Graphics2D) g;
        Deck deck = jeu.getDeck();
        Carte [] plateau = deck.getPlateau();
        
        
        int width = getWidth(); 
        int height = getHeight();

        int tailleY = height / 6;
        int tailleX = width / 13;

        int y = height / 2 - tailleY / 2; // Centre de la fenêtre
        int x;

        // Affichage du plateau
        // Affichage du codex
        int codexX = (width / 9) - (tailleX / 2);
        CodexGraphique codex = new CodexGraphique(deck.getCodex(), codexX , y, getWidth(), getHeight());
        codex.paintComponent(drawable);
        for (int i = 0; i < plateau.length; i++) {
            x = tailleX + (plateau[i].getIndex() +1) * tailleX + (tailleX / 9 * (plateau[i].getIndex() +1));
            if (plateau[i] != null) {
                CarteGraphique carte = new CarteGraphique(plateau[i], x, y, width, height);
                carte.paintComponent(drawable);
            }
        }

        // affichage des sceptres
        int sceptreJ1 = deck.getSceptre(Jeu.JOUEUR_1);
        int sceptreJ2 = deck.getSceptre(Jeu.JOUEUR_2);

        int sceptreX1 =  tailleX + (sceptreJ1 +1) * tailleX + (tailleX / 9 * (sceptreJ1 +1));
        int sceptreY1 = y + tailleY + (tailleY / 9 * 2);
        
        int sceptreX2 = tailleX + (sceptreJ2 +1) * tailleX + (tailleX / 9 * (sceptreJ2 +1));
        int sceptreY2 = y - tailleY - (tailleY / 9 * 2);

        
        SceptreGraphique sceptre1 = new SceptreGraphique(sceptreX1, sceptreY1+(height/6), width, -height);
        SceptreGraphique sceptre2 = new SceptreGraphique(sceptreX2, sceptreY2, width, height);
        sceptre1.paintComponent(drawable);
        sceptre2.paintComponent(drawable);

    }

    void afficheCarte(Graphics2D drawable, Carte carte, int x, int y, int tailleX, int tailleY) {
        String imageStr = carte.getValue() +"_"+ Carte.symboleToString(carte.getSymbol()) +"_"+ Carte.couleurToString(carte.getColor()) +".png";
        try {
            InputStream is = getClass().getResourceAsStream("/res/Images/"+imageStr);
            Image img = ImageIO.read(is);
            drawable.drawImage(img, x, y, tailleX, tailleY, null);
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement de l'image : " + imageStr);
        }
    }
}
