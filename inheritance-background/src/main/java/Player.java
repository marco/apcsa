import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * A player that can be controlled with the keyboard.
 *
 * @author skunkmb
 */
public class Player {
    /**
     * The minimum amount of space between the player and the side of the area
     * when spawned.
     */
    public static final int MINIMUM_MARGIN = 50;

    /**
     * The movement width of player, based on the tile sizes.
     */
    private static final int MOVEMENT_WIDTH = 30;

    /**
     * The movement height of player, based on the tile sizes.
     */
    private static final int MOVEMENT_HEIGHT = 30;

    /**
     * The current active player.
     */
    private static Player currentPlayer;

    /**
     * The x location of the player.
     */
    private int x;

    /**
     * The y location of the player.
     */
    private int y;

    /**
     * The window width for this player.
     */
    private int windowWidth;

    /**
     * The window height for this player.
     */
    private int windowHeight;

    /**
     * The direction of the player (0 represents down, 1 represents right, 2
     * represents up, and 3 represents left).
     */
    private int direction;

    /**
     * Whether or not the character is dead.
     */
    private boolean isDead;

    /**
     * Whether or not the player is swimming.
     */
    private boolean isSwimming;

    /**
     * Whether or not the player has won the game.
     */
    private boolean hasWon;

    /**
     * The player's different images, with each index corresponding to a value of
     * `direction`, along with images for swimming.
     */
    private BufferedImage[] images = new BufferedImage[8];

    /**
     * Initializes a `Player`.
     *
     * @param x The initial x location of the player.
     * @param y The initial y location of the player.
     * @param windowWidth The current width of the window.
     * @param windowHeight The current height of the window.
     */
    public Player(int x, int y, int windowWidth, int windowHeight) {
        try {
            for (int i = 0; i < 4; i++) {
                // Swimming images should be offset by 4.
                images[i] = ImageIO.read(new URL("file:images/player-images/player-" + i + ".png"));
                images[i + 4] = ImageIO.read(new URL(
                        "file:images/player-images/player-" + i + "-swimming.png"
                ));
            }
        } catch (Exception e) {
            System.out.println("Failed to load player images.");
        }

        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        setLocation(x, y);

        currentPlayer = this;
    }

    /**
     * Gets the current active player.
     *
     * @return The current active player.
     */
    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Sets the location of the player.
     *
     * @param newX The new x location of the player.
     * @param newY The new y location of the player.
     */
    private void setLocation(int newX, int newY) {
        // Wrap the player around if they go offscreen.
        int finalX = newX;
        int finalY = newY;
        if (newX < 0) {
            finalX = windowWidth - getWidth();
        } else if (newY < 0) {
            finalY = windowHeight - getHeight();
        } else if (windowWidth <= newX) {
            finalX = 0;
        } else if (windowHeight <= newY) {
            finalY = 0;
        }

        x = finalX;
        y = finalY;
    }

    /**
     * Gets the current width of the player.
     *
     * @return The current width of the player.
     */
    public int getWidth() {
        // Constant directions for different directions and conditions.
        final int verticalWidth = 42;
        final int horizontalWidth = 36;
        final int swimmingVerticalWidth = 40;
        final int swimmingHorizontalWidth = 54;

        if (!isSwimming) {
            switch (direction) {
            case 0:
            case 2:
                return verticalWidth;
            case 1:
            case 3:
                return horizontalWidth;
            default:
                return 0;
            }
        } else {
            switch (direction) {
            case 0:
            case 2:
                return swimmingVerticalWidth;
            case 1:
            case 3:
                return swimmingHorizontalWidth;
            default:
                return 0;
            }
        }
    }

    /**
     * Gets the current height of the player.
     *
     * @return The current height of the player.
     */
    public int getHeight() {
        // Constant directions for different directions and conditions.
        final int verticalHeight = 66;
        final int horizontalHeight = 60;
        final int swimmingVerticalHeight = 54;
        final int swimmingHorizontalHeight = 40;

        if (!isSwimming) {
            switch (direction) {
            case 0:
            case 2:
                return verticalHeight;
            case 1:
            case 3:
                return horizontalHeight;
            default:
                return 0;
            }
        } else {
            switch (direction) {
            case 0:
            case 2:
                return swimmingVerticalHeight;
            case 1:
            case 3:
                return swimmingHorizontalHeight;
            default:
                return 0;
            }
        }
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
     * Outputs the current location of the player.
     */
    protected void outputLocation() {
        System.out.println(
            "I am here: " + x + ", " + y
                + ". Direction: " + direction
                + ". Is dead: " + isDead + "."
        );
    }

    /**
     * Moves the player.
     *
     * @param xChange The change in the x location of the player.
     * @param yChange The change in the y location of the player.
     */
    public void move(int xChange, int yChange) {
        if (0 < xChange) {
            direction = 3;
        } else if (xChange < 0) {
            direction = 1;
        } else if (0 < yChange) {
            direction = 0;
        } else {
            direction = 2;
        }

        setLocation(x + xChange * MOVEMENT_WIDTH, y + yChange * MOVEMENT_HEIGHT);
    }

    /**
     * Marks the character as dead, so that it will not render in future frames.
     */
    public void die() {
        isDead = true;
    }

    /**
     * Marks the character as winning, so that it will show the winning indicator in
     * future frames.
     */
    public void win() {
        hasWon = true;
    }

    /**
     * Marks the character as swimming, so that it will render as swimming in
     * future frames.
     */
    public void swim() {
        isSwimming = true;
    }

    /**
     * Marks the character as walking, so that it will render as walking in
     * future frames.
     */
    public void walk() {
        isSwimming = false;
    }

    /**
     * Gets whether or not the player is dead.
     *
     * @return Whether or not the player is dead.
     */
    public boolean getIsDead() {
        return isDead;
    }

    /**
     * Gets whether or not the player has won.
     *
     * @return Whether or not the player has won.
     */
    public boolean getHasWon() {
        return hasWon;
    }

    /**
     * Draws the player image to a `Graphics2D`.
     *
     * @param graphics2D The `Graphics2D` to use.
     */
    public void draw(Graphics2D graphics2D) {
        if (!isSwimming) {
            graphics2D.drawImage(images[direction], null, x, y);
        } else {
            // Swimming images are offset by 4.
            graphics2D.drawImage(images[direction + 4], null, x, y);
        }
    }
}
