package Serveur;

class Partie{
    String hote;
    String mot_de_passe;
    boolean visible;

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
}