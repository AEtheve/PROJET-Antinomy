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
    
}
