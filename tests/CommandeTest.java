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
        jeu.joue(coup1);

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
        jeu.joue(coup1);

        Commande commande = new Commande(coup1, 4, Carte.PSY, Jeu.JOUEUR_2);
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

        Commande commande = new Commande(coup1, 4, Carte.PSY, Jeu.JOUEUR_2);
        assertEquals(Jeu.JOUEUR_2, commande.getTour());

    }


}
