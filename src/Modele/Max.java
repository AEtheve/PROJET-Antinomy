package Modele;

public class Max extends IASelection {
    public Max() {
        INIT_VALUE = Integer.MIN_VALUE;
    }

    @Override
    public boolean selection(int precedent, int nouveau) {
        return nouveau > precedent;
    }

    @Override
    public boolean conditionCoupe(int precedent, int nouveau) {
        return  nouveau >= precedent;
    }

    @Override
    public IASelection next() {
        return new Min();
    }
}