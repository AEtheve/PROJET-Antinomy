package Vue;

import Modele.Coup;

public interface InterfaceUtilisateur {
	void toggleFullscreen();
	void miseAJour();
	void changeEtatIA(boolean b);
	void animeCoup(Coup coup);
	void setGagnant(Boolean gagnant);
}
