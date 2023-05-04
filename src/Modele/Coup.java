package Modele;

public class Coup {
    private final static int ECHANGE = 0;
    private final static int SWAP_DROIT = 1;
    private final static int SWAP_GAUCHE = 2;

    private byte type; 
    private byte carte_main, carte_plateau;


    public Coup (int type, int carte_main, int carte_plateau){
        if (type==ECHANGE){
            this.type = (byte) type;
            this.carte_main = (byte) carte_main;
            this.carte_plateau = (byte) carte_plateau;
        }
        throw new IllegalArgumentException("Type de coup invalide");
    }

    public Coup(int type, int joueur){
        if (type==SWAP_DROIT || type==SWAP_GAUCHE){
            this.type = (byte) type;
        }
        throw new IllegalArgumentException("Type de coup invalide");
    }

    public byte getType(){
        return this.type;
    }

    private Boolean estSwapValide(Jeu j){
        int pos_sc = j.getDeck().getSceptre(j.getTour());
        
        switch(type){
            case SWAP_DROIT:
                if (pos_sc>=12) return false;
                return true;
            case SWAP_GAUCHE:
                if (pos_sc<=3) return false;
                return true;
            default:
                throw new IllegalArgumentException("Position du sceptre invalide");
        }
    }

    private Boolean estEchangeValide(Jeu j){
        Carte[] plateau = j.getDeck().getPlateau();
        Carte[] main =  j.getMain(j.getTour());

        for (int i = 0; i < plateau.length; i++){
            if (plateau[i].getIndex()==this.carte_plateau){
                for (int k = 0; k < main.length; k++){
                    if (main[k].getIndex()==this.carte_main){
                        if (main[k].getColor()==plateau[i].getColor() || main[k].getSymbol()==plateau[i].getSymbol() || j.getDeck().getSceptre(j.getTour())+main[k].getValue()==i){
                            return true;
                        }
                        return false;
                    }
                }
            }
        }
        throw new IllegalArgumentException("Carte non trouvée");
    }

    public Boolean estCoupValide(Jeu j){
        if (this.type==ECHANGE){
            return estEchangeValide(j);
        }
        else if (this.type==SWAP_DROIT || this.type==SWAP_GAUCHE){
            return estSwapValide(j);
        }
        throw new IllegalArgumentException("Type de coup invalide");
    }



    
}
