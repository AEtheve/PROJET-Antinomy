package Modele;

import java.util.ArrayList;
import java.util.Random;

import Global.Configuration;
import Structures.Sequence;

public class IAAleatoire extends IA {
    private Random r;

    public IAAleatoire() {
        r = new Random();
    }

    @Override
    public Sequence<Coup> joue() {
        Sequence<Coup> resultat = Configuration.nouvelleSequence();

        if (jeu.getDeck().getSceptre(jeu.getTour()) == -1) {
            int possibles[] = jeu.getSceptrePossibleInit();
            int random = r.nextInt(possibles.length);
            Coup coup = new Coup(Coup.SCEPTRE, possibles[random]);
            resultat.insereQueue(coup);
        } else {
            
            Carte cartes[] = jeu.getMain(jeu.getTour());
            int random_select = r.nextInt(cartes.length);
            
            Carte carte = cartes[random_select];

            Carte [] cartes_possibles = jeu.getCartesPossibles(carte);
        
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
        return resultat;
    }

}
