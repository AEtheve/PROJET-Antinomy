package Modele;

public class Coup {
    private final static int ECHANGE = 0;
    private final static int SWAP_DROIT = 1;
    private final static int SWAP_GAUCHE = 2;

    private int type;
    Carte carte_main, carte_plateau;
    Main m;
    Carte swap [];

    public Coup (int type){
        this.type = type;
    }

    
}
