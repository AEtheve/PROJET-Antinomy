package Vue;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class MenuButton extends JComponent {
    Runnable action;
    Image imageB, imageS;
    Boolean estSurvol = false;

    public MenuButton(Runnable action, String name) {
        this.action = action;
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                estClique();
            }
        });

        // addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        //     public void mouseMoved(java.awt.event.MouseEvent evt) {
        //         if(estSurvol) return;
        //         else {
        //             estSurvol = true;
        //             parent.repaint();
        //         }
        //     }
        // });


        imageB = new ImageIcon("res/Images/Menu/Bouton/" + name).getImage();
        // imageS = new ImageIcon("res/Images/Menu/Bouton_Survol/" + name).getImage();
    }

    public void estClique() {
        action.run();
    }

    public void paintComponent(Graphics g) {
        // if(estSurvol)
        //     g.drawImage(imageS, 0, 0, getWidth(), getHeight(), null);
        // else
        g.drawImage(imageB, 0, 0, getWidth(), getHeight(), null);
    }


}
