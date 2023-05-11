package Modele;

public class Carte {
    public static final int TERRE = 1;
    public static final int PSY = 2;
    public static final int EAU = 3;
    public static final int FEU = 4;

    public static final int PLUME = 1;
    public static final int CLE = 2;
    public static final int CRANE = 3;
    public static final int COURONNE = 4;

    private byte index;
    private byte type;

    public Carte(int symbole, int couleur_carte, int valeur_carte, int index, boolean retournee) {
        this.index = (byte) index;
        type = 0;
        type += (symbole - 1) << 6;
        type += (couleur_carte - 1) << 4;
        type += (valeur_carte - 1) << 2;
        if (retournee)
            type += 0b1;
        else
            type += 0;
    }

    public Carte(int index, int type) {
        this.index = (byte) index;
        this.type = (byte) type;
    }

    public boolean isVisible() {
        if ((type & 0b1) == 1)
            return true;
        else
            return false;
    }

    public void setVisbility(boolean retournee) {
        if (retournee)
            type = (byte) (type | 0b1);
        else
            type = (byte) (type & ~0b1);
    }

    public int getType() {
        return (type >> 2) & 0xFF;
    }

    public byte getByteType() {
        return type;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        if (index > 16 || index < 0)
            throw new IllegalArgumentException("Index trop grand");
        this.index = (byte) index;
    }

    public int getColor() {
        return ((type & 0b110000) >> 4) + 1;
    }

    public int getSymbol() {
        return ((type & 0b11000000) >> 6) + 1;
    }

    public int getValue() {
        return ((type & 0b1100) >> 2) + 1;
    }

    public String toString() {
        String s = "";
        s += "(" + getValue() + " " + couleurToString(getColor()) + " " + symboleToString(getSymbol()) + ")";
        return s;
    }

    public static String symboleToString(int symbole) {
        switch (symbole) {
            case PLUME:
                return "plume\u001B[0m";
            case CLE:
                return "cle\u001B[0m";
            case CRANE:
                return "crane\u001B[0m";
            case COURONNE:
                return "couronne\u001B[0m";
        }
        return "Erreur";
    }

    public static String couleurToString(int couleur) {
        switch (couleur) {
            case TERRE:
                return "\u001B[32mterre";
            case PSY:
                return "\u001B[35mpsy";
            case EAU:
                return "\u001B[34meau";
            case FEU:
                return "\u001B[31mfeu";
        }
        return "Erreur";
    }
}
