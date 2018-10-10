import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Area class.
 * @author jddevaughnbrown
 *
 */
@SuppressWarnings("serial")
public abstract class Area extends JPanel implements KeyListener {
    /**
     * Calculates the number of tiles based on the Window's width.
     */
    protected static final int NUM_TILES_X = Window.WIDTH / 64;
    /**
     * Calculates the number of tiles based on the Window's height.
     */
    protected static final int NUM_TILES_Y = Window.HEIGHT / 64;

    /**
     * The maximum x position to place a tree on the screen.
     */
    protected static final double MAX_TREE_X = Window.WIDTH - Tree.WIDTH;
    /**
     * The maximum y position to place a tree on the screen.
     */
    protected static final double MAX_TREE_Y = Window.HEIGHT - Tree.HEIGHT;

    /**
     * The trees that are scattered around the area.
     */
    protected Tree[] trees;

    /**
     * The area tile map.
     */
    protected Tile[][] tiles;

    /**
     * The player that the user controls.
     */
    protected Player player;

    /**
     * The death indicator that references the player.
     */
    protected DeathIndicator deathIndicator;

    /**
     * The win indicator that references the player.
     */
    protected WinIndicator winIndicator;

    /**
     * The grass and stone images used as the floor texture.
     */
    private BufferedImage grassImage, stoneImage;

    /**
     * To hide this parameter from being passed around.
     */
    private Graphics2D g2;

    /**
     * The constructor for the Area class.
     */
    public Area() {
        // Load the grass image from the file.
        try {
            grassImage = ImageIO.read(new URL("file:images/grass.png"));
        } catch (IOException e) {
            System.out.println("Failed to load 'grass.png' image.");
        }

        // Load the stone image from the file.
        try {
            stoneImage = ImageIO.read(new URL("file:images/stone.png"));
        } catch (IOException e) {
            System.out.println("Failed to load 'stone.png' image.");
        }

        g2 = null;

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(Window.WIDTH, Window.HEIGHT));
    }

    /**
     * Draws the specified tree.
     * @param i - the array position of the tree to be drawn
     */
    protected void drawTree(int i) {
        if (trees != null) {
            trees[i].draw(g2);
        }
    }

    /**
     * Draws a `Tile`.
     *
     * @param i The column of the tile to draw.
     * @param j The row of the tile to draw.
     */
    protected void drawTile(int i, int j) {
        tiles[i][j].draw(g2);
    }

    /**
     * Draws the `Player`.
     */
    protected void drawPlayer() {
        if (!player.getIsDead()) {
            player.draw(g2);
        }

        player.outputLocation();
    }

    /**
     * Draws the `DeathIndicator`.
     */
    protected void drawDeathIndicator() {
        deathIndicator.draw(g2);
    }

    /**
     * Draws the `WinIndicator`.
     */
    protected void drawWinIndicator() {
        winIndicator.draw(g2);
    }

    // Overridden function from JPanel, which allows us to
    // write our own paint method which draws our area.
    @Override
    public void paint(Graphics g) {
        // This calls JPanel's paintComponent method to handle
        // the lower-level details of drawing in a window.
        super.paint(g);

        g2 = (Graphics2D) g;

        drawTiles();
        drawPlayer();
        drawTrees();

        // On the rare chance that the player touches a fire and the treasure
        // simultaneously, don't show the death indicator. (The win indicator
        // will be shown instead.)
        if (player.getIsDead() && !player.getHasWon()) {
            drawDeathIndicator();
        }

        if (player.getHasWon()) {
            drawWinIndicator();
        }

        // Sync for cross-platform smooth rendering.
        Toolkit.getDefaultToolkit().sync();
    }

    /**
     * Draws the tiles to the screen.
     */
    protected void drawTiles() {
        for (int i = 0; i < NUM_TILES_X; i++) {
            for (int j = 0; j < NUM_TILES_Y; j++) {
                drawTile(i, j);
            }
        }
    }

    /**
     * Draws the trees to the screen.
     */
    protected void drawTrees() {
        for (int i = 0; i < trees.length; i++) {
            drawTree(i);
        }
    }

    /**
     * Is called after repainting.
     */
    protected abstract void onRepaint();


    @Override
    public void keyTyped(KeyEvent keyEvent) {
        switch (keyEvent.getKeyChar()) {
        case 'w':
            player.move(0, -1);
            break;
        case 'a':
            player.move(-1, 0);
            break;
        case 's':
            player.move(0, 1);
            break;
        case 'd':
            player.move(1, 0);
            break;
        default:
            break;
        }

        onRepaint();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }
}
