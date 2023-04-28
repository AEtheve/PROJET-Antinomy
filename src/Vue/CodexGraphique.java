package Vue;

import Modele.Codex;
import Global.Configuration;
import java.awt.*;


public class CodexGraphique {
    Codex codex;
    Image img;
    int posX, posY;
    int sizeX, sizeY;
    
    public CodexGraphique(Codex codex) {
        this.codex = codex;
        img = Configuration.lisImage("codex");
    }

    public void dessinImage(Graphics g, int posX, int posY, int sizeX, int sizeY){
        Graphics2D drawable = (Graphics2D) g;
        drawable.drawImage(img, posX, posY, sizeX, sizeY, null);
    }
}

