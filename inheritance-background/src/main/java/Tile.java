import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * A tile to be drawn by `Area`.
 *
 * @author skunkmb
 */
abstract class Tile {
    /**
     * The width of tiles, based on the image sizes.
     */
    public static final int WIDTH = 64;

    /**
     * The height of tiles, based on the image sizes.
     */
    public static final int HEIGHT = 64;

    /**
     * The x location of the tile.
     */
    private int x;

    /**
     * The y location of the tile.
     */
    private int y;

    /**
     * The image of the tile.
     */
    private BufferedImage image;

    /**
     * Instantiates a `Tile`.
     *
     * @param column The column where the `Tile` is located.
     * @param row The row where the `Tile` is located.
     * @param fileName The filename of the image for this `Tile`.
     */
    public Tile(int column, int row, String fileName) {
        try {
            image = ImageIO.read(new URL("file:" + fileName));
        } catch (Exception e) {
            System.out.println("Failed to load file \"" + fileName + "\".");
        }

        setLocation(column, row);
    }

    /**
     * Gets the x location.
     *
     * @return The x location.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y location.
     *
     * @return The y location.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the location of this `Tile`.
     *
     * @param column The column for the new location.
     * @param row The row for the new location.
     */
    private void setLocation(int column, int row) {
        this.x = column * WIDTH;
        this.y = row * HEIGHT;
    }

    /**
     * Draws this tile.
     *
     * @param graphics2D The `Graphics2D` object to draw on.
     */
    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage(image, null, x, y);
    }
}
