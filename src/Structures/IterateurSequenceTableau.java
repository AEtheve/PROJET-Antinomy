package Structures;
/*
 * Sokoban - Encore une nouvelle version (à but pédagogique) du célèbre jeu
 * Copyright (C) 2018 Guillaume Huard
 * 
 * Ce programme est libre, vous pouvez le redistribuer et/ou le
 * modifier selon les termes de la Licence Publique Générale GNU publiée par la
 * Free Software Foundation (version 2 ou bien toute autre version ultérieure
 * choisie par vous).
 * 
 * Ce programme est distribué car potentiellement utile, mais SANS
 * AUCUNE GARANTIE, ni explicite ni implicite, y compris les garanties de
 * commercialisation ou d'adaptation dans un but spécifique. Reportez-vous à la
 * Licence Publique Générale GNU pour plus de détails.
 * 
 * Vous devez avoir reçu une copie de la Licence Publique Générale
 * GNU en même temps que ce programme ; si ce n'est pas le cas, écrivez à la Free
 * Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307,
 * États-Unis.
 * 
 * Contact:
 *          Guillaume.Huard@imag.fr
 *          Laboratoire LIG
 *          700 avenue centrale
 *          Domaine universitaire
 *          38401 Saint Martin d'Hères
 */

import java.util.NoSuchElementException;

class IterateurSequenceTableau<T> implements Iterateur<T> {

	SequenceTableau<T> e;
	int position, rang, last;

	IterateurSequenceTableau(SequenceTableau<T> e) {
		this.e = e;
		rang = 0;
		position = e.debut;
		last = -1;
	}

	@Override
	public boolean aProchain() {
		return rang < e.taille;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T prochain() {
		if (aProchain()) {
			last = position;
			position = (position + 1) % e.elements.length;
			rang++;
			return (T) e.elements[last];
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public void supprime() {
		if (last != -1) {
			// On recule
			position = last;
			// On décale les éléments qui suivent
			int courant = rang;
			while (courant < e.taille) {
				int next = (last + 1) % e.elements.length;
				e.elements[last] = e.elements[next];
				last = next;
				courant++;
			}
			last = -1;
			rang--;
			e.taille--;
		} else {
			throw new IllegalStateException();
		}
	}
}
