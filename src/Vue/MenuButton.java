package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuButton extends JComponent {
    JComponent parent;
    Runnable action;
    Image image;
    Image imageB, imageS;
    Boolean estSurvol = false;

    public MenuButton(Runnable action, String name, JComponent parent) {
        this.parent = parent;
        this.action = action;
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                estClique();
            }
        });

        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                if(estSurvol) return;
                image = imageS;
                parent.repaint();
                estSurvol = true;
            }
        });


        imageB = image = new ImageIcon("res/Images/Menu/Bouton/" + name).getImage();
        imageS = new ImageIcon("res/Images/Menu/Bouton_Survol/" + name).getImage();
    }

    public void reset() {
        image = imageB;
        estSurvol = false;
        parent.repaint();
    }

    public void estClique() {
        action.run();
    }

    public void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }


}
