package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OnlineMenu extends JPanel {

    OnlineMenu(JFrame fenetre, ContinuumGraphique continuumGraphique) {
        super(new BorderLayout());

        // Bouton "Créer une partie"
        JButton creerPartieButton = new JButton("Créer une partie");
        creerPartieButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        creerPartieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        buttonsPanel.add(creerPartieButton);
        buttonsPanel.add(Box.createHorizontalStrut(10));

        buttonsPanel.add(refreshButton);

        add(buttonsPanel, BorderLayout.NORTH);

        String[][] parties = {
                { "Partie 1", "Mot de passe requis" },
        };

        JPanel partiesPanel = new JPanel(new GridLayout(3, 5, 10, 10));
        partiesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int i = 0; i < parties.length; i++) {
            String nomPartie = parties[i][0];
            String infoPartie = parties[i][1];

            JPanel partiePanel = new JPanel();
            partiePanel.setLayout(new BoxLayout(partiePanel, BoxLayout.Y_AXIS));
            partiePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            partiePanel.setPreferredSize(new Dimension(200, 200));
            partiePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            partiePanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (infoPartie.equals("Mot de passe requis")) {
                        JPasswordField passwordField = new JPasswordField();
                        Object[] popupContent = { "Mot de passe:", passwordField };
                        String popupTitle = "Mot de passe requis";
                        int option = JOptionPane.showOptionDialog(
                                fenetre,
                                popupContent,
                                popupTitle,
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                null,
                                null);

                        if (option == JOptionPane.OK_OPTION) {
                        }
                    } else {
                        // Rejoindre la partie sans mot de passe
                    }
                }
            });

            JLabel nomLabel = new JLabel(nomPartie);
            nomLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            partiePanel.add(nomLabel);

            JLabel infoLabel = new JLabel(infoPartie);
            infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            partiePanel.add(infoLabel);

            partiesPanel.add(partiePanel);
        }

        add(partiesPanel, BorderLayout.CENTER);
    }
}
