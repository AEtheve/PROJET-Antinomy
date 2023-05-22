package Modele;

public class Min extends IASelection {
    public Min() {
        INIT_VALUE = Integer.MAX_VALUE;
    }

    @Override
    public boolean selection(int precedent, int nouveau) {
        return precedent > nouveau;
    }

    @Override
    public boolean conditionCoupe(int precedent, int nouveau) {
        return  precedent >= nouveau;
    }

    @Override
    public IASelection next() {
        return new Max();
    }
}