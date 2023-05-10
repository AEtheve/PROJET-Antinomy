package Vue;

import javax.swing.*;

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

    CarteGraphique[] cartesG1;
    CarteGraphique[] cartesG2;
    CarteGraphique[] continuumG;
    int width, height;
    CodexGraphique codex;

    SceptreGraphique sceptre1, sceptre2;

    int sceptreJ1, sceptreJ2;

    ContinuumGraphique(Jeu jeu, ControleurJoueur ctrl, HashMap<String, Image> imagesCache) {
        this.jeu = jeu;
        this.deck = jeu.getDeck();
        this.continuum = deck.getContinuum();
        this.ctrl = ctrl;
        this.imagesCache = imagesCache;
        continuumG = new CarteGraphique[continuum.length];
        sceptreJ1 = deck.getSceptre(Jeu.JOUEUR_1);
        sceptreJ2 = deck.getSceptre(Jeu.JOUEUR_2);
    }

    void init() {
        Carte[] mainJ1 = jeu.getMain(Jeu.JOUEUR_1);
        Carte[] mainJ2 = jeu.getMain(Jeu.JOUEUR_2);

        cartesG1 = new CarteGraphique[mainJ1.length];
        cartesG2 = new CarteGraphique[mainJ2.length];

        Carte carteCourante;
        for (int i = 0; i < mainJ1.length + cartesG2.length; i++) {
            if (i < mainJ1.length) {
                carteCourante = mainJ1[i];
            } else {
                carteCourante = mainJ2[i - mainJ1.length];
            }
            CarteGraphique carte = new CarteGraphique(ctrl, carteCourante, "Main", 0, 0, 0, 0, imagesCache);

            if (jeu.getDeck().getSceptre(Jeu.JOUEUR_1) != -1 && jeu.getDeck().getSceptre(Jeu.JOUEUR_2) != -1) {
                if (jeu.getTour() == Jeu.JOUEUR_1) {
                    carte.setSelectable(true);
                }
            }
            if (i < mainJ1.length) {
                cartesG1[i] = carte;
            } else {
                cartesG2[i - mainJ1.length] = carte;
            }
            this.add(carte);
        }

        for (int i = 0; i < continuum.length; i++) {
            CarteGraphique carte = new CarteGraphique(ctrl, continuum[i], "Continuum", 0, 0, 0, 0, imagesCache);
            continuumG[i] = carte;
            carte.setSelectable(carte.carte.getColor() == deck.getCodex().getIndex());
            this.add(carte);
        }

        codex = new CodexGraphique(deck.getCodex(), 0, 0, 0, 0, imagesCache);
        this.add(codex);

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

        sceptre1 = new SceptreGraphique(0, 0, width, height, imagesCache, false);
        sceptre2 = new SceptreGraphique(0, 0, width, height, imagesCache, true);
        this.add(sceptre1);
        this.add(sceptre2);
    }

    public void miseAJour() {
        this.repaint();

        if (jeu.getDeck().getSceptre(Jeu.JOUEUR_1) != -1 && jeu.getDeck().getSceptre(Jeu.JOUEUR_2) != -1) {
            for (int i = 0; i < cartesG1.length; i++) {
                if (cartesG1[i] != null) {
                    cartesG1[i].setSelectable(jeu.getTour() == Jeu.JOUEUR_1);
                }
            }
            for (int i = 0; i < cartesG2.length; i++) {
                if (cartesG2[i] != null) {
                    cartesG2[i].setSelectable(jeu.getTour() == Jeu.JOUEUR_2);
                }
            }
        }
        for (int i = 0; i < cartesG1.length; i++) {
            cartesG1[i].carte = jeu.getMain(Jeu.JOUEUR_1)[i];
        }
        for (int i = 0; i < cartesG2.length; i++) {
            cartesG2[i].carte = jeu.getMain(Jeu.JOUEUR_2)[i];
        }
        for (int i = 0; i < continuum.length; i++) {
            continuumG[i].carte = continuum[i];
        }
        sceptreJ1 = deck.getSceptre(Jeu.JOUEUR_1);
        sceptreJ2 = deck.getSceptre(Jeu.JOUEUR_2);

        for (int j = 0; j < continuum.length; j++) {
            boolean selectable = false;
            if (cartesPossibles != null) {
                for (int i = 0; i < cartesPossibles.length; i++) {
                    if (continuum[j] == cartesPossibles[i]) {
                        selectable = true;
                    }
                }
            }
            if (jeu.getDeck().getSceptre(Jeu.JOUEUR_1) == -1 || jeu.getDeck().getSceptre(Jeu.JOUEUR_2) == -1) {
                continuumG[j].setSelectable(continuumG[j].carte.getColor() == deck.getCodex().getIndex());
            } else{
            continuumG[j].setSelectable(selectable);
            }
        }
    }

    public void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();

        paintMains(width, height);
        paintContinuum(width, height);

        paintCodex(width, height);
        paintSceptres(width, height);

        g.setColor(new Color(199, 175, 161));
        g.fillRect(0, 0, width, height);

        // affichage des scores sous forme de texte:
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 20));

        g.drawString("Score Joueur 1 : " + Compteur.getInstance().getJ1Points(), 10, height - 50);
        g.drawString("Score Joueur 2 : " + Compteur.getInstance().getJ2Points(), 10, 50);
    }

    private void paintCodex(int width, int height) {
        int tailleX = width / 13;
        int tailleY = height / 6;

        int codexX = (width / 9) - (tailleX / 2);
        int codexY = height / 2 - tailleY / 2;

        int ratioX = 475;
        int ratioY = 700;

        codexX = codexX + (tailleX - tailleX) / 2;
        codexY = codexY + (tailleY - tailleY) / 2;

<<<<<<< Updated upstream
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
=======
        if (tailleX * ratioY > tailleY * ratioX) {
            tailleX = tailleY * ratioX / ratioY;
            codexX = codexX + (tailleX - tailleX) / 2;
        } else {
            tailleY = tailleX * ratioY / ratioX;
            codexY = codexY + (tailleY - tailleY) / 2;
>>>>>>> Stashed changes
        }

        codex.setBounds(codexX, codexY, tailleX, tailleY);
    }

    private void paintMains(int width, int height) {
        for (int i = 0; i < cartesG1.length + cartesG2.length; i++) {
            CarteGraphique carte;
            if (i < cartesG1.length) {
                carte = cartesG1[i];
            } else {
                carte = cartesG2[i - cartesG1.length];
            }
            if (carte != null) {
                int tailleY = height / 6;
                int tailleX = width / 13;
                int ratioX = 475;
                int ratioY = 700;
                int x = width / 2 + (i % cartesG1.length - 1) * tailleX + (tailleX / 9 * (i % cartesG1.length - 1));
                int y;
                if (i < cartesG1.length) {
                    y = height - tailleY - (int) (0.03 * height);
                } else {
                    y = (int) (0.03 * height);
                }

                if (tailleX * ratioY > tailleY * ratioX) {
                    tailleX = tailleY * ratioX / ratioY;
                    x = x + (tailleX - tailleX) / 2;
                } else {
                    tailleY = tailleX * ratioY / ratioX;
                    y = y + (tailleY - tailleY) / 2;
                }

                if (carte.isHover()){
                    carte.setBounds(x - (tailleX / 20), y, tailleX + (tailleX / 10), tailleY + (tailleY / 10));
                } else {
                    carte.setBounds(x, y, tailleX, tailleY);
                }
            }
<<<<<<< Updated upstream
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
=======
>>>>>>> Stashed changes
        }
    }

    private void paintContinuum(int width, int height) {
        for (int i = 0; i < continuum.length; i++) {
            CarteGraphique carte = continuumG[i];
            if (carte != null) {
                int tailleY = height / 6;
                int tailleX = width / 13;
                int ratioX = 475;
                int ratioY = 700;
                int x = tailleX + (continuum[i].getIndex() + 1) * tailleX
                        + (tailleX / 9 * (continuum[i].getIndex() + 1));
                int y = height / 2 - tailleY / 2;

                if (tailleX * ratioY > tailleY * ratioX) {
                    tailleX = tailleY * ratioX / ratioY;
                    x = x + (tailleX - tailleX) / 2;
                } else {
                    tailleY = tailleX * ratioY / ratioX;
                    y = y + (tailleY - tailleY) / 2;
                }
                carte.setBounds(x, y, tailleX, tailleY);
            }
        }
    }

    void paintSceptres(int width, int height) {
        int tailleX = width / 13;
        int tailleY = height / 6;

        int y = height / 2 - tailleY / 2;

        int sceptreX1 = tailleX + (sceptreJ1 + 1) * tailleX + (tailleX / 9 * (sceptreJ1 + 1));
        int sceptreY1 = y + tailleY + (tailleY / 9 * 2);

        sceptre1.setBounds(sceptreX1, sceptreY1, tailleX, tailleY);

        int sceptreX2 = tailleX + (sceptreJ2 + 1) * tailleX + (tailleX / 9 * (sceptreJ2 + 1));
        int sceptreY2 = y - tailleY - (tailleY / 9 * 2);

<<<<<<< Updated upstream
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

                        if (deck.getSceptre(Jeu.JOUEUR_1) == -1 || deck.getSceptre(Jeu.JOUEUR_2) == -1) {
                            if (jeu.getDeck().getCodex().getIndex() != cartes[i].carte.getColor()) {
                                cartes[i].setSelectable(false);
                            }
                        }
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
=======
        sceptre2.setBounds(sceptreX2, sceptreY2, tailleX, tailleY);
>>>>>>> Stashed changes

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