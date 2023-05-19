package Global;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.io.FileNotFoundException;
import javax.imageio.ImageIO;

import Structures.Sequence;
import Structures.SequenceListe;
import Structures.SequenceTableau;

import java.awt.Image;

public class Configuration {
	static Configuration instance = null;
    final static int silence = 0;
	public final static String typeInterface = "Graphique";
	public static String theme = "Images";
	String typeSequences;
	boolean fixedSeed = true;
	public final static int MAX = 5;

	public static int difficulteIA = 2; // 1 : Aléatoire

	protected Configuration() {
		typeSequences = "Liste";
	}
	
	public static InputStream ouvre(String s) {
		InputStream in = null;
		try {
			in = new FileInputStream("res/" + s);
		} catch (FileNotFoundException e) {
			alerte("impossible de trouver le fichier " + s);
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

	public static Image lisImage(String nom) {
		InputStream in = Configuration.ouvre(theme + "/" + nom + ".png");
        Configuration.info("Chargement de l'image " + nom);
        try {
            return ImageIO.read(in);
        } catch (Exception e) {
			Configuration.alerte("Impossible de charger l'image " + nom);
        }
        return null;
    }

	public static Image lisImage(String nom, HashMap<String, Image> imagesCache) {
		Image img = imagesCache.get(nom);
		if (img == null) {
			img = lisImage(nom);
			imagesCache.put(nom, img);
		}
		return img;
	}

	public static void setTheme(String theme) {
		Configuration.theme = theme;
	}

	public static void setFixedSeed(boolean fixedSeed) {
		instance().fixedSeed = fixedSeed;
	}

	public static void setDifficulteIA(int difficulteIA) {
		Configuration.difficulteIA = difficulteIA;
	}




	public static boolean getFixedSeed() {
		return instance().fixedSeed;
	}

	public static <E> Sequence<E> nouvelleSequence() {
		return instance().creerNouvelleSequence();
	}

	public <E> Sequence<E> creerNouvelleSequence() {
		switch (typeSequences) {
			case "Liste" :
				return new SequenceListe<>();
			case "Tableau" :
				return new SequenceTableau<>();
			default:
				erreur("Type de séquence invalide : " + typeSequences);
				return null;
		}
	}

	public static Configuration instance() {
		if (instance == null)
			instance = new Configuration();
		return instance;
	}
	


}