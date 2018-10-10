import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * An indicator that will tell the player that they died, if they did.
 *
 * @author skunkmb
 */
public class WinIndicator {
    /**
     * The win indicator image to display.
     */
    private BufferedImage image;

    /**
     * Instantiates a `WinIndicator`.
     */
    public WinIndicator() {
        try {
            image = ImageIO.read(new URL("file:images/win.png"));
        } catch (Exception e) {
            System.out.println("Failed to load \"win.png\".");
        }
    }

    /**
     * Draws the `WinIndicator`.
     *
     * @param graphics2D The `Graphics2D` object to draw on.
     */
    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage(image, null, 100, 100);
    }
}
