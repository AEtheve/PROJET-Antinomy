package Vue;
    
import java.awt.*;
import java.util.ArrayList;

public class PoseGraphique {
    Image img;
    int posX, posY;
    int sizeX, sizeY;
    ArrayList<Integer> selection;
    
    public PoseGraphique(){
    }
    
    public void setSelection(ArrayList<Integer> selection){
        this.selection = selection;
    }
    
    public void dessin(Graphics g, int width, int height){
        int tailleY = height / 6;
        int tailleX = width / 13;

        int y = height / 2 + (int) (0.2 * tailleY);

        int x = tailleX + tailleX + (tailleX / 9 );

        if (selection != null){
            for (int i = 0; i < selection.size(); i++) {
                x = tailleX + (selection.get(i)+1) * tailleX + (tailleX / 9 * (selection.get(i)+1));
                g.setColor(Color.BLACK);
                g.drawRect(x, y +tailleY/2, tailleX, tailleY);
            }
        }

    }

    public int estPoseGraphique(int height, int width, int x, int y) {
        int tailleY = height / 6;
        int tailleX = width / 13;

        int y1 = height / 2 + (int) (0.2 * tailleY) + tailleY/2;

        int x1 = tailleX + tailleX + (tailleX / 9 );

        if (selection != null){
            for (int i = 0; i < selection.size(); i++) {
                x1 = tailleX + (selection.get(i)+1) * tailleX + (tailleX / 9 * (selection.get(i)+1));
                if (x > x1 && x < x1 + tailleX && y > y1 && y < y1 + tailleY) return selection.get(i);
            }
        }
        return -1;
    }

}
