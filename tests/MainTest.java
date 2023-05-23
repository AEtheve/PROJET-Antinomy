package tests;

import org.junit.Test;

import Global.Configuration;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ObjectInputFilter.Config;

import Modele.Main;
import Modele.Carte;
import Modele.Historique;
import Modele.JeuEntier;

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
        assertEquals("\u001B[31m(1 feu cle)\u001B[0m", m.getCarte(1).toString());
        
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

        assertEquals("\u001B[31m(4 feu plume)\u001B[0m", m.getCarte(0).toString());
        assertEquals("\u001B[31m(1 feu cle)\u001B[0m", m.getCarte(1).toString());
        assertEquals("\u001B[31m(3 feu couronne)\u001B[0m", m.getCarte(2).toString());
        assertEquals("\u001B[31m(2 feu crane)\u001B[0m", m.getCarte(3).toString());
        assertEquals("\u001B[34m(3 eau plume)\u001B[0m", m.getCarte(4).toString());
        
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
        
        assertEquals("[\u001B[31m(4 feu plume)\u001B[0m, \u001B[31m(1 feu cle)\u001B[0m, \u001B[31m(3 feu couronne)\u001B[0m, \u001B[31m(2 feu crane)\u001B[0m, \u001B[34m(3 eau plume)\u001B[0m]", m.toString());


    }

    @Test
    public void testClone(){

        Configuration.setFixedSeed(true);
        JeuEntier jeu = new JeuEntier();
        jeu.setHistorique(new Historique());
        Carte [] main = jeu.getMain(jeu.getTour());
        Main m = new Main(main);
        Main m2 = (Main)m.clone();
        assertNotEquals(m, m2);
        assertEquals(m.toString(), m2.toString());

    }

}
