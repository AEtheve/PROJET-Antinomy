package Modele;

public class HeuristiqueScore extends Heuristique {

    @Override
    public int heuristique(JeuCompact jeu, boolean tour) {
        return (tour ? (jeu.scoreJ1 - jeu.scoreJ2) : (jeu.scoreJ2 - jeu.scoreJ1));
    }
}