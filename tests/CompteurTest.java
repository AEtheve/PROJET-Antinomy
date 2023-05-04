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
    public void testIncremente(){

        Compteur compteur = new Compteur();
        assertEquals(-1, compteur.Incremente(0));

    }

    @Test
    public void testVol(){

        Compteur compteur = new Compteur();
        compteur.Vol(0);
        assertEquals(0, compteur.getJ1Points());
        compteur.Vol(1);
        assertEquals(0, compteur.getJ2Points());

        compteur.Incremente(0);
        compteur.Incremente(0);
        assertEquals(2,compteur.getJ1Points());
        assertEquals(0,compteur.getJ2Points());

        compteur.Vol(1);
        assertEquals(1, compteur.getJ1Points());
        assertEquals(1, compteur.getJ2Points());

    }

    @Test
    public void testGetJ1Points(){

        Compteur compteur = new Compteur();
        assertEquals(0, compteur.getJ1Points());

        compteur.Incremente(0);
        assertEquals(1, compteur.getJ1Points());

    }

    @Test
    public void testGetJ2Points(){

        Compteur compteur = new Compteur();
        assertEquals(0, compteur.getJ2Points());

        compteur.Incremente(1);
        assertEquals(1, compteur.getJ2Points());

    }

}
