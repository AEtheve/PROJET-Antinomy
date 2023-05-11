package Modele;

public class Compteur {
    private static Compteur instance;

    public static Compteur getInstance() {
        if (instance == null)
            instance = new Compteur();
        return instance;
    }

    private int J1, J2;

    public Compteur() {
        J1 = J2 = 0;
    }

    public int Incremente(Boolean joueur) {
        System.out.println("Joueur 1: " + J1 + " Joueur 2: " + J2);
        if (J1 == 5 || J2 == 5)
            throw new IllegalStateException("Le compteur est déjà à 5");
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

    public boolean isJ1Gagnant() {
        return J1 == 5;
    }

    public boolean isJ2Gagnant() {
        return J2 == 5;
    }

    public void setScore(Boolean joueur, int score) {
        if (joueur)
            J1 = score;
        else
            J2 = score;
    }

    public void Vol(Boolean voleur) {
        if (voleur) {
            if (J2 <= 0)
                return;
            J1++;
            J2--;
        } else {
            if (J1 <= 0)
                return;
            J2++;
            J1--;
        }
    }

    public int getJ1Points() {
        return J1;
    }

    public int getJ2Points() {
        return J2;
    }

    public void reset() {
        J1 = J2 = 0;
    }

}