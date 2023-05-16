package Vue;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class MenuButton extends JComponent {
    Runnable action;
    Image imageB, imageS;
    Boolean estSurvol = false;
    Boolean lock = false;

    public MenuButton(Runnable action, String name, Boolean lock) {
        this.action = action;
        this.lock = lock;
        if(!lock){
            addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
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
            imageB = new ImageIcon("res/Images/Menu/Bouton/" + name).getImage();
        }
        
        imageS = new ImageIcon("res/Images/Menu/Bouton_Survol/" + name).getImage();
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

        imageB = new ImageIcon("res/Images/Menu/" + name).getImage();
    }

    public void estClique() {
        action.run();
    }

    public void paintComponent(Graphics g) {
        if (estSurvol || lock)
            g.drawImage(imageS, 0, 0, getWidth(), getHeight(), null);
        else
            g.drawImage(imageB, 0, 0, getWidth(), getHeight(), null);
    }


}
