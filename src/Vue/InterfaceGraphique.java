package Vue;

import javax.swing.*;
import java.awt.*;

import Modele.Jeu;
import Controleur.Controleur;

public class InterfaceGraphique implements Runnable{
    Jeu jeu;
    JFrame fenetre;
    boolean maximized;
    Controleur ctrl;

    InterfaceGraphique(Jeu j,Controleur c){
        this.jeu = j;
        this.ctrl = c;
    }

    public static void demarrer(Jeu j,Controleur c){
        InterfaceGraphique ig = new InterfaceGraphique(j,c);
        ctrl.ajoute(ig);    
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
