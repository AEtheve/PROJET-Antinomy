package tests;

import org.junit.Test;
import static org.junit.Assert.*;

import Modele.Carte;

public class CarteTest {

    @Test
    public void testIsVisible() {

        Carte carte = new Carte(Carte.PLUME, Carte.TERRE, 1, 0, false);
        assertFalse(carte.isVisible());
        carte.setVisbility(true);
        assertTrue(carte.isVisible());
    }

    @Test
    public void testSetVisible(){

        Carte carte = new Carte(Carte.PLUME, Carte.TERRE, 1, 0, false);
        carte.setVisbility(true);
        assertTrue(carte.isVisible());
        carte.setVisbility(false);
        assertFalse(carte.isVisible());
    }

    @Test
    public void testGetType(){

        byte type = 1;
        Carte carte = new Carte(1, type);
        carte.setType((byte)0);
        assertEquals(0, carte.getType());
    }



    @Test
    public void testGetIndex(){

        for (int i=0; i<15; i++){
            Carte carte = new Carte(Carte.PLUME, Carte.TERRE, 1, i, false);
            assertEquals(i, carte.getIndex());
        }
    }

    @Test
    public void testSetIndexOver(){
            
        for (int i=0; i<9; i++){
            Carte carte = new Carte(Carte.PLUME, Carte.TERRE, 1, 0, false);
            carte.setIndex(i);
            assertEquals(i, carte.getIndex());
        }

        Carte carte = new Carte(Carte.PLUME, Carte.TERRE, 1, 0, false);

        assertThrows(IllegalArgumentException.class, () -> {
            carte.setIndex(20);
        });

    }


    @Test
    public void testSetIndexUnder(){

        for (int i=0; i<9; i++){
            Carte carte = new Carte(Carte.PLUME, Carte.TERRE, 1, 0, false);
            carte.setIndex(i);
            assertEquals(i, carte.getIndex());
        }

        Carte carte = new Carte(Carte.PLUME, Carte.TERRE, 1, 0, false);

        assertThrows(IllegalArgumentException.class, () -> {
            carte.setIndex(-1);
        });

    }

    @Test
    public void testGetColor(){

        for (int i=1; i<5; i++){
            Carte carte = new Carte(Carte.PLUME, i, 1, 0, false);
            assertEquals(i, carte.getColor());
        }
    }

    @Test
    public void testGetSymbol(){

        for (int i=1; i<5; i++){
            Carte carte = new Carte(i, Carte.TERRE, 1, 0, false);
            assertEquals(i, carte.getSymbol());
        }
    }

    @Test
    public void testGetValue(){

        for (int i=1; i<5; i++){
            Carte carte = new Carte(Carte.PLUME, Carte.TERRE, i, 0, false);
            assertEquals(i, carte.getValue());
        }
    }
    
    @Test
    public void testToString(){
            
        Carte carte = new Carte(Carte.PLUME, Carte.TERRE, 1, 0, false);
        assertEquals("\u001B[32m(1 terre plume)\u001B[0m", carte.toString());

        Carte carte2 = new Carte(Carte.CLE, Carte.PSY, 2, 0, false);
        assertEquals("\u001B[35m(2 psy cle)\u001B[0m", carte2.toString());

        Carte carte3 = new Carte(Carte.CRANE, Carte.EAU, 3, 0, false);
        assertEquals("\u001B[34m(3 eau crane)\u001B[0m", carte3.toString());

        Carte carte4 = new Carte(Carte.COURONNE, Carte.FEU, 4, 0, false);
        assertEquals("\u001B[31m(4 feu couronne)\u001B[0m", carte4.toString());
    }

    @Test
    public void testSymboleToString(){

        assertEquals("plume", Carte.symboleToString(Carte.PLUME));
        assertEquals("cle", Carte.symboleToString(Carte.CLE));
        assertEquals("crane", Carte.symboleToString(Carte.CRANE));
        assertEquals("couronne", Carte.symboleToString(Carte.COURONNE));
        assertEquals("Erreur", Carte.symboleToString(5));
    }

    @Test
    public void testCouleurToString(){

        assertEquals("terre", Carte.couleurToString(Carte.TERRE));
        assertEquals("psy", Carte.couleurToString(Carte.PSY));
        assertEquals("eau", Carte.couleurToString(Carte.EAU));
        assertEquals("feu", Carte.couleurToString(Carte.FEU));
        assertEquals("Erreur", Carte.couleurToString(5));
    }

    
    @Test
    public void testCLone(){

        Carte carte = new Carte(Carte.PLUME, Carte.TERRE, 1, 0, false);
        Carte carteClone = (Carte)carte.clone();
        assertTrue(carte.equals(carteClone));
        assertTrue(carteClone.equals(carte));

        Carte carte2 = new Carte(Carte.CLE, Carte.PSY, 2, 0, false);
        Carte carteClone2 = (Carte)carte2.clone();
        assertFalse(carte.equals(carteClone2));
    }

}