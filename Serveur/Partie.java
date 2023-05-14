package Serveur;

import java.io.DataInputStream;

class Partie{
    int id;
    private String hote;
    private String mot_de_passe;
    private boolean visible;
    private ServeurCentral serveurCentral;

    Partie(int id, String hote, String mot_de_passe){
        this.id = id;
        this.hote = hote;
        this.mot_de_passe = mot_de_passe;
        visible = true;
    }

    public boolean estVisible(){
        return visible;
    }

    public void setPasVisible(){
        visible = false;
    }

    public int getId(){
        return id;
    }

    public String getHote(){
        return hote;
    }

    public String getMotDePasse(){
        return mot_de_passe;
    }

    public boolean MotDePasseRequis(){
        return mot_de_passe != "" && mot_de_passe != null;
    }

    public void setServeurCentral(ServeurCentral serveurCentral){
        this.serveurCentral = serveurCentral;
    }

    public ServeurCentral getServeurCentral(){
        return serveurCentral;
    }
}

class Parties {
    private Partie[] parties;
    private int nbParties;
    public Parties() {
        parties = new Partie[10];
        nbParties = 0;
    }

    public void ajouterPartie(Partie partie) {
        parties[nbParties++] = partie;
    }

    public void supprimerPartie(int i) {
        parties[i] = parties[nbParties - 1];
        nbParties--;
    }

    public int getNbParties() {
        return nbParties;
    }

    public Partie getPartie(int i) {
        return parties[i];
    }

    public ServeurCentral getServeurCentral(int i){
        return parties[i].getServeurCentral();
    }

    public void setServeurCentral(int i, ServeurCentral serveurCentral){
        parties[i].setServeurCentral(serveurCentral);
    }

    public void rejoindrePartie(int id, ThreadDialogue tjoueur){
        parties[id].getServeurCentral().rejoindrePartie(tjoueur);
    }
}