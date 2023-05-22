package Modele;

public class HeuristiqueScoreMain extends Heuristique {

    @Override
    public int heuristique(JeuCompact jeu, boolean tour) {
        if (tour) {
            return 70 * (jeu.scoreJ1 - jeu.scoreJ2) + 30 * (jeu.heuristiquePositionJ1 - jeu.heuristiquePositionJ2);
        } else {
            return 70 * (jeu.scoreJ2 - jeu.scoreJ1) + 30 * (jeu.heuristiquePositionJ2 - jeu.heuristiquePositionJ1);
        }    
    }
}