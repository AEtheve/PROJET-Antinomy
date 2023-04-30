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

        int y = height - tailleY - (int)(0.03 * height);
        int x;

        if(joueur1.getCarte(0).estVisible()){
            boolean selec;
            if (cartesG1[0].carte.estSurvolee() || cartesG1[1].carte.estSurvolee() || cartesG1[2].carte.estSurvolee() || cartesG1[0].carte.estSelectionnee() || cartesG1[1].carte.estSelectionnee() || cartesG1[2].carte.estSelectionnee()) selec = true;  
            else selec = false;
            for(int i = 0; i < cartesG1.length; i++){
                x = width / 2 + i * tailleX + (tailleX / 9 * i);
                cartesG1[i].dessinImage(g, x, y, tailleX, tailleY,selec);
            }
            y = - (int)(0.07 * height);
            for(int i = 0; i < cartesG2.length; i++){
                x = 3 * width / 5 + (int)(tailleX / 2.5 * (i + 1));
                cartesG2[i].dessinImage(g, x, y, tailleX, tailleY,false);
            }
        } else{
            boolean selec;
            if (cartesG2[0].carte.estSurvolee() || cartesG2[1].carte.estSurvolee() || cartesG2[2].carte.estSurvolee()|| cartesG1[0].carte.estSelectionnee() || cartesG1[1].carte.estSelectionnee() || cartesG1[2].carte.estSelectionnee()) selec = true;  
            else selec = false;
            for(int i = 0; i < cartesG1.length; i++){
                x = width / 2 + i * tailleX + (tailleX / 9 * i);
                cartesG2[i].dessinImage(g, x, y, tailleX, tailleY,selec);
            }
            y = - (int)(0.07 * height);
            for(int i = 0; i < cartesG2.length; i++){
                x = width / 2 + i * tailleX + (tailleX / 9 * i);
                cartesG1[i].dessinImage(g, x, y, tailleX, tailleY,false);
            }
        }
    }

    public void dessinSceptre(Graphics g, int width, int height){
        int tailleY = height / 6;
        int tailleX = width / 13;

        int index = joueur1.getCurseur();
        int y = height / 2 + (int) (0.2 * tailleY);
        int x = tailleX + (index+1) * tailleX + (tailleX / 9 * (index+1));

        sceptreG1.dessinImage(g, x, y, tailleX, tailleY);

        index = joueur2.getCurseur();
        x = tailleX + (index+1) * tailleX + (tailleX / 9 * (index+1));
        y = height / 2 - (int) (2.2 * tailleY);

        sceptreG2.dessinImage(g, x, y, tailleX, tailleY);

    }
    
    int getCarteSelectionneeValeur(){
        for(int i = 0; i < 3; i++){
            if(joueur1.getCarte(i).estSelectionnee()) return joueur1.getCarte(i).getValeur();
        }
        return -1;
    }

    int getCarteSelectionneeSymbole(){
        for(int i = 0; i < 3; i++){
            if(joueur1.getCarte(i).estSelectionnee()) return joueur1.getCarte(i).getSymbole();
        }
        return -1;
    }

    int getCarteSelectionneeCouleur(){
        for(int i = 0; i < 3; i++){
            if(joueur1.getCarte(i).estSelectionnee()) return joueur1.getCarte(i).getCouleur();
        }
        return -1;
    }

    int getCarteSelectionneeIndex(){
        for(int i = 0; i < 3; i++){
            if(joueur1.getCarte(i).estSelectionnee()) return i;
        }
        return -1;
    }
}
