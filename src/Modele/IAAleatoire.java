package Modele;

import java.util.ArrayList;
import java.util.Random;

import Global.Configuration;
import Structures.Sequence;

public class IAAleatoire extends IA {
    private Random r;

    Sequence<Coup> resultat = Configuration.nouvelleSequence();

    public IAAleatoire() {
        r = new Random();
    }

    @Override
    public Sequence<Coup> joue() {
        if (jeu.getDeck().getSceptre(jeu.getTour()) == -1) {
            int possibles[] = jeu.getSceptrePossibleInit();
            int random = r.nextInt(possibles.length);
            Coup coup = new Coup(Coup.SCEPTRE, possibles[random]);
            resultat.insereQueue(coup);
        } else {
            if (jeu.verifParadoxe()) {
                boolean swapGauchePossible = false;
                boolean swapDroitPossible = false;
                int pos_sc = jeu.getDeck().getSceptre(jeu.getTour());
                if (pos_sc - 3 >= 0 && jeu.getTour() == Jeu.JOUEUR_1) {
                    swapGauchePossible = true;
                }
                if (pos_sc + 3 < jeu.getDeck().getContinuum().length && jeu.getTour() == Jeu.JOUEUR_1) {
                    swapDroitPossible = true;
                }
                if (pos_sc - 1 >= 0 && jeu.getTour() == Jeu.JOUEUR_2) {
                    swapGauchePossible = true;
                }
                if (pos_sc + 1 < jeu.getDeck().getContinuum().length && jeu.getTour() == Jeu.JOUEUR_2) {
                    swapDroitPossible = true;
                }
                if (swapGauchePossible && swapDroitPossible) {
                    int random = r.nextInt(2);
                    if (random == 0) {
                        Coup coup = new Coup(Coup.SWAP_GAUCHE);
                        resultat.insereQueue(coup);
                    } else {
                        Coup coup = new Coup(Coup.SWAP_DROIT);
                        resultat.insereQueue(coup);
                    }
                } else if (swapGauchePossible) {
                    Coup coup = new Coup(Coup.SWAP_GAUCHE);
                    resultat.insereQueue(coup);
                } else if (swapDroitPossible) {
                    Coup coup = new Coup(Coup.SWAP_DROIT);
                    resultat.insereQueue(coup);
                } else {
                    System.out.println("Aucun swap possible");
                }
            } else {
                Carte cartes[] = jeu.getMain(jeu.getTour());
                int random_select = r.nextInt(cartes.length);

                Carte carte = cartes[random_select];

                Carte[] cartes_possibles = jeu.getCartesPossibles(carte);

                ArrayList<Integer> cartes_possibles_index = new ArrayList<Integer>();
                for (int i = 0; i < cartes_possibles.length; i++) {
                    if (cartes_possibles[i] != null) {
                        cartes_possibles_index.add(cartes_possibles[i].getIndex());
                    }
                }

                int random_move = r.nextInt(cartes_possibles_index.size());

                Coup coup = new Coup(Coup.ECHANGE, carte.getIndex(), cartes_possibles_index.get(random_move));

                resultat.insereQueue(coup);
            }
        }
        return resultat;
    }

}