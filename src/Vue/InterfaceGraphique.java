package Vue;

import Modele.Coup;
import Modele.Jeu;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.HashMap;

import Controleur.ControleurMediateur;
import Global.Configuration;

public class InterfaceGraphique implements Runnable, InterfaceUtilisateur {
    // Jeu jeu;
    ControleurMediateur ctrl;
    boolean maximized;
    JFrame fenetre;
    // Clip swap_clip = null;
    // Clip sceptre_clip = null;
    HashMap<String, Image> imagesCache = new HashMap<String, Image>();

    JPanel gameMenu;
    JPanel finMenu;

    MenuPrincipalGraphique menuPrincipal;
    MenuOptionsGraphique menuOptions;
    MenuJeuGraphique menuJeu;
    ContinuumGraphique continuumGraphique;
    OnlineMenu onlineMenu;
    // MenuOnGame menuOnGame;
    Jeu jeu;
    Clip clip, swap_clip, sceptre_clip;
    Boolean clipB;

    public InterfaceGraphique(ControleurMediateur ctrl) {
        this.ctrl = ctrl;
        // addBackgroundSound();

        // KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
        //     if (e.getID() == KeyEvent.KEY_PRESSED) {
        //         if (e.getKeyCode() == KeyEvent.VK_LEFT) {
        //             this.ctrl.annulerCoup();
        //         }
        //         if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
        //             this.ctrl.refaireCoup();
        //         }
        //     }
        //     return false;
        // });
    }

    // public void setTheme(String theme) {
    //     Configuration.setTheme(theme);
    //     System.out.println(Configuration.theme);
    //     imagesCache.clear();
    //     continuumGraphique.miseAJour();

    // }

    public static void demarrer(ControleurMediateur ctrl) {
        InterfaceGraphique vue = new InterfaceGraphique(ctrl);
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
        creerMenuPrincipal();
        creerMenuOptions();
        creerMenuJeu();
        // creerMenuOnGame();
        addBackgroundSound();
    }

    /*
    ############################### FENETRE ###############################
    */

