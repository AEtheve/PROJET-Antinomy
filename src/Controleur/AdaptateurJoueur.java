package Controleur;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurJoueur implements ActionListener {
	ControleurMediateur control;
	JToggleButton toggle;
	int num;

	AdaptateurJoueur(ControleurMediateur c, JToggleButton b, int n) {
		control = c;
		toggle = b;
		num = n;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (toggle.isSelected())
			control.changeJoueur(num, 1);
		else
			control.changeJoueur(num, 0);
	}
}
