package Modele;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;
import Modele.Carte;

public class Jeu {
    Carte[] cartes;
    
    Joueur p1, p2;
    Plateau plateau;


    public Jeu(Joueur p1, Joueur p2, Plateau plateau) {
        this.p1 = p1;
        this.p2 = p2;
        this.plateau = plateau;
    }

    public Carte[] CréerCartes() {
        int i=1;
        int pos =0;

        this.cartes = new Carte[16];
        for (int valeur =1; valeur<=4; valeur++){
            for (int symbole =1; symbole<=4; symbole++){
                cartes[pos]=new Carte(symbole, i, valeur,0, false, false);
                pos++;
                i=(i+1)%4;
            }
        }
        return cartes;
    }

    public void afficherCartes(){
        for (int i=0; i<16; i++){
            System.out.println("[C :"+cartes[i].getCouleur()+", S :"+cartes[i].getSymbole()+", V :"+cartes[i].getValeur()+" ]");
        }
    }

    void jouer(Joueur j, Coup c){
        plateau.jouer(c);
    }



}
