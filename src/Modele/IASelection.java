package Modele;

public class IASelection {
    public int INIT_VALUE;

    public boolean selection(int precedent, int nouveau) {
        return false;
    }

    public boolean conditionCoupe(int precedent, int nouveau) {
        return false;
    }

    public IASelection next() {
        return null;
    }
}