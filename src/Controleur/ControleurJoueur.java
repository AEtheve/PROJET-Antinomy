package Controleur;

import Vue.InterfaceGraphique;
import Modele.Jeu;
import Modele.Carte;

public class ControleurJoueur {
    private Jeu j;
    private InterfaceGraphique ig;
    int etat; //
    Carte CarteMainAJouer;
    Carte [] CartesPossibles;

    public ControleurJoueur(Jeu j, InterfaceGraphique ig) {
        this.j = j;
        this.ig = ig;
        etat = 0;
        CarteMainAJouer = null;
    }

    public void SelectCarte(Carte c) {
        if (CarteMainAJouer == null) {
            for (Carte carte : j.getDeck().getPlateau()) {
                if (carte == c) {
                    SelectCartePlateau(c);
                    return;
                }
            }
        }
        if (j.getTour()) {
            for (Carte carte : j.getMain(true)) {
                if (carte == c) {
                    SelectCarteMain(c);
                    return;
                }
            }
        } else {
            for (Carte carte : j.getMain(false)) {
                if (carte == c) {
                    SelectCarteMain(c);
                    return;
                }
            }
        }
        if (c != j.getDeck().getCodex()) {
            throw new IllegalArgumentException("Carte non valide");
        }
        return;
    }

    void SelectCarteMain(Carte c) {
        CarteMainAJouer = c;
        etat = 1;
        CartesPossibles = j.getCartesPossibles(c);
    }

    void SelectCartePlateau(Carte c) {
        if (etat != 1) return;
        for (Carte carte : CartesPossibles) {
            if (carte == c) {
                JouerCoup(CarteMainAJouer, c);
                etat = 0;
                CarteMainAJouer = null;
                return;
            }
        }
        
    }

    public void JouerCoup(Carte cMain, Carte cPlateau){
        
    }

}
