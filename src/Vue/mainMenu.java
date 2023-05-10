package Vue;

import javax.swing.*;

public class mainMenu extends JPanel {

    mainMenu(JFrame fenetre, ContinuumGraphique continuumGraphique) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JButton jouerButton = new JButton("Jouer");
        JButton chargerButton = new JButton("Charger");
        JButton tutorielButton = new JButton("Tutoriel");
        JButton optionsButton = new JButton("Options");
        JButton quitterButton = new JButton("Quitter");

        add(jouerButton);
        add(chargerButton);
        add(tutorielButton);
        add(optionsButton);
        add(quitterButton);

        chargerButton.setEnabled(false);
        tutorielButton.setEnabled(false);
        optionsButton.setEnabled(false);

        jouerButton.addActionListener(e -> {

            JPanel gameModeMenu = new gameModeMenu(fenetre, continuumGraphique);
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
