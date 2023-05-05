package Modele;

import java.util.stream.IntStream;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.List;

public class Jeu {
    private static Jeu instance;
    final public static Boolean JOUEUR_1 = true;
    final public static Boolean JOUEUR_2 = false;

    public static Jeu getInstance() {
        if (instance == null) instance = new Jeu();
        return instance;
    }

    
    Deck deck;
    Carte [] cartes;
    Main J1, J2;
    Boolean tour; // true = tour du J1

    public Jeu(){
        CreerCartes();        
        J1 = new Main(creerMain());
        J2 = new Main(creerMain());
        Carte codex = creerCodex();

        Compteur.getInstance();
        tour = true;

        
        deck = new Deck(cartes, codex);
    }

    public Deck getDeck(){
        return this.deck;
    }

    Carte creerCodex(){
        // Créer le codex à partir du tableau de cartes passé en argument
        Carte codex = this.cartes[this.cartes.length-1];
        codex.setIndex(0);

        Carte [] cartes = new Carte[this.cartes.length-1];
        int ndx = 0;
        for (int i=0; i < this.cartes.length-1; i++){
            cartes[i] = this.cartes[i];
            cartes[i].setIndex(ndx);
            ndx++;
        }
        this.cartes = cartes;

        codex.setIndex(cartes[cartes.length-1].getColor());

        return codex;
    }

    Carte[] shuffle(Carte[] c){
        // Mélange le tableau de cartes passé en argument
        Carte[] cartes_res = new Carte[c.length];
        List<Integer> range = IntStream.rangeClosed(0, c.length-1).boxed().collect(Collectors.toList());
        Collections.shuffle(range);
        for (int i = 0; i < c.length; i++){
            cartes_res[i] = c[range.get(i)];
        }
        return cartes_res;
    }

    void CreerCartes() {
        int couleur = 5;
        int pos = 0;

        Carte [] cartes = new Carte[16];
        for (int symbole =1; symbole<=4; symbole++){
            couleur--;
            for (int valeur =4; valeur>0; valeur--){
                cartes[pos] = new Carte(symbole, couleur, valeur,0, true);
                pos++;
                couleur--;
                if(couleur == 0) couleur = 4;
            }
        }

        this.cartes = shuffle(cartes);
    }

    void supprimeDejaUtilisees(){
        // Supprime la carte passée en argument du tableau de cartes
        Carte[] cartes_res = new Carte[cartes.length-3];
        int pos = 0;
        for (int i=0;i< cartes.length;i++){
            if (cartes[i] != null){
                cartes_res[pos] = cartes[i];
                pos++;
            }
        }
        cartes = cartes_res;
    }

    Carte[] creerMain(){
        // Créer et retourner une main de 3 cartes
        Carte[] main = new Carte[3];
        int ndx = 0;
        for (int i=0;i<3;i++){
            int index = (int) (Math.random()*cartes.length);
            while (cartes[index]==null) index = (int) (Math.random()*cartes.length);
            main[i] = cartes[index];
            main[i].setIndex(ndx);
            ndx ++;
            cartes[index] = null;
        }
        supprimeDejaUtilisees();

        return main;
    }

    public Boolean getTour(){
        return tour;
    }

    public void switchTour(){
        tour = !tour;
    }
    
    public Carte[] getMain(Boolean joueur){
        if (joueur) return J1.getMain();
        return J2.getMain();
    }

    public Carte[] getCartesPossibles(Carte c){
        Carte [] plateau = deck.getPlateau();
        Carte [] cartesPossibles = new Carte[plateau.length];
        int i = 0;
        int j, k;
        for(j = 0; j < plateau.length; j++){
            if (tour){
                k = deck.getSceptre(tour);
                if (plateau[j].getIndex() < k){
                    if(plateau[j].getColor() == c.getColor() || plateau[j].getSymbol() == c.getSymbol()){
                        cartesPossibles[i] = plateau[j];
                        i++;
                    }
                }
                if(k+c.getValue() == plateau[j].getIndex()){
                    cartesPossibles[i]=plateau[deck.getSceptre(tour)+c.getValue()];
                    i++;
                }
            } else {
                k = deck.getSceptre(tour);
                if (plateau[j].getIndex() > k){
                    if(plateau[j].getColor() == c.getColor() || plateau[j].getSymbol() == c.getSymbol()){
                        cartesPossibles[i] = plateau[j];
                        i++;
                    }
                }
                if(k-c.getValue() == plateau[j].getIndex()){
                    cartesPossibles[i]=plateau[k-c.getValue()];
                    i++;
                }
            }
            
        }
        return cartesPossibles;
    }

