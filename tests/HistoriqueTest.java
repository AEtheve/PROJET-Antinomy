package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import Modele.Historique;
import Modele.JeuEntier;
import Structures.Sequence;
import Modele.Coup;
import Global.Configuration;
import Modele.Commande;
import Modele.Carte;

public class HistoriqueTest {
    
    @Test
    public void testAjouteFutur(){

        Configuration.setFixedSeed(true);
        JeuEntier jeu = new JeuEntier();
        jeu.setHistorique(new Historique());
        assertNull(jeu.getHistorique().getCommandePrec());
        Coup sceptre1 = new Coup(Coup.SCEPTRE, 4);
        jeu.joue(sceptre1);
        Commande commande = new Commande(sceptre1, -1, Carte.PSY, jeu.getTour());
        jeu.getHistorique().ajouterHistorique(commande);
        assertEquals(commande, jeu.getHistorique().getCommandePrec());

        Coup sceptre2 = new Coup(Coup.SCEPTRE, 8);
        jeu.joue(sceptre2);

        Commande commande2 = new Commande(sceptre2, -1, Carte.PSY, jeu.getTour());
        jeu.getHistorique().ajouteFutur(commande2);
        assertEquals(commande2, jeu.getHistorique().getCommandeSuiv());
        // jeu.getHistorique().afficheFutur();

    }

    @Test
    public void testajoutePasse(){

        Configuration.setFixedSeed(true);
        JeuEntier jeu = new JeuEntier();
        jeu.setHistorique(new Historique());
        assertNull(jeu.getHistorique().getCommandePrec());
        Coup sceptre1 = new Coup(Coup.SCEPTRE, 4);
        jeu.joue(sceptre1);
        Commande commande = new Commande(sceptre1, -1, Carte.PSY, jeu.getTour());
        jeu.getHistorique().ajouterHistorique(commande);
        assertEquals(commande, jeu.getHistorique().getCommandePrec());

        Coup sceptre2 = new Coup(Coup.SCEPTRE, 8);
        jeu.joue(sceptre2);

        Commande commande2 = new Commande(sceptre2, -1, Carte.PSY, jeu.getTour());
        jeu.getHistorique().ajoutePasse(commande2);    
    
        assertEquals(commande2, jeu.getHistorique().getCommandePrec());
        // jeu.getHistorique().affichePasse();
    
    }

    @Test
    public void testPeutAnnuler(){

        Configuration.setFixedSeed(true);
        JeuEntier jeu = new JeuEntier();
        assertFalse(jeu.getHistorique().peutAnnuler());
        assertNull(jeu.getHistorique().annuler());
        jeu.setHistorique(new Historique());
        Coup sceptre1 = new Coup(Coup.SCEPTRE, 4);
        jeu.joue(sceptre1);
        Commande commande = new Commande(sceptre1, -1, Carte.PSY, jeu.getTour());
        jeu.getHistorique().ajouterHistorique(commande);
        assertTrue(jeu.getHistorique().peutAnnuler());
        jeu.getHistorique().annuler();
        jeu.getHistorique().annuler();
        assertFalse(jeu.getHistorique().peutAnnuler());
    }

    @Test
    public void testPeutRefaire(){

        Configuration.setFixedSeed(true);
        JeuEntier jeu = new JeuEntier();
        assertFalse(jeu.getHistorique().peutRefaire());
        assertNull(jeu.getHistorique().refaire());
        jeu.setHistorique(new Historique());
        Coup sceptre1 = new Coup(Coup.SCEPTRE, 4);
        jeu.joue(sceptre1);
        Commande commande = new Commande(sceptre1, -1, Carte.PSY, jeu.getTour());
        jeu.getHistorique().ajouterHistorique(commande);
        assertFalse(jeu.getHistorique().peutRefaire());
        Coup sceptre2 = new Coup(Coup.SCEPTRE, 8);
        jeu.joue(sceptre2);
        Commande commande2 = new Commande(sceptre2, -1, Carte.PSY, jeu.getTour());
        jeu.getHistorique().ajouteFutur(commande2);
        assertTrue(jeu.getHistorique().peutRefaire());
        jeu.getHistorique().refaire();
        assertFalse(jeu.getHistorique().peutRefaire());
    }

    @Test
    public void testGetHistoriquePasse(){

        Configuration.setFixedSeed(true);
        JeuEntier jeu = new JeuEntier();
        jeu.setHistorique(new Historique());
        Coup sceptre1 = new Coup(Coup.SCEPTRE, 4);
        jeu.joue(sceptre1);
        Commande commande = new Commande(sceptre1, -1, Carte.PSY, jeu.getTour());
        jeu.getHistorique().ajoutePasse(commande);
        Sequence<Commande> historique = jeu.getHistorique().getHistoriquePasse();
        assertEquals(commande, historique.extraitTete());
    }

    @Test
    public void testGetHistoriqueFutur(){

        Configuration.setFixedSeed(true);
        JeuEntier jeu = new JeuEntier();
        jeu.setHistorique(new Historique());
        Coup sceptre1 = new Coup(Coup.SCEPTRE, 4);
        jeu.joue(sceptre1);
        Commande commande = new Commande(sceptre1, -1, Carte.PSY, jeu.getTour());
        jeu.getHistorique().ajouteFutur(commande);
        Sequence<Commande> historique = jeu.getHistorique().getHistoriqueFutur();
        assertEquals(commande, historique.extraitTete());
        jeu.getHistorique().afficheFutur();

    }
}
