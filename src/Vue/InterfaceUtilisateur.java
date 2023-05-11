package Vue;

import java.util.ArrayList;

import Modele.Carte;
import Modele.Coup;

public interface InterfaceUtilisateur {
	void toggleFullscreen();
	void miseAJour();
	void changeEtatIA(boolean b);
	void animeCoup(Coup coup);
	void setCartesPossibles(ArrayList<Carte> cartesPossibles);
	void setSelectCarteMain1(int index);
	void setSelectCarteMain2(int index);
	void setGagnant(Boolean gagnant);
}
