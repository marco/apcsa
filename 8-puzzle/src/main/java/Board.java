import java.util.ArrayList;
import java.util.List;

/**
 * An 8-puzzle board containing multiple blocks.
 *
 * @author skunkmb
 */
public class Board {
    /**
     * The values of each block in the board. `0` is used to represent
     * the empty block.
     */
    private int[] blocks;

    /**
     * The `n` value for the board, which represents the width and
     * height of the board.
     */
    private int n;

    /**
     * The cached Hamming value for the board, or `-1` if it has not
     * been cached.
     */
    private int cachedHamming = -1;

    /**
     * The cached Manhattan value for the board, or `-1` if it has not
     * been cached.
     */
    private int cachedManhattan = -1;

    /**
     * The cached open row for the board, or `-1` if it has not
     * been cached.
     */
    private int cachedOpenRow = -1;

    /**
     * The cached open column for the board, or `-1` if it has not
     * been cached.
     */
    private int cachedOpenCol = -1;

    /**
     * The cached twin for the board, or `null` if it has not been cached.
     */
    private Board cachedTwin = null;

    /**
     * Constructs a `Board` given a 2D array of blocks.
     *
     * @param blocks The starting blocks in the board. This 2D array
     * is converted internally into a 1D array for efficiency.
     */
    public Board(int[][] blocks) {
        if (blocks == null) {
            throw new IllegalArgumentException("`blocks` cannot be `null`.");
        }

        if (blocks.length < 2) {
            throw new IllegalArgumentException("`blocks` cannot be empty.");
        }

        if (blocks.length != blocks[0].length) {
            throw new IllegalArgumentException("`blocks` must be a square.");
        }

        this.blocks = getBlocks1D(blocks);
        n = blocks.length;
    }

    /**
     * Constructs a `Board` given a 1D array of blocks and an `n` value.
     * This constructor is used internally for easy creation of twin
     * and neighbor boards without converting back into a 2D array.
     *
     * @param blocks The starting blocks in the board.
     * @param n The width and height for the board.
     */
    private Board(int[] blocks, int n) {
        this.blocks = blocks;
        this.n = n;
    }

    /**
     * Returns the dimensions (width and height) of this board.
     *
     * @return The `n` value of the board.
     */
    public int dimension() {
        return n;
    }

