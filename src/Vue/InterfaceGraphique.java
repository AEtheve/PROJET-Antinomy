package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import Global.Configuration;
import Modele.Carte;
import Modele.Codex;
import Modele.Humain;


import Modele.Jeu;

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
        
        Codex codex = new Codex(0, 0, 0, 0, true);

        plateau = new PlateauGraphique(jeu.getCartes(), codex, j);
        fenetre.add(plateau);
        Configuration.info("Plateau créé");
    }

    private void creationJoueur(){
        Carte [] cartes = jeu.creerMain();
        
        Humain joueur = new Humain("TEST");
        joueur.setMain(cartes);
        
        Carte[] cartes2 = jeu.creerMain();
        Humain joueur2 = new Humain("TEST2");
        joueur2.setMain(cartes2);
        joueur2.retourneMain();

        jeu.setHumain1(joueur);
        jeu.setHumain2(joueur2);

        j = new HumainGraphique(joueur, joueur2);

        fenetre.addMouseMotionListener(new java.awt.event.MouseMotionListener() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                int width = fenetre.getWidth();
                int height = fenetre.getHeight();
                int tailleY = height / 6;
                    int tailleX = width / 14;

                    int y = height - tailleY - (int)(0.03 * height);
                    int x;

                    for(int i = 0; i < cartes2.length; i++) {
                        x = width / 2 + i * tailleX + (tailleX / 9 * i);
                        if (evt.getX() > x && evt.getX() < x + tailleX && evt.getY() > y && evt.getY() < y + tailleY) {
                            j.joueur1.getCarte(i).setSurvolee(true);
                        } else {
                            j.joueur1.getCarte(i).setSurvolee(false);
                        }

                        plateau.miseAJour();
                    }
            }

            public void mouseDragged(java.awt.event.MouseEvent evt) {
                int width = fenetre.getWidth();
                int height = fenetre.getHeight();
                int tailleY = height / 6;
                    int tailleX = width / 14;

                    int y = height - tailleY - (int)(0.03 * height);
                    int x;

                    for(int i = 0; i < cartes2.length; i++) {
                        x = width / 2 + i * tailleX + (tailleX / 9 * i);
                        if (evt.getX() > x && evt.getX() < x + tailleX && evt.getY() > y && evt.getY() < y + tailleY) {
                            j.joueur1.getCarte(i).setSurvolee(true);
                            plateau.miseAJour();
                        } else {
                            j.joueur1.getCarte(i).setSurvolee(false);
                            plateau.miseAJour();
                        }
                    }
            }
        });

        fenetre.addMouseListener(new MouseListener() {
            public void mousePressed(MouseEvent e) {
                int width = fenetre.getWidth();
                int height = fenetre.getHeight();
                int tailleY = height / 6;
                int tailleX = width / 14;

                int y = height - tailleY - (int)(0.03 * height);
                int x;

                for(int i = 0; i < cartes2.length; i++) {
                    x = width / 2 + i * tailleX + (tailleX / 9 * i);
                    if (e.getX() > x && e.getX() < x + tailleX && e.getY() > y && e.getY() < y + tailleY) {
                        j.joueur1.getCarte(i).setSelectionnee(true);
                    }
                    else{
                        j.joueur1.getCarte(i).setSelectionnee(false);
                    }
                }
            }

            public void mouseClicked(MouseEvent e) { }
            public void mouseReleased(MouseEvent e) { }
            public void mouseEntered(MouseEvent e) { }
            public void mouseExited(MouseEvent e) { }
        });
    }
}