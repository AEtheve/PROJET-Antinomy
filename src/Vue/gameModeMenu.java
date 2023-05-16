package Vue;

import javax.swing.*;

import Controleur.ControleurMediateur;
import Controleur.ControleurMediateurOnline;
import Modele.JeuEntier;

public class gameModeMenu extends JPanel {

    ContinuumGraphique continuumGraphique;

    gameModeMenu(InterfaceGraphique vue, JFrame fenetre) {
        super();
        ControleurMediateur ctrl = vue.ctrl;

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
            vue.continuumGraphique = new ContinuumGraphique(ctrl, vue.imagesCache);
            continuumGraphique = vue.continuumGraphique;
            continuumGraphique.initParams(ctrl.getInterfaceMain(JeuEntier.JOUEUR_1), ctrl.getInterfaceMain(JeuEntier.JOUEUR_2), ctrl.getInterfaceDeck(), ctrl.getInterfaceTour(), JeuEntier.JOUEUR_1);
            continuumGraphique.initializeComponents();
        
            JPanel PlayMenu = new JPanel();
            PlayMenu.setLayout(new BoxLayout(PlayMenu, BoxLayout.Y_AXIS));
            PlayMenu.add(continuumGraphique);
            fenetre.setContentPane(PlayMenu);
            fenetre.revalidate();
        });

        modeIAButton.addActionListener(e -> {
            vue.continuumGraphique = new ContinuumGraphique(ctrl, vue.imagesCache);
            continuumGraphique = vue.continuumGraphique;
            continuumGraphique.initParams(ctrl.getInterfaceMain(JeuEntier.JOUEUR_1), ctrl.getInterfaceMain(JeuEntier.JOUEUR_2), ctrl.getInterfaceDeck(), ctrl.getInterfaceTour(), JeuEntier.JOUEUR_1);
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
            vue.ctrl = onlineControleur;

        
            OnlineMenu onlineMenu = new OnlineMenu(fenetre, vue, continuumGraphique);
            fenetre.setContentPane(onlineMenu);
            fenetre.revalidate();
        });
    }
}
