package tests;

import org.junit.Test;

import Global.Configuration;

import static org.junit.Assert.*;

import java.util.Arrays;

import Modele.Carte;
import Modele.Commande;
import Modele.Compteur;
import Modele.Jeu;
import Modele.JeuCompact;
import Modele.JeuEntier;
import Modele.Main;
import Modele.Deck;
import Modele.Historique;
import Modele.Coup;

import Structures.Couple;
import Structures.Sequence;

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
        assertEquals(1,jeu.getCompteur().getJ1Points());
        assertEquals(0, jeu.getCompteur().getJ2Points());

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
        
        assertTrue(jeu.getSwap());
        Coup coup8 = new Coup(Coup.SWAP_DROIT);
        jeu.joue(coup8);

        assertFalse(jeu.estFini());
        
    }

    @Test  
    public void testGetCoupsPossibles(){

        Configuration.setFixedSeed(true);
        JeuEntier jeu = new JeuEntier();
        jeu.setHistorique(new Historique());

        Sequence<Couple<Coup,Coup>> coups_possibles = jeu.getCoupsPossibles();
        Sequence<Couple<Coup,Coup>> coups_possibles_attendus = Configuration.nouvelleSequence();

        Coup sceptre_1 = new Coup(Coup.SCEPTRE, 4);
        jeu.joue(sceptre_1);

        int [] pos_sceptre = new int[3];
        pos_sceptre[0] = 0;
        pos_sceptre[1] = 4;
        pos_sceptre[2] = 8;

        for (int i=0; i<pos_sceptre.length; i++){
            coups_possibles_attendus.insereTete(new Couple<Coup, Coup>(new Coup(Coup.SCEPTRE, pos_sceptre[i]), null));
        }

        jeu.joue(new Coup(Coup.SCEPTRE, 8));

        Sequence<Couple<Coup,Coup>> coups_possibles_courant = coups_possibles;
        while (!coups_possibles_courant.estVide() && !coups_possibles_attendus.estVide()){
            Couple<Coup,Coup> coup_courant = coups_possibles_courant.extraitTete();
            Couple<Coup,Coup> coup_attendu = coups_possibles_attendus.extraitTete();
            assertEquals(coup_attendu.toString(), coup_courant.toString());
        }

        coups_possibles = jeu.getCoupsPossibles();
        coups_possibles_attendus = Configuration.nouvelleSequence();

        coups_possibles_attendus.insereTete(new Couple<Coup, Coup>(new Coup(Coup.ECHANGE, 2, 8), null));
        coups_possibles_attendus.insereTete(new Couple<Coup, Coup>(new Coup(Coup.ECHANGE, 1, 6), new Coup(Coup.SWAP_GAUCHE)));
        coups_possibles_attendus.insereTete(new Couple<Coup, Coup>(new Coup(Coup.ECHANGE, 1, 0), null));
        coups_possibles_attendus.insereTete(new Couple<Coup, Coup>(new Coup(Coup.ECHANGE, 0, 7), null));
        coups_possibles_attendus.insereTete(new Couple<Coup, Coup>(new Coup(Coup.ECHANGE, 0, 1), null));

    
        while (!coups_possibles.estVide() && !coups_possibles_attendus.estVide()){
            Couple<Coup,Coup> coup_courant = coups_possibles.extraitTete();
            Couple<Coup,Coup> coup_attendu = coups_possibles_attendus.extraitTete();
            assertEquals(coup_attendu.toString(), coup_courant.toString());
        }

        jeu.joue(new Coup(Coup.ECHANGE, 1, 6));
        Coup coup2 = new Coup(Coup.SWAP_GAUCHE);
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

        coups_possibles = jeu.getCoupsPossibles();
        coups_possibles_attendus = Configuration.nouvelleSequence();

        coups_possibles_attendus.insereTete(new Couple<Coup, Coup>(new Coup(Coup.ECHANGE, 2, 8), null));
        coups_possibles_attendus.insereTete(new Couple<Coup, Coup>(new Coup(Coup.ECHANGE, 2, 3), null));
        coups_possibles_attendus.insereTete(new Couple<Coup, Coup>(new Coup(Coup.ECHANGE, 2, 1), new Coup(Coup.SWAP_GAUCHE)));
        coups_possibles_attendus.insereTete(new Couple<Coup, Coup>(new Coup(Coup.ECHANGE, 1, 6), null));
        coups_possibles_attendus.insereTete(new Couple<Coup, Coup>(new Coup(Coup.ECHANGE, 1, 3), null));
        coups_possibles_attendus.insereTete(new Couple<Coup, Coup>(new Coup(Coup.ECHANGE, 1, 1), null));
        coups_possibles_attendus.insereTete(new Couple<Coup, Coup>(new Coup(Coup.ECHANGE, 0, 8), null));
        coups_possibles_attendus.insereTete(new Couple<Coup, Coup>(new Coup(Coup.ECHANGE, 0, 2), null));
        coups_possibles_attendus.insereTete(new Couple<Coup, Coup>(new Coup(Coup.ECHANGE, 0, 1), null));

        while (!coups_possibles.estVide() && !coups_possibles_attendus.estVide()){
            Couple<Coup,Coup> coup_courant = coups_possibles.extraitTete();
            Couple<Coup,Coup> coup_attendu = coups_possibles_attendus.extraitTete();
            assertEquals(coup_attendu.toString(), coup_courant.toString());
        }

    }

    @Test
    public void testCommandesHistorique(){

        Configuration.setFixedSeed(true);
        JeuEntier jeu = new JeuEntier();
        Historique historique = new Historique();
        jeu.setHistorique(historique);
        jeu.setInitJoueurCommence(false);
        assertFalse(jeu.getInitJoueurCommence());
        jeu.setInitJoueurCommence(true);

        Coup sceptre_1 = new Coup(Coup.SCEPTRE, 4);
        jeu.joue(sceptre_1);
        assertEquals(4,jeu.getDeck().getSceptre(jeu.JOUEUR_1));
        Commande commande = new Commande(sceptre_1, -1, Carte.PSY, jeu.getTour(),jeu.getCompteur().getJ1Points(), jeu.getCompteur().getJ2Points());
        jeu.getHistorique().ajoutePasse(commande);;
        jeu.revertSceptre(commande);
        jeu.getHistorique().ajouteFutur(commande);
        assertEquals(-1,jeu.getDeck().getSceptre(jeu.JOUEUR_1));
        jeu.refaireCoup();

        Coup sceptre_2 = new Coup(Coup.SCEPTRE, 8);
        jeu.joue(sceptre_2);
        Commande commande2 = new Commande(sceptre_2, -1, Carte.PSY, jeu.getTour(), jeu.getCompteur().getJ1Points(), jeu.getCompteur().getJ2Points());
        jeu.getHistorique().ajoutePasse(commande2);

        Coup coup1 = new Coup(Coup.ECHANGE, 1, 6);
        jeu.joue(coup1);
        Commande commande3 = new Commande(coup1, 4, Carte.PSY, jeu.getTour(), jeu.getCompteur().getJ1Points(), jeu.getCompteur().getJ2Points());
        jeu.getHistorique().ajoutePasse(commande3);
        
        assertEquals(6,jeu.getDeck().getSceptre(jeu.JOUEUR_1));
        jeu.revertEchange(commande3, Jeu.JOUEUR_1);
        jeu.getHistorique().ajouteFutur(commande3);
        assertEquals(4,jeu.getDeck().getSceptre(jeu.JOUEUR_1));
        jeu.refaireCoup();
        assertEquals(6,jeu.getDeck().getSceptre(jeu.JOUEUR_1));
        
        Coup coup2 = new Coup(Coup.SWAP_GAUCHE);
        jeu.joue(coup2);
        Commande commande4 = new Commande(coup2, 6, Carte.FEU, jeu.getTour(), jeu.getCompteur().getJ1Points(), jeu.getCompteur().getJ2Points());
        jeu.getHistorique().ajoutePasse(commande4);
        Commande c = historique.annuler();
        jeu.revertSwap(c);
        
        Carte [] continuum = new Carte[9];
        Main main1 = new Main(continuum);
        jeu.restaure(continuum, main1, main1, null, 0, 0, null, 0, 0);
        assertNull(jeu.getDeck().getContinuum()[1]);
        assertNull(jeu.getMain(Jeu.JOUEUR_1)[1]);
        assertNull(jeu.getMain(Jeu.JOUEUR_2)[1]);
        assertNull(jeu.getTour());
        assertEquals(0,jeu.getCompteur().getJ1Points());
        assertEquals(0,jeu.getCompteur().getJ2Points());
        assertEquals(0,jeu.getDeck().getSceptre(jeu.JOUEUR_1));
        assertEquals(0,jeu.getDeck().getSceptre(jeu.JOUEUR_2));

    }


}
