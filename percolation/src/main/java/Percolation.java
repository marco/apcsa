import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * A percolation grid that can be used to check if a given grid
 * percolates or not.
 *
 * @author skunkmb
 */
public class Percolation {
    /**
     * The grid that this `Percolation` uses. `true` is used to
     * represent open sites, and `false` represents closed sites.
     */
    private boolean[][] siteGrid;

    /**
     * The width and height of the grid used by this `Percolation`.
     */
    private int gridSize;

    /**
     * The current number of open sites.
     */
    private int openSitesAmount = 0;

    /**
     * A union finder used to determine whether or not a site is full.
     * Top sites are unioned to a virtual top site, but bottom sites
     * are *not* unioned to a virtual bottom site, to prevent
     * backwashing.
     */
    private WeightedQuickUnionUF fullUnionFinder;

    /**
     * A union finder used to determine whether or not a site is full.
     * Top sites are unioned to a virtual top site, and bottom sites
     * are unioned to a virtual bottom site.
     */
    private WeightedQuickUnionUF percolationUnionFinder;

    /**
     * Constructs a `Percolation`.
     *
     * @param gridSize The width and height of the site grid to use.
     */
    public Percolation(int gridSize)  {
        if (gridSize < 1) {
            throw new IllegalArgumentException(
                "The size of the grid cannot be less than 1."
            );
        }

        this.gridSize = gridSize;
        siteGrid = new boolean[gridSize][gridSize];
        fullUnionFinder = new WeightedQuickUnionUF(
            gridSize * gridSize + 1
        );
        percolationUnionFinder = new WeightedQuickUnionUF(
            gridSize * gridSize + 2
        );
    }

    /**
     * Opens a site in the percolation grid.
     *
     * @param row The row of the site to open (1-indexed).
     * @param column The column of the site to open (1-indexed).
     */
    public void open(int row, int column) {
        validateRowAndColumn(row, column);

        if (!isOpen(row, column)) {
            openSitesAmount++;
            siteGrid[row - 1][column - 1] = true;
        }

        if (row == 1) {
            unionBothFinders(
                getIDForTopVirtualSite(),
                getIDForRowAndColumn(row, column)
            );
        }

        if (row > 1 && isOpen(row - 1, column)) {
            unionBothFinders(
                getIDForRowAndColumn(row - 1, column),
                getIDForRowAndColumn(row, column)
            );
        }

        if (row < gridSize && isOpen(row + 1, column)) {
            unionBothFinders(
                getIDForRowAndColumn(row + 1, column),
                getIDForRowAndColumn(row, column)
            );
        }

        if (column > 1 && isOpen(row, column - 1)) {
            unionBothFinders(
                getIDForRowAndColumn(row, column - 1),
                getIDForRowAndColumn(row, column)
            );
        }

        if (column < gridSize && isOpen(row, column + 1)) {
            unionBothFinders(
                getIDForRowAndColumn(row, column + 1),
                getIDForRowAndColumn(row, column)
            );
        }

        if (row == gridSize) {
            percolationUnionFinder.union(
                getIDForBottomVirtualSite(),
                getIDForRowAndColumn(row, column)
            );
        }
    }

    /**
     * Returns whether or not a site in the percolation grid is open.
     *
     * @param row The row of the site to open (1-indexed).
     * @param column The column of the site to open (1-indexed).
     * @return Whether or not the site is open.
     */
    public boolean isOpen(int row, int column) {
        validateRowAndColumn(row, column);
        return siteGrid[row - 1][column - 1];
    }

    /**
     * Returns whether or not a site in the percolation grid is full.
     * In order for the site to be full, it must be an open site that
     * can be connected to an open site in the top row through a chain
     * of neighboring open sites.
     *
     * @param row The row of the site to check (1-indexed).
     * @param column The column of the site to open (1-indexed).
     * @return Whether or not the site is full.
     */
    public boolean isFull(int row, int column) {
        validateRowAndColumn(row, column);

        int siteID = getIDForRowAndColumn(row, column);
        int topVirtualID = getIDForTopVirtualSite();

        return fullUnionFinder.connected(topVirtualID, siteID);
    }

    /**
     * Returns the current number of open sites in the percolation grid.
     *
     * @return The current number of open sites.
     */
    public int numberOfOpenSites() {
        return openSitesAmount;
    }

    /**
     * Returns whether or not the grid percolates. In order for the
     * grid to percolate, one of the sites in the bottom row must be
     * full.
     *
     * @return Whether or not the grid percolates.
     */
    public boolean percolates() {
        int topVirtualID = getIDForTopVirtualSite();
        int bottomVirtualID = getIDForBottomVirtualSite();

        return percolationUnionFinder.connected(topVirtualID, bottomVirtualID);
    }

    /**
     * Returns a one-dimensional ID for a row and a column.
     *
     * @param row The row of the site.
     * @param column The column of the site.
     * @return The one-dimensional ID to use.
     */
    private int getIDForRowAndColumn(int row, int column) {
        return (row - 1) * gridSize + (column - 1);
    }

    /**
     * Returns the ID that represents the top virtual site.
     *
     * @return The ID that represents the top virtual site.
     */
    private int getIDForTopVirtualSite() {
        return getIDForRowAndColumn(gridSize, gridSize) + 1;
    }

    /**
     * Returns the ID that represents the bottom virtual site.
     *
     * @return The ID that represents the bottom virtual site.
     */
    private int getIDForBottomVirtualSite() {
        return getIDForRowAndColumn(gridSize, gridSize) + 2;
    }

    /**
     * Unions a set of IDs on both the `percolationUnionFinder` and the
     * `fullUnionFinder`.
     *
     * @param id1 The first ID to set.
     * @param id2 The second ID to set.
     */
    private void unionBothFinders(int id1, int id2) {
        percolationUnionFinder.union(id1, id2);
        fullUnionFinder.union(id1, id2);
    }

    /**
     * Validates a given row-column pair, and throws an error if it is
     * invalid. Rows and columns both must be greater than or equal to
     * 1 and less than or equal to the size of the grid.
     *
     * @param row The row to check.
     * @param column The column to check.
     */
    private void validateRowAndColumn(int row, int column) {
        if (row < 1) {
            throw new IllegalArgumentException(
                "The row index cannot be less than 1."
            );
        }

        if (row > gridSize) {
            throw new IllegalArgumentException(
                "The row index cannot be greater than " + gridSize + "."
            );
        }

        if (column < 1) {
            throw new IllegalArgumentException(
                "The column index cannot be less than 1."
            );
        }

        if (column > gridSize) {
            throw new IllegalArgumentException(
                "The column index cannot be greater than " + gridSize + "."
            );
        }
    }
}
