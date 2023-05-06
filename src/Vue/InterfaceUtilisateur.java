package Vue;

import Modele.Carte;
import Modele.Coup;

public interface InterfaceUtilisateur {
	void toggleFullscreen();
	void miseAJour();
	void animeCoup(Coup coup);
	void setCartesPossibles(Carte[] cartesPossibles);
}
