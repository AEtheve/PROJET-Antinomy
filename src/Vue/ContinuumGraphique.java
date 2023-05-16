package Vue;

import javax.imageio.ImageIO;
import javax.swing.*;

import Controleur.ControleurMediateur;
import Global.Configuration;
import Modele.Carte;
import Modele.Compteur;
import Modele.Deck;
import Modele.Jeu;

import java.awt.*;
import java.awt.event.*;

import java.util.HashMap;

public class ContinuumGraphique extends JPanel {
    Carte[] continuum;

    ControleurMediateur ctrl;
    HashMap<String, Image> imagesCache = new HashMap<String, Image>();

    CarteGraphique[] cartesG1;
    CarteGraphique[] cartesG2;
    CarteGraphique[] continuumG;
    int width, height;
    CodexGraphique codex;

    SceptreGraphique sceptre1, sceptre2;
    Retour retour;
    Apres apres;
    // Engrenage engrenage;
    MenuButton engrenage;
    MenuOnGameGraphique menuOnGameGraphique;
    Indice indice;

    int sceptreJ1, sceptreJ2;

    int scoreJ1 = 0, scoreJ2 = 0;

    int tailleX, tailleY;

    Image background = Configuration.lisImage("game_background", imagesCache);

    Deck interfaceDeck;
    Carte[] interfaceMainJ1;
    Carte[] interfaceMainJ2;

    Boolean interfaceTour;

    Boolean continuumInverse = false;

    ParticleComponent particleComponent;

    Boolean particleTarget = Jeu.JOUEUR_1;


