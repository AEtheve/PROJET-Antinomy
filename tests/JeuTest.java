package tests;

import org.junit.Test;

import Global.Configuration;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import Modele.Carte;
import Modele.Compteur;
import Modele.Jeu;
import Modele.JeuEntier;
import Modele.Main;
import Modele.Deck;
import Modele.Historique;
import Modele.Coup;

public class JeuTest {

    public boolean verifierDoublons(Carte[] cartes){

        for (int i=0;i<cartes.length-1;i++){
            for (int j=i+1;j<cartes.length;j++){
                if (cartes[i].getColor()==cartes[j].getColor() && cartes[i].getSymbol()==cartes[j].getSymbol()
                    && cartes[i].getValue()==cartes[j].getValue())
                    return false; 
                    
                }
            }
        return true;
    }

    public boolean verifierCarte(Carte carte){

        switch(carte.getColor()){
            case Carte.TERRE:
                switch(carte.getSymbol()){
                    case Carte.CLE:
                        return (carte.getValue() == 2);
                    case Carte.COURONNE:
                        return (carte.getValue() == 4);
                    case Carte.PLUME:
                        return (carte.getValue() == 1);
                    case Carte.CRANE:
                        return (carte.getValue() == 3);
                    default:
                        return false;
                }
            case Carte.EAU:
                switch(carte.getSymbol()){
                    case Carte.CLE:
                        return (carte.getValue() == 4);
                    case Carte.COURONNE:
                        return (carte.getValue() == 2);
                    case Carte.PLUME:
                        return (carte.getValue() == 3);
                    case Carte.CRANE:
                        return (carte.getValue() == 1);
                    default:
                        return false;
            }
            case Carte.PSY:
                switch(carte.getSymbol()){
                    case Carte.CLE:
                        return (carte.getValue() == 3);
                    case Carte.COURONNE:
                        return (carte.getValue() == 1);
                    case Carte.PLUME:
                        return (carte.getValue() == 2);
                    case Carte.CRANE:
                        return (carte.getValue() == 4);
                    default:
                        return false;
            }
            case Carte.FEU:
                switch(carte.getSymbol()){
                    case Carte.CLE:
                        return (carte.getValue() == 1);
                    case Carte.COURONNE:
                        return (carte.getValue() == 3);
                    case Carte.PLUME:
                        return (carte.getValue() == 4);
                    case Carte.CRANE:
                        return (carte.getValue() == 2);
                    default:
                        return false;
            }
            default:
                return false;
        }
    }

    @Test
    public void testCreerCartes() {
        Carte[] cartes = new Carte[16];
        int i=1;
        int pos =0;

        for (int valeur =1; valeur<=4; valeur++){
            for (int symbole =1; symbole<=4; symbole++){
                cartes[pos]=new Carte(symbole, i, valeur,0, false);
                pos++;
                i=(i+1)%5;
                if(i==0) i++;
            }
            i=(i+1)%5;
            if(i==0) i++;
        }
        assertTrue(verifierDoublons(cartes));
    }

    @Test
    public void testGetDeck(){

        JeuEntier jeu = new JeuEntier();
        Deck deck = jeu.getDeck();
        Carte [] cartes = deck.getContinuum();
        for(int i=0; i<cartes.length; i++){
            assertTrue(verifierCarte(cartes[i]));
        }
        assertTrue(verifierDoublons(cartes));

    }

    @Test
    public void testCreerCodex(){

        Configuration.setFixedSeed(true);
        JeuEntier jeu = new JeuEntier();
        // Carte codex = jeu.getDeck().getCodex();
        Carte codex = jeu.getDeck().getCodex();
        assertEquals(codex.getIndex(),Carte.PSY);        
        
    }

    @Test
    public void testShuffle(){

        Configuration.setFixedSeed(true);
        JeuEntier jeu = new JeuEntier();
        Carte [] cartes = jeu.getDeck().getContinuum();
        Carte [] cartes_shuffle = jeu.shuffle(cartes);
        assertNotEquals(cartes, cartes_shuffle);
        
    }

    @Test
    public void testSwitchTour(){

        Jeu j = new Jeu();
        assertEquals(j.getTour(),true);
        j.switchTour();
        assertEquals(j.getTour(),false);
        j.switchTour();
        assertEquals(j.getTour(),true);
    }
    
    @Test
    public void testGetMain() {

        JeuEntier jeu = new JeuEntier();

        Carte [] joueur_1 = jeu.getMain(Jeu.JOUEUR_1);
        assertNotNull(joueur_1);
        for (int i=0; i<joueur_1.length; i++){
            assertTrue(verifierCarte(joueur_1[i]));
        }
        assertTrue(verifierDoublons(joueur_1));


        Carte [] joueur_2 = jeu.getMain(Jeu.JOUEUR_2);
        assertNotNull(joueur_2);
        for (int i=0; i<joueur_2.length; i++){
            assertTrue(verifierCarte(joueur_2[i]));
        }
        assertTrue(verifierDoublons(joueur_2));
    }

