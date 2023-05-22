package Global;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.io.FileNotFoundException;
import javax.imageio.ImageIO;

import Structures.Sequence;
import Structures.SequenceListe;
import Structures.SequenceTableau;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;


public class Configuration {
	static Configuration instance = null;
    final static int silence = 2;
	public final static String typeInterface = "Graphique";
	public static String theme = "Images";
	String typeSequences;
	boolean fixedSeed = true;
	public final static int MAX = 5;

	public static int difficulteIA = 10; // 1 : Aléatoire, >1 : MinMax
	public static int typeHeuristique = 1; // 1 : score, 2 : score + position

	public static Boolean animation = true;

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


	public static Image lisImage(String nom) {
		InputStream in = Configuration.ouvre(theme + "/" + nom + ".png");
        Configuration.info("Chargement de l'image " + nom);
        try {
			BufferedImage image = ImageIO.read(in);
			// image = Daltonisme.apply(image, Daltonisme.Type.TRITANOMALY);
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

	public static void switchAnimation() {
		Configuration.animation = !Configuration.animation;
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


class Daltonisme {
    public enum Type {
        PROTANOPIA,
        DEUTERANOPIA,
        TRITANOPIA,
        DEUTERANOMALY,
        PROTANOMALY,
        TRITANOMALY
    }

    public static BufferedImage apply(BufferedImage image, Type type) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = new Color(image.getRGB(x, y), true);
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                int alpha = color.getAlpha();

				Color newColor;
                switch (type) {
                    case PROTANOPIA:
                        newColor = convertProtanopia(red, green, blue, alpha);
                        break;
                    case DEUTERANOPIA:
                        newColor = convertDeuteranopia(red, green, blue, alpha);
                        break;
                    case TRITANOPIA:
                        newColor = convertTritanopia(red, green, blue, alpha);
                        break;
                    case DEUTERANOMALY:
                        newColor = convertDeuteranomaly(red, green, blue, alpha);
                        break;
                    case PROTANOMALY:
                        newColor = convertProtanomaly(red, green, blue ,alpha);
                        break;
                    case TRITANOMALY:
                        newColor = convertTritanomaly(red, green, blue, alpha);
                        break;
                    default:
                        newColor = new Color(red, green, blue, alpha);
                        break;
                }
                newImg.setRGB(x, y, newColor.getRGB());
            }
        }
        return newImg;
    }

	private static Color convertProtanopia(int red, int green, int blue, int alpha) {
		int newRed = (int) (0.56667 * green + 0.43333 * blue);
		int newGreen = (int) (0.55833 * red + 0.44167 * blue);
		int newBlue = (int) (0.24167 * red + 0.75833 * green);
		return new Color(newRed, newGreen, newBlue, alpha);
	}

	private static Color convertDeuteranopia(int red, int green, int blue, int alpha) {
		int newRed = (int) (0.625 * green + 0.375 * blue);
		int newGreen = (int) (0.7 * red + 0.3 * blue);
		int newBlue = (int) (0.3 * red + 0.7 * green);
		return new Color(newRed, newGreen, newBlue, alpha);
	}

	private static Color convertTritanopia(int red, int green, int blue, int alpha) {
		int newRed = (int) (0.95 * green + 0.05 * blue);
		int newGreen = (int) (0.43333 * red + 0.56667 * blue);
		int newBlue = (int) (0.475 * red + 0.525 * green);
		return new Color(newRed, newGreen, newBlue, alpha);
	}

	private static Color convertDeuteranomaly(int red, int green, int blue, int alpha) {
		int newRed = (int) (0.8 * green + 0.2 * blue);
		int newGreen = (int) (0.25833 * red + 0.74167 * blue);
		int newBlue = (int) (0.14167 * red + 0.85833 * green);
		return new Color(newRed, newGreen, newBlue, alpha);
	}

	private static Color convertProtanomaly(int red, int green, int blue, int alpha) {
		int newRed = (int) (0.81667 * green + 0.18333 * blue);
		int newGreen = (int) (0.33333 * red + 0.66667 * blue);
		int newBlue = (int) (0.00833 * red + 0.99167 * green);
		return new Color(newRed, newGreen, newBlue, alpha);
	}

	private static Color convertTritanomaly(int red, int green, int blue, int alpha) {
		int newRed = (int) (0.96667 * green + 0.03333 * blue);
		int newGreen = (int) (0.05 * red + 0.95 * blue);
		int newBlue = (int) (0 * red + 0.73333 * green);
		return new Color(newRed, newGreen, newBlue, alpha);
	}
}
