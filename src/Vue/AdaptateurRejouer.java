package Vue;

import Controleur.ControleurMediateurLocal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurRejouer implements ActionListener {
    ControleurMediateurLocal control;

	AdaptateurRejouer(ControleurMediateurLocal c) {
		control = c;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			control.restartGame();
		} catch (Exception ex) {
			// On ne fait rien si la valeur est invalide
		}
	}
    
}