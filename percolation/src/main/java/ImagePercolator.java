import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.imageio.ImageIO;

/**
 * Converts an image to a list of open points for a percolation
 * file.
 *
 * @author skunkmb
 */
public class ImagePercolator {
    /**
     * The minimum brightness value to open a point on the grid.
     */
    private static final int OPEN_THRESHOLD = 30;

    /**
     * The hexadecimal value to use when finding the red portion of an
     * image.
     */
    private static final int RED_MASK = 0x00ff0000;

    /**
     * The amount to bitshift the red portion of an image.
     */
    private static final int RED_SHIFT = 16;

    /**
     * The hexadecimal value to use when finding the green portion of an
     * image.
     */
    private static final int GREEN_MASK = 0x0000ff00;

    /**
     * The amount to bitshift the green portion of an image.
     */
    private static final int GREEN_SHIFT = 8;

    /**
     * The hexadecimal value to use when finding the blue portion of an
     * image.
     */
    private static final int BLUE_MASK = 0x000000ff;

    /**
     * The amount to bitshift the blue portion of an image.
     */
    private static final int BLUE_SHIFT = 0;

    /**
     * Asks for an input and an output file and saves the BMP file as a
     * percolation grid.
     *
     * @param args Command-line arguments. These are ignored.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input file (BMP)?");
        String inputName = scanner.nextLine();
        System.out.println("Output file?");
        String outputName = scanner.nextLine();

        System.out.println("Reading from \"" + inputName + "\"");
        System.out.println("Writing to \"" + outputName + "\"");

        File file = new File(inputName);
        BufferedImage image = null;

        try {
            image = ImageIO.read(file);
            PrintWriter writer = new PrintWriter(outputName, "UTF-8");
            writer.println(image.getHeight());
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    int color = image.getRGB(x, y);
                    int red = (color & RED_MASK) >> RED_SHIFT;
                    int green = (color & GREEN_MASK) >> GREEN_SHIFT;
                    int blue = (color & BLUE_MASK) >> BLUE_SHIFT;
                    if (red >= OPEN_THRESHOLD && green >= OPEN_THRESHOLD && blue >= OPEN_THRESHOLD) {
                        writer.println((y + 1) + " " + (x + 1));
                    }
                }
            }
            writer.close();
            scanner.close();
            System.out.println("Done.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
