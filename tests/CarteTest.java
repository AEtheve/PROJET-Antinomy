package tests;

import org.junit.Test;
import static org.junit.Assert.*;

import Modele.Carte;

public class CarteTest {

    @Test
    public void testIsVisible() {

        Carte carte = new Carte(1, 1, 1, 0, false);
        assertFalse(carte.isVisible());
        carte.setVisbility(true);
        assertTrue(carte.isVisible());
    }

    @Test
    public void testSetVisible(){

        Carte carte = new Carte(1, 1, 1, 0, false);
        carte.setVisbility(true);
        assertTrue(carte.isVisible());
        carte.setVisbility(false);
        assertFalse(carte.isVisible());
    }

    @Test
    public void testGetType(){

       
    }

    @Test
    public void testGetIndex(){

        for (int i=0; i<15; i++){
            Carte carte = new Carte(1, 1, 1, i, false);
            assertEquals(i, carte.getIndex());
        }
    }

    @Test
    public void testSetIndexOver(){
            
        for (int i=0; i<15; i++){
            Carte carte = new Carte(1, 1, 1, 0, false);
            carte.setIndex(i);
            assertEquals(i, carte.getIndex());
        }

        Carte carte = new Carte(1, 1, 1, 0, false);

        assertThrows(IllegalArgumentException.class, () -> {
            carte.setIndex(20);
        });

    }


    @Test
    public void testSetIndexUnder(){

        for (int i=0; i<15; i++){
            Carte carte = new Carte(1, 1, 1, 0, false);
            carte.setIndex(i);
            assertEquals(i, carte.getIndex());
        }

        Carte carte = new Carte(1, 1, 1, 0, false);

        assertThrows(IllegalArgumentException.class, () -> {
            carte.setIndex(-1);
        });

    }

    @Test
    public void testGetColor(){

        for (int i=1; i<5; i++){
            Carte carte = new Carte(1, i, 1, 0, false);
            assertEquals(i, carte.getColor());
        }
    }

    @Test
    public void testGetSymbol(){

        for (int i=1; i<5; i++){
            Carte carte = new Carte(i, 1, 1, 0, false);
            assertEquals(i, carte.getSymbol());
        }
    }

    @Test
    public void testGetValue(){

        for (int i=1; i<5; i++){
            Carte carte = new Carte(1, 1, i, 0, false);
            assertEquals(i, carte.getValue());
        }
    }
    
    @Test
    public void testToString(){
            
        Carte carte = new Carte(1, 1, 1, 0, false);
        assertEquals("(1 plume, terre)", carte.toString());

        Carte carte2 = new Carte(2, 2, 2, 0, false);
        assertEquals("(2 cle, psy)", carte2.toString());

        Carte carte3 = new Carte(3, 3, 3, 0, false);
        assertEquals("(3 crane, eau)", carte3.toString());

        Carte carte4 = new Carte(4, 4, 4, 0, false);
        assertEquals("(4 couronne, feu)", carte4.toString());
    }

    @Test
    public void testSymboleToString(){

        assertEquals("plume", Carte.symboleToString(1));
        assertEquals("cle", Carte.symboleToString(2));
        assertEquals("crane", Carte.symboleToString(3));
        assertEquals("couronne", Carte.symboleToString(4));
        assertEquals("Erreur", Carte.symboleToString(5));
    }

    @Test
    public void testCouleurToString(){

        assertEquals("terre", Carte.couleurToString(1));
        assertEquals("psy", Carte.couleurToString(2));
        assertEquals("eau", Carte.couleurToString(3));
        assertEquals("feu", Carte.couleurToString(4));
        assertEquals("Erreur", Carte.couleurToString(5));
    }

}
