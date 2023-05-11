package tests;

import org.junit.Test;

import Global.Configuration;

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

        Configuration.setFixedSeed(true);
        Jeu jeu = new Jeu();
        Deck d = jeu.getDeck();

        Carte codex = d.getCodex();
        assertEquals(codex.getColor(), Carte.TERRE);

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

        Carte [] continuum = new Carte[9];
        continuum[0] = new Carte(Carte.PLUME,Carte.EAU,3,7,false);
        continuum[1] = new Carte(Carte.CLE,Carte.EAU,4,9,false);
        continuum[2] = new Carte(Carte.COURONNE,Carte.EAU,2,2,false);
        continuum[3] = new Carte(Carte.CRANE,Carte.PSY,4,4,false);
        continuum[4] = new Carte(Carte.CLE,Carte.FEU,1,5,false);
        continuum[5] = new Carte(Carte.COURONNE,Carte.FEU,3,6,false);
        continuum[6] = new Carte(Carte.PLUME,Carte.TERRE,1,1,false);
        continuum[7] = new Carte(Carte.CLE,Carte.TERRE,2,8,false);
        continuum[8] = new Carte(Carte.PLUME,Carte.FEU,4,3,false);

        Carte codex = new Carte(Carte.PLUME,Carte.TERRE,1,Carte.FEU,false);

        Deck deck = new Deck(continuum, codex);

        String s = "[(1 \u001B[32mterre plume\u001B[0m)\n" +
                   "(2 \u001B[34meau couronne\u001B[0m)\n" +
                   "(4 \u001B[31mfeu plume\u001B[0m)\n" +
                   "(4 \u001B[35mpsy crane\u001B[0m)\n" +
                   "(1 \u001B[31mfeu cle\u001B[0m)\n" +
                   "(3 \u001B[31mfeu couronne\u001B[0m)\n" +
                   "(3 \u001B[34meau plume\u001B[0m)\n" +
                   "(2 \u001B[32mterre cle\u001B[0m)\n" +
                   "(4 \u001B[34meau cle\u001B[0m)]";
                   
        assertEquals(s, deck.toString());

    }

    @Test
    public void testProchainCodex(){

        Carte codex = new Carte(Carte.PLUME,Carte.PSY,2,Carte.PSY,false);
        Deck deck = new Deck(null, codex);

        deck.prochainCodex();
        assertEquals(Carte.FEU, deck.getCodex().getIndex());

        deck.prochainCodex();
        assertEquals(Carte.EAU, deck.getCodex().getIndex());

        deck.prochainCodex();
        assertEquals(Carte.TERRE, deck.getCodex().getIndex());

        deck.prochainCodex();
        assertEquals(Carte.PSY, deck.getCodex().getIndex());

        Carte codex_erreur = new Carte(Carte.PLUME,Carte.PSY,2,17,false);
        Deck deck_erreur = new Deck(null, codex_erreur);
        assertThrows(IllegalArgumentException.class, () -> {
            deck_erreur.prochainCodex();
        });

    }
    
}