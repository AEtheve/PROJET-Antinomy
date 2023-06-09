package Modele;

import Structures.Iterateur;
import Structures.Sequence;
import Structures.SequenceListe;
import Global.Configuration;


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
        Configuration.info("Historique passe :");
        Iterateur<Commande> ite = historique_passe.iterateur();
        while (ite.aProchain()) {
            Commande c = ite.prochain();
            Configuration.info("Score J1 :"+c.scoreJ1+" Score J2 :"+c.scoreJ2+" Coup : "+c.coup.toString());
        }
    }

    public void afficheFutur(){
        Configuration.info("Historique futur :");
        Iterateur<Commande> ite = historique_futur.iterateur();
        while (ite.aProchain()) {
            Commande c = ite.prochain();
            Configuration.info("Score J1 :"+c.scoreJ1+" Score J2 :"+c.scoreJ2+" Coup : "+c.coup.toString());
        }
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

    public void ajouteFutur(Commande c) {
        historique_futur.insereTete(c);
    }

    public void ajoutePasse(Commande c) {
        historique_passe.insereTete(c);
    }

    public Sequence<Commande> getHistoriquePasse() {
        return historique_passe;
    }

    public Sequence<Commande> getHistoriqueFutur() {
        return historique_futur;
    }
}