package tests;

import org.junit.Test;

import Global.Configuration;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Random;

import Modele.Carte;
import Modele.Compteur;
import Modele.JeuEntier;
import Modele.Deck;

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
        assertEquals(codex.getColor(),Carte.TERRE);        
        
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

        JeuEntier j = new JeuEntier();
        assertEquals(j.getTour(),true);
        j.switchTour();
        assertEquals(j.getTour(),false);
        j.switchTour();
        assertEquals(j.getTour(),true);
    }
    
    @Test
    public void testGetMain() {

        JeuEntier jeu = new JeuEntier();

        Carte [] joueur_1 = jeu.getMain(JeuEntier.JOUEUR_1);
        assertNotNull(joueur_1);
        for (int i=0; i<joueur_1.length; i++){
            assertTrue(verifierCarte(joueur_1[i]));
        }
        assertTrue(verifierDoublons(joueur_1));


        Carte [] joueur_2 = jeu.getMain(JeuEntier.JOUEUR_2);
        assertNotNull(joueur_2);
        for (int i=0; i<joueur_2.length; i++){
            assertTrue(verifierCarte(joueur_2[i]));
        }
        assertTrue(verifierDoublons(joueur_2));
    }

    @Test
    public void testGetCartesPossibles(){

        // Random random = new Random(0);
        // random.setSeed(0);
        JeuEntier jeu = new JeuEntier();
        Carte [] main_1 = jeu.getMain(JeuEntier.JOUEUR_1);
        // Carte [] main_2 = jeu.getMain(Jeu.JOUEUR_2);
        // Carte [] cartes = jeu.getDeck().getContinuum();
        // Carte codex = jeu.getDeck().getCodex();

        // set sceptre joueur 1, position 3
        jeu.getDeck().setSceptre(JeuEntier.JOUEUR_1, 3);
        jeu.getDeck().setSceptre(JeuEntier.JOUEUR_2, 1);
        Carte carte = main_1[1];


        // System.out.println(Arrays.toString(cartes));
        // System.out.println(codex);

        Carte [] cartes_possibles = new Carte[2];
        cartes_possibles[0] = new Carte(Carte.CRANE,Carte.PSY,4,2,false);
        cartes_possibles[1] = new Carte(Carte.CLE, Carte.FEU, 1, 4, false);

        // assertArrayEquals(cartes_possibles, jeu.getCartesPossibles(carte));
        System.out.println(Arrays.toString(jeu.getCartesPossibles(carte)));
        // assertThat(cartes_possibles).isEqualToComparingFieldByField(jeu.getCartesPossibles(carte));

    }
}
