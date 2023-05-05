package tests;

import org.junit.Test;
import static org.junit.Assert.*;

import Modele.Compteur;

public class CompteurTest {

    @Test
    public void testGetInstance(){

        Compteur compteur = Compteur.getInstance();
        assertNotNull(compteur);
        assertEquals(0, compteur.getJ1Points());
        assertEquals(0, compteur.getJ2Points());

    }
    
    @Test
    public void testIncremente_J0(){

        Compteur compteur = new Compteur();
        boolean joueur1 = true;
        boolean joueur2 = false;
        
        compteur.Incremente(joueur1);
        assertEquals(1, compteur.getJ1Points());
        assertEquals(-1, compteur.Incremente(joueur1));

        compteur.Incremente(joueur1);
        compteur.Incremente(joueur1);
        assertEquals(0,compteur.Incremente(joueur1));
        assertEquals(5, compteur.getJ1Points());
 
       // On vérifie que le compteur ne dépasse pas 5 
        assertThrows(IllegalStateException.class, () -> {
            compteur.Incremente(joueur1);
        });

        assertThrows(IllegalStateException.class, () -> {
            compteur.Incremente(joueur2);
        });
    }

    @Test
    public void testIncremente_J1(){

        Compteur compteur = new Compteur();
        boolean joueur2 = false;

        compteur.Incremente(joueur2);
        compteur.Incremente(joueur2);
        compteur.Incremente(joueur2);
        compteur.Incremente(joueur2);
        assertEquals(1, compteur.Incremente(joueur2));

    }

    @Test
    public void testVol(){

        Compteur compteur = new Compteur();
        boolean voleur1 = true;
        boolean voleur2 = false;
        boolean joueur1 = true;

        compteur.Vol(voleur1);
        assertEquals(0, compteur.getJ1Points());
        compteur.Vol(voleur2);
        assertEquals(0, compteur.getJ2Points());

        compteur.Incremente(joueur1);
        compteur.Incremente(joueur1);
        assertEquals(2,compteur.getJ1Points());
        assertEquals(0,compteur.getJ2Points());

        compteur.Vol(voleur2);
        assertEquals(1, compteur.getJ1Points());
        assertEquals(1, compteur.getJ2Points());

        compteur.Vol(voleur1);
        assertEquals(2, compteur.getJ1Points());
        assertEquals(0, compteur.getJ2Points());

    }

    @Test
    public void testGetJ1Points(){

        Compteur compteur = new Compteur();
        boolean joueur1 = true;
        assertEquals(0, compteur.getJ1Points());

        compteur.Incremente(joueur1);
        assertEquals(1, compteur.getJ1Points());

    }

    @Test
    public void testGetJ2Points(){

        Compteur compteur = new Compteur();
        boolean joueur2 = false;
        assertEquals(0, compteur.getJ2Points());

        compteur.Incremente(joueur2);
        assertEquals(1, compteur.getJ2Points());

    }

}