    JComponent maskPanel = new JComponent() {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (ctrl.getState() == ControleurMediateur.ONLINEWAITPLAYERS) {
                g.setColor(new Color(0, 0, 0, 100));
                g.fillRect(0, 0, getWidth(), getHeight());
        
                g.setColor(new Color(255, 255, 255, 255));
                g.fillRect(getWidth() / 2 - 200, getHeight() / 2 - 50, 400, 100);

                g.setColor(new Color(0, 0, 0, 255));
                FontMetrics fm = g.getFontMetrics();
                String message = "En attente de la connexion d'un adversaire..";
                int x = (getWidth() - fm.stringWidth(message)) / 2;
                int y = (fm.getAscent() + (getHeight() - (fm.getAscent() + fm.getDescent())) / 2);
                g.drawString(message, x, y);
            }
        }
    };

    ContinuumGraphique(ControleurMediateur ctrl, HashMap<String, Image> imagesCache) {
        this.interfaceDeck = ctrl.getInterfaceDeck();
        this.continuum = interfaceDeck.getContinuum();
        this.ctrl = ctrl;
        this.imagesCache = imagesCache;
        continuumG = new CarteGraphique[continuum.length];
        sceptreJ1 = interfaceDeck.getSceptre(Jeu.JOUEUR_1);
        sceptreJ2 = interfaceDeck.getSceptre(Jeu.JOUEUR_2);

        Timer chrono = new Timer(16, e -> {
            ctrl.tictac();
        });
        chrono.start();
    }

    void initParams(Carte[] interfaceMainJ1, Carte[] interfaceMainJ2, Deck interfaceDeck, Boolean interfaceTour,
            Boolean continuumInverse) {
        this.interfaceMainJ1 = interfaceMainJ1;
        this.interfaceMainJ2 = interfaceMainJ2;
        this.interfaceDeck = interfaceDeck;
        this.interfaceTour = interfaceTour;
        this.continuumInverse = !continuumInverse;
    }

    void initParams(Carte[] interfaceMainJ1, Carte[] interfaceMainJ2, Deck interfaceDeck, Boolean interfaceTour) {
        this.interfaceMainJ1 = interfaceMainJ1;
        this.interfaceMainJ2 = interfaceMainJ2;
        this.interfaceDeck = interfaceDeck;
        this.interfaceTour = interfaceTour;
    }

    private void initializeMainCartes() {

        cartesG1 = new CarteGraphique[interfaceMainJ1.length];
        cartesG2 = new CarteGraphique[interfaceMainJ2.length];

        createAndAddCartesG(interfaceMainJ1, cartesG1, Jeu.JOUEUR_1);
        createAndAddCartesG(interfaceMainJ2, cartesG2, Jeu.JOUEUR_2);
    }

    private void createAndAddCartesG(Carte[] main, CarteGraphique[] cartesG, boolean joueur) {
        for (int i = 0; i < main.length; i++) {
            CarteGraphique carte = new CarteGraphique(ctrl, main[i], "Main", imagesCache);
            cartesG[i] = carte;
            this.add(carte);
            setComponentZOrder(carte, 0);
        }
    }

    private void initializeContinuumCartes() {
        for (int i = 0; i < continuum.length; i++) {
            CarteGraphique carte = new CarteGraphique(ctrl, continuum[i], "Continuum", imagesCache);
            continuumG[i] = carte;
            carte.setSelectable(carte.carte.getColor() == interfaceDeck.getCodex().getIndex());
            this.add(carte);
        }
    }

    private void initializeCodex() {
        codex = new CodexGraphique(interfaceDeck.getCodex(), 0, 0, 0, 0, imagesCache);
        this.add(codex);
    }

    private void initializeMouseListener() {
        this.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                if (interfaceTour == Jeu.JOUEUR_1) {
                    clearHoverState(cartesG1, ctrl.getSelectedCarteIndex());
                    clearHoverState(cartesG2, -1);
                } else {
                    clearHoverState(cartesG2, ctrl.getSelectedCarteIndex());
                    clearHoverState(cartesG1, -1);
                }
            }
        });
        addMouseListener(new AdaptateurSouris(null, ctrl, "Background"));
    }

    private void initializeSceptres() {
        sceptre1 = new SceptreGraphique(0, 0, width, height, imagesCache, true);
        sceptre2 = new SceptreGraphique(0, 0, width, height, imagesCache, false);
        this.add(sceptre1);
        this.add(sceptre2);
    }

    public void initializeComponents() {
        initializeMainCartes();
        initializeContinuumCartes();
        initializeCodex();
        initializeMouseListener();
        initializeSceptres();
        initEngrenage();
        initIndice();
        initMenuJeu();

        if (ctrl.getHistorique() != null)
            initBoutonsHistorique();

        if (ctrl.getState() == ControleurMediateur.ONLINEWAITPLAYERS) {
            this.add(maskPanel);
            setComponentZOrder(maskPanel, 0);
        } 
    }

    private void initMenuJeu(){
        menuOnGameGraphique = new MenuOnGameGraphique(this);
    }

    private void initEngrenage() {
        // engrenage = new Engrenage(ctrl, "Engrenage", imagesCache);
        Runnable menuButton = new Runnable() {
            public void run() {
                ajouteMenu();
            }
        };
        engrenage = new MenuButton(menuButton, "Engrenage.png");
        this.add(engrenage);
    }

    private void ajouteMenu(){
        this.add(menuOnGameGraphique);
        repaint();
        revalidate();
    }

    public void enleveMenu(){
        this.remove(menuOnGameGraphique);
        repaint();
        revalidate();
    }

    private void initIndice() {
        indice = new Indice(ctrl, "Indice", imagesCache);
        this.add(indice);
    }


    private void initBoutonsHistorique() {
        retour = new Retour(ctrl, "Retour", imagesCache);
        this.add(retour);

        apres = new Apres(ctrl, "Apres", imagesCache);
        this.add(apres);
    }

    private void clearHoverState(CarteGraphique[] cartesG, int selectedCarte) {
        for (int i = 0; i < cartesG.length; i++) {
            if (cartesG[i] != null && cartesG[i].isHover() && i != selectedCarte) {
                cartesG[i].setHover(false);
            }
        }
    }

    public void miseAJour() {
        this.repaint();


        if (interfaceDeck.getSceptre(Jeu.JOUEUR_1) != -1 && interfaceDeck.getSceptre(Jeu.JOUEUR_2) != -1) {
            for (int i = 0; i < cartesG1.length; i++) {
                if (cartesG1[i] != null && ctrl.getTypeJoueur(0) == 0) {
                    cartesG1[i].setSelectable(interfaceTour == (continuumInverse ? Jeu.JOUEUR_2 : Jeu.JOUEUR_1));
                }
            }
            for (int i = 0; i < cartesG2.length; i++) {
                if (cartesG2[i] != null && ctrl.getTypeJoueur(1) == 0) {
                    cartesG2[i].setSelectable(interfaceTour == (continuumInverse ? Jeu.JOUEUR_1 : Jeu.JOUEUR_2));
                }
            }
        }
        updateCarteGMains();
        updateContinuumG();
        updateSceptresG();
        updateScoreG();
    }

    private void updateSceptresG() {
        sceptreJ1 = interfaceDeck.getSceptre(Jeu.JOUEUR_1);
        sceptreJ2 = interfaceDeck.getSceptre(Jeu.JOUEUR_2);
        updateContinuumSelectability();
    }

    private void updateScoreG() {
        boolean scoreJ1Up = scoreJ1 < Compteur.getInstance().getJ1Points();
        boolean scoreJ2Up = scoreJ2 < Compteur.getInstance().getJ2Points();

        if (scoreJ1Up || scoreJ2Up) {
            TriggerParticles(scoreJ1Up);
        }
        scoreJ1 = Compteur.getInstance().getJ1Points();
        scoreJ2 = Compteur.getInstance().getJ2Points();
    }

    private void TriggerParticles(boolean scoreJ1Up) {
        int x1 = codex.getX() + codex.getWidth() / 2;
        int y1 = codex.getY() + codex.getHeight() / 2;

        int x2;
        int y2;

        paintSceptres(getWidth(), getHeight());

        if (scoreJ1Up) {
            if (scoreJ2 != Compteur.getInstance().getJ2Points()) {
                x1 = sceptre2.getX() + sceptre2.getWidth() / 2;
                y1 = sceptre2.getY() + sceptre2.getHeight() / 2;
                setComponentZOrder(sceptre2, 2);
            }
            x2 = sceptre1.getX() + sceptre1.getWidth() / 2;
            y2 = sceptre1.getY() + sceptre1.getHeight() / 2;
            particleTarget = Jeu.JOUEUR_1;
        } else {
            if (scoreJ1 != Compteur.getInstance().getJ1Points()) {
                x1 = sceptre1.getX() + sceptre1.getWidth() / 2;
                y1 = sceptre1.getY() + sceptre1.getHeight() / 2;
                setComponentZOrder(sceptre1, 2);
            }
            x2 = sceptre2.getX() + sceptre2.getWidth() / 2;
            y2 = sceptre2.getY() + sceptre2.getHeight() / 2;
            particleTarget = Jeu.JOUEUR_2;
        }

        if (particleComponent != null) {
            remove(particleComponent);
        }
        particleComponent = new ParticleComponent(x1, y1, x2, y2);
        add(particleComponent);
        particleComponent.setBounds(0, 0, getWidth(), getHeight());

        // setComponentZOrder(codex, 0);
        // setComponentZOrder(particleComponent, 1);
        // TODO : mettre les particules devant sans rendre le composant visible pour la souris

        Timer timer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int targetX ;
                int targetY ;
                if (particleTarget == Jeu.JOUEUR_1) {
                    targetX = sceptre1.getX() + sceptre1.getWidth() / 2;
                    targetY = sceptre1.getY() + sceptre1.getHeight() / 2;
                } else {
                    targetX = sceptre2.getX() + sceptre2.getWidth() / 2;
                    targetY = sceptre2.getY() + sceptre2.getHeight() / 2;
                }
                if (particleComponent.updateParticles(targetX, targetY) == 0) {
                    remove(particleComponent);
                    repaint();
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        timer.start();
    }

    private void updateContinuumSelectability() {
        for (int j = 0; j < continuum.length; j++) {
            boolean selectable = isCartePossible(continuum[j]);
            if (interfaceDeck.getSceptre(Jeu.JOUEUR_1) == -1 || interfaceDeck.getSceptre(Jeu.JOUEUR_2) == -1) {
                continuumG[j].setSelectable(continuumG[j].carte.getColor() == interfaceDeck.getCodex().getIndex());
            } else {
                continuumG[j].setSelectable(selectable);
            }
        }
    }

    private boolean isCartePossible(Carte carte) {
        Carte cartesPossibles[] = ctrl.getCartesPossibles();
        if (cartesPossibles != null) {
            for (Carte cartePossible : cartesPossibles) {
                if (carte == cartePossible) {
                    return true;
                }
            }
        } else if (ctrl.getState() == ControleurMediateur.WAITSWAP) {
            int sceptrepos = interfaceDeck.getSceptre(interfaceTour);
            if (sceptrepos != -1) {
                int[] indices;
                if(ctrl.getSwapDroit() && ctrl.getSwapGauche()){
                    indices = new int [] {sceptrepos + 1, sceptrepos + 2, sceptrepos + 3, sceptrepos - 1, sceptrepos - 2, sceptrepos - 3 };
                } else if (ctrl.getSwapDroit()){
                    indices = new int [] {sceptrepos + 1, sceptrepos + 2, sceptrepos + 3};
                } else {
                    indices = new int [] {sceptrepos - 1, sceptrepos - 2, sceptrepos - 3 };
                }
                for (int i = 0; i < indices.length; i++) {
                    if (indices[i] >= 0 && indices[i] < continuum.length) {
                        if (carte == continuum[indices[i]]) {
                            return true;
                        }
                    }
                }

            }
        }
        return false;
    }

    private void updateContinuumG() {
        continuum = interfaceDeck.getContinuum();
        for (int i = 0; i < continuum.length; i++) {
            continuumG[i].carte = continuum[i];
            continuumG[i].miseAJour();
        }
    }

    private void updateCarteGMains() {
        updateCarteMain(cartesG1, Jeu.JOUEUR_1);
        updateCarteMain(cartesG2, Jeu.JOUEUR_2);
    }

    private void updateCarteMain(CarteGraphique[] cartesG, boolean joueur) {
        for (int i = 0; i < cartesG.length; i++) {
            clearHoverState(cartesG, interfaceTour != joueur ? -1 : ctrl.getSelectedCarteIndex());

            cartesG[i].carte = joueur == Jeu.JOUEUR_1 ? interfaceMainJ1[i] : interfaceMainJ2[i];
            if (interfaceTour == joueur && ctrl.getTypeJoueur(joueur == Jeu.JOUEUR_1 ? 0 : 1) == 0) {
                cartesG[i].adaptateurSouris.setEnable(true);
            } else {
                cartesG[i].adaptateurSouris.setEnable(false);
            }
            cartesG[i].miseAJour();
        }
    }

    public void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();

        paintMains(width, height);
        paintContinuum(width, height);

        paintCodex(width, height);
        paintSceptres(width, height);

        g.drawImage(background, 0, 0, width, height, null);

        if (retour != null)
            paintRetour(width, height);
        if (apres != null)
            paintApres(width, height);

        paintEngrenage(width, height);
        paintIndice(width, height);

        // affichage des scores sous forme de texte:
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 20));

        if(ctrl.getState() == ControleurMediateur.WAITSCEPTRE) {
            Image bulle_sceptre_aide = Configuration.lisImage("placez_votre_scèptre", imagesCache);
            g.drawImage(bulle_sceptre_aide,5*width/6, 2*height/8,(int) ( 1.1*(width/6)), height/6, null);
        }


        Image cadran = Configuration.lisImage("Cadran_joueur", imagesCache);

        Image j1 = Configuration.lisImage("J1", imagesCache);
        Image j2 = Configuration.lisImage("J2", imagesCache);
        Image tour_j1 = Configuration.lisImage("tour_joueur_1", imagesCache);
        Image tour_j2 = Configuration.lisImage("tour_joueur_2", imagesCache);
        Image diamant_vide = Configuration.lisImage("diamant_vide", imagesCache);
        Image diamant = Configuration.lisImage("diamant", imagesCache);

        int tailleXCadran = tailleX * 4;
        int tailleYCadran = tailleY;
        int posXCadran = width/2 - (tailleXCadran)/2;
        
        g.drawImage(cadran, posXCadran, height , tailleXCadran , -tailleYCadran - (int)(0.06 * height) , null);
        g.drawImage(cadran, posXCadran, 0 , tailleXCadran ,tailleYCadran + (int)(0.06 * height) , null);

        int xtour = width - width/3-width/55; ;

        g.drawImage(j1, width/4, height - height/5, (height/12)* 3, height/12, null);
        g.drawImage(j2, width/4, height/100, (height/12)* 3, height/12, null);
        // g.drawString("Score Joueur 1 : " + scoreJ1, 10, height - 50);
        // g.drawString("Score Joueur 2 : " + scoreJ2, 10, 50);
        // g.drawString("Tour : " + interfaceTour, 10, height - 20);

        if (interfaceTour == Jeu.JOUEUR_1) {
            g.drawImage(tour_j1, xtour, height - height/5, (height/6)* 2, height/6, null);
        } else {
            g.drawImage(tour_j2, xtour, height/30, (height/6)* 2, height/6, null);
        }

        int xDiamant1 = width/4 - width/64 ;
        for (int i =0 ; i < 5; i++) {
            if (i > scoreJ1 - 1) {
                g.drawImage(diamant_vide, xDiamant1 , height - height/8, (int) (height/20* 1.16), height/20, null);
            } else {
                g.drawImage(diamant, xDiamant1 ,height- height/8, (int) (height/20* 1.16), height/20, null);

            }
            xDiamant1 += width/32;
        }


        int xDiamant2 = width/4 - width/64 ;
        for (int i =0 ; i < 5; i++) {
            if (i > scoreJ2 - 1) {
                g.drawImage(diamant_vide, xDiamant2 , height/10, (int) (height/20* 1.16), height/20, null);
            } else {
                g.drawImage(diamant, xDiamant2 , height/10, (int) (height/20* 1.16), height/20, null);

            }
            xDiamant2 += width/32;
        }

        

        // g.drawImage(diamant_vide, xDiamant , height/10, (int) (height/20* 1.16), height/20, null);
        // g.drawImage(diamant_vide, xDiamant  + width/32 , height/10, (int) (height/20* 1.16), height/20, null);
        // g.drawImage(diamant_vide, xDiamant + 2* width/32 , height/10, (int) (height/20* 1.16), height/20, null);
        // g.drawImage(diamant_vide, xDiamant  + 3* width/32 , height/10, (int) (height/20* 1.16), height/20, null);
        // g.drawImage(diamant_vide, xDiamant  + 4* width/32 , height/10, (int) (height/20* 1.16), height/20, null);

        

        if (ctrl.getState() == ControleurMediateur.ONLINEWAITPLAYERS) {
            maskPanel.setBounds(0, 0, width, height);
        } else{
            if (maskPanel != null) {
                remove(maskPanel);
            }
        }
    }

    private void paintRetour(int width, int height) {
        int retourX = width - (width / 9) - ((width / 13) / 2);
        int retourY = height - (height / 9);

        int ratioX = 475;
        int ratioY = 475;

        int tailleY = height / 12;
        int tailleX = width / 26;

        if (tailleX * ratioY > tailleY * ratioX) {
            tailleX = tailleY * ratioX / ratioY;
            retourX = retourX + (tailleX - tailleX) / 2;
        } else {
            tailleY = tailleX * ratioY / ratioX;
            retourY = retourY + (tailleY - tailleY) / 2;
        }
        retour.setBounds(retourX, retourY, tailleX, tailleY);
    }

    private void paintApres(int width, int height) {

        int tailleY = height / 12;
        int tailleX = width / 26;

        int apresX = width - (width / 9) - (tailleX / 2) + (tailleX / 9 * 4);
        int apresY = height - (height / 9);

        int ratioX = 475;
        int ratioY = 475;

        if (tailleX * ratioY > tailleY * ratioX) {
            tailleX = tailleY * ratioX / ratioY;
            apresX = apresX + (tailleX - tailleX) / 2;
        } else {
            tailleY = tailleX * ratioY / ratioX;
            apresY = apresY + (tailleY - tailleY) / 2;
        }
        apres.setBounds(apresX, apresY, tailleX, tailleY);
    }

    private void paintEngrenage(int width, int height) {
        int tailleY = height / 12;
        int tailleX = width / 26;

        int engrenageX = width - (width / 9) - (tailleX / 2) + 3 * (tailleX / 9 * 4) + (tailleX / 9 * 4);
        int engrenageY = height - (height / 9);

        int ratioX = 475;
        int ratioY = 475;

        if (tailleX * ratioY > tailleY * ratioX) {
            tailleX = tailleY * ratioX / ratioY;
            engrenageX = engrenageX + (tailleX - tailleX) / 2;
        } else {
            tailleY = tailleX * ratioY / ratioX;
            engrenageY = engrenageY + (tailleY - tailleY) / 2;
        }
        engrenage.setBounds(engrenageX, engrenageY, tailleX, tailleY);
    }

    private void paintIndice(int width, int height) {
        int tailleY = height / 12;
        int tailleX = width / 26;

        int indiceX = width - (width / 9) - (tailleX / 2) + 2 * (tailleX / 9 * 4) + (tailleX / 9 * 4);
        int indiceY = (height / 9)/2;

        int ratioX = 475;
        int ratioY = 475;

        if (tailleX * ratioY > tailleY * ratioX) {
            tailleX = tailleY * ratioX / ratioY;
            indiceX = indiceX + (tailleX - tailleX) / 2;
        } else {
            tailleY = tailleX * ratioY / ratioX;
            indiceY = indiceY + (tailleY - tailleY) / 2;
        }
        indice.setBounds(indiceX, indiceY, tailleX, tailleY);
    }
    

    private void paintCodex(int width, int height) {
        int tailleX = width / 13;
        int tailleY = height / 6;

        int codexX;
        if (continuumInverse) {
            codexX = width - (width / 9) - (tailleX / 2);
        } else {
            codexX = (width / 9) - (tailleX / 2);
        }
        int codexY = height / 2 - tailleY / 2;

        int ratioX = 475;
        int ratioY = 700;

        codexX = codexX + (tailleX - tailleX) / 2;
        codexY = codexY + (tailleY - tailleY) / 2;

        if (tailleX * ratioY > tailleY * ratioX) {
            tailleX = tailleY * ratioX / ratioY;
            codexX = codexX + (tailleX - tailleX) / 2;
        } else {
            tailleY = tailleX * ratioY / ratioX;
            codexY = codexY + (tailleY - tailleY) / 2;
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
                tailleY = height / 6;
                tailleX = width / 13;
                int ratioX = 475;
                int ratioY = 700;
                
                if (tailleX * ratioY > tailleY * ratioX) {
                    tailleX = tailleY * ratioX / ratioY;
                } else {
                    tailleY = tailleX * ratioY / ratioX;
                }

                int x = width / 2 + (int)(i % 3 - 1.5) * tailleX + (int)(tailleX / 9 * (i % 3 - 1.5));
                int y;

                if (i < cartesG1.length) {
                    y = height - tailleY - (int) (0.03 * height);
                } else {
                    y = (int) (0.03 * height);
                }

                if (carte.isHover()) {
                    carte.setBounds(x - (tailleX / 20), y, tailleX + (tailleX / 10), tailleY + (tailleY / 10));
                } else {
                    carte.setBounds(x, y, tailleX, tailleY);
                }
            }
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
                int x;
                if (continuumInverse) {
                    x = width - (width / 9) - (tailleX / 2) - (continuum[i].getIndex() + 1) * tailleX
                            - (tailleX / 9 * (continuum[i].getIndex() + 1));
                } else {
                    x = tailleX + (continuum[i].getIndex() + 1) * tailleX
                            + (tailleX / 9 * (continuum[i].getIndex() + 1));
                }
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

        int sceptreX1;
        int sceptreY1;

        int sceptreX2;
        int sceptreY2;

        if (continuumInverse) {
            sceptreX1 = width - (width / 9) - (tailleX / 2) - (sceptreJ1 + 1) * tailleX
                    - (tailleX / 9 * (sceptreJ1 + 1));
            sceptreY1 = y + tailleY + (tailleY / 9 * 2);

            sceptreX2 = width - (width / 9) - (tailleX / 2) - (sceptreJ2 + 1) * tailleX
                    - (tailleX / 9 * (sceptreJ2 + 1));
            sceptreY2 = y - tailleY - (tailleY / 9 * 2);
        } else {
            sceptreX1 = tailleX + (sceptreJ1 + 1) * tailleX + (tailleX / 9 * (sceptreJ1 + 1));
            sceptreY1 = y + tailleY + (tailleY / 9 * 2);

            sceptreX2 = tailleX + (sceptreJ2 + 1) * tailleX + (tailleX / 9 * (sceptreJ2 + 1));
            sceptreY2 = y - tailleY - (tailleY / 9 * 2);
        }

        int ratioX = 475;
        int ratioY = 703;

        if (tailleX * ratioY > tailleY * ratioX) {
            tailleX = tailleY * ratioX / ratioY;
            sceptreX1 = sceptreX1 + (tailleX - tailleX) / 2;
        } else {
            tailleY = tailleX * ratioY / ratioX;
            sceptreY1 = sceptreY1 + (tailleY - tailleY) / 2;
        }

        sceptre1.setBounds(sceptreX1, sceptreY1, tailleX, tailleY);

        if (tailleX * ratioY > tailleY * ratioX) {
            tailleX = tailleY * ratioX / ratioY;
            sceptreX2 = sceptreX2 + (tailleX - tailleX) / 2;
        } else {
            tailleY = tailleX * ratioY / ratioX;
            sceptreY2 = sceptreY2 + (tailleY - tailleY) / 2;
        }
        sceptre2.setBounds(sceptreX2, sceptreY2, tailleX, tailleY);
    }
}
