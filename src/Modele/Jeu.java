package Modele;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;
import Modele.Carte;

public class Jeu {
    Carte[] cartes;
    
    // Joueur p1, p2; TODO
    Humain p1, p2;
    Plateau plateau;


    // public Jeu(Joueur p1, Joueur p2, Plateau plateau) { // TODO
    public Jeu(Humain p1, Humain p2, Plateau plateau) {
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
                // cartes[pos]=new Carte(symbole, i, valeur,0, false, false);
                cartes[pos]=new Carte(symbole, i, valeur,0, true);
                pos++;
                i=(i+1)%5;
                if(i==0) i++;
            }
            i=(i+1)%5;
            if(i==0) i++;
        }
        return cartes;
    }

    public void afficherJeuDeCartes(Carte[] c){
        System.out.println("Jeu de cartes : ");
        for (int i=0;i<c.length;i++){
            System.out.println("Carte "+(i+1)+" : "+c[i].getValeur()+" "+c[i].getSymbole()+" "+c[i].getCouleur());
            // System.out.println("type : "+c[i].getType());
        }
    }


    void jouer(Joueur j, Coup c){
        plateau.jouer(c);
    }

    public void initJeu(){
        // Créer les mains des deux joueurs et le plateau
        cartes = CréerCartes();
        Carte[] main = creerMain(cartes);
        Carte[] main2 = creerMain(cartes);
        afficherMain(main);
        afficherMain(main2);
        afficherJeuDeCartes(this.cartes);
    }

    public Carte[]creerMain(Carte[] cartes){
        // Créer et retourner une main de 3 cartes
        Carte[] main = new Carte[3];
        for (int i=0;i<3;i++){
            int index = (int) (Math.random()*cartes.length);
            while (cartes[index]==null) index = (int) (Math.random()*16);
            main[i]=cartes[index];
            supprimeDejaUtilisees(cartes[index]);
        }

        System.out.println("MAIN : ");
        for (int i=0;i<3;i++){
            System.out.println("Carte "+(i+1)+" : "+main[i].getValeur()+" "+main[i].getSymbole()+" "+main[i].getCouleur());
        }
        return main;
    }

    void afficherMain(Carte[] main){
        // Affiche la main passée en argument
        System.out.println("MAIN : ");
        for (int i=0;i<3;i++){
            System.out.println("Carte "+(i+1)+" : "+main[i].getValeur()+" "+main[i].getSymbole()+" "+main[i].getCouleur());
        }
    }

    void supprimeDejaUtilisees(Carte carte){
        // Supprime la carte passée en argument du tableau de cartes
        Carte[] cartes_res = new Carte[cartes.length-1];
        int pos = 0;
        for (int i=0;i<this.cartes.length;i++){
            if (!carteEgale(carte, this.cartes[i])){
                if (pos==cartes_res.length) break;
                cartes_res[pos]=this.cartes[i];
                pos++;
            }
        }
        this.cartes = cartes_res;
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

    boolean carteEgale(Carte c1, Carte c2){
        int verif=0;
        if (c1.getCouleur()==c2.getCouleur()) verif++;
        if (c1.getSymbole()==c2.getSymbole()) verif++;
        if (c1.getValeur()==c2.getValeur()) verif++;
        if (verif>1) return true;
        return false;
    }

    void creerPlateau(Carte[] c){
        for (int i=0;i<1;i++){
            
        }
    }

    public Carte [] getCartes(){
        return this.cartes;
    }


}
