package Vue;

import Modele.Coup;
import Modele.Jeu;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
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
    ContinuumGraphique continuumGraphique;
    HashMap<String, Image> imagesCache = new HashMap<String, Image>();

    public InterfaceGraphique(Jeu jeu, ControleurJoueur ctrl){
        this.jeu = jeu;
        this.ctrl = ctrl;

        addSwapSound();
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

    public void run(){
        creationFenetre();
        JPanel gameMenu = new JPanel();

        gameMenu.setLayout(new BoxLayout(gameMenu, BoxLayout.Y_AXIS));
        
        continuumGraphique = new ContinuumGraphique(jeu, ctrl, imagesCache);
        gameMenu.add(continuumGraphique);
    
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
        ajouteBarreDesMenus(fenetre);
        Configuration.info("Fenetre principale créée");
    }


    @Override
    public void miseAJour(){
        continuumGraphique.miseAJour();
    }

    @Override
    public void animeCoup(Coup coup) {
        System.out.println("animeCoup");
        if (coup.getType() == Coup.ECHANGE) {
            swap_clip.setFramePosition(0);
            swap_clip.loop(0);
        }
    }
}