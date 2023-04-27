package Vue;

import javax.swing.*;
import java.awt.*;
import Global.Configuration;
import Modele.Carte;
import Modele.Codex;


public class InterfaceGraphique implements Runnable {
    boolean maximized;
    JFrame fenetre;

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

        Codex codex = new Codex(0, 0, 0, 0, 0);
        Carte carte1 = new Carte(1, 1, 1, 1, true, true);
        Carte [] cartes = {carte1, carte1, carte1, carte1, carte1, carte1, carte1, carte1, carte1, carte1, carte1, carte1, carte1, carte1, carte1};

        PlateauGraphique plateau = new PlateauGraphique(cartes, codex);
        fenetre.add(plateau);
        
    }

    private void creationPlateau(){
        // Création du plateau
        Configuration.info("Creation du plateau");
        Carte carte = new Carte(1, 1, 1, 1, true, true);
        Carte [] cartes = {carte, carte, carte, carte, carte, carte, carte, carte ,carte};

        PlateauGraphique plateau = new PlateauGraphique(cartes, null);
        fenetre.add(plateau);
        Configuration.info("Plateau créé");
    }
}