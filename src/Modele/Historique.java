package Modele;

import Structures.Sequence;
import Structures.SequenceListe;

public class Historique {
    private Sequence<Commande> historique_passe;
    private Sequence<Commande> historique_futur;

    /*
    ############################# Constructeurs #############################
    */

    public Historique() {
        reinitialise();
    }

    /*
    ############################# Methodes #############################
    */

    public void affichePasse(){
        System.out.println("Historique passé :"+historique_passe.toString());
    }

    public void afficheFutur(){
        System.out.println("Historique futur :"+historique_futur.toString());
    }

    void reinitialise() {
        historique_passe = new SequenceListe<Commande>();
        historique_futur = new SequenceListe<Commande>();
    }

    public void ajouterHistorique(Commande cmd) {
        historique_passe.insereTete(cmd);
        historique_futur = new SequenceListe<Commande>();
    }

    public boolean peutAnnuler() {
        return !historique_passe.estVide();
    }

    public boolean peutRefaire() {
        return !historique_futur.estVide();
    }

    public Commande annuler() {
        if (!peutAnnuler()) return null;
        return getCommandePrec();
    }

    public Commande refaire() {
        if (!peutRefaire()) return null;
        return getCommandeSuiv();
    }

    /*
    ############################# Getters #############################
    */

    public Commande getCommandePrec() {
        if (historique_passe.estVide()) return null;
        return historique_passe.extraitTete();
    }

    public Commande getCommandeSuiv() {
        if (historique_futur.estVide()) return null;
        return historique_futur.extraitTete();
    }

    public void addFutur(Commande c) {
        historique_futur.insereTete(c);
    }

    public void addPasse(Commande c) {
        historique_passe.insereTete(c);
    }
}