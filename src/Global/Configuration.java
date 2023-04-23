package Global;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.FileNotFoundException;

public class Configuration {
    static final int silence = 0;

	public static InputStream ouvre(String s) {
		InputStream in = null;
		try {
			in = new FileInputStream("res/" + s);
		} catch (FileNotFoundException e) {
			erreur("impossible de trouver le fichier " + s);
		}
		return in;
	}

	public static void affiche(int niveau, String message) {
		if (niveau > silence)
			System.err.println(message);
	}

	public static void info(String s) {
		affiche(1, "INFO : " + s);
	}

    public static void alerte(String s) {
        affiche(2, "ALERTE : " + s);
    }

	public static void erreur(String s) {
		affiche(3, "ERREUR : " + s);
		System.exit(1);
	}


}
