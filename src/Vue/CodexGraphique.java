package Vue;

import Modele.Codex;
import Global.Configuration;
import java.awt.*;
import javax.swing.*;


public class CodexGraphique extends JComponent {
    Codex codex;
    Image img;
    int posX, posY;
    int sizeX, sizeY;
    
    public CodexGraphique(Codex codex) {
        this.codex = codex;
        img = Configuration.lisImage("codex");
    }

    public void miseAJour(){
        repaint();
    }

    public void dessinImage(int posX, int posY, int sizeX, int sizeY){
        this.posX = posX;
        this.posY = posY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public void paintComponent(Graphics g){
        // System.out.println("paintComponent Carte");
        Graphics2D drawable = (Graphics2D) g;
        drawable.drawImage(img, posX, posY, sizeX, sizeY, null);
    }
}

