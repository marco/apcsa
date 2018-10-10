import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 * Represents a tree in the scene, providing access to the tree's x and y location
 * as well as the ability to place the tree into the scene.
 * @author jddevaughnbrown
 *
 */
public class Tree {

    /**
     * The size of the tree. For our purposes, this is known to be the size of the tree image.
     */
    public static final int WIDTH = 128, HEIGHT = 128;

    /**
     * The x-y location of the tree in pixels. For our purposes, the region is 640 by 480.
     */
    private int x, y;

    /**
     * The image of the tree.
     */
    private BufferedImage treeImage;

    /**
     * Constructs the Tree class.
     * @param x - the x location of the tree
     * @param y - the y location of the tree
     * @param filename - the filename of the file that contains the tree image
     */
    public Tree(int x, int y, String filename) {
        try {
            treeImage = ImageIO.read(new URL("file:" + filename));
        } catch (IOException e) {
            System.out.println("Failed to load 'tree_1.png' image.");
        }

        setLocation(x, y);
    }

    /**
     * Sets the location of the tree.
     * @param xPos - the x location of the tree
     * @param yPos - the y location of the tree
     */
    public void setLocation(int xPos, int yPos) {
        this.x = xPos;
        this.y = yPos;
    }

    /**
     * Gets the x-axis location of the tree.
     * @return the x-axis location
     */
    public int getX() {
        return this.x;
    }

    /**
     *  Get the y-axis location of the tree.
     * @return the y-axis location
     */
    public int getY() {
        return this.y;
    }

    /**
     *  Draws the tree at its location in the window.
     * @param g2 - the graphics object to draw the tree on
     */
    public void draw(Graphics2D g2) {
        g2.drawImage(treeImage, null, x, y);
    }

}
