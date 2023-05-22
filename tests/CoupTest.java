package tests;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ObjectInputFilter.Config;
import java.util.Arrays;

import Modele.Coup;
import Modele.Historique;
import Modele.Jeu;
import Modele.JeuCompact;
import Modele.JeuEntier;
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
        JeuEntier jeu = new JeuEntier();
        jeu.setHistorique(new Historique());
        
        Coup sceptre1 = new Coup(Coup.SCEPTRE, 1);
        jeu.joue(sceptre1);
        
        Coup sceptre2 = new Coup(Coup.SCEPTRE, 3);
        jeu.joue(sceptre2);

        Coup c1 = new Coup(Coup.ECHANGE, 2, 4);
        // assertTrue(c1.estCoupValide(jeu));
        Coup c2 = new Coup(Coup.ECHANGE, 2, 2);
        assertFalse(c2.estCoupValide(jeu));

    }

    @Test
    public void testCoupValide2(){

        Configuration.setFixedSeed(true);
        JeuEntier jeu = new JeuEntier();
        jeu.setHistorique(new Historique());
        
        Coup sceptre_1 = new Coup(Coup.SCEPTRE, 1);
        assertFalse(sceptre_1.estCoupValide(jeu));
        Coup sceptre1 = new Coup(Coup.SCEPTRE, 4);
        assertTrue(sceptre1.estCoupValide(jeu));
        jeu.joue(sceptre1);
        Coup sceptre2 = new Coup(Coup.SCEPTRE, 8);
        jeu.joue(sceptre2);

        Coup coup_1 = new Coup(Coup.ECHANGE, 2, 2);
        assertFalse(coup_1.estCoupValide(jeu));

        Coup coup1 = new Coup(Coup.ECHANGE, 1, 6);
        jeu.joue(coup1);

        Coup coup_2 = new Coup(Coup.SWAP_DROIT);
        assertFalse(coup_2.estCoupValide(jeu));
        Coup coup2 = new Coup(Coup.SWAP_GAUCHE);
        assertTrue(coup2.estCoupValide(jeu));

        assertThrows(IllegalArgumentException.class, () -> {

            Coup coup_3 = new Coup(Coup.ECHANGE_SWAP, 3);
        });

        jeu.joue(coup2);

        Coup coup3 = new Coup(Coup.ECHANGE,1,6);
        jeu.joue(coup3);
        jeu.prochainCodex();

        Coup coup4 = new Coup(Coup.ECHANGE,2,4);
        jeu.joue(coup4);

        Coup coup5 = new Coup(Coup.SWAP_GAUCHE);
        jeu.joue(coup5);

        Coup coup6 = new Coup(Coup.ECHANGE,1,4);
        jeu.joue(coup6);
        jeu.prochainCodex();

        Coup coup7 = new Coup(Coup.ECHANGE,2,1);
        jeu.joue(coup7);

        Coup coup_7 = new Coup(Coup.SWAP_GAUCHE);
        assertFalse(coup_7.estCoupValide(jeu));
        
        Coup coup8 = new Coup(Coup.SWAP_DROIT);
        jeu.joue(coup8);

    }

    @Test
    public void testToString(){

        Coup c1 = new Coup(Coup.ECHANGE, 1, 3);
        String s1 = "Echange 1 avec 3";
        assertEquals(s1, c1.toString());

        Coup c2 = new Coup(Coup.SWAP_DROIT);
        String s2 = "Swap droit";
        assertEquals(s2, c2.toString());

        Coup c3 = new Coup(Coup.SWAP_GAUCHE);
        String s3 = "Swap gauche";
        assertEquals(s3, c3.toString());

        Coup sceptre1 = new Coup(Coup.SCEPTRE, 1);
        String s4 = "Sceptre 1";
        assertEquals(s4, sceptre1.toString());

        Coup c4 = new Coup(Coup.ECHANGE_SWAP, 1, 3);
        String s5 = "Echange 1 avec 3 puis swap";
        assertEquals(s5, c4.toString());

        Coup c5 = new Coup(Coup.ECHANGE_SWAP, 1, 3);
        c5.setType((byte)5);
        assertThrows(IllegalArgumentException.class, () -> {

            c5.toString();
        }); 

    }

    @Test
    public void testEstCoupValideCompact(){

        Configuration.setFixedSeed(true);
        JeuCompact jeu = new JeuCompact();
        jeu.setHistorique(new Historique());
        
        Coup sceptre_1 = new Coup(Coup.SCEPTRE, 1);
        assertFalse(sceptre_1.estCoupValide(jeu));
        Coup sceptre1 = new Coup(Coup.SCEPTRE, 4);
        assertTrue(sceptre1.estCoupValide(jeu));
        jeu.joue(sceptre1);
        Coup sceptre2 = new Coup(Coup.SCEPTRE, 8);
        jeu.joue(sceptre2);

        Coup coup_1 = new Coup(Coup.ECHANGE, 2, 2);
        assertFalse(coup_1.estCoupValide(jeu));

        Coup coup1 = new Coup(Coup.ECHANGE, 1, 6);
        jeu.joue(coup1);

        Coup coup_2 = new Coup(Coup.SWAP_DROIT);
        assertFalse(coup_2.estCoupValide(jeu));
        Coup coup2 = new Coup(Coup.SWAP_GAUCHE);
        assertTrue(coup2.estCoupValide(jeu));

        assertThrows(IllegalArgumentException.class, () -> {

            Coup coup_3 = new Coup(Coup.ECHANGE_SWAP, 3);
        });

        jeu.joue(coup2);

        Coup coup3 = new Coup(Coup.ECHANGE,1,6);
        jeu.joue(coup3);
        jeu.prochainCodex();

        Coup coup4 = new Coup(Coup.ECHANGE,2,4);
        jeu.joue(coup4);

        Coup coup5 = new Coup(Coup.SWAP_GAUCHE);
        jeu.joue(coup5);

        Coup coup6 = new Coup(Coup.ECHANGE,1,4);
        jeu.joue(coup6);
        jeu.prochainCodex();

        Coup coup7 = new Coup(Coup.ECHANGE,2,1);
        jeu.joue(coup7);

        Coup coup_7 = new Coup(Coup.SWAP_GAUCHE);
        assertFalse(coup_7.estCoupValide(jeu));
        
        Coup coup8 = new Coup(Coup.SWAP_DROIT);
        jeu.joue(coup8);
        
    }
    

}
