package tests;

import org.junit.Test;
import static org.junit.Assert.*;

import Modele.Coup;
import Modele.JeuEntier;

public class CoupTest {
    
    @Test
    public void testGetType() {

        Coup c1 = new Coup(Coup.ECHANGE, 1, 3);
        assertEquals(Coup.ECHANGE,c1.getType());

        Coup c2 = new Coup(Coup.ECHANGE_SWAP, 1, 3);
        assertEquals(Coup.ECHANGE_SWAP, c2.getType());

        assertThrows(IllegalArgumentException.class, () -> {

            Coup c3 = new Coup(Coup.SCEPTRE, 1, 3);
        });

        Coup c4 = new Coup(Coup.SCEPTRE, 3);
        assertEquals(Coup.SCEPTRE, c4.getType());

        assertThrows(IllegalArgumentException.class, () -> {

            Coup c5 = new Coup(Coup.ECHANGE, 3);
        });

        Coup c6 = new Coup(Coup.SWAP_DROIT);
        assertEquals(Coup.SWAP_DROIT, c6.getType());

        Coup c7 = new Coup(Coup.SWAP_GAUCHE);
        assertEquals(Coup.SWAP_GAUCHE, c7.getType());

        Coup c8 = new Coup(Coup.SCEPTRE);
        assertEquals(Coup.SCEPTRE, c8.getType());

        assertThrows(IllegalArgumentException.class, () -> {

            Coup c9 = new Coup(Coup.ECHANGE_SWAP);
        });

    }

    @Test
    public void testSetType(){

        Coup c1 = new Coup(Coup.ECHANGE, 1,3);
        assertEquals(Coup.ECHANGE, c1.getType());
        c1.setType((byte)Coup.ECHANGE_SWAP);
        assertEquals(Coup.ECHANGE_SWAP, c1.getType());
    }

    @Test
    public void testGetCarteMain(){

        Coup c1 = new Coup(Coup.ECHANGE, 1,3);
        assertEquals(1, c1.getCarteMain());
    }

    @Test
    public void testGetCarteContinuum(){

        Coup c1 = new Coup(Coup.ECHANGE, 1,3);
        assertEquals(3, c1.getCarteContinuum());
    }

    @Test
    public void testEstSwapValide(){

        JeuEntier j = new JeuEntier();
        int pos_sc = j.getDeck().getSceptre(j.getTour());
        
    }

}
