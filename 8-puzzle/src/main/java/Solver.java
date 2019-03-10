import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import edu.princeton.cs.algs4.MinPQ;

/**
 * An 8-puzzle solver that solves a board.
 *
 * @author skunkmb
 */
public class Solver {
    /**
     * The total number of moves required to solve the board, or
     * `-1` if there is no solution.
     */
    private int numberOfMoves;

    /**
     * The final `BoardNode` in the solution.
     */
    private BoardNode finalBoardNode;

    /**
     * A cached solution `Iterable` to the board, or `null` of no
     * solution has been cached.
     */
    private Iterable<Board> cachedSolution;

    /**
     * A node that contains a board, number of moves, and more
     * information about the board.
     *
     * @author skunkmb
     */
    private class BoardNode {
        /**
         * The current number of moves to get to this board.
         */
        private int moveNumber;

        /**
         * The `Board` that this node represents.
         */
        private Board board;

        /**
         * The board that came before this board. (This board is a
         * neighbor of its parent.)
         */
        private BoardNode parent;

        /**
         * Whether or not this board is a twin of the board to solve.
         */
        private boolean isTwin;

        /**
         * A cached Manhattan value for this board, or `-1` if no value
         * has been cached.
         */
        private int cachedManhattan = -1;

        /**
         * A cached Manhattan priority for this board, or `-1` if no priority
         * has been cached.
         */
        private int cachedManhattanPriority = -1;

        /**
         * Constructs a new `BoardNode`.
         *
         * @param board The board for this node.
         * @param moveNumber The number of moves for this node.
         * @param parent The parent of this node.
         * @param isTwin Whether or not this node is a twin of the board
         * to solve.
         */
        private BoardNode(Board board, int moveNumber, BoardNode parent, boolean isTwin) {
            this.board = board;
            this.moveNumber = moveNumber;
            this.parent = parent;
            this.isTwin = isTwin;
        }

        /**
         * Returns the Manhattan priority of this node.
         *
         * @return The Manhattan priority of this node.
         */
        private int getManhattanPriority() {
            if (cachedManhattanPriority != -1) {
                return cachedManhattanPriority;
            }

            cachedManhattanPriority = board.manhattan() + moveNumber;
            return cachedManhattanPriority;
        }

        /**
         * Returns the tiebreaker Manhattan value of this node.
         *
         * @return The tiebreaker Manhattan value  of this node.
         */
        private int getTiebreakerValue() {
            if (cachedManhattan != -1) {
                return cachedManhattan;
            }

            cachedManhattan = board.manhattan();
            return cachedManhattan;
        }
    }

    /**
     * A comparator for comparing the Manhattan values of boards. If the
     * Manhattan priorities are equal, then the Manhattan values are
     * compared directly.
     *
     * @author skunkmb
     */
    private class ManhattanBoardComparator implements Comparator<BoardNode> {
        @Override
        public int compare(BoardNode boardNode1, BoardNode boardNode2) {
            int priorityDifference = boardNode1.getManhattanPriority() - boardNode2.getManhattanPriority();

            if (priorityDifference == 0) {
                return boardNode1.getTiebreakerValue() - boardNode2.getTiebreakerValue();
            }

            return priorityDifference;
        }
    }

    /**
     * Constructs a `Solver`.
     *
     * @param initial The initial board for this solver.
     */
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("Initial board cannot be `null`.");
        }

        BoardNode twinInitialNode = new BoardNode(initial.twin(), 0, null, true);
        BoardNode initialNode = new BoardNode(initial, 0, null, false);
        MinPQ<BoardNode> queue = new MinPQ<BoardNode>(new ManhattanBoardComparator());
        queue.insert(initialNode);
        queue.insert(twinInitialNode);

        BoardNode start = null;

        while (true) {
            start = queue.delMin();

            if (start.board.isGoal()) {
                if (start.isTwin) {
                    numberOfMoves = -1;
                    finalBoardNode = null;
                } else {
                    numberOfMoves = start.moveNumber;
                    finalBoardNode = start;
                }

                break;
            }

            for (Board neighbor : start.board.neighbors()) {
                if (start.parent == null || !neighbor.equals(start.parent.board)) {
                    queue.insert(new BoardNode(neighbor, start.moveNumber + 1, start, start.isTwin));
                }
            }
        }
    }

    /**
     * Returns the number of moves in the solution, or `-1` if there
     * is no solution.
     *
     * @return The number of moves.
     */
    public int moves() {
        return numberOfMoves;
    }

    /**
     * Returns whether or not the board is solvable.
     *
     * @return Whether or not the board is solvable.
     */
    public boolean isSolvable() {
        return numberOfMoves != -1;
    }

    /**
     * Returns an `Iterable` of all boards in the solution. This includes
     * the ending board and the starting board.
     *
     * @return The solution.
     */
    public Iterable<Board> solution() {
        if (numberOfMoves == -1) {
            return null;
        }

        if (cachedSolution != null) {
            return cachedSolution;
        }

        List<Board> currentSolution = new ArrayList<Board>();
        BoardNode currentBoardNode = finalBoardNode;

        while (currentBoardNode != null) {
            currentSolution.add(0, currentBoardNode.board);
            currentBoardNode = currentBoardNode.parent;
        }

        cachedSolution = currentSolution;
        return cachedSolution;
    }
}
