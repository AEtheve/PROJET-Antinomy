package tests;

import org.junit.Test;

import Global.Configuration;

import static org.junit.Assert.*;

import Modele.Deck;
import Modele.Historique;
import Modele.Jeu;
import Modele.JeuEntier;
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
    public void testSetContinuum(){

        JeuEntier jeu = new JeuEntier();
        jeu.setHistorique(new Historique());
        Carte [] jeu_continuum = jeu.getDeck().getContinuum();
        Carte [] continuum = new Carte[5];
        Deck d = new Deck(null, null);
        assertNull(d.getContinuum());
        d.setContinuum(jeu_continuum);
        assertNotNull(d.getContinuum());
        assertEquals(jeu_continuum.toString(), d.getContinuum().toString());

        Carte [] continuum_ = new Carte[5];
        assertThrows(IllegalArgumentException.class, () -> {
            d.setContinuum(continuum_);
        });

    }

    @Test
    public void testSetCodex() {

        Configuration.setFixedSeed(true);
        Jeu jeu = new Jeu();
        Deck d = jeu.getDeck();

        Carte codex = d.getCodex();
        assertEquals(codex.getIndex(), Carte.PSY);

        Carte codex_replacement = new Carte(Carte.PLUME,Carte.EAU,3,7,false);
        d.setCodex(codex_replacement);
        assertEquals(codex_replacement.getIndex(), d.getCodex().getIndex());

    }


    @Test
    public void testGetCodex() {

        Configuration.setFixedSeed(true);
        Jeu jeu = new Jeu();
        Deck d = jeu.getDeck();

        Carte codex = d.getCodex();
        assertEquals(codex.getIndex(), Carte.PSY);

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

        String s = "[\u001B[32m(1 terre plume)\u001B[0m\n" +
                   "\u001B[34m(2 eau couronne)\u001B[0m\n" +
                   "\u001B[31m(4 feu plume)\u001B[0m\n" +
                   "\u001B[35m(4 psy crane)\u001B[0m\n" +
                   "\u001B[31m(1 feu cle)\u001B[0m\n" +
                   "\u001B[31m(3 feu couronne)\u001B[0m\n" +
                   "\u001B[34m(3 eau plume)\u001B[0m\n" +
                   "\u001B[32m(2 terre cle)\u001B[0m\n" +
                   "\u001B[34m(4 eau cle)\u001B[0m]";
                   
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

    @Test
    public void testClone(){

        JeuEntier jeu = new JeuEntier();
        jeu.setHistorique(new Historique());
        Deck deck = jeu.getDeck();
        Deck deck_clone = (Deck)deck.clone();
        assertNotEquals(deck, deck_clone);

    }
    
}