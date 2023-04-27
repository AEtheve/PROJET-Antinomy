package Modele;



public interface Joueur {
    Carte getCarte(int index);
    void setCarte(Carte carte, int index);

    void clicCarte(Carte carte);
    void clicCoup(Carte carte);

    void debutTour();
    void finTour();
}
