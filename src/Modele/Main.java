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
        Main mainTriee = new Main(main);
        for (int i = 0; i < mainTriee.main.length; i++) {
            for (int j = i + 1; j < mainTriee.main.length; j++) {
                if (mainTriee.main[i].getIndex() > mainTriee.main[j].getIndex()) {
                    Carte tmp = mainTriee.main[i];
                    mainTriee.main[i] = mainTriee.main[j];
                    mainTriee.main[j] = tmp;
                }
            }
        }

        String s = "[";
        for (int i = 0; i < mainTriee.main.length; i++) {
            s += mainTriee.main[i].toString();
            if (i < mainTriee.main.length - 1) {
                s += ", ";
            }
        }
        s += "]";
        return s;
    }
}
