package Vue;

import Modele.Carte;
import Modele.Compteur;
import Modele.Coup;
import Modele.Jeu;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.HashMap;

import Controleur.ControleurJoueur;
import Global.Configuration;

public class InterfaceGraphique implements Runnable, InterfaceUtilisateur{
    Jeu jeu;
    ControleurJoueur ctrl;
    boolean maximized;
    JFrame fenetre;
    Clip swap_clip = null;
    Clip sceptre_clip = null;
    ContinuumGraphique continuumGraphique;
    HashMap<String, Image> imagesCache = new HashMap<String, Image>();

    JPanel gameMenu;
    JPanel finMenu;

    public InterfaceGraphique(Jeu jeu, ControleurJoueur ctrl){
        this.jeu = jeu;
        this.ctrl = ctrl;

        addSwapSound();
        addSceptreSound();
        // addBackgroundSound();

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            if (e.getID() == KeyEvent.KEY_PRESSED) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                this.ctrl.annulerCoup();
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                System.out.println("right");
                }
            }
            return false;
        });
    }

    private void addBackgroundSound() {
        AudioInputStream audioIn;
        Clip clip = null;
        try {
            File file = new File("./res/Audios/background.wav");
            audioIn =  AudioSystem.getAudioInputStream(file.toURI().toURL());
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addSwapSound() {
        AudioInputStream audioIn;
        
        try {
            File file = new File("./res/Audios/swap.wav");
            audioIn =  AudioSystem.getAudioInputStream(file.toURI().toURL());
            swap_clip = AudioSystem.getClip();
            swap_clip.open(audioIn);
            FloatControl gainControl = (FloatControl) swap_clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-20.0f);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addSceptreSound() {
        AudioInputStream audioIn;
        
        try {
            File file = new File("./res/Audios/sceptre.wav");
            audioIn =  AudioSystem.getAudioInputStream(file.toURI().toURL());
            sceptre_clip = AudioSystem.getClip();
            sceptre_clip.open(audioIn);
            FloatControl gainControl = (FloatControl) sceptre_clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-20.0f);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ajouteBarreDesMenus(JFrame frame) {
		BarreDesMenus barreDesMenus = new BarreDesMenus(this);
		frame.setJMenuBar(barreDesMenus);
	}

    public void setTheme(String theme) {
        Configuration.setTheme(theme);
        System.out.println(Configuration.theme);
        imagesCache.clear();
        continuumGraphique.miseAJour();

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

    public void run() {
    creationFenetre();


    continuumGraphique = new ContinuumGraphique(jeu, ctrl, imagesCache);
    mainMenu mainMenu = new mainMenu(fenetre, continuumGraphique);

    fenetre.setContentPane(mainMenu);
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
        ajouteBarreDesMenus(fenetre);
        Configuration.info("Fenetre principale créée");
    }


    @Override
    public void miseAJour(){
        continuumGraphique.miseAJour();
    }

    @Override
    public void animeCoup(Coup coup) {
        if (coup.getType() == Coup.ECHANGE) {
            swap_clip.setFramePosition(0);
            swap_clip.loop(0);
            continuumGraphique.setSelectCarteMain1(-1);
            continuumGraphique.setSelectCarteMain2(-1);
        }
        else if (coup.getType() == Coup.SCEPTRE) {
            sceptre_clip.setFramePosition(0);
            sceptre_clip.loop(0);
        }
    }

    @Override
    public void setCartesPossibles(Carte[] cartesPossibles) {
        continuumGraphique.setCartesPossibles(cartesPossibles);
    }

    @Override
    public void setSelectCarteMain1(int index) {
        continuumGraphique.setSelectCarteMain1(index);
    }

    @Override
    public void setSelectCarteMain2(int index) {
        continuumGraphique.setSelectCarteMain2(index);
    }

    @Override
    public void setGagnant(Boolean gagnant) {
        finMenu = new JPanel();
            finMenu.setLayout(new BoxLayout(finMenu, BoxLayout.Y_AXIS));
            Image Victoire = gagnant ? Configuration.lisImage("VictoireMenu", imagesCache) : Configuration.lisImage("DefaiteMenu", imagesCache);
            Victoire = Victoire.getScaledInstance(800, 600, Image.SCALE_SMOOTH);
            JLabel label = new JLabel(new ImageIcon(Victoire));
            finMenu.add(label);
            fenetre.setContentPane(finMenu);
            fenetre.revalidate();
            fenetre.repaint();
    }

    @Override
    public void changeEtatIA(boolean b) {
        // TODO ANIMATION
    }

    public void rejouer () {
        ctrl.rejouer();
        Compteur.getInstance().reset();
        continuumGraphique = new ContinuumGraphique(jeu, ctrl, imagesCache);
        JPanel PlayMenu = new JPanel();
        PlayMenu.setLayout(new BoxLayout(PlayMenu, BoxLayout.Y_AXIS));
        PlayMenu.add(continuumGraphique);
        fenetre.setContentPane(PlayMenu);
        fenetre.revalidate();
    }
}