    @Test
    public void testGetCartesPossibles1(){

        Configuration.setFixedSeed(true);
        JeuEntier jeu = new JeuEntier();
        jeu.setHistorique(new Historique());
        Carte [] main_1 = jeu.getMain(Jeu.JOUEUR_1);
        
        Coup sceptre1 = new Coup(Coup.SCEPTRE, 4);
        jeu.joue(sceptre1);
        Coup sceptre2 = new Coup(Coup.SCEPTRE, 8);
        jeu.joue(sceptre2);

        Carte carte1 = main_1[0];
        Carte [] cartes_possibles1 = new Carte[2];
        cartes_possibles1[0] = new Carte(Carte.COURONNE, Carte.TERRE, 4, 1, false);
        cartes_possibles1[1] = new Carte(Carte.CRANE, Carte.TERRE, 3, 7, false);
        assertEquals(Arrays.toString(cartes_possibles1), Arrays.toString(jeu.getCartesPossibles(carte1)));

        Carte carte2 = main_1[1];
        Carte [] cartes_possibles2 = new Carte[2];
        cartes_possibles2[0] = new Carte(Carte.CRANE,Carte.PSY,4,0,false);
        cartes_possibles2[1] = new Carte(Carte.CLE, Carte.FEU, 1, 6, false);

        assertEquals(Arrays.toString(cartes_possibles2), Arrays.toString(jeu.getCartesPossibles(carte2)));

        Carte carte3 = main_1[2];
        Carte [] cartes_possibles3 = new Carte[1];
        cartes_possibles3[0] = new Carte(Carte.PLUME, Carte.PSY, 2, 8, false);

        assertEquals(Arrays.toString(cartes_possibles3), Arrays.toString(jeu.getCartesPossibles(carte3)));

    }

    @Test
    public void testGetCartesPossibles2(){

        Configuration.setFixedSeed(true);
        JeuEntier jeu = new JeuEntier();
        jeu.setHistorique(new Historique());
        Carte [] main_2 = jeu.getMain(Jeu.JOUEUR_2);
        
        Coup sceptre1 = new Coup(Coup.SCEPTRE, 4);
        jeu.joue(sceptre1);
        Coup sceptre2 = new Coup(Coup.SCEPTRE, 8);
        jeu.joue(sceptre2);

        Coup coup = new Coup(Coup.ECHANGE, 1, 6);
        jeu.joue(coup);

        Coup coup2 = new Coup(Coup.SWAP_GAUCHE);
        jeu.joue(coup2);

        Carte carte1 = main_2[1];

        Carte [] cartes_possibles1 = new Carte[1];
        cartes_possibles1[0] = new Carte(Carte.CRANE, Carte.FEU, 2, 6, false);

        assertEquals(Arrays.toString(cartes_possibles1), Arrays.toString(jeu.getCartesPossibles(carte1)));
    }


    @Test
    public void testProchainCodex(){

        Configuration.setFixedSeed(true);
        JeuEntier jeu = new JeuEntier();
        jeu.prochainCodex();
        Carte prochain_codex = new Carte(Carte.PLUME,Carte.PSY,2,Carte.FEU,false);
        assertEquals(jeu.getDeck().getCodex().getIndex(),prochain_codex.getIndex());
    }

    @Test
    public void testGetIndexCartePossible(){

        Configuration.setFixedSeed(true);
        JeuEntier jeu = new JeuEntier();
        jeu.setHistorique(new Historique());

        Coup sceptre1 = new Coup(Coup.SCEPTRE, 4);
        jeu.joue(sceptre1);
        
        Coup sceptre2 = new Coup(Coup.SCEPTRE, 8);
        jeu.joue(sceptre2);

        int [] cartes_possibles = new int[1];
        cartes_possibles[0] = 8;

        Carte carte_main = jeu.getMain(Jeu.JOUEUR_1)[2];
        Carte [] res = new Carte[8];
        res = jeu.getCartesPossibles(carte_main);
        assertEquals(Arrays.toString(cartes_possibles), Arrays.toString(jeu.getIndexCartePossible(res)));
        
    }

    @Test
    public void testSetMain(){

        JeuEntier jeu = new JeuEntier();
        Carte [] main_1 = jeu.getMain(Jeu.JOUEUR_1);
        Carte [] main = new Carte[3];
        main[0] = new Carte(Carte.CLE, Carte.TERRE, 2, 0, false);
        main[1] = new Carte(Carte.COURONNE, Carte.EAU, 4, 1, false);
        main[2] = new Carte(Carte.PLUME, Carte.PSY, 1, 2, false);
        jeu.setMain(main,Jeu.JOUEUR_1);

        assertNotEquals(Arrays.toString(main_1), Arrays.toString(jeu.getMain(Jeu.JOUEUR_1)));
        assertEquals(Arrays.toString(main), Arrays.toString(jeu.getMain(Jeu.JOUEUR_1)));

        Carte [] main_2 = jeu.getMain(Jeu.JOUEUR_2);
        Carte [] main2 = new Carte[3];
        main2[0] = new Carte(Carte.CLE, Carte.FEU, 2, 0, false);
        main2[1] = new Carte(Carte.CRANE, Carte.TERRE, 4, 1, false);
        main2[2] = new Carte(Carte.COURONNE, Carte.EAU, 1, 2, false);
        jeu.setMain(main2,Jeu.JOUEUR_2);

        assertNotEquals(Arrays.toString(main_2), Arrays.toString(jeu.getMain(Jeu.JOUEUR_2)));
        assertEquals(Arrays.toString(main2), Arrays.toString(jeu.getMain(Jeu.JOUEUR_2)));

    }

    @Test
    public void testCLheureDuDuDuDuel(){

        Compteur score = Compteur.getInstance();
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
        jeu.joue(coup2);
        assertEquals(1,score.getJ1Points());
        assertEquals(0,score.getJ2Points());

        Coup coup3 = new Coup(Coup.ECHANGE,1,6);
        jeu.joue(coup3);
        // assertEquals(0,score.getJ1Points());
        // assertEquals(1,score.getJ2Points());
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

        System.out.println("J1: " + score.getJ1Points());
        System.out.println("J2: " + score.getJ2Points());
        
    }

}
