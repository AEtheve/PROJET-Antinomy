package Modele;

public class Deck {
    private Carte[] continuum;
    private Carte codex;
    private int sceptreJ1, sceptreJ2;

    /*
    ############################# Constructeurs #############################
    */

    public Deck(Carte[] continuum, Carte codex) {
        this.continuum = continuum;
        this.codex = codex;
        sceptreJ1 = sceptreJ2 = -1;
    }

    /*
    ############################# Getters #############################
    */

    public Carte[] getContinuum() {
        // Renvoie le plateau de jeu
        return continuum;
    }

    public Carte getCodex() {
        // Renvoie le codex
        return codex;
    }

    public int getSceptre(Boolean joueur) {
        // Renvoie la position du sceptre d'un joueur (joueur 1 = true, joueur 2 = false)
        if (joueur)
            return sceptreJ1;
        else
            return sceptreJ2;
    }

    /*
    ############################# Setters #############################
    */

    public void setCodex(Carte c) {
        // Permet de mettre une carte en tant que codex (pour le chargement de partie)
        this.codex = c;
    }

    public void setSceptre(Boolean joueur, int pos) {
        // Permet de modifier la position du septre d'un joueur (joueur 1 = true, joueur 2 = false)
        if (joueur)
            sceptreJ1 = pos;
        else
            sceptreJ2 = pos;
    }

    public void setContinuum(Carte[] c) {
        // Permet de modifier le continuum (pour le chargement de partie)
        if (c.length != 9)
            throw new IllegalArgumentException("Le continuum doit contenir 9 cartes");
        this.continuum = c;
    }

    /*
    ############################# Methodes de jeu #############################
    */

    public void prochainCodex() {
        // Permet de passer au codex suivant
        switch(codex.getIndex()){
            case Carte.EAU:
                codex.setIndex(Carte.TERRE);
                break;
            case Carte.TERRE:
                codex.setIndex(Carte.PSY);
                break;
            case Carte.PSY:
                codex.setIndex(Carte.FEU);
                break;
            case Carte.FEU:
                codex.setIndex(Carte.EAU);
                break;
            default:
                throw new IllegalArgumentException("Codex invalide");
        }
    }

    /*
    ############################# Methodes d'affichage #############################
    */

    public String toString() {
        /* Tri le continuum par ordre croissant d'index et renvoie le continuum sous
        forme de String */
        Deck deckTriee = new Deck(continuum, codex);
        for (int i = 0; i < deckTriee.continuum.length; i++) {
            for (int j = i + 1; j < deckTriee.continuum.length; j++) {
                if (deckTriee.continuum[i].getIndex() > deckTriee.continuum[j].getIndex()) {
                    Carte tmp = deckTriee.continuum[i];
                    deckTriee.continuum[i] = deckTriee.continuum[j];
                    deckTriee.continuum[j] = tmp;
                }
            }
        }
        String s = "[";
        for (int i = 0; i < deckTriee.continuum.length; i++) {
            s += deckTriee.continuum[i].toString();
            if (i < deckTriee.continuum.length - 1) {
                s += "\n";
            }
        }
        s += "]";
        return s;
    }
}