import java.awt.Rectangle;

/**
 * A fire tile to be drawn be `Area`.
 *
 * @author skunkmb
 */
public class WaterTile extends Tile {
    /**
     * Instantiates a `WaterTile`.
     *
     * @param column The column where the `WaterTile` is located.
     * @param row The row where the `WaterTile` is located.
     */
    public WaterTile(int column, int row) {
        super(column, row, "images/water.png");
    }

    /**
     * Checks if the player is touching this water tile, and if so, marks the
     * player as "swimming".
     */
    public void checkPlayerSwim() {
        Rectangle tileRectangle = new Rectangle(getX(), getY(), WIDTH, HEIGHT);
        Rectangle playerRectangle = new Rectangle(
            Player.getCurrentPlayer().getX(),
            Player.getCurrentPlayer().getY(),
            Player.getCurrentPlayer().getWidth(),
            Player.getCurrentPlayer().getHeight()
        );

        if (tileRectangle.intersects(playerRectangle)) {
            Player.getCurrentPlayer().swim();
        }
    }
}
