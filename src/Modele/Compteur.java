package Modele;

import Global.Configuration;

public class Compteur {
    private static Compteur instance;
    private int J1, J2;


    /*
    ############################# Constructeur #############################
     */

    public Compteur() {
        // Initialisation du compteur
        J1 = J2 = 0;
    }

    /*
    ############################# Getteurs #############################
    */

    public static Compteur getInstance() {
        /* Création d'une instance de compteur pour avoir le score des joueurs
        de n'importe qu'elle classe */
        if (instance == null)
            instance = new Compteur();
        return instance;
    }

    public int getJ1Points() {
        return J1;
    }

    public int getJ2Points() {
        return J2;
    }


    /*
    ############################# Setteurs #############################
    */

    public void setScore(Boolean joueur, int score) {
        // Permet de modifier le score d'un joueur, utiliser pour le chargement
        if (joueur)
            J1 = score;
        else
            J2 = score;
    }

    /*
    ############################# Methodes #############################
    */  

    public void reset() {
        // Remet le compteur à 0
        J1 = J2 = 0;
    }

    public boolean isJ1Gagnant() {
        return J1 == Configuration.MAX;
    }

    public boolean isJ2Gagnant() {
        return J2 == Configuration.MAX;
    }

    /*
    ############################# Fonctions de jeu #############################
    */

    public int Incremente(Boolean joueur) {
        /* Incremente le score d'un joueur et renvoie 0 si le joueur 1 a gagné,
        1 si le joueur 2 a gagné, -1 sinon */
        Configuration.info("Joueur 1: " + J1 + " Joueur 2: " + J2);
        if (isJ1Gagnant() || isJ2Gagnant())
            throw new IllegalStateException("Le compteur est déjà à" + Configuration.MAX);
        if (joueur) {
            J1++;
            if (isJ1Gagnant())
                return 0;
        } else {
            J2++;
            if (isJ2Gagnant())
                return 1;
        }
        return -1;
    }    

    public int Vol(Boolean voleur) {
        /* Vol un point à un joueur et renvoie 0 si le joueur 1 a gagné,
        1 si le joueur 2 a gagné, -1 sinon */
        if (voleur) {
            if (J2 <= 0)
                return -1;
            J2--;
        } else {
            if (J1 <= 0)
                return -1;
            J1--;
        }
        return Incremente(voleur);
    }
}
