package Vue;

import javax.swing.*;

public class gameModeMenu extends JPanel {

    gameModeMenu(JFrame fenetre, ContinuumGraphique continuumGraphique) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JButton modeAmisButton = new JButton("VS Amis");
        JButton modeIAButton = new JButton("VS IA");
        JButton modeOnlineButton = new JButton("VS Online");

        add(modeAmisButton);
        add(modeIAButton);
        add(modeOnlineButton);

        modeAmisButton.addActionListener(e -> {
            JPanel PlayMenu = new JPanel();
            PlayMenu.setLayout(new BoxLayout(PlayMenu, BoxLayout.Y_AXIS));
            PlayMenu.add(continuumGraphique);
            fenetre.setContentPane(PlayMenu);
            fenetre.revalidate();
        });

        modeIAButton.addActionListener(e -> {
            JPanel PlayMenu = new JPanel();
            PlayMenu.setLayout(new BoxLayout(PlayMenu, BoxLayout.Y_AXIS));
            PlayMenu.add(continuumGraphique);
            fenetre.setContentPane(PlayMenu);
            fenetre.revalidate();
            continuumGraphique.ctrl.basculeIA();
        });

        modeOnlineButton.addActionListener(e -> {

            OnlineMenu onlineMenu = new OnlineMenu(fenetre, continuumGraphique);
            fenetre.setContentPane(onlineMenu);
            fenetre.revalidate();
        });
    }
}
