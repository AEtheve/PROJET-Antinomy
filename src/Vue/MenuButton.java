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

        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                if (!estSurvol) {
                    estSurvol = true;
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            }
        });

        imageB = new ImageIcon("res/Images/Menu/" + name).getImage();
    }

    public void estClique() {
        action.run();
    }

    public void paintComponent(Graphics g) {
        g.drawImage(imageB, 0, 0, getWidth(), getHeight(), null);
    }


}
