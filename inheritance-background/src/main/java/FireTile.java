import java.awt.Rectangle;

/**
 * A fire tile to be drawn be `Area`.
 *
 * @author skunkmb
 */
public class FireTile extends Tile {
    /**
     * Instantiates a `FireTile`.
     *
     * @param column The column where the `FireTile` is located.
     * @param row The row where the `FireTile` is located.
     */
    public FireTile(int column, int row) {
        super(column, row, "images/fire.png");
    }

    /**
     * Checks if the player is touching this fire tile, and if so, marks the
     * player as "dead".
     */
    public void checkPlayerDeath() {
        Rectangle tileRectangle = new Rectangle(getX(), getY(), WIDTH, HEIGHT);
        Rectangle playerRectangle = new Rectangle(
            Player.getCurrentPlayer().getX(),
            Player.getCurrentPlayer().getY(),
            Player.getCurrentPlayer().getWidth(),
            Player.getCurrentPlayer().getHeight()
        );

        if (tileRectangle.intersects(playerRectangle)) {
            Player.getCurrentPlayer().die();
        }
    }
}
