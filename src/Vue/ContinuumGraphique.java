package Vue;

import javax.swing.*;

import Controleur.AdaptateurSouris;
import Controleur.ControleurJoueur;
import Modele.Carte;
import Modele.Compteur;
import Modele.Deck;
import Modele.Jeu;

import java.awt.*;
import java.util.HashMap;

public class ContinuumGraphique extends JPanel {
    Jeu jeu;
    Deck deck;
    Carte[] continuum;
    Carte[] cartesPossibles;
    int selectedCarte1 = -1;
    int selectedCarte2 = -1;

    JFrame fenetre;

    ControleurJoueur ctrl;
    HashMap<String, Image> imagesCache = new HashMap<String, Image>();

    ContinuumGraphique(Jeu jeu, ControleurJoueur ctrl, HashMap<String, Image> imagesCache) {
        this.jeu = jeu;
        this.deck = jeu.getDeck();
        this.continuum = deck.getContinuum();
        this.ctrl = ctrl;
        this.imagesCache = imagesCache;
    }

    public void miseAJour() {
        this.removeAll();
        this.revalidate();
        this.repaint();
    }

    public void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();

        int tailleY = height / 6;
        int tailleX = width / 13;

        int y = height / 2 - tailleY / 2; // Centre de la fenêtre

        CarteGraphique[] cartes = new CarteGraphique[continuum.length];

        paintContinuum(width, height, tailleX, y, cartes);
        paintCodex(width, tailleX, y);
        paintSceptres(width, height, tailleY, tailleX, y);
        paintMains(width, height, tailleY, tailleX);

        g.setColor(new Color(199, 175, 161));
        g.fillRect(0, 0, width, height);

