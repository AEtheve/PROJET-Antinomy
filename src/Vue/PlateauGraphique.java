package Vue;

import javax.swing.*;

import Modele.Carte;
import Modele.Codex;
import java.awt.*;

public class PlateauGraphique extends JComponent {
    CarteGraphique [] cartes;
    CodexGraphique codex;

    public PlateauGraphique(Carte [] cartes, Codex codex){
        // this.codex = new CodexGraphique(codex);

        this.cartes = new CarteGraphique[cartes.length];

        for (int i = 0; i < cartes.length; i++) {
            this.cartes[i] = new CarteGraphique(cartes[i], i, this);
        }
    }

    public void miseAJour() {
        for (int i = 0; i < cartes.length; i++) {
            cartes[i].miseAJour();
        }
        // codex.miseAJour();
    }

    public void paintComponent(Graphics g) {
        for (int i = 0; i < cartes.length; i++) {
            cartes[i].paintComponent(g);
        }
        // codex.paintComponent(g);
    }
    
}
