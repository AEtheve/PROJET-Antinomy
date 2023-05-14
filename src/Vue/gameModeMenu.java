package Vue;

import javax.swing.*;

import Controleur.ControleurMediateur;
import Controleur.ControleurMediateurOnline;
import Modele.Jeu;

public class gameModeMenu extends JPanel {

    gameModeMenu(JFrame fenetre, ControleurMediateur ctrl, ContinuumGraphique continuumGraphique) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JButton modeAmisButton = new JButton("VS Amis");
        modeAmisButton.setAlignmentX(CENTER_ALIGNMENT);
        JButton modeIAButton = new JButton("VS IA");
        modeIAButton.setAlignmentX(CENTER_ALIGNMENT);
        JButton modeOnlineButton = new JButton("VS Online");
        modeOnlineButton.setAlignmentX(CENTER_ALIGNMENT);

        add(Box.createVerticalGlue());
        add(modeAmisButton);
        add(modeIAButton);
        add(modeOnlineButton);
        add(Box.createVerticalGlue());

        modeAmisButton.addActionListener(e -> {
            continuumGraphique.initParams(ctrl.getInterfaceMain(Jeu.JOUEUR_1), ctrl.getInterfaceMain(Jeu.JOUEUR_2), ctrl.getInterfaceTour());
            continuumGraphique.initializeComponents();
        
            JPanel PlayMenu = new JPanel();
            PlayMenu.setLayout(new BoxLayout(PlayMenu, BoxLayout.Y_AXIS));
            PlayMenu.add(continuumGraphique);
            fenetre.setContentPane(PlayMenu);
            fenetre.revalidate();
        });

        modeIAButton.addActionListener(e -> {
            continuumGraphique.initParams(ctrl.getInterfaceMain(Jeu.JOUEUR_1), ctrl.getInterfaceMain(Jeu.JOUEUR_2), ctrl.getInterfaceTour());
            continuumGraphique.initializeComponents();
        
            JPanel PlayMenu = new JPanel();
            PlayMenu.setLayout(new BoxLayout(PlayMenu, BoxLayout.Y_AXIS));
            PlayMenu.add(continuumGraphique);
            fenetre.setContentPane(PlayMenu);
            fenetre.revalidate();
            continuumGraphique.ctrl.changeJoueur(1, 1); // Active l'IA
        });

        modeOnlineButton.addActionListener(e -> {
            ControleurMediateur onlineControleur = new ControleurMediateurOnline();
            // ctrl = onlineControleur;
            
            continuumGraphique.initParams(ctrl.getInterfaceMain(Jeu.JOUEUR_1), ctrl.getInterfaceMain(Jeu.JOUEUR_2), ctrl.getInterfaceTour());
            continuumGraphique.initializeComponents();

        
            OnlineMenu onlineMenu = new OnlineMenu(fenetre, continuumGraphique);
            fenetre.setContentPane(onlineMenu);
            fenetre.revalidate();
        });
    }
}