        // affichage des scores sous forme de texte:
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 20));

        g.drawString("Score Joueur 1 : " + Compteur.getInstance().getJ1Points(), 10, height - 50);
        g.drawString("Score Joueur 2 : " + Compteur.getInstance().getJ2Points(), 10, 50);
    }

    private void paintMains(int width, int height, int tailleY, int tailleX) {
        int y;
        int x;
        Carte[] mainJ1 = jeu.getMain(Jeu.JOUEUR_1);
        Carte[] mainJ2 = jeu.getMain(Jeu.JOUEUR_2);

        CarteGraphique[] cartesG1 = new CarteGraphique[mainJ1.length];
        CarteGraphique[] cartesG2 = new CarteGraphique[mainJ2.length];

        for (int i = 0; i < mainJ1.length; i++) {
            x = width / 2 + (i - 1) * tailleX + (tailleX / 9 * (i - 1));
            y = height - tailleY - (int) (0.03 * height); // Centre de la fenêtre
            CarteGraphique carte = new CarteGraphique(mainJ1[i], x, y, width, height, imagesCache);

            if (jeu.getDeck().getSceptre(Jeu.JOUEUR_1) != -1 && jeu.getDeck().getSceptre(Jeu.JOUEUR_2) != -1) {
                if (jeu.getTour() == Jeu.JOUEUR_1) {
                    carte.setSelectable(true);
                }
            }
            cartesG1[i] = carte;
            if (i == selectedCarte1) {
                carte.setHover(true);
            }
            this.add(carte);

            this.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
                public void mouseMoved(java.awt.event.MouseEvent evt) {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    for (int i = 0; i < cartesG1.length; i++) {
                        if (cartesG1[i] != null) {
                            if (cartesG1[i].isHover() && i != selectedCarte1) {
                                cartesG1[i].setHover(false);
                                cartesG1[i].repaint();
                            }
                        }
                    }
                }
            });

            carte.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
                public void mouseMoved(java.awt.event.MouseEvent evt) {
                    if (!carte.isHover()) {
                        carte.setHover(true);
                        carte.repaint();
                        setCursor(new Cursor(Cursor.HAND_CURSOR));
                    }
                }
            });

            carte.addMouseListener(new AdaptateurSouris(mainJ1[i], ctrl, "Main1"));
        }

        for (int i = 0; i < mainJ2.length; i++) {
            x = width / 2 + (i - 1) * tailleX + (tailleX / 9 * (i - 1));
            y = (int) (0.03 * height);
            CarteGraphique carte = new CarteGraphique(mainJ2[i], x, y, width, height, imagesCache);

            if (jeu.getDeck().getSceptre(Jeu.JOUEUR_1) != -1 && jeu.getDeck().getSceptre(Jeu.JOUEUR_2) != -1) {
                if (jeu.getTour() == Jeu.JOUEUR_2) {
                    carte.setSelectable(true);
                }
            }
            cartesG2[i] = carte;
            if (i == selectedCarte2) {
                carte.setHover(true);
            }
            this.add(carte);

            this.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
                public void mouseMoved(java.awt.event.MouseEvent evt) {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    for (int i = 0; i < cartesG2.length; i++) {
                        if (cartesG2[i] != null) {
                            if (cartesG2[i].isHover() && i != selectedCarte2) {
                                cartesG2[i].setHover(false);
                                cartesG2[i].repaint();
                            }
                        }
                    }
                }
            });

            carte.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
                public void mouseMoved(java.awt.event.MouseEvent evt) {
                    if (!carte.isHover()) {
                        carte.setHover(true);
                        carte.repaint();
                        setCursor(new Cursor(Cursor.HAND_CURSOR));
                    }
                }
            });

            carte.addMouseListener(new AdaptateurSouris(mainJ2[i], ctrl, "Main2"));
        }
    }

    private void paintSceptres(int width, int height, int tailleY, int tailleX, int y) {
        int sceptreJ1 = deck.getSceptre(Jeu.JOUEUR_1);
        int sceptreJ2 = deck.getSceptre(Jeu.JOUEUR_2);

        int sceptreX1 = tailleX + (sceptreJ1 + 1) * tailleX + (tailleX / 9 * (sceptreJ1 + 1));
        int sceptreY1 = y + tailleY + (tailleY / 9 * 2);

        int sceptreX2 = tailleX + (sceptreJ2 + 1) * tailleX + (tailleX / 9 * (sceptreJ2 + 1));
        int sceptreY2 = y - tailleY - (tailleY / 9 * 2);

        SceptreGraphique sceptre1 = new SceptreGraphique(sceptreX1, sceptreY1, width, height, imagesCache, false);
        SceptreGraphique sceptre2 = new SceptreGraphique(sceptreX2, sceptreY2, width, height, imagesCache, true);
        this.add(sceptre1);
        this.add(sceptre2);
    }

    private void paintCodex(int width, int tailleX, int y) {
        int codexX = (width / 9) - (tailleX / 2);
        CodexGraphique codex = new CodexGraphique(deck.getCodex(), codexX, y, getWidth(), getHeight(), imagesCache);
        this.add(codex);
    }

    private void paintContinuum(int width, int height, int tailleX, int y, CarteGraphique[] cartes) {
        int x;
        for (int i = 0; i < continuum.length; i++) {
            x = tailleX + (continuum[i].getIndex() + 1) * tailleX + (tailleX / 9 * (continuum[i].getIndex() + 1));
            if (continuum[i] != null) {
                CarteGraphique carte = new CarteGraphique(continuum[i], x, y, width, height, imagesCache);
                cartes[i] = carte;
                this.add(carte);

                carte.addMouseListener(new AdaptateurSouris(continuum[i], ctrl, "Continuum"));

                this.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
                    public void mouseMoved(java.awt.event.MouseEvent evt) {
                        for (int i = 0; i < cartes.length; i++) {
                            if (cartes[i] != null) {
                                if (cartes[i].isHover()) {
                                    cartes[i].setHover(false);
                                    cartes[i].repaint();
                                }
                            }
                        }
                    }
                });

                carte.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
                    public void mouseMoved(java.awt.event.MouseEvent evt) {
                        if (!carte.isHover()) {
                            carte.setHover(true);
                            carte.repaint();
                            setCursor(new Cursor(Cursor.HAND_CURSOR));
                        }
                    }
                });

            }
        }

        if (cartesPossibles == null) {

            if (ctrl.getState() == ControleurJoueur.WAITPLAYER1SWAP
                    || ctrl.getState() == ControleurJoueur.WAITPLAYER2SWAP) {
                int sceptre = deck.getSceptre(jeu.getTour());
                if (sceptre != -1) {
                    int[] indices = {sceptre - 1, sceptre + 1, sceptre - 2, sceptre + 2, sceptre - 3, sceptre + 3};
                    for (int index : indices) {
                        if (index >= 0 && index < cartes.length) {
                            cartes[index].setSelectable(true);
                        }
                    }
                }
            } else {
                for (int i = 0; i < cartes.length; i++) {
                    if (cartes[i] != null) {
                        cartes[i].setSelectable(true);
                    }
                }
            }
        } else {
            for (int i = 0; i < cartes.length; i++) {
                if (cartes[i] != null) {
                    for (int j = 0; j < cartesPossibles.length; j++) {
                        if (cartesPossibles[j] != null) {
                            if (cartes[i].carte == cartesPossibles[j]) {
                                cartes[i].setSelectable(true);
                            }
                        }
                    }
                }
            }
        }

    }

    void setCartesPossibles(Carte[] cartesPossibles) {
        this.cartesPossibles = cartesPossibles;
    }

    void setSelectCarteMain1(int index) {
        selectedCarte1 = index;
    }

    void setSelectCarteMain2(int index) {
        selectedCarte2 = index;
    }
}