    private void creationFenetre() {
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

    void creerMenuPrincipal(){
        menuPrincipal = new MenuPrincipalGraphique(this);
        fenetre.add(menuPrincipal);
    }

    void creerMenuOptions(){
        menuOptions = new MenuOptionsGraphique(this);
    }

    void creerMenuJeu(){
        menuJeu = new MenuJeuGraphique(this);
    }

    void creerContinuum(){
        continuumGraphique = new ContinuumGraphique(ctrl, imagesCache);
        continuumGraphique.initParams(ctrl.getInterfaceMain(Jeu.JOUEUR_1), ctrl.getInterfaceMain(Jeu.JOUEUR_2), ctrl.getInterfaceDeck(), ctrl.getInterfaceTour(), Jeu.JOUEUR_1);
        continuumGraphique.initializeComponents();
    }

    void creerOnlineGame(){
        onlineMenu = new OnlineMenu(fenetre, this, continuumGraphique);
    }

    /*
    ############################### SWITCH OPTION MENU ################################
    */

    public void switchToMenuOptions(){
        fenetre.remove(menuPrincipal);
        fenetre.add(menuOptions);
        refresh();
    }

    public void switchOptionsToMenuPrincipal(){
        fenetre.remove(menuOptions);
        fenetre.add(menuPrincipal);
        refresh();
    }

    /*
    ############################### SWITCH GAME ################################
    */

    public void switchToMenuJeu(){
        fenetre.remove(menuPrincipal);
        fenetre.add(menuJeu);
        refresh();
    }

    public void switchJeuToMenuPrincipal(){
        fenetre.remove(menuJeu);
        fenetre.add(menuPrincipal);
        refresh();
    }

    /*
    ############################### SWITCH FOR GAME ################################
    */

    public void switchToGameLocal(){
        creerContinuum();
        fenetre.remove(menuJeu);
        fenetre.add(continuumGraphique);
        refresh();
    }

    public void switchToGameIA(){
        creerContinuum();
        fenetre.remove(menuJeu);
        fenetre.add(continuumGraphique);
        continuumGraphique.ctrl.changeJoueur(1, 1);
        refresh();
    }

    public void switchToGameOnline(){
        creerOnlineGame();
        fenetre.remove(menuJeu);
        fenetre.add(onlineMenu);
        refresh();
    }

    //TODO: a modif
    // void creerMenuOnGame(){
    //     menuOnGame = new MenuOnGame(this);
    // }

    // void ajoutJeu(){
    //     jeu = new Jeu();
    //     fenetre.add(jeu);
    // }

    /*
    ############################### UPDATE ################################
    */

    @Override
    public void miseAJour() {
        continuumGraphique.initParams(ctrl.getInterfaceMain(Jeu.JOUEUR_1), ctrl.getInterfaceMain(Jeu.JOUEUR_2), ctrl.getInterfaceDeck(), ctrl.getInterfaceTour());
        continuumGraphique.miseAJour();
    }

    private void refresh(){
        fenetre.revalidate();
        fenetre.repaint();
    }

    /*
    ############################### ANIMATION ################################
    */

    @Override
    public void animeCoup(Coup coup) {
        if (coup.getType() == Coup.ECHANGE){
            if(swap_clip != null){
                swap_clip.setFramePosition(0);
                swap_clip.loop(0);
            }
        } else if (coup.getType() == Coup.SCEPTRE) {
            if(sceptre_clip != null){
                sceptre_clip.setFramePosition(0);
                sceptre_clip.loop(0);
            }
        }
    }

    @Override
    public void setGagnant(Boolean gagnant) {
        finMenu = new JPanel();
        finMenu.setLayout(new BoxLayout(finMenu, BoxLayout.Y_AXIS));
        Image Victoire = gagnant ? Configuration.lisImage("VictoireMenu", imagesCache)
                : Configuration.lisImage("DefaiteMenu", imagesCache);
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

    // public void rejouer() {
    //     ctrl.rejouer();
    //     Compteur.getInstance().reset();
    //     continuumGraphique = new ContinuumGraphique(jeu, ctrl, imagesCache);
    //     JPanel PlayMenu = new JPanel();
    //     PlayMenu.setLayout(new BoxLayout(PlayMenu, BoxLayout.Y_AXIS));
    //     PlayMenu.add(continuumGraphique);
    //     fenetre.setContentPane(PlayMenu);
    //     fenetre.revalidate();
    // }

    // public void sauvegarder(String path) {
    //     ctrl.sauvegarder(path);
    // }

    // public void restaure(String path){
    //     ctrl.restaure(path);
    //     continuumGraphique.miseAJour();
    //     miseAJour();
    // }

    /*
    ############################### AUDIO ################################
    */

    private void addBackgroundSound() {
        AudioInputStream audioIn;
        try {
            File file = new File("./res/Audios/background.wav");
            audioIn = AudioSystem.getAudioInputStream(file.toURI().toURL());
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(gainControl.getMinimum());
            clipB = false;
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void switchBackgroundSound() {
        if (clipB) {
            stopBackgroundSound();
        } else {
            playBackgroundSound();
        }
    }

    private void playBackgroundSound() {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(gainControl.getMaximum());
        clipB = true;
    }
    

    private void stopBackgroundSound() {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(gainControl.getMinimum());
        clipB = false;
    }

    public void switchSoundEffect(){
        if(sceptre_clip != null && swap_clip != null){
            stopSoundEffect();
        }else{
            addSoundEffect();
        }
    }

    private void addSoundEffect(){
        addSceptreSound();
        addSwapSound();
    }

    private void stopSoundEffect(){
        sceptre_clip.stop();
        swap_clip.stop();
        sceptre_clip = null;
        swap_clip = null;
    }

    private void addSwapSound() {
        AudioInputStream audioIn;
        try {
            File file = new File("./res/Audios/swap.wav");
            audioIn = AudioSystem.getAudioInputStream(file.toURI().toURL());
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
            audioIn = AudioSystem.getAudioInputStream(file.toURI().toURL());
            sceptre_clip = AudioSystem.getClip();
            sceptre_clip.open(audioIn);
            FloatControl gainControl = (FloatControl) sceptre_clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-20.0f);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}