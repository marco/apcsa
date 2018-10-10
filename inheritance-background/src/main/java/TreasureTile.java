import java.awt.Rectangle;

/**
 * A fire tile to be drawn be `Area`.
 *
 * @author skunkmb
 */
public class TreasureTile extends Tile {
    /**
     * Instantiates a `TreasureTile`.
     *
     * @param column The column where the `TreasureTile` is located.
     * @param row The row where the `TreasureTile` is located.
     */
    public TreasureTile(int column, int row) {
        super(column, row, "images/treasure.png");
    }

    /**
     * Checks if the player is touching this treasure tile, and if so, marks
     * the player as "winning".
     */
    public void checkPlayerWin() {
        Rectangle tileRectangle = new Rectangle(getX(), getY(), WIDTH, HEIGHT);
        Rectangle playerRectangle = new Rectangle(
            Player.getCurrentPlayer().getX(),
            Player.getCurrentPlayer().getY(),
            Player.getCurrentPlayer().getWidth(),
            Player.getCurrentPlayer().getHeight()
        );

        if (tileRectangle.intersects(playerRectangle)) {
            Player.getCurrentPlayer().win();
        }
    }
}
