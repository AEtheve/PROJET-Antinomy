package Vue;

import javax.swing.*;
import java.awt.*;
import Global.Configuration;


public class InterfaceGraphique implements Runnable {
    boolean maximized;
    JFrame frame;

    public InterfaceGraphique() {
        
    }


    public static void demarrer() {
        InterfaceGraphique vue = new InterfaceGraphique();
		// c.ajouteInterfaceUtilisateur(vue);
		SwingUtilities.invokeLater(vue);

	}

    public void toggleFullscreen() {
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = env.getDefaultScreenDevice();
		if (maximized) {
			device.setFullScreenWindow(null);
			maximized = false;
		} else {
			device.setFullScreenWindow(frame);
			maximized = true;
		}
	}

    public void run(){
        creationFenetre();
    }

    private void creationFenetre(){
        // Création de la fenêtre principale
        JFrame fenetre = new JFrame();
        fenetre.setTitle("Antinomy");
        fenetre.setSize(800, 600);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setLocationRelativeTo(null);
        fenetre.setResizable(true);
        fenetre.setVisible(true);
        Configuration.info("Fenetre principale créée");
    }
}