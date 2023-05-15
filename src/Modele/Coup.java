package Modele;

import java.util.ArrayList;

import Global.Configuration;

public class Coup {
    // Enumération des types de coups
    public final static int ECHANGE = 0;
    public final static int SWAP_DROIT = 1;
    public final static int SWAP_GAUCHE = 2;
    public final static int SCEPTRE = 3;
    public final static int ECHANGE_SWAP = 4;

    private byte type;
    private byte carte_main, carte_continuum;

    /*
    ############################# Constructeurs #############################
    */

    public Coup(int type, int carte_main, int carte_continuum) {
        if (type == ECHANGE || type == ECHANGE_SWAP) {
            this.type = (byte) type;
            this.carte_main = (byte) carte_main;
            this.carte_continuum = (byte) carte_continuum;
            return;
        }
        throw new IllegalArgumentException("Type de coup invalide");
    }

    public Coup(int type, int carte_continuum) {
        if (type == SCEPTRE) {
            this.type = (byte) type;
            this.carte_main = (byte) carte_main;
            this.carte_continuum = (byte) carte_continuum;
            return;
        }
        throw new IllegalArgumentException("Type de coup invalide");
    }

    public Coup(int type) {
        if (type == SWAP_DROIT || type == SWAP_GAUCHE) {
            this.type = (byte) type;
            return;
        }
        if (type == SCEPTRE) {
            this.type = (byte) type;

            return;
        }
        throw new IllegalArgumentException("Type de coup invalide");
    }

    /*
    ############################# Getters #############################
    */

    public byte getType() {
        return this.type;
    }

    public byte getCarteMain() {
        return carte_main;
    }

    public byte getCarteContinuum() {
        return carte_continuum;
    }

    /*
    ############################# Setters #############################
    */

    public byte setType(byte type) {
        return this.type = type;
    }

    /*
    ############################# Méthodes #############################
    */

    private Boolean estSwapValide(Jeu j) {
        int pos_sc = j.getDeck().getSceptre(j.getTour());
        switch (type) {
            case SWAP_DROIT:
                return pos_sc <= 5;
            case SWAP_GAUCHE:
                return pos_sc >= 3;
            default:
                throw new IllegalArgumentException("Position du sceptre invalide");
        }
    }

	private Boolean estSwapValide(JeuCompact j) {
        int pos_sc = j.getDeck().getSceptre(j.getTour());
        switch (type) {
            case SWAP_DROIT:
                return pos_sc <= 12;
            case SWAP_GAUCHE:
                return pos_sc >= 3;
            default:
                throw new IllegalArgumentException("Position du sceptre invalide");
        }
    }

    private Boolean estEchangeValide(Jeu j) {
        Carte[] continuum = j.getDeck().getContinuum();
        Carte[] main = j.getMain(j.getTour());

       Carte [] cartesPossibles = j.getCartesPossibles(main[this.carte_main]);
        Carte carte_continuum = continuum[this.carte_continuum];
        for (Carte cartePossible : cartesPossibles) {
            if (cartePossible.getIndex() == carte_continuum.getIndex()) {
                return true;
            }
        }
        
        return false;
    }

	private Boolean estEchangeValide(JeuCompact j) {
        Carte[] continuum = j.getDeck().getContinuum();
        Carte[] main = j.getMain(j.getTour());

        for (Carte carteContinuum : continuum) {
            if (carteContinuum.getIndex() == this.carte_continuum) {
                for (Carte carteMain : main) {
                    if (carteMain.getIndex() == this.carte_main) {
                        if (carteMain.getColor() == carteContinuum.getColor()
                                || carteMain.getSymbol() == carteContinuum.getSymbol()
                                || j.getDeck().getSceptre(j.getTour()) + carteMain.getValue() == carteContinuum
                                        .getValue()) {
                            return true;
                        }
                        return false;
                    }
                }
            }
        }
        throw new IllegalArgumentException("Carte non trouvée");
    }

    public Boolean estCoupValide(Jeu j) {
        if (this.type == ECHANGE) {
            return estEchangeValide(j);
        } else if (this.type == SWAP_DROIT || this.type == SWAP_GAUCHE) {
            return estSwapValide(j);
        }
        else if (this.type == SCEPTRE) {
            int possibles[] = j.getSceptrePossibleInit();
            for (int i = 0; i < possibles.length; i++) {
                if (possibles[i] == this.carte_continuum) {
                    return true;
                }
                if (i == possibles.length - 1) {
                    Configuration.info("Position non valide");
                    Configuration.info(java.util.Arrays.toString(possibles));
                    return false;
                }
            }
        }
        throw new IllegalArgumentException("Type de coup invalide");
    }

    public String toString(){
        String str = "";
        switch (type) {
            case ECHANGE:
                str += "Echange " + carte_main + " avec " + carte_continuum;
                break;
            case SWAP_DROIT:
                str += "Swap droit";
                break;
            case SWAP_GAUCHE:
                str += "Swap gauche";
                break;
            case SCEPTRE:
                str += "Sceptre " + carte_continuum;
                break;
            case ECHANGE_SWAP:
                str += "Echange " + carte_main + " avec " + carte_continuum + " puis swap";
                break;
            default:
                throw new IllegalArgumentException("Type de coup invalide");
        }
        return str;
    }    

}
