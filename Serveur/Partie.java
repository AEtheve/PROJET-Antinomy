package Serveur;

class Partie{
    private String hote;
    private String mot_de_passe;
    private boolean visible;

    Partie(String hote, String mot_de_passe){
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

    public String getHote(){
        return hote;
    }

    public String getMotDePasse(){
        return mot_de_passe;
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
}