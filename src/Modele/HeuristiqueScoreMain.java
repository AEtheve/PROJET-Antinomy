package Modele;

public class HeuristiqueScoreMain extends Heuristique {

    @Override
    public int heuristique(JeuCompact jeu, boolean tour) {
        if (tour) {
            return 70 * (jeu.scoreJ1 - jeu.scoreJ2) + 30 * (jeu.heuristiqueScoreMainJ1 - jeu.heuristiqueScoreMainJ2);
        } else {
            return 70 * (jeu.scoreJ2 - jeu.scoreJ1) + 30 * (jeu.heuristiqueScoreMainJ2 - jeu.heuristiqueScoreMainJ1);
        }    
    }
}