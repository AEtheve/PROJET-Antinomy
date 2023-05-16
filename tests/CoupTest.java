package tests;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ObjectInputFilter.Config;
import java.util.Arrays;

import Modele.Carte;
import Modele.Coup;
import Modele.JeuEntier;
import Modele.Jeu;
import Global.Configuration;

public class CoupTest {
    
    @Test
    public void testGetType() {

        Coup c1 = new Coup(Coup.ECHANGE, 1, 3);
        assertEquals(Coup.ECHANGE,c1.getType());

        Coup c2 = new Coup(Coup.ECHANGE_SWAP, 1, 3);
        assertEquals(Coup.ECHANGE_SWAP, c2.getType());

        assertThrows(IllegalArgumentException.class, () -> {

            Coup c3 = new Coup(Coup.SCEPTRE, 1, 3);
        });

        Coup c4 = new Coup(Coup.SCEPTRE, 3);
        assertEquals(Coup.SCEPTRE, c4.getType());

        assertThrows(IllegalArgumentException.class, () -> {

            Coup c5 = new Coup(Coup.ECHANGE, 3);
        });

        Coup c6 = new Coup(Coup.SWAP_DROIT);
        assertEquals(Coup.SWAP_DROIT, c6.getType());

        Coup c7 = new Coup(Coup.SWAP_GAUCHE);
        assertEquals(Coup.SWAP_GAUCHE, c7.getType());

        Coup c8 = new Coup(Coup.SCEPTRE);
        assertEquals(Coup.SCEPTRE, c8.getType());

        assertThrows(IllegalArgumentException.class, () -> {

            Coup c9 = new Coup(Coup.ECHANGE_SWAP);
        });

    }

    @Test
    public void testSetType(){

        Coup c1 = new Coup(Coup.ECHANGE, 1,3);
        assertEquals(Coup.ECHANGE, c1.getType());
        c1.setType((byte)Coup.ECHANGE_SWAP);
        assertEquals(Coup.ECHANGE_SWAP, c1.getType());
    }

    @Test
    public void testGetCarteMain(){

        Coup c1 = new Coup(Coup.ECHANGE, 1,3);
        assertEquals(1, c1.getCarteMain());
    }

    @Test
    public void testGetCarteContinuum(){

        Coup c1 = new Coup(Coup.ECHANGE, 1,3);
        assertEquals(3, c1.getCarteContinuum());
    }

    @Test
    public void testCoupValide(){

        Configuration.setFixedSeed(true);
        Jeu jeu = new Jeu();
        
        Coup sceptre1 = new Coup(Coup.SCEPTRE, 1);
        jeu.joue(sceptre1);
        
        Coup sceptre2 = new Coup(Coup.SCEPTRE, 3);
        jeu.joue(sceptre2);

        Coup c1 = new Coup(Coup.ECHANGE, 2, 4);
        assertTrue(c1.estCoupValide(jeu));
        Coup c2 = new Coup(Coup.ECHANGE, 2, 2);
        assertFalse(c2.estCoupValide(jeu));

    }

    @Test
    public void testCoupValide2(){
        JeuEntier j = new JeuEntier();
        int pos_sc = j.getDeck().getSceptre(j.getTour());
        Configuration.setFixedSeed(true);
        Jeu jeu = new Jeu();
        
        Coup sceptre_1 = new Coup(Coup.SCEPTRE, 0);
        assertFalse(sceptre_1.estCoupValide(jeu));
        Coup sceptre1 = new Coup(Coup.SCEPTRE, 1);
        assertTrue(sceptre1.estCoupValide(jeu));
        jeu.joue(sceptre1);
        
        Coup sceptre2 = new Coup(Coup.SCEPTRE, 3);
        jeu.joue(sceptre2);

        Coup coup_1 = new Coup(Coup.ECHANGE, 2, 2);
        assertFalse(coup_1.estCoupValide(jeu));
        Coup coup1 = new Coup(Coup.ECHANGE, 2, 4);
        jeu.joue(coup1);

        Coup coup2 = new Coup(Coup.ECHANGE, 2, 4);
        jeu.joue(coup2);

        Coup coup3 = new Coup(Coup.ECHANGE, 2, 5);
        jeu.joue(coup3);

        Coup coup4 = new Coup(Coup.SWAP_GAUCHE);
        Coup coup5 = new Coup(Coup.SWAP_DROIT);
        assertTrue(coup4.estCoupValide(jeu));
        assertTrue(coup5.estCoupValide(jeu));

        assertThrows(IllegalArgumentException.class, () -> {

            Coup c3 = new Coup(Coup.ECHANGE_SWAP, 3);
        });

        jeu.joue(coup4);

        Coup coup6 = new Coup(Coup.ECHANGE, 0, 8);
        jeu.joue(coup6);

        Coup coup7 = new Coup(Coup.ECHANGE, 0, 4);
        jeu.joue(coup7);

        Coup coup8 = new Coup(Coup.ECHANGE, 1, 5);
        jeu.joue(coup8);

        Coup coup9 = new Coup(Coup.ECHANGE, 0, 2);
        jeu.joue(coup9);

        Coup coup10 = new Coup(Coup.SWAP_DROIT);
        assertTrue(coup10.estCoupValide(jeu));

        Coup coup11 = new Coup(Coup.SWAP_GAUCHE);
        assertFalse(coup11.estCoupValide(jeu));

        jeu.joue(coup10);

        Coup coup12 = new Coup(Coup.ECHANGE, 0, 2);
        jeu.joue(coup12);

        Coup coup13 = new Coup(Coup.ECHANGE, 2, 6);
        jeu.joue(coup13);

        Coup coup14 = new Coup(Coup.SWAP_DROIT);
        assertFalse(coup14.estCoupValide(jeu));
        assertThrows(IllegalArgumentException.class, () -> {
            coup14.setType((byte)5);
            jeu.joue(coup14);
        });

        Coup coup15 = new Coup(Coup.SWAP_GAUCHE);
        assertTrue(coup15.estCoupValide(jeu));

    }
    

}
