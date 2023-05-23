package Global;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Daltonisme {
    public enum Type {
		NORMAL,
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