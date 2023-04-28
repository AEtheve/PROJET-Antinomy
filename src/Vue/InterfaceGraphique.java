package Vue;

import javax.swing.*;
import java.awt.*;
import Global.Configuration;
import Modele.Carte;
import Modele.Codex;
import Modele.Humain;

import Modele.Jeu;
import Modele.Plateau;


public class InterfaceGraphique implements Runnable {
    boolean maximized;
    JFrame fenetre;
    PlateauGraphique plateau;
    HumainGraphique j;
    Jeu jeu;

    public InterfaceGraphique() {
        
    }


    public static void demarrer() {
        InterfaceGraphique vue = new InterfaceGraphique();
		// c.ajouteInterfaceUtilisateur(vue);
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


        jeu = new Jeu();
        jeu.CréerCartes();

        creationFenetre();
        creationJoueur();
        creationPlateau();
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
        // fenetre.getContentPane().setBackground(new Color(199, 175, 161));
        fenetre.setVisible(true);
        Configuration.info("Fenetre principale créée");
        
    }

    private void creationPlateau(){
        // Création du plateau
        Configuration.info("Creation du plateau");
        
        Codex codex = null; // TODO
        Plateau plateau_object = null; // TODO

        plateau = new PlateauGraphique(jeu.getCartes(), null, j);
        fenetre.add(plateau);
        Configuration.info("Plateau créé");
    }

    private void creationJoueur(){
        // Carte carte = new Carte(1, 1, 1, 1, true);
        // Carte carte1 = new Carte(1, 1, 1, 1, false);
        // Carte carte2 = new Carte(1, 1, 1, 1, false);
        // Carte carte3 = new Carte(1, 1, 1, 1, false);
        Carte [] cartes = jeu.creerMain();
        
        Humain joueur = new Humain("TEST");
        joueur.setMain(cartes);
        
        Carte[] cartes2 = jeu.creerMain();
        Humain joueur2 = new Humain("TEST2");
        joueur2.setMain(cartes2);

        jeu.setHumain1(joueur);
        jeu.setHumain2(joueur2);

        j = new HumainGraphique(joueur, joueur2);

    }
}