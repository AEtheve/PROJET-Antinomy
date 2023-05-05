package Vue;

import Modele.Coup;
import Modele.Jeu;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

import Controleur.ControleurJoueur;
import Global.Configuration;

public class InterfaceGraphique implements Runnable, InterfaceUtilisateur{
    Jeu jeu;
    ControleurJoueur ctrl;
    boolean maximized;
    JFrame fenetre;

    PlateauGraphique plateauGraphique;
    

    public InterfaceGraphique(Jeu jeu, ControleurJoueur ctrl){
        this.jeu = jeu;
        this.ctrl = ctrl;
    }

    public static void demarrer(Jeu j, ControleurJoueur ctrl){
		InterfaceGraphique vue = new InterfaceGraphique(j, ctrl);
        ctrl.ajouteInterfaceUtilisateur(vue);
		SwingUtilities.invokeLater(vue);
	}

    public void toggleFullscreen() {
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = env.getDefaultScreenDevice();
		if (maximized) {
			device.setFullScreenWindow(null);
			maximized = false;
		} else {
			device.setFullScreenWindow(fenetre);
			maximized = true;
		}
	}

    public void run(){
        creationFenetre();
        JPanel gameMenu = new JPanel();

        gameMenu.setLayout(new BoxLayout(gameMenu, BoxLayout.Y_AXIS));
        
        plateauGraphique = new PlateauGraphique(jeu, ctrl);
        gameMenu.add(plateauGraphique);
    
        fenetre.setContentPane(gameMenu);
        // fenetre.setContentPane(autreMenu); // TODO: faire autre menu
        
        
    }

    private void creationFenetre(){
        // Création de la fenêtre principale
        Configuration.info("Creation de la fenetre principale");
        fenetre = new JFrame();
        fenetre.setTitle("Antinomy");
        fenetre.setSize(800, 600);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setExtendedState(JFrame.MAXIMIZED_BOTH);
        fenetre.setResizable(true);
        fenetre.getContentPane().setBackground(new Color(199, 175, 161));
        fenetre.setVisible(true);
        fenetre.pack();
        Configuration.info("Fenetre principale créée");
    }


    @Override
    public void miseAJour(){
        plateauGraphique.miseAJour();
    }

    @Override
    public void animeCoup(Coup coup) {
        throw new UnsupportedOperationException("Unimplemented method 'animeCoup'");
    }
}