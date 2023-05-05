package Vue;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import Global.Configuration;

import javax.swing.JMenuBar;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import Modele.Jeu;

public class BarreDesMenus extends JMenuBar {

    JMenu menu, menu2, smenu, coups;
    InterfaceGraphique interfaceGraphique;

    AbstractAction Quitter = new AbstractAction("Quitter") {
        // Action réalisée lors du clic sur l'item Quitter du menu déroulant
        public void actionPerformed(ActionEvent e) {
            System.out.println("Merci d'avoir joué au jeu");
            System.exit(0);
        }
    };

    public BarreDesMenus(InterfaceGraphique interfaceGraphique) {
        JMenuItem menuitem = new JMenuItem();
        this.interfaceGraphique = interfaceGraphique;
        menu = new JMenu("Options de jeu");
        menuitem = new JMenuItem();
        menuitem.setAction(Quitter);

        menu.add(menuitem);
        this.add(menu);

        menu2 = new JMenu("Thème du jeu");
        menuitem = new JMenuItem("Thème basique");
        menu2.add(menuitem);

        menuitem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                interfaceGraphique.setTheme("Images");
            }
        });

        menuitem = new JMenuItem("Thème custom");
        menu2.add(menuitem);
        this.add(menu2);

        menuitem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                interfaceGraphique.setTheme("Images2");
            }
        });

        menuitem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                interfaceGraphique.setTheme("custom");
            }
        });

    


    }

}
