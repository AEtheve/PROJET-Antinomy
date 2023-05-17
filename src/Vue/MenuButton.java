package Vue;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class MenuButton extends JComponent {
    InterfaceGraphique ig;
    Runnable action;
    Image imageB, imageS, imageBBarre = null, imageSBarre = null;
    Boolean estSurvol = false;
    Boolean lock = false;
    Boolean Musique = false, Sons = false;

    public MenuButton(Runnable action, String name, Boolean lock) {
        this.action = action;
        this.lock = lock;
        if(!lock){
            addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    estSurvol = false;
                    repaint();
                    estClique();
                }
    
                public void mouseEntered(MouseEvent e) {
                    estSurvol = true;
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                    repaint();
                }
    
                public void mouseExited(MouseEvent e) {
                    estSurvol = false;
                    repaint();
                }
            });
            imageB = new ImageIcon("res/Images/Menu/Bouton/" + name + ".png").getImage();
        }
        
        imageS = new ImageIcon("res/Images/Menu/Bouton_Survol/" + name + ".png").getImage();
    }

    public MenuButton(Runnable action, String name, InterfaceGraphique ig) {
        this.action = action;
        this.ig = ig;

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                estSurvol = false;
                repaint();
                estClique();
            }

            public void mouseEntered(MouseEvent e) {
                estSurvol = true;
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                repaint();
            }

            public void mouseExited(MouseEvent e) {
                estSurvol = false;
                repaint();
            }
        });

        imageB = new ImageIcon("res/Images/Menu/Bouton/" + name + ".png").getImage();
        imageBBarre = new ImageIcon("res/Images/Menu/Bouton/" + name + "_barre.png").getImage();
        imageS = new ImageIcon("res/Images/Menu/Bouton_Survol/" + name + ".png").getImage();
        imageSBarre = new ImageIcon("res/Images/Menu/Bouton_Survol/" + name + "_barre.png").getImage();
        if(name.equals("Musique")){
            Musique = true;
        }
        if(name.equals("Effets_sonores")){
            Sons = true;
        }
    }

    public MenuButton(Runnable action, String name) {
        this.action = action;
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                estClique();
            }

            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });

        imageB = new ImageIcon("res/Images/Menu/" + name + ".png").getImage();
    }

    public void estClique() {
        action.run();
    }

    public void paintComponent(Graphics g) {
        if (estSurvol || lock){
            if(Musique && !ig.getBackgroundSound() || Sons && !ig.getSoundEffect())
                g.drawImage(imageSBarre, 0, 0, getWidth(), getHeight(), null);              
            else
                g.drawImage(imageS, 0, 0, getWidth(), getHeight(), null);
        } else {
            if(Musique && !ig.getBackgroundSound() || Sons && !ig.getSoundEffect())
                g.drawImage(imageBBarre, 0, 0, getWidth(), getHeight(), null);              
            else
                g.drawImage(imageB, 0, 0, getWidth(), getHeight(), null);
        }
    }


}
