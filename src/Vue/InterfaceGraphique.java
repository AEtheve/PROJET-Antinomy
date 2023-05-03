package Vue;

import javax.swing.*;
import java.awt.*;

import Modele.Jeu;

public class InterfaceGraphique implements Runnable{
    Jeu jeu;
    JFrame fenetre;
    boolean maximized;

    InterfaceGraphique(Jeu j){
        this.jeu = j;
    }

    public static void demarrer(Jeu j){
        InterfaceGraphique ig = new InterfaceGraphique(j);
        SwingUtilities.invokeLater(ig);
    }

    public void toogleFullScreen(){
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        if(maximized){
            device.setFullScreenWindow(null);
            maximized = false;
        } else {
            device.setFullScreenWindow(fenetre);
            maximized = true;
        }
    }

    public void run(){
        creationFenetre();
    }

    void creationFenetre(){
        fenetre = new JFrame("Antinomy");

        // fenetre.add(new DeckGraphique());

        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setSize(800, 600);
        fenetre.setExtendedState(JFrame.MAXIMIZED_BOTH);
        fenetre.setVisible(true);
    }

}
