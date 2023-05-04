package Controleur;

import Vue.InterfaceUtilisateur;
import Modele.Jeu;
import Modele.Coup;
import Modele.Carte;
import Modele.Compteur;

public class ControleurJoueur {
    private Jeu j;
    int etat;
    InterfaceUtilisateur vue;
    Carte CarteMainAJouer;
    Carte[] CartesPossibles;

    public ControleurJoueur(Jeu j) {
        this.j = j;
        etat = 0;
        CarteMainAJouer = null;
    }

    public void SelectCarte(Carte c) {
        if (CarteMainAJouer != null) {
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
        if (etat != 1)
            return;
        for (Carte carte : CartesPossibles) {
            if (carte == c) {
                JouerCoup(CarteMainAJouer, c);
                etat = 0;
                CarteMainAJouer = null;
                return;
            }
        }

    }

    public void ajouteInterfaceUtilisateur(InterfaceUtilisateur v) {
        vue = v;
    }

    public void JouerCoup(Carte cMain, Carte cPlateau) {
        Coup coup = new Coup(Coup.ECHANGE, cMain.getIndex(), cPlateau.getIndex());
        if (!coup.estCoupValide(j))
            throw new IllegalArgumentException("Coup invalide");
        j.execCoup(coup);
        if (verifParadoxe()) {
            int res = Compteur.getInstance().Incremente(j.getTour());
            if(res == 0){
                System.out.println("Joueur 1 gagne");
                //TODO: Fin de partie
            } else if (res == 1){
                System.out.println("Joueur 2 gagne");
                // TODO: Fin de partie
            }
            // TODO: Choisir la direction du swap
            coup = new Coup(Coup.SWAP_DROIT);
            j.execCoup(coup);
            
        }
    }

    public boolean verifParadoxe() {
        return j.verifParadoxe();
    }

    public void toucheClavier(String touche) {
        switch (touche) {
            case "jouer_1":
                SelectCarte(j.getMain(j.getTour())[0]);
                // vue.miseAJour();
                break;
            case "jouer_2":
                SelectCarte(j.getMain(j.getTour())[1]);
                break;
            case "jouer_3":
                SelectCarte(j.getMain(j.getTour())[2]);
                break;
            default:
                System.out.println("Commande inconnue");
                break;
        }
    }
}