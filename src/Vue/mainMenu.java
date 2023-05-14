package Vue;

import javax.swing.*;

import Controleur.ControleurMediateur;

public class mainMenu extends JPanel {

    mainMenu(JFrame fenetre, ControleurMediateur ctrl, ContinuumGraphique continuumGraphique) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JButton jouerButton = new JButton("Jouer");
        jouerButton.setAlignmentX(CENTER_ALIGNMENT);
        JButton chargerButton = new JButton("Charger");
        chargerButton.setAlignmentX(CENTER_ALIGNMENT);
        JButton tutorielButton = new JButton("Tutoriel");
        tutorielButton.setAlignmentX(CENTER_ALIGNMENT);
        JButton optionsButton = new JButton("Options");
        optionsButton.setAlignmentX(CENTER_ALIGNMENT);
        JButton quitterButton = new JButton("Quitter");
        quitterButton.setAlignmentX(CENTER_ALIGNMENT);

        add(Box.createVerticalGlue());
        add(jouerButton);
        add(chargerButton);
        add(tutorielButton);
        add(optionsButton);
        add(quitterButton);
        add(Box.createVerticalGlue());

        chargerButton.setEnabled(false);
        tutorielButton.setEnabled(false);
        optionsButton.setEnabled(false);

        jouerButton.addActionListener(e -> {

            JPanel gameModeMenu = new gameModeMenu(fenetre, ctrl, continuumGraphique);
            fenetre.setContentPane(gameModeMenu);
            fenetre.revalidate();
        });

        chargerButton.addActionListener(e -> {
            // TODO
        });

        tutorielButton.addActionListener(e -> {
            // TODO
        });

        optionsButton.addActionListener(e -> {
            // TODO
        });

        quitterButton.addActionListener(e -> {
            System.exit(0);
        });
    }
}
