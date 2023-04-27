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
                i=(i+1)%5;
                if(i==0) i++;
            }
            i=(i+1)%5;
            if(i==0) i++;
        }
        return cartes;
    }

    public void afficherCartes(){
        for (int i=0; i<16; i++){
            System.out.println("[C :"+cartes[i].getCouleur()+", S :"+cartes[i].getSymbole()+", V :"+cartes[i].getValeur()+" ]");
        }
    }

    public boolean verifierJeuDeCartes(){
        for (int i=0;i<16;i++){
            for (int j=0;j<16;j++){
                if (i!=j){
                    int verif=0;
                    if (cartes[i].getCouleur()==cartes[j].getCouleur()) verif++;
                    if (cartes[i].getSymbole()==cartes[j].getSymbole()) verif++;
                    if (cartes[i].getValeur()==cartes[j].getValeur()) verif++;
                    if (verif>1) return false;
                }
            }
        }
        return true;
    }

    void jouer(Joueur j, Coup c){
        plateau.jouer(c);
    }



}
