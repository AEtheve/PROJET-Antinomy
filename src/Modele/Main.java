package Modele;

public class Main {
    Carte [] main;

    public Main(Carte [] main){
        this.main = main;
    }


    public Carte[] getMain() {
        return main;
    }

    public void setCarte(Carte c, int i){
        main[i] = c;
    }

    public String toString(){
        String s = "[";
        for (int i = 0; i < main.length; i++) {
            s += main[i].toString();
            if (i < main.length - 1) {
                s += ", ";
            }
        }
        s += "]";
        return s;
    }
}
