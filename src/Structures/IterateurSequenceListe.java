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

class IterateurSequenceListe<T> implements Iterateur<T> {

	SequenceListe<T> e;
	Maillon<T> pprec, prec, courant;
	boolean last;

	IterateurSequenceListe(SequenceListe<T> e) {
		this.e = e;
		pprec = prec = null;
		courant = e.tete;
		last = false;
	}

	@Override
	public boolean aProchain() {
		return courant != null;
	}

	@Override
	public T prochain() {
		if (aProchain()) {
			pprec = prec;
			prec = courant;
			courant = courant.suivant;
			last = true;
			return prec.element;
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public void supprime() {
		if (last) {
			if (pprec == null) {
				e.tete = courant;
			} else {
				pprec.suivant = courant;
			}
			if (prec == e.queue) {
				e.queue = pprec;
			}
			prec = pprec;
			last = false;
		} else {
			throw new IllegalStateException();
		}
	}
}
