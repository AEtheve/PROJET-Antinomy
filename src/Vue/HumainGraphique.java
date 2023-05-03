package Vue;

import Modele.Humain;

import java.awt.*;

public class HumainGraphique {
    Humain joueur1, joueur2;
    SceptreGraphique sceptreG1, sceptreG2;
    CarteGraphique [] cartesG1, cartesG2;
    
    public HumainGraphique(Humain joueur1, Humain joueur2){
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;

        this.cartesG1 = new CarteGraphique[3];
        this.cartesG2 = new CarteGraphique[3];
        for(int i = 0; i < 3; i++){
            this.cartesG1[i] = new CarteGraphique(joueur1.getCarte(i));
            this.cartesG2[i] = new CarteGraphique(joueur2.getCarte(i));
        }

        sceptreG1 = new SceptreGraphique();
        sceptreG2 = new SceptreGraphique();
    }

    public void dessinCartes(Graphics g, int width, int height){

        int tailleY = height / 6;
        int tailleX = width / 13;

        int y;
        int x;
        boolean selec;
        if (cartesG1[0].carte.estSurvolee() || cartesG1[1].carte.estSurvolee() || cartesG1[2].carte.estSurvolee() || cartesG1[0].carte.estSelectionnee() || cartesG1[1].carte.estSelectionnee() || cartesG1[2].carte.estSelectionnee()) selec = true;  
        else selec = false;

        if(joueur1.getCarte(0).estVisible()){
            y = height - tailleY - (int)(0.03 * height); // Centre de la fenêtre
            for(int i = 0; i < cartesG1.length; i++){
                x = width / 2  + (i-1) * tailleX + (tailleX / 9 * (i-1));
                cartesG1[i].dessinImage(g, x, y, tailleX, tailleY,selec);
            }
        } else{
            y = height - tailleY + (int)(0.07 * height);
            for(int i = 0; i < cartesG2.length; i++){
                x = 3 * width / 5 + (int)(tailleX / 2.5 * (i + 1));
                cartesG1[i].dessinImage(g, x, y, tailleX, tailleY,false);
            }
        }

        if (cartesG2[0].carte.estSurvolee() || cartesG2[1].carte.estSurvolee() || cartesG2[2].carte.estSurvolee()|| cartesG1[0].carte.estSelectionnee() || cartesG1[1].carte.estSelectionnee() || cartesG1[2].carte.estSelectionnee()) selec = true;  
        else selec = false;

        if (joueur2.getCarte(0).estVisible()){
            y = (int) (0.03 * height);
            for(int i = 0; i < cartesG1.length; i++){
                x = width / 2  + (i-1) * tailleX + (tailleX / 9 * (i-1));
                cartesG2[i].dessinImage(g, x, y, tailleX, tailleY,selec);
            }
        } else {
            y = - (int)(0.07 * height);
            for(int i = 0; i < cartesG2.length; i++){
                x = 3 * width / 5 + (int)(tailleX / 2.5 * (i + 1));
                cartesG2[i].dessinImage(g, x, y + tailleY, tailleX, -tailleY,false);
            }
        }
    }

    public void dessinSceptre(Graphics g, int width, int height){
        int tailleY = height / 6;
        int tailleX = width / 13;

        int index = joueur1.getCurseur();
        int y = height / 2 + tailleY / 2 + (int) (0.2 * tailleY);
        int x = tailleX + (index+1) * tailleX + (tailleX / 9 * (index+1));

        sceptreG1.dessinImage(g, x, y+tailleY, tailleX, -tailleY);

        index = joueur2.getCurseur();
        x = tailleX + (index+1) * tailleX + (tailleX / 9 * (index+1));
        y = height/2 - 2 * tailleY + (int) (0.3 * tailleY);

        sceptreG2.dessinImage(g, x, y, tailleX, tailleY);

    }
    
    int getCarteSelectionneeValeur(){
        if (getCarteSelectionneeIndex() != -1) return joueur1.getCarte(getCarteSelectionneeIndex()).getValeur();
        else return -1;
    }

    int getCarteSelectionneeSymbole(){
        if (getCarteSelectionneeIndex() != -1) return joueur1.getCarte(getCarteSelectionneeIndex()).getSymbole();
        else return -1;
    }

    int getCarteSelectionneeCouleur(){
        if (getCarteSelectionneeIndex() != -1) return joueur1.getCarte(getCarteSelectionneeIndex()).getCouleur();
        else return -1;
    }

    int getCarteSelectionneeIndex(){
        for(int i = 0; i < 3; i++){
            if(joueur1.getCarte(i).estSelectionnee()) return i;
        }
        return -1;
    }
}