    /**
     * Returns the Hamming value for this board.
     *
     * @return The Hamming value for this board.
     */
    public int hamming() {
        if (cachedHamming != -1) {
            return cachedHamming;
        }

        int currentHammingValue = 0;
        int currentCorrectValue = 1;

        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i] != currentCorrectValue && blocks[i] != 0) {
                currentHammingValue++;
            }
            currentCorrectValue++;
        }

        cachedHamming = currentHammingValue;
        return cachedHamming;
    }

    /**
     * Returns the Manhattan value for this board.
     *
     * @return The Manhattan value for this board.
     */
    public int manhattan() {
        if (cachedManhattan != -1) {
            return cachedManhattan;
        }

        int currentManhattanValue = 0;

        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i] == 0) {
                continue;
            }

            int correctBlockRow = getRowFor1DIndex(blocks[i] - 1);
            int correctBlockCol = getColFor1DIndex(blocks[i] - 1);
            int currentBlockRow = getRowFor1DIndex(i);
            int currentBlockCol = getColFor1DIndex(i);

            currentManhattanValue += (
                Math.abs(correctBlockRow - currentBlockRow)
                    + Math.abs(correctBlockCol - currentBlockCol)
            );
        }

        cachedManhattan = currentManhattanValue;
        return cachedManhattan;
    }

    /**
     * Returns whether or not this board is a goal board.
     *
     * @return Whether or not this is a goal.
     */
    public boolean isGoal() {
        return hamming() == 0;
    }

    /**
     * Returns a twin board for this board. A twin board is one where
     * two filled blocks are swapped. (It is guaranteed that either a
     * board or its twin are solvable, but not both.)
     *
     * @return The twin board.
     */
    public Board twin() {
        if (cachedTwin != null) {
            return cachedTwin;
        }

        if (blocks[0] == 0) {
            cachedTwin = getSwapped2D(0, 1, n - 1, 1);
        } else if (blocks[1] == 0) {
            cachedTwin = getSwapped2D(0, 0, n - 1, 0);
        } else {
            cachedTwin = getSwapped2D(0, 0, 0, 1);
        }

        return cachedTwin;
    }

    /**
     * Returns a new cloned `Board` with two 1D indexes swapped.
     *
     * @param index1 The first 1D index.
     * @param index2 The second 1D index.
     * @return The new cloned `Board`.
     */
    private Board getSwapped(int index1, int index2) {
        int[] newBlocks = cloneBlocks(blocks);
        int temp2 = newBlocks[index2];
        newBlocks[index2] = newBlocks[index1];
        newBlocks[index1] = temp2;
        return new Board(newBlocks, n);
    }

    /**
     * Returns a new cloned `Board` with two 2D index pairs swapped.
     *
     * @param row1 The first row.
     * @param col1 The first column.
     * @param row2 The second row.
     * @param col2 The second column.
     * @return The new cloned `Board`.
     */
    private Board getSwapped2D(int row1, int col1, int row2, int col2) {
        return getSwapped(
            getIndexFor2DIndex(row1, col1, n),
            getIndexFor2DIndex(row2, col2, n)
        );
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || !(other instanceof Board)) {
            return false;
        }

        int[] otherBlocks = ((Board) other).blocks;

        if (otherBlocks.length != this.blocks.length) {
            return false;

        }

        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i] != otherBlocks[i]) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns an `Iterable` of all neighbors for this board.
     *
     * @return The neighbors of this board.
     */
    public Iterable<Board> neighbors() {
        int openRow = getOpenRow();
        int openCol = getOpenCol();

        List<Board> otherBoards = new ArrayList<Board>();

        if (openRow > 0) {
            otherBoards.add(getSwapped2D(openRow, openCol, openRow - 1, openCol));
        }

        if (openRow < n - 1) {
            otherBoards.add(getSwapped2D(openRow, openCol, openRow + 1, openCol));
        }

        if (openCol > 0) {
            otherBoards.add(getSwapped2D(openRow, openCol, openRow, openCol - 1));
        }

        if (openCol < n - 1) {
            otherBoards.add(getSwapped2D(openRow, openCol, openRow, openCol + 1));
        }

        return otherBoards;
    }

    /**
     * Returns the open row of this board.
     *
     * @return The open row.
     */
    private int getOpenRow() {
        if (cachedOpenRow != -1) {
            return cachedOpenRow;
        }

        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i] == 0) {
                cachedOpenRow = getRowFor1DIndex(i);
                return cachedOpenRow;
            }
        }

        throw new IllegalStateException("No open block found.");
    }

    /**
     * Returns the open column of this board.
     *
     * @return The open column.
     */
    private int getOpenCol() {
        if (cachedOpenCol != -1) {
            return cachedOpenCol;
        }

        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i] == 0) {
                cachedOpenCol = getColFor1DIndex(i);
                return cachedOpenCol;
            }
        }

        throw new IllegalStateException("No open block found.");
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n);
        stringBuilder.append("\n");

        for (int i = 0; i < blocks.length; i++) {
            stringBuilder.append(String.format("%2d ", blocks[i]));

            if (i % n == n - 1 && i < n * n - 1) {
                stringBuilder.append("\n");
            }
        }

        return stringBuilder.toString();
    }

    /**
     * Returns a 1D array of blocks from a 2D array.
     *
     * @param blocks2D The 2D array to convert.
     * @return The 1D array.
     */
    private int[] getBlocks1D(int[][] blocks2D) {
        int[] blocks1D = new int[blocks2D.length * blocks2D.length];

        for (int i = 0; i < blocks2D.length; i++) {
            for (int j = 0; j < blocks2D.length; j++) {
                if (blocks2D[i] == null) {
                    throw new IllegalArgumentException("`blocks` cannot contain `null`.");
                }

                blocks1D[getIndexFor2DIndex(i, j, blocks2D.length)] = blocks2D[i][j];
            }
        }

        return blocks1D;
    }

    /**
     * Returns a 2D row for a 1D index.
     *
     * @param index The 1D index.
     * @return The 2D row.
     */
    private int getRowFor1DIndex(int index) {
        return index / n;
    }

    /**
     * Returns a 2D column for a 1D index.
     *
     * @param index The 1D index.
     * @return The 2D column.
     */
    private int getColFor1DIndex(int index) {
        return index % n;
    }

    /**
     * Returns a 1D index for a 2D index pair.
     *
     * @param row The 2D row.
     * @param col The 2D column.
     * @param n2D The width and height of the board.
     * @return The 1D index.
     */
    private int getIndexFor2DIndex(int row, int col, int n2D) {
        return col + row * n2D;
    }

    /**
     * Clones a 1D array of blocks.
     *
     * @param oldBlocks The 1D array to clone.
     * @return The new blocks array.
     */
    private int[] cloneBlocks(int[] oldBlocks) {
        int[] newBlocks = new int[oldBlocks.length];

        for (int i = 0; i < oldBlocks.length; i++) {
            newBlocks[i] = oldBlocks[i];
        }

        return newBlocks;
    }
}
