import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * An indicator that will tell the player that they died, if they did.
 *
 * @author skunkmb
 */
public class DeathIndicator {
    /**
     * The death indicator image to display.
     */
    private BufferedImage image;

    /**
     * Instantiates a `DeathIndicator`.
     */
    public DeathIndicator() {
        try {
            image = ImageIO.read(new URL("file:images/death.png"));
        } catch (Exception e) {
            System.out.println("Failed to load \"death.png\".");
        }
    }

    /**
     * Draws the `DeathIndicator`.
     *
     * @param graphics2D The `Graphics2D` object to draw on.
     */
    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage(image, null, 100, 100);
    }
}
