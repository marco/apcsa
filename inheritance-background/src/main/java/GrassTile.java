/**
 * A fire tile to be drawn be `Area`.
 *
 * @author skunkmb
 */
public class GrassTile extends Tile {
    /**
     * Instantiates a `GrassTile`.
     *
     * @param column The column where the `GrassTile` is located.
     * @param row The row where the `GrassTile` is located.
     */
    public GrassTile(int column, int row) {
        super(column, row, "images/grass.png");
    }
}
