package Modele;

public class Commande {
    Coup coup;
    int pos_prev_sceptre;
    int scoreJ1, scoreJ2;
    int codex;
    Boolean tour;

    public Commande(Coup coup, int pos_prev_sceptre,int codex, Boolean tour) {
        this.coup = coup;
        this.pos_prev_sceptre = pos_prev_sceptre;
        this.scoreJ1 = Compteur.getInstance().getJ1Points();
        this.scoreJ2 = Compteur.getInstance().getJ2Points();
        this.codex = codex;
        this.tour = tour;
    }

    public Coup getCoup() {
        return coup;
    }

    public int getPosSeptre() {
        return pos_prev_sceptre;
    }
    
    public void setScore(){
        Compteur.getInstance().setScore(true, scoreJ1);
        Compteur.getInstance().setScore(false, scoreJ2);
    }

    public String toString() {
        return coup.toString();
    }

    public Boolean getTour(){
        return tour;
    }
}
