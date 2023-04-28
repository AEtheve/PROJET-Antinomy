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
        // hsl(0, 0%, 90%)
        fenetre.getContentPane().setBackground(new Color(199, 223, 197));
        fenetre.setVisible(true);
        Configuration.info("Fenetre principale créée");
        
    }

    private void creationPlateau(){
        // Création du plateau
        Configuration.info("Creation du plateau");
        
        Codex codex = null; // TODO
        Plateau plateau_object = null; // TODO

        Jeu jeu = new Jeu(j.joueur1, j.joueur2, plateau_object);
        jeu.CréerCartes();

        plateau = new PlateauGraphique(jeu.getCartes(), null, j);
        fenetre.add(plateau);
        Configuration.info("Plateau créé");
    }

    private void creationJoueur(){
        // Carte carte = new Carte(1, 1, 1, 1, true);
        Carte carte1 = new Carte(1, 1, 1, 1, false);
        Carte carte2 = new Carte(1, 1, 1, 1, false);
        Carte carte3 = new Carte(1, 1, 1, 1, false);
        Carte [] cartes = {carte1, carte2, carte3};
        
        Humain joueur = new Humain("TEST", cartes);
        
        Carte carte_2_1 = new Carte(4, 1, 1, 1, true);
        Carte carte_2_2 = new Carte(4, 2, 2, 1, true);
        Carte carte_2_3 = new Carte(4, 3, 4, 1, true);
        Carte [] cartes2 = {carte_2_1, carte_2_2, carte_2_3};
        Humain joueur2 = new Humain("TEST2", cartes2);

        j = new HumainGraphique(joueur, joueur2);

    }
}