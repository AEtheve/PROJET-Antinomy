package tests;

import org.junit.Test;

import org.junit.Test;
import static org.junit.Assert.*;

import Modele.Main;
import Modele.Carte;

public class MainTest {
    
    @Test
    public void testGetMain() {

        Main m = new Main(null);
        assertNull(m.getMain());

        Carte [] main = new Carte[5];
        main[0] = new Carte(Carte.PLUME, Carte.FEU, 4, 0, false);
        main[1] = new Carte(Carte.CLE, Carte.FEU, 1, 0, false);
        main[2] = new Carte(Carte.COURONNE, Carte.FEU, 3, 0, false);
        main[3] = new Carte(Carte.CRANE, Carte.FEU, 2, 0, false);
        main[4] = new Carte(Carte.PLUME, Carte.EAU, 3, 0, false);

        m = new Main(main);
        assertNotNull(m.getMain());

    }

    @Test
    public void testSetCarte(){

        Main m = new Main(null);
        Carte [] main = new Carte[5];
        main[0] = new Carte(Carte.PLUME, Carte.FEU, 4, 0, false);
        m = new Main(main);

        Carte c = new Carte(Carte.CLE, Carte.FEU, 1, 0, false);
        m.setCarte(c, 1);
        assertEquals("(1 feu cle)", m.getCarte(1).toString());
        
    }

    @Test
    public void testGetCarte(){

        Main m = new Main(null);
        Carte [] main = new Carte[5];

        main[0] = new Carte(Carte.PLUME, Carte.FEU, 4, 0, false);
        main[1] = new Carte(Carte.CLE, Carte.FEU, 1, 0, false);
        main[2] = new Carte(Carte.COURONNE, Carte.FEU, 3, 0, false);
        main[3] = new Carte(Carte.CRANE, Carte.FEU, 2, 0, false);
        main[4] = new Carte(Carte.PLUME, Carte.EAU, 3, 0, false);
        m = new Main(main);

        assertEquals("(4 feu plume)", m.getCarte(0).toString());
        assertEquals("(1 feu cle)", m.getCarte(1).toString());
        assertEquals("(3 feu couronne)", m.getCarte(2).toString());
        assertEquals("(2 feu crane)", m.getCarte(3).toString());
        assertEquals("(3 eau plume)", m.getCarte(4).toString());
        
    }

    @Test
    public void testToString(){

        assertThrows(NullPointerException.class, () -> {
            Main main = new Main(null);
            main.toString();
        });

        Main m = new Main(null);
        Carte [] main = new Carte[5];

        main[0] = new Carte(Carte.PLUME, Carte.FEU, 4, 0, false);
        main[1] = new Carte(Carte.CLE, Carte.FEU, 1, 0, false);
        main[2] = new Carte(Carte.COURONNE, Carte.FEU, 3, 0, false);
        main[3] = new Carte(Carte.CRANE, Carte.FEU, 2, 0, false);
        main[4] = new Carte(Carte.PLUME, Carte.EAU, 3, 0, false);
        m = new Main(main);
        
        assertEquals("[(4 feu plume), (1 feu cle), (3 feu couronne), (2 feu crane), (3 eau plume)]", m.toString());


    }

}
