package Modele;

import java.util.ArrayList;
import java.util.Random;

import Global.Configuration;
import Structures.Couple;
import Structures.Iterateur;
import Structures.Sequence;

public class IAAleatoire extends IA {
    private Random r;

    public IAAleatoire() {
        r = new Random();
    }

    @Override
    public Couple<Coup, Coup> joue() {
        Sequence<Couple<Coup, Coup>> coups = jeu.getCoupsPossibles();
        Iterateur<Couple<Coup, Coup>> it = coups.iterateur();
        int n = 0;
        while (it.aProchain()) {
            it.prochain();
            n++;
        }
        int i = r.nextInt(n);
        it = coups.iterateur();
        while (i > 0) {
            it.prochain();
            i--;
        }
        return it.prochain();
    }

}