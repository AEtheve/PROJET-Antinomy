package Modele;

public class Compteur {
    private static Compteur instance;

    public static Compteur getInstance() {
        if (instance == null) instance = new Compteur();
        return instance;
    }

    private int J1, J2;

    public Compteur() {
        J1 = J2 = 0;
    }

    public int Incremente(Boolean joueur) {
        System.out.println("Joueur 1: " + J1 + " Joueur 2: " + J2);
        if (J1 > 5 || J2 > 5)
         throw new IllegalStateException("Le compteur est déjà à 5");
        if (joueur){
            J1++; 
            if (J1>=5) return 0;
        } else {
            J2++;
            if(J2>=5) return 1;
        }
        return -1;
    }

    public void Vol(Boolean voleur) {
        if (voleur){
            if (J2<=0) return;
            J1++; 
            J2--;
        } else {
            if (J1<=0) return;
            J2++;
            J1--;
        }
    }

    public int getJ1Points(){
        return J1;
    }

    public int getJ2Points(){
        return J2;
    }

}