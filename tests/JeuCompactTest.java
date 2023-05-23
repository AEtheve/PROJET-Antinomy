package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import Global.Configuration;
import Modele.JeuCompact;
import Modele.JeuEntier;
import Modele.Coup;
import Modele.Historique;

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
        j.setSwap(false);
        j.joue(new Coup(Coup.ECHANGE,1,6));
        j.prochainCodex();
        j.joue(new Coup(Coup.ECHANGE, 2, 4));
        j.joue(new Coup(Coup.SWAP_GAUCHE));
        j.setSwap(false);
        j.joue(new Coup(Coup.ECHANGE, 1, 4));
        j.prochainCodex();
        j.joue(new Coup(Coup.ECHANGE, 2, 1));
        j.joue(new Coup(Coup.SWAP_DROIT));
        j.setSwap(false);

    }

    @Test
    public void testCLheureDuDuDuDuel(){

        Configuration.setFixedSeed(true);
        JeuEntier jeu = new JeuEntier();
        JeuCompact j = jeu.getJeuCompact();
        j.setHistorique(new Historique());
        
        Coup sceptre1 = new Coup(Coup.SCEPTRE, 4);
        j.joue(sceptre1);
        Coup sceptre2 = new Coup(Coup.SCEPTRE, 8);
        j.joue(sceptre2);

        Coup coup1 = new Coup(Coup.ECHANGE, 1, 6);
        j.joue(coup1);
        j.setScores(1, 0);

        Coup coup2 = new Coup(Coup.SWAP_GAUCHE);
        j.joue(coup2);
        assertEquals(1,j.scoreJ1);
        assertEquals(0, j.scoreJ2);

        j.setSwap(false);

        Coup coup3 = new Coup(Coup.ECHANGE,1,6);
        j.joue(coup3);
        j.prochainCodex();

        Coup coup4 = new Coup(Coup.ECHANGE,2,4);
        j.joue(coup4);

        Coup coup5 = new Coup(Coup.SWAP_GAUCHE);
        j.joue(coup5);
        j.setSwap(false);

        Coup coup6 = new Coup(Coup.ECHANGE,1,4);
        j.joue(coup6);
        j.prochainCodex();

        Coup coup7 = new Coup(Coup.ECHANGE,2,1);
        j.joue(coup7);

        Coup coup_7 = new Coup(Coup.SWAP_GAUCHE);
        assertFalse(coup_7.estCoupValide(j));
        
        assertTrue(j.getSwap());
        Coup coup8 = new Coup(Coup.SWAP_DROIT);
        j.joue(coup8);
        j.setSwap(false);

        assertFalse(j.estFini());
        
    }
    
}
