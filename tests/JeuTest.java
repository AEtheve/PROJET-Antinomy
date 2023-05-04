package tests;

import org.junit.Test;
import static org.junit.Assert.*;

import Modele.Carte;

public class JeuTest {
    public boolean verifierJeuDeCartes(Carte[] cartes){
        for (int i=0;i<16;i++){
            for (int j=0;j<16;j++){
                if (i!=j){
                    int verif=0;
                    if (cartes[i].getColor()==cartes[j].getColor()) verif++;
                    if (cartes[i].getSymbol()==cartes[j].getSymbol()) verif++;
                    if (cartes[i].getValue()==cartes[j].getValue()) verif++;
                    if (verif>1) return false;
                }
            }
        }
        return true;
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
        assertTrue(verifierJeuDeCartes(cartes));
    }
    
}
