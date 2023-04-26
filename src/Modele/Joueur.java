package Modele;



public interface Joueur {
    Carte getCarte();
    void setCarte(Carte carte);

    String getNom();
    void setNom(String nom);

    void clicCarte(Carte carte);
    void clicCoup(Carte carte);

    void debutTour();
    void finTour();
}
