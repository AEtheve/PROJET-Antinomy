package Modele;

public class Main {
    private Carte[] main;

    /*
    ############################# Constructeurs #############################
    */

    public Main(Carte[] main) {
        this.main = main;
    }

    /*
    ############################# Clone #############################
    */
    
	@Override
	public Object clone() {
		Carte[] copie_main = new Carte[main.length];
		for (int i=0; i<main.length; i++) {
			copie_main[i] = (Carte)main[i].clone();
		}
		return new Main(copie_main);
	}
    /*
    ############################# Getters #############################
    */
    public Carte[] getMain() {
        // Renvoie la main du joueur
        return main;
    }

    public Carte getCarte(int i) {
        // Renvoie une carte de la main
        return main[i];
    }

    /*
    ############################# Setters #############################
    */

    public void setCarte(Carte c, int i) {
        // Permet de modifier une carte de la main
        main[i] = c;
    }

    /*
    ############################# Méthodes d'affichage #############################
    */

    public String toString() {
        /* Tri la main par ordre croissant d'index et renvoie la main sous 
        forme de String */
        Main mainTriee = new Main(main);
        for (int i = 0; i < mainTriee.main.length; i++) {
            for (int j = i + 1; j < mainTriee.main.length; j++) {
                if (mainTriee.main[i].getIndex() > mainTriee.main[j].getIndex()) {
                    Carte tmp = mainTriee.main[i];
                    mainTriee.main[i] = mainTriee.main[j];
                    mainTriee.main[j] = tmp;
                }
            }
        }

        String s = "[";
        for (int i = 0; i < mainTriee.main.length; i++) {
            s += mainTriee.main[i].toString();
            if (i < mainTriee.main.length - 1) {
                s += ", ";
            }
        }
        s += "]";
        return s;
    }
}
