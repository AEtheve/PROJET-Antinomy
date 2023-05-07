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

    public Coup getCoupPrec(int n) {
        Coup coup = null;
        Sequence<Coup> historique_passe_copie = new SequenceListe<Coup>();
        Coup c;
        for (int i = 0; i < n-1; i++) {
            c = historique_passe.extraitTete();
            historique_passe_copie.insereTete(c);
        }
        coup = historique_passe.extraitTete();
        historique_passe.insereTete(coup);
        while (!historique_passe_copie.estVide()) {
            c = historique_passe_copie.extraitTete();
            historique_passe.insereTete(c);
        }
        return coup;
    }

}
