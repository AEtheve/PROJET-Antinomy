package Vue;

import javax.swing.*;

import Global.Configuration;
import Modele.Carte;
import java.awt.*;

public class CarteGraphique extends JComponent {
    Carte carte;
    Image img;
    
    public CarteGraphique(Carte carte) {
        this.carte = carte;
        String nom = "" + carte.getType();
        img = Configuration.lisImage(nom);
    }

    

    public void paintComponent(Graphics g) {

    }
}
