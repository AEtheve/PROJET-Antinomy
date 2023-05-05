package Global;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.io.FileNotFoundException;
import javax.imageio.ImageIO;
import java.awt.Image;

public class Configuration {
    final static int silence = 0;
	public final static String typeInterface = "Graphique";
	public static String theme = "Images";

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

}
