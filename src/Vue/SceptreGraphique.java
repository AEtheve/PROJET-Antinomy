package Vue;
    
import Global.Configuration;
import java.awt.*;

public class SceptreGraphique {
    Image img;
    int posX, posY;
    int sizeX, sizeY;
    
    public SceptreGraphique(){
        img = Configuration.lisImage("sceptre");
    }
    
    
    public void dessinImage(Graphics g, int posX, int posY, int sizeX, int sizeY){
        Graphics2D drawable = (Graphics2D) g;
        drawable.drawImage(img, posX, posY, sizeX, sizeY, null);
    }

}