package Controleur;

import Modele.JeuEntier;
import Global.Configuration;
import Modele.Carte;
import Modele.Coup;

class JoueurHumain extends Joueur {

    public JoueurHumain(JeuEntier j, int num) {
        super(j, num);
    }

    @Override
    boolean jeu(int index, int state, String type) {
        if(j.estFini()){
            return false;
        }
        if(state == ControleurMediateur.STARTGAME || state == ControleurMediateur.WAITSCEPTRE){
            Configuration.info("Pose du sceptre pour le joueur " + num);
            Coup coup = new Coup(Coup.SCEPTRE, index);
            if (coup.estCoupValide(j)){
                j.joue(coup);
                return true;
            }
            return false;
        } 
        if (type == "Main" && (state == ControleurMediateur.WAITSELECT || state == ControleurMediateur.WAITMOVE)) {			
            Configuration.info("Clic une carte de sa main ");
            carteAJouer = j.getMain(j.getTour())[index];
            cartesPossibles = j.getCartesPossibles(carteAJouer);
            return true;
		}
        if (type == "Continuum" && (state == ControleurMediateur.WAITMOVE) && cartesPossibles != null){
            Configuration.info("Clic une carte du continuum");
            Carte cartePlateau = j.getDeck().getContinuum()[index];
            if (cartePlateau == null) {
                throw new IllegalArgumentException("Carte inexistante");
            }
            for(Carte carte: cartesPossibles){
                if(cartePlateau == carte){
                    Configuration.info("Echange");
                    Coup c = new Coup(Coup.ECHANGE, carteAJouer.getIndex(), cartePlateau.getIndex());
                    j.joue(c);
                    carteAJouer = null;
                    cartesPossibles = null;
                    swap_droit = true; swap_gauche = true;
                    if(!(new Coup(Coup.SWAP_GAUCHE)).estCoupValide(j)){
                        swap_gauche = false;
                    }
                    if(!(new Coup(Coup.SWAP_DROIT)).estCoupValide(j)) {
                        swap_droit = false;
                    }
                    return true;
                }
            }
        } 
        if(type == "Continuum" && (state == ControleurMediateur.WAITSWAP)){
            Configuration.info("Clic pour un swap");
            Coup c;
            if(index < j.getDeck().getSceptre(j.getTour()) && index >= 0){
                Configuration.info("Swap gauche");
                c = new Coup(Coup.SWAP_GAUCHE);
                if(!c.estCoupValide(j)) {
                    return false;
                }
            } else if(index > j.getDeck().getSceptre(j.getTour()) && index <= 15){
                Configuration.info("Swap droit");
                c = new Coup(Coup.SWAP_DROIT);
                if(!c.estCoupValide(j)) {
                    return false;
                }
            } else {
                throw new IllegalArgumentException("Position du swap invalide");
            }
            j.joue(c);
            return true;
        }

        return false;
    }
}