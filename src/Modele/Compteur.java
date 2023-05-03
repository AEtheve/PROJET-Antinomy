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

    public int Incremente(int joueur) {
        if (joueur == 0){
            J1++; 
            if (J1>=5) return 0;
        } else {
            J2++;
            if(J2>=5) return 1;
        }
        return -1;
    }

    public void Vol(int voleur) {
        if (voleur == 0){
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