    public void execCoup(Coup c){
        Coup c_prec = Historique.getInstance().getCoupPrec();
        if (c_prec != null && c_prec.getType()!=Coup.SWAP_DROIT && c_prec.getType()!=Coup.SWAP_GAUCHE){
            if (!c.estCoupValide(this)) throw new IllegalArgumentException("Coup invalide");
        }

        switch(c.getType()){
            case Coup.ECHANGE:
                execEchange(c);
                switchTour();
                break;
            case Coup.SWAP_DROIT:
            case Coup.SWAP_GAUCHE:
                execSwap(c);
                break;
            default:
                throw new IllegalArgumentException("Type de coup invalide");
        }
    }

    public void execEchange(Coup c){
        int ndx;
        Carte carte = null;
        for(ndx = 0; ndx < 3; ndx++ ){
            if (tour){
                if (J1.getMain()[ndx].getIndex() == c.getCarteMain()) {
                    carte = J1.getMain()[ndx];
                    break;
                }
            }
            else{
                if (J2.getMain()[ndx].getIndex() == c.getCarteMain()) {
                    carte = J2.getMain()[ndx];
                    break;
                }
            }
        }
        Carte [] plateau = deck.getPlateau();
        int ndx_plateau;
        for (int i = 0; i < plateau.length; i++){
            if (plateau[i].getIndex() == c.getCartePlateau()){
                ndx_plateau = plateau[i].getIndex();
                plateau[i].setIndex(carte.getIndex());
                if (tour){
                    J1.setCarte(plateau[i], ndx);
                } else {
                    J2.setCarte(plateau[i], ndx);
                }
                deck.setSceptre(tour,ndx_plateau);
                carte.setIndex(ndx_plateau);
                plateau[i] = carte;
                break;
            }
        }
    }

    public void execSwap(Coup c){
        Carte [] plateau = deck.getPlateau();
        plateau = shuffle(plateau);
        int pos_sc = deck.getSceptre(tour);
        int j = 0;
        Coup coup;
        for (int i = 0 ; i < plateau.length; i++){
            if (c.getType()==Coup.SWAP_DROIT){
                if (plateau[i].getIndex() == pos_sc + 1 || plateau[i].getIndex() == pos_sc + 2 || plateau[i].getIndex() == pos_sc + 3){
                    if (tour){
                        coup = new Coup(Coup.ECHANGE, J1.getCarte(j).getIndex() , plateau[i].getIndex());
                    } else {
                        coup = new Coup(Coup.ECHANGE, J2.getCarte(j).getIndex() , plateau[i].getIndex());
                    }
                    j++;
                    execCoup(coup);
                }
            }
            else if (c.getType() == Coup.SWAP_GAUCHE){
                if (plateau[i].getIndex() == pos_sc - 1 || plateau[i].getIndex() == pos_sc - 2 || plateau[i].getIndex() == pos_sc - 3){
                    if (tour){
                        coup = new Coup(Coup.ECHANGE, J1.getCarte(j).getIndex() , plateau[i].getIndex());
                    } else {
                        coup = new Coup(Coup.ECHANGE, J2.getCarte(j).getIndex() , plateau[i].getIndex());
                    }
                    j++;
                    execCoup(coup);
                }
            }
        }

    }

    public boolean verifParadoxe(){
        Carte [] main;
        if(tour){
            main = J2.getMain();
        } else {
            main = J1.getMain();
        }

        if(main[0].getColor() == deck.getCodex().getIndex() || main[1].getColor() == deck.getCodex().getIndex() || main[2].getColor() == deck.getCodex().getIndex()){
            return false;
        }
        
        if(main[0].getColor() == main[1].getColor() && main[0].getColor() == main[2].getColor()){
            System.out.println("Paradoxe de couleur");
            return true;
        }
        if(main[0].getSymbol() == main[1].getSymbol() && main[0].getSymbol() == main[2].getSymbol()){
            System.out.println("Paradoxe de symbole");
            return true;
        }
        if(main[0].getValue() == main[1].getValue() && main[0].getValue() == main[2].getValue()){
            System.out.println("Paradoxe de valeur");
            return true;
        }
        return false;
    }

    public void prochainCodex(){
        deck.prochainCodex;
    }
}