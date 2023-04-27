package Vue;

import javax.swing.*;
    
import Global.Configuration;
import Modele.Carte;
import java.awt.*;

public class SceptreGraphique extends JComponent{
    Image img;
    int posX, posY;
    int sizeX, sizeY;
    
    public SceptreGraphique(){
        img = Configuration.lisImage("sceptre");
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
        Graphics2D drawable = (Graphics2D) g;
        drawable.drawImage(img, posX, posY, sizeX, sizeY, null);
    }
        
}
