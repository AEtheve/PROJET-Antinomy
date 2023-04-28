package Vue;

import javax.swing.*;
import java.awt.*;
import Global.Configuration;
import Modele.Carte;
import Modele.Humain;


public class InterfaceGraphique implements Runnable {
    boolean maximized;
    JFrame fenetre;
    PlateauGraphique plateau;
    HumainGraphique j;

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
        fenetre.setVisible(true);
        Configuration.info("Fenetre principale créée");
        
    }

    private void creationPlateau(){
        // Création du plateau
        Configuration.info("Creation du plateau");
        Carte carte = new Carte(1, 1, 1, 1, true);
        Carte [] cartes = {carte, carte, carte, carte, carte, carte, carte, carte ,carte};

        plateau = new PlateauGraphique(cartes, null, j);
        // fenetre.getContentPane().add(plateau);
        fenetre.add(plateau);
        Configuration.info("Plateau créé");
    }

    private void creationJoueur(){
        Carte carte = new Carte(1, 1, 1, 1, true);
        Carte [] cartes = {carte, carte, carte};
        Humain joueur = new Humain("TEST", cartes);
        
        Carte carte2 = new Carte(1, 1, 1, 1, false);
        Carte [] cartes2 = {carte2, carte2, carte2};
        Humain joueur2 = new Humain("TEST2", cartes2);

        j = new HumainGraphique(joueur, joueur2);
        // fenetre.add(j);
        // fenetre.getContentPane().add(j);

    }
}