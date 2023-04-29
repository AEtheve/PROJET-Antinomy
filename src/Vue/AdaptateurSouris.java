package Vue;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class AdaptateurSouris extends MouseAdapter {
    CollecteurEvenements cont;
	PlateauGraphique plateauGraphique;

    public AdaptateurSouris(PlateauGraphique p, CollecteurEvenements c) {
        cont = c;
		plateauGraphique = p;
    }

    @Override
	public void mousePressed(MouseEvent e) {
		int caseX = 0;
		int caseY = 0;
		
		cont.onClick(caseX, caseY);
	}

    @Override
	public void mouseClicked(MouseEvent e) { }
	@Override
	public void mouseReleased(MouseEvent e) { }
	@Override
	public void mouseEntered(MouseEvent e) { }
	@Override
	public void mouseExited(MouseEvent e) { }
}