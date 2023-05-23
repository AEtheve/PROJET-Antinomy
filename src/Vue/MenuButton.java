package Vue;

import javax.swing.*;

import Global.Configuration;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class MenuButton extends JComponent {
    InterfaceGraphique ig;
    HashMap<String, Image> imagesCache;
    Runnable action;
    Image imageB, imageS, imageBBarre = null, imageSBarre = null;
    Boolean estSurvol = false;
    Boolean lock = false;
    Boolean Musique = false, Sons = false, Animation = false;
    String name, type;

    public MenuButton(Runnable action, String name, Boolean lock, HashMap<String, Image> imagesCache) {
        this.action = action;
        this.lock = lock;
        if(!lock){
            addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    estSurvol = false;
                    repaint();
                    estClique();
                }
    
                public void mouseEntered(MouseEvent e) {
                    estSurvol = true;
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                    repaint();
                }
    
                public void mouseExited(MouseEvent e) {
                    estSurvol = false;
                    repaint();
                }
            });
            
        }
        type = "Bouton avec survol";
        this.name = name;
        this.imagesCache = imagesCache;
    }

    public MenuButton(Runnable action, String name, InterfaceGraphique ig, HashMap<String, Image> imagesCache) {
        this.action = action;
        this.ig = ig;

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                estSurvol = false;
                repaint();
                estClique();
            }

            public void mouseEntered(MouseEvent e) {
                estSurvol = true;
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                repaint();
            }

            public void mouseExited(MouseEvent e) {
                estSurvol = false;
                repaint();
            }
        });
        type = "Bouton selectionnable";
        this.name = name;
        this.imagesCache = imagesCache;

        if(name.equals("Musique")){
            Musique = true;
        }
        if(name.equals("Effets_sonores")){
            Sons = true;
        }
    }

    public MenuButton(Runnable action, String name, HashMap<String, Image> imagesCache) {
        this.action = action;
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                estClique();
            }

            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });
        type = "Bouton";
        this.name = name;
        this.imagesCache = imagesCache;
    }

    public void unLock(){
        lock = false;
    }

    public void Lock(){
        lock = true;
    }

    public void estClique() {
        action.run();
    }

    public void switchAnimation(){
        Animation = !Animation;
    }

    private void getImage(){
        if(type == "Bouton avec survol"){
            imageB = Configuration.lisImage("Menu/Bouton/" + name, imagesCache);
            imageS = Configuration.lisImage("Menu/Bouton_Survol/" + name, imagesCache);
        }
        else if(type == "Bouton selectionnable"){
            imageB = Configuration.lisImage("Menu/Bouton/" + name, imagesCache);
            imageBBarre = Configuration.lisImage("Menu/Bouton/" + name + "_barre", imagesCache);
            imageS = Configuration.lisImage("Menu/Bouton_Survol/" + name, imagesCache);
            imageSBarre = Configuration.lisImage("Menu/Bouton_Survol/" + name + "_barre", imagesCache);
        }
        else if(type == "Bouton"){
            imageB = Configuration.lisImage("Menu/" + name, imagesCache);
        }
    }

    public void paintComponent(Graphics g) {
        getImage();
        
        if (estSurvol || lock){
            if(Musique && !ig.getBackgroundSound() || Sons && !ig.getSoundEffect() || Animation)
                g.drawImage(imageSBarre, 0, 0, getWidth(), getHeight(), null);              
            else
                g.drawImage(imageS, 0, 0, getWidth(), getHeight(), null);
        } else {
            if(Musique && !ig.getBackgroundSound() || Sons && !ig.getSoundEffect() || Animation)
                g.drawImage(imageBBarre, 0, 0, getWidth(), getHeight(), null);              
            else
                g.drawImage(imageB, 0, 0, getWidth(), getHeight(), null);
        }
    }


}
