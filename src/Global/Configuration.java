package Global;

import java.io.InputStream;
import java.util.HashMap;
import javax.imageio.ImageIO;

import Structures.Sequence;
import Structures.SequenceListe;
import Structures.SequenceTableau;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;



public class Configuration {
	static Configuration instance = null;
    final static int silence = 2;
	public final static String typeInterface = "Graphique";
	public static String theme = "Images";
	String typeSequences;
	boolean fixedSeed = false;
	public final static int MAX = 5;

	public static int difficulteIA = 1; // 1 : Aléatoire, >1 : MinMax
	public static int typeHeuristique = 1; // 1 : score, 2 : score + position

	public static Boolean animation = true;

	public static Daltonisme.Type daltonisme = Daltonisme.Type.NORMAL;

	protected Configuration() {
		typeSequences = "Liste";
	}
	
	public static InputStream ouvre(String s) {
		InputStream in = null;
		try {
			in = Configuration.class.getResourceAsStream("/" + s);
		} catch (Exception e) {
			Configuration.erreur("Impossible d'ouvrir le fichier " + s);
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

	public static Clip lisAudio(String nom){
		try {
			InputStream in = Configuration.ouvre(nom);
			InputStream bufferedIn = new BufferedInputStream(in);
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(bufferedIn);
			Clip c = AudioSystem.getClip();
			c.open(audioIn);
			return c;
		} catch (Exception e) {
            e.printStackTrace();
        }
		return null;
	}

	public static Image lisImage(String nom) {
		InputStream in = Configuration.ouvre(theme + "/" + nom + ".png");
        Configuration.info("Chargement de l'image " + nom);
        try {
			BufferedImage image = ImageIO.read(in);
			image = Daltonisme.apply(image, Configuration.daltonisme);
			return image;
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

	public static void setTypeHeuristique(int typeHeuristique) {
		Configuration.typeHeuristique = typeHeuristique;
	}

	public static void setDaltonisme(Daltonisme.Type daltonisme) {
		Configuration.daltonisme = daltonisme;
	}

	public static void switchAnimation() {
		Configuration.animation = !Configuration.animation;
	}

	public static Daltonisme.Type getDaltonisme() {
		return Configuration.daltonisme;
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
