package Vue;

import Modele.Coup;
import Modele.Jeu;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Random;

import Controleur.ControleurMediateur;
import Controleur.ControleurMediateurLocal;
import Global.Configuration;

public class InterfaceGraphique implements Runnable, InterfaceUtilisateur {
    ControleurMediateur ctrl;
    boolean maximized;
    JFrame fenetre;
    HashMap<String, Image> imagesCache = new HashMap<String, Image>();

    JPanel gameMenu;
    JPanel finMenu;

    MenuPrincipalGraphique menuPrincipal;
    MenuOptionsGraphique menuOptions;
    MenuJeuGraphique menuJeu;
    ContinuumGraphique continuumGraphique;
    OnlineMenu onlineMenu;
    MenuTuto menuTuto;
    MenuSelectionJoueurGraphique menuSelectionJoueurGraphique;

    Jeu jeu;
    Clip clip, swap_clip, sceptre_clip;
    Boolean clipB, clipB_swap, clipB_sceptre;
    Boolean joueurDebut;

    public InterfaceGraphique() {
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

    public static void demarrer() {
        InterfaceGraphique vue = new InterfaceGraphique();
        // ctrl.ajouteInterfaceUtilisateur(vue);
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
        creerTuto();
        // creerMenuOnGame();
        addBackgroundSound();
        addSceptreSound();
        addSwapSound();
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
        menuPrincipal = new MenuPrincipalGraphique(this, imagesCache);
        fenetre.add(menuPrincipal);
    }

    void creerMenuOptions(){
        menuOptions = new MenuOptionsGraphique(this, imagesCache);
    }

    void creerMenuJeu(){
        menuJeu = new MenuJeuGraphique(this, imagesCache);
    }

    void creerContinuum(){
        continuumGraphique = new ContinuumGraphique(this, ctrl, imagesCache);
        continuumGraphique.initParams(ctrl.getInterfaceMain(Jeu.JOUEUR_1), ctrl.getInterfaceMain(Jeu.JOUEUR_2), ctrl.getInterfaceDeck(), ctrl.getInterfaceTour(), Jeu.JOUEUR_1);
        continuumGraphique.initializeComponents();
    }

    void creerOnlineGame(){
        onlineMenu = new OnlineMenu(fenetre, this, continuumGraphique);
    }

    void creerTuto(){
        menuTuto = new MenuTuto(this, imagesCache);
    }

    void creerChoixJoueur(String type){
        menuSelectionJoueurGraphique = new MenuSelectionJoueurGraphique(this, type, imagesCache);
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
    ############################### SWITCH TUTO MENU ################################
    */

    public void switchToMenuTuto(){
        fenetre.remove(menuPrincipal);
        fenetre.add(menuTuto);
        refresh();
    }

    public void switchTutoToMenuPrincipal(){
        fenetre.remove(menuTuto);
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

    public void switchToGameLoad(){
        // ctrl
        JFileChooser choix = new JFileChooser(".");
        choix.showOpenDialog(null);
        this.ctrl = new ControleurMediateurLocal();
        this.ctrl.ajouteInterfaceUtilisateur(this);
        try{
            String path = choix.getSelectedFile().getAbsolutePath();
            System.out.println(path);
            ctrl.loadGame(path);
            // continuumGraphique.miseAJour();
        }catch(NullPointerException e){
            System.out.println(e);
            System.out.println("Aucun fichier selectionné");
        }
        creerContinuum();
        fenetre.remove(menuPrincipal);
        fenetre.add(continuumGraphique);
        continuumGraphique.miseAJour();
        refresh();
    }

    public void switchToGameLocal(){
        creerChoixJoueur("local");
        fenetre.remove(menuJeu);
        fenetre.add(menuSelectionJoueurGraphique);
        refresh();
    }

    public void switchToGameIA(){
        creerChoixJoueur("ia");
        fenetre.remove(menuJeu);
        fenetre.add(menuSelectionJoueurGraphique);
        refresh();
    }

    public void backToMenuJeu(){
        fenetre.remove(menuSelectionJoueurGraphique);
        fenetre.add(menuJeu);
        refresh();
    }

    public void switchToGameOnline(){
        // this.ctrl = new ControleurMediateurLocal();
        // this.ctrl.ajouteInterfaceUtilisateur(this);
        creerOnlineGame();
        fenetre.remove(menuJeu);
        fenetre.add(onlineMenu);
        refresh();
    }

    public void backToMenuPrincipal(){
        fenetre.remove(continuumGraphique);
        fenetre.add(menuPrincipal);
        rejouer();
        refresh();
    }

    public void setJoueur1(String type){
        joueurDebut = Jeu.JOUEUR_1;
        launchGame(type);
    }

    public void setJoueur2(String type){
        joueurDebut = Jeu.JOUEUR_2;
        launchGame(type);
    }

    public void setJoueurRandom(String type){
        Random rand = new Random();
        joueurDebut = rand.nextBoolean();
        launchGame(type);
    }

    private void launchGame(String type){
        Jeu.setInitJoueurCommence(joueurDebut);
        ctrl = new ControleurMediateurLocal();
        ctrl.ajouteInterfaceUtilisateur(this);
        creerContinuum();
        fenetre.remove(menuSelectionJoueurGraphique);
        fenetre.add(continuumGraphique);
        if(type == "ia"){
            continuumGraphique.ctrl.changeJoueur(1, 1);
        }
        refresh();
    }

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
        continuumGraphique.animeCoup(coup);
    }

    @Override
    public void setGagnant(Boolean gagnant) {
        finMenu = new JPanel();
        finMenu.setLayout(new BoxLayout(finMenu, BoxLayout.Y_AXIS));
        Image Victoire = gagnant ? Configuration.lisImage("Menu/VictoireMenu", imagesCache)
                : Configuration.lisImage("Menu/DefaiteMenu", imagesCache);
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

    public void rejouer() {
        ctrl.rejouer();
        refresh();
    }

    public void sauvegarder(String filename) {
        ctrl.sauvegarder(filename);
    }

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
        if(clipB_swap || clipB_sceptre){
            stopSoundEffect();
        }else{
            playSoundEffect();
        }
    }

    private void playSoundEffect() {
        FloatControl gainControl = (FloatControl) swap_clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-20.0f);
        clipB_swap = true;
        gainControl = (FloatControl) sceptre_clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-20.0f);
        clipB_sceptre = true;
    }
    

    private void stopSoundEffect() {
        FloatControl gainControl = (FloatControl) swap_clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(gainControl.getMinimum());
        clipB_swap = false;
        gainControl = (FloatControl) sceptre_clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(gainControl.getMinimum());
        clipB_sceptre = false;
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
            clipB_swap = true;
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
            clipB_sceptre = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Boolean getBackgroundSound(){
        return clipB;
    }

    public Boolean getSoundEffect(){
        return clipB_swap;
    }

}