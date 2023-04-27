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

    void afficherJeuDeCartes(Carte[] c){
        System.out.println("Jeu de cartes : ");
        for (int i=0;i<c.length;i++){
            System.out.println("Carte "+(i+1)+" : "+c[i].getValeur()+" "+c[i].getSymbole()+" "+c[i].getCouleur());
        }
    }


    void jouer(Joueur j, Coup c){
        plateau.jouer(c);
    }

    public void initJeu(){
        Carte[] c = CréerCartes();
        Carte[] main = creerMain(c);
        afficherMain(main);
        c = supprimeDejaUtilisees(c, main);
        afficherJeuDeCartes(c);
    }

    Carte[]creerMain(Carte[] cartes){
        Carte[] main = new Carte[3];
        //Pioche 3 cartes aléatoires
        for (int i=0;i<3;i++){
            int index = (int) (Math.random()*16);
            while (cartes[index]==null) index = (int) (Math.random()*16);
            main[i]=cartes[index];
        }
        return main;
    }

    void afficherMain(Carte[] main){
        System.out.println("MAIN : ");
        for (int i=0;i<3;i++){
            System.out.println("Carte "+(i+1)+" : "+main[i].getValeur()+" "+main[i].getSymbole()+" "+main[i].getCouleur());
        }
    }

    Carte[] supprimeDejaUtilisees(Carte[] cartes,Carte[] main){
        Carte[] cartes_res = new Carte[cartes.length-main.length];
        int pos = 0;
        for (int i=0;i<cartes.length;i++){
            if (!carteTrouvée(main, cartes[i])){
                cartes_res[pos]=cartes[i];
                pos++;
            }
        }
        return cartes_res;
    }

    boolean carteTrouvée(Carte[] cartes, Carte c){
        for (int i=0;i<cartes.length;i++){
            int verif=0;
            if (cartes[i].getCouleur()==c.getCouleur()) verif++;
            if (cartes[i].getSymbole()==c.getSymbole()) verif++;
            if (cartes[i].getValeur()==c.getValeur()) verif++;
            if (verif>1) return true;
        }
        return false;
    }

    void creerPlateau(Carte[] c){
        for (int i=0;i<1;i++){
            
        }
    }



}
