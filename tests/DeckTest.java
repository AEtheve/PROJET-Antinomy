package tests;

import org.junit.Test;
import static org.junit.Assert.*;

import Modele.Deck;
import Modele.Jeu;
import Modele.Carte;

public class DeckTest {
    
    @Test
    public void testGetContinuum() {

        Deck d = new Deck(null, null);
        assertNull(d.getContinuum());

        Carte [] continuum = new Carte[5];
        d = new Deck(continuum, null);
        assertNotNull(d.getContinuum());

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
        boolean joueur1 = Jeu.JOUEUR_1;
        boolean joueur2 = Jeu.JOUEUR_2;

        d.setSceptre(joueur1, 4); 
        assertEquals(4, d.getSceptre(joueur1)); 
        assertEquals(-1, d.getSceptre(joueur2)); 

        d.setSceptre(joueur2, 7);
        assertEquals(4, d.getSceptre(joueur1)); 
        assertEquals(7, d.getSceptre(joueur2)); 

    }

    @Test
    public void testGetSceptre() {

        Deck d = new Deck(null, null);
        boolean joueur1 = Jeu.JOUEUR_1;
        boolean joueur2 = Jeu.JOUEUR_2;

        assertEquals(-1, d.getSceptre(joueur1)); 
        assertEquals(-1, d.getSceptre(joueur2)); 

        d.setSceptre(joueur1, 4); 
        assertEquals(4, d.getSceptre(joueur1)); 
        assertEquals(-1, d.getSceptre(joueur2)); 

        d.setSceptre(joueur2, 7);
        assertEquals(4, d.getSceptre(joueur1)); 
        assertEquals(7, d.getSceptre(joueur2));

    }

    @Test
    public void testToString(){

        // Carte [] continuum = new Carte[5];
        // // Jeu jeu = new Jeu();     
        // // initialisation du continuum

        // Carte codex = new Carte(Carte.PLUME,Carte.TERRE,1,1,false);
        // Deck deck = new Deck(continuum, codex);

    }

    @Test
    public void testProchainCodex(){

        Carte codex = new Carte(Carte.PLUME,Carte.TERRE,1,1,false);
        Deck deck = new Deck(null, codex);

        deck.prochainCodex();
        assertEquals(2, deck.getCodex().getIndex());

        deck.prochainCodex();
        assertEquals(3, deck.getCodex().getIndex());

        deck.prochainCodex();
        assertEquals(4, deck.getCodex().getIndex());

        deck.prochainCodex();
        assertEquals(1, deck.getCodex().getIndex());


    }
    
}
