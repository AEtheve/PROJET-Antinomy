package Modele;

import java.util.ArrayList;

import Structures.Sequence;
import Structures.SequenceListe;

public class Historique {

    Sequence<Coup> historique_passe;
    Sequence<Coup> historique_futur;

    public Historique() {
        reinitialise();
    }


    void reinitialise() {
        historique_passe = new SequenceListe<Coup>();
        historique_futur = new SequenceListe<Coup>();
    }

    public void ajouterCoup(Coup c) {
        historique_passe.insereTete(c);
        historique_futur = new SequenceListe<Coup>();
    }

    public boolean peutAnnuler() {
        return !historique_passe.estVide();
    }

    public boolean peutRefaire() {
        return !historique_futur.estVide();
    }

    public Coup annuler() {
        if (peutAnnuler()) {
            Coup c = historique_passe.extraitTete();
            historique_futur.insereTete(c);
            return c;
        }
        return null;
    }

    public Coup refaire() {
        if (peutRefaire()) {
            Coup c = historique_futur.extraitTete();
            historique_passe.insereTete(c);
            return c;
        }
        return null;
    }

}
