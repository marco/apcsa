/**
 * A fire tile to be drawn be `Area`.
 *
 * @author skunkmb
 */
public class StoneTile extends Tile {
    /**
     * Instantiates a `StoneTile`.
     *
     * @param column The column where the `StoneTile` is located.
     * @param row The row where the `StoneTile` is located.
     */
    public StoneTile(int column, int row) {
        super(column, row, "images/stone.png");
    }
}
