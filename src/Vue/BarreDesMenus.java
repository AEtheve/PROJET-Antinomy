package Vue;

import javax.swing.JMenu;
import javax.swing.JMenuItem;


import javax.swing.JMenuBar;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import java.awt.event.ActionEvent;

public class BarreDesMenus extends JMenuBar {

    JMenu menu, menu2, smenu, coups;
    InterfaceGraphique interfaceGraphique;

    AbstractAction Rejouer = new AbstractAction("Rejouer") {
        public void actionPerformed(ActionEvent e) {
            // interfaceGraphique.rejouer();
        }
    };
    
    AbstractAction Sauvegarder = new AbstractAction("Sauvegarder") {
        public void actionPerformed(ActionEvent e) {
            JFileChooser choix = new JFileChooser(".");
            choix.showSaveDialog(null);
            try{
                String path = choix.getSelectedFile().getAbsolutePath();
                // interfaceGraphique.sauvegarder(path);
            }catch(NullPointerException ex){
                return;
            }
        }
    };

    AbstractAction Restaure = new AbstractAction("Restaurer") {
        public void actionPerformed(ActionEvent e) {
            JFileChooser choix = new JFileChooser(".");
            choix.showOpenDialog(null);
            try{
                String path = choix.getSelectedFile().getAbsolutePath();
                // interfaceGraphique.restaure(path);
            }catch(NullPointerException ex){
                return;
            }
        }
        
    };

    AbstractAction Quitter = new AbstractAction("Quitter") {
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
        menuitem.setAction(Rejouer);
        menu.add(menuitem);
        
        menuitem = new JMenuItem();
        menuitem.setAction(Quitter);
        menu.add(menuitem);

        menuitem = new JMenuItem();
        menuitem.setAction(Sauvegarder);
        menu.add(menuitem);

        menuitem = new JMenuItem();
        menuitem.setAction(Restaure);
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
