package tests;

import Modele.Commande;
import Modele.Coup;
import Modele.Historique;
import Modele.Jeu;
import Modele.JeuEntier;
import Global.Configuration;
import Modele.Carte;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CommandeTest {
    
    @Test
    public void testGetCoup(){

        Configuration.setFixedSeed(true);
        JeuEntier jeu = new JeuEntier();
        jeu.setHistorique(new Historique());

        Coup sceptre1 = new Coup(Coup.SCEPTRE, 4);
        jeu.joue(sceptre1);
        Coup sceptre2 = new Coup(Coup.SCEPTRE, 8);
        jeu.joue(sceptre2);

        Coup coup1 = new Coup(Coup.ECHANGE, 1, 6);

        Commande commande = new Commande(coup1, 4, Carte.PSY, Jeu.JOUEUR_2);
        assertEquals(coup1, commande.getCoup());
    }

    @Test
    public void testGetPosSeptre(){

        Configuration.setFixedSeed(true);
        JeuEntier jeu = new JeuEntier();
        jeu.setHistorique(new Historique());

        Coup sceptre1 = new Coup(Coup.SCEPTRE, 4);
        jeu.joue(sceptre1);
        Coup sceptre2 = new Coup(Coup.SCEPTRE, 8);
        jeu.joue(sceptre2);

        Coup coup1 = new Coup(Coup.ECHANGE, 1, 6);

        Commande commande = new Commande(coup1, 4, Carte.PSY, jeu.getTour());
        assertEquals(4, commande.getPosSeptre());

    }

    @Test
    public void testGetTour(){

        Configuration.setFixedSeed(true);
        JeuEntier jeu = new JeuEntier();
        jeu.setHistorique(new Historique());

        Coup sceptre1 = new Coup(Coup.SCEPTRE, 4);
        jeu.joue(sceptre1);
        Coup sceptre2 = new Coup(Coup.SCEPTRE, 8);
        jeu.joue(sceptre2);

        Coup coup1 = new Coup(Coup.ECHANGE, 1, 6);
        jeu.joue(coup1);
        Coup coup2 = new Coup(Coup.SWAP_GAUCHE);

        Commande commande = new Commande(coup2, 4, Carte.PSY, jeu.getTour());
        assertEquals(Jeu.JOUEUR_1, commande.getTour());
        jeu.joue(coup2);

        Coup coup3 = new Coup(Coup.ECHANGE, 1, 6);
        Commande commande2 = new Commande(coup3, 8, Carte.FEU, jeu.getTour());
        assertEquals(Jeu.JOUEUR_2, commande2.getTour());


    }

    @Test
    public void testGetScore_J1_J2(){

        Configuration.setFixedSeed(true);
        JeuEntier jeu = new JeuEntier();
        jeu.setHistorique(new Historique());

        Coup sceptre1 = new Coup(Coup.SCEPTRE, 4);
        jeu.joue(sceptre1);
        Coup sceptre2 = new Coup(Coup.SCEPTRE, 8);
        jeu.joue(sceptre2);

        Commande commande = new Commande(sceptre2, -1, Carte.PSY, Jeu.JOUEUR_2);
        assertEquals(0, commande.getScoreJ1());
        assertEquals(0, commande.getScoreJ2());


        Coup coup1 = new Coup(Coup.ECHANGE, 1, 6);
        jeu.joue(coup1);
        Coup coup2 = new Coup(Coup.SWAP_GAUCHE);
        jeu.joue(coup2);

        Commande commande2 = new Commande(coup1, 4, Carte.PSY, Jeu.JOUEUR_1);
        commande2.setScore();
        assertEquals(1, commande2.getScoreJ1());
        assertEquals(0, commande2.getScoreJ2());


        
    }

    @Test
    public void testGetCodex(){

        Configuration.setFixedSeed(true);
        JeuEntier jeu = new JeuEntier();
        jeu.setHistorique(new Historique());

        Coup sceptre1 = new Coup(Coup.SCEPTRE, 4);
        Commande commande = new Commande(sceptre1, -1, Carte.PSY, Jeu.JOUEUR_1);
        jeu.joue(sceptre1);
        assertEquals(Carte.PSY, commande.getCodex());
    }

    @Test
    public void testToString(){

        Configuration.setFixedSeed(true);
        JeuEntier jeu = new JeuEntier();
        jeu.setHistorique(new Historique());

        Coup sceptre1 = new Coup(Coup.SCEPTRE, 4);
        jeu.joue(sceptre1);
        Coup sceptre2 = new Coup(Coup.SCEPTRE, 8);
        jeu.joue(sceptre2);

        Coup coup1 = new Coup(Coup.ECHANGE, 1, 6);

        Commande commande = new Commande(coup1, 4, Carte.PSY, jeu.getTour());
        assertEquals("Echange 1 avec 6", commande.toString());
    }


}
