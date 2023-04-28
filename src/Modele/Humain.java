package Modele;

public class Humain implements Joueur {
    Carte [] main;
    String nom;
    int curseur;

    public Humain(String nom){
        this.nom = nom;
        curseur = 0;
    }

    public void setCurseur(int curseur){
        this.curseur = curseur;
    }

    public int getCurseur(){
        return curseur;
    }

    public Carte getCarte(int index){
        return main[index];
    }

    public void setCarte(Carte carte, int index){
        main[index] = carte;
    }

    public void setMain(Carte[] main){
        this.main = main;
    }

    public void retourneMain(){
        for (int i=0;i<3;i++){
            main[i].setVisibilite(!main[i].estVisible());
        }
    }

    public String getNom(){
        return nom;
    }

    public void clicCarte(Carte carte){
        // TODO
    }

    public void clicCoup(Carte carte){
        // TODO
    }

    public void debutTour(){
        // TODO
    }

    public void finTour(){
        // TODO
    }
    
}
