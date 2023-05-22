package Modele;

public class Commande {
    Coup coup;
    int pos_prev_sceptre;
    int scoreJ1, scoreJ2;
    int codex;
    Boolean tour;

    public Commande(Coup coup, int pos_prev_sceptre,int codex, Boolean tour, int scoreJ1, int scoreJ2) {
        this.coup = coup;
        this.pos_prev_sceptre = pos_prev_sceptre;
        // this.scoreJ1 = Compteur.getInstance().getJ1Points();
        // this.scoreJ2 = Compteur.getInstance().getJ2Points();
        this.scoreJ1 = scoreJ1;
        this.scoreJ2 = scoreJ2;
        this.codex = codex;
        this.tour = tour;
    }

    public Coup getCoup() {
        return coup;
    }

    public int getPosSeptre() {
        return pos_prev_sceptre;
    }
    
    // public void setScore(){
        // Compteur.getInstance().setScore(true, scoreJ1);
        // Compteur.getInstance().setScore(false, scoreJ2);

    // }

    public String toString() {
        return coup.toString();
    }

    public Boolean getTour(){
        return tour;
    }

    public int getCodex(){
        return codex;
    }

    public int getScoreJ1(){
        return scoreJ1;
    }

    public int getScoreJ2(){
        return scoreJ2;
    }
}
