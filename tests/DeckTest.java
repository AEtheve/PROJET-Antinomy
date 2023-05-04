package tests;

import org.junit.Test;
import static org.junit.Assert.*;

import Modele.Deck;
import Modele.Carte;

public class DeckTest {
    
    @Test
    public void testGetPlateau() {

        Deck d = new Deck(null, null);
        assertNull(d.getPlateau());

        Carte [] plateau = new Carte[5];
        d = new Deck(plateau, null);
        assertNotNull(d.getPlateau());

    }

    @Test
    public void testGetCodex() {

        Deck d = new Deck(null, null);
        assertNull(d.getCodex());

        Carte codex = new Carte(1,1,1,1,false);
        d = new Deck(null, codex);
        assertNotNull(d.getCodex());

    }

    @Test
    public void testSetSceptre() {

        Deck d = new Deck(null, null);
        d.setSceptre(1, 4); 
        assertEquals(4, d.getSceptre(true)); // true = joueur 1, sceptre en 4
        assertEquals(-1, d.getSceptre(false)); // false = joueur 2, pas de sceptre

        d.setSceptre(2, 7);
        assertEquals(4, d.getSceptre(true)); // true = joueur 1, sceptre en 4
        assertEquals(7, d.getSceptre(false)); // false = joueur 2, sceptre en 7

    }

    @Test
    public void testGetSceptre() {

        Deck d = new Deck(null, null);
        assertEquals(-1, d.getSceptre(true)); 
        assertEquals(-1, d.getSceptre(false)); 

        d.setSceptre(1, 4); 
        assertEquals(4, d.getSceptre(true)); 
        assertEquals(-1, d.getSceptre(false)); 

        d.setSceptre(2, 7);
        assertEquals(4, d.getSceptre(true)); 
        assertEquals(7, d.getSceptre(false));

    }

    
}
