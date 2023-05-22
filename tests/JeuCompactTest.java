package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import Global.Configuration;
import Modele.JeuCompact;
import Modele.JeuEntier;
import Modele.Coup;

public class JeuCompactTest {

    @Test
    public void testGetJeuCompact(){

        Configuration.setFixedSeed(true);
        JeuEntier jeu = new JeuEntier();
        JeuCompact j = jeu.getJeuCompact();

        j.setScores(2,3);
        assertEquals(2, j.scoreJ1);
        assertEquals(3, j.scoreJ2);
    }

    @Test
    public void testClone(){

        Configuration.setFixedSeed(true);
        JeuEntier jeu = new JeuEntier();
        JeuCompact j = jeu.getJeuCompact();
        JeuCompact j2 = (JeuCompact) j.clone();
        assertNotEquals(j, j2);
    }

    @Test
    public void testJoue(){

        Configuration.setFixedSeed(true);
        JeuEntier jeu = new JeuEntier();
        JeuCompact j = jeu.getJeuCompact();
        
        j.joue(new Coup(Coup.SCEPTRE, 4));
        j.joue(new Coup(Coup.SCEPTRE, 8));
        j.joue(new Coup(Coup.ECHANGE, 1, 6));
        j.joue(new Coup(Coup.SWAP_GAUCHE));
        j.joue(new Coup(Coup.ECHANGE,1,6));
        j.prochainCodex();
        j.joue(new Coup(Coup.ECHANGE, 2, 4));
        j.joue(new Coup(Coup.SWAP_GAUCHE));
        j.joue(new Coup(Coup.ECHANGE, 1, 4));
        j.prochainCodex();
        j.joue(new Coup(Coup.ECHANGE, 2, 1));
        j.joue(new Coup(Coup.SWAP_DROIT));


    }
    
}
