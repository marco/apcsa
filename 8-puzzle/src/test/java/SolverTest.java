import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Test;

import edu.princeton.cs.algs4.In;

public class SolverTest {
    private static final int MAX_MOVE_NUMBER = 30;
    private Solver generateSolver(String filename) {
        // Create the initial board from file.
        In in = new In("8puzzle-test-files/" + filename);
        int n = in.readInt();
        int[][] blocks = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocks[i][j] = in.readInt();
            }
        }

        Board board = new Board(blocks);
        return new Solver(board);
    }

    @Test
    public void testMoveNumberAndSolvable() {
        // This test checks the `moves` and `isSolvable` method for
        // every individual test case.
        File folder = new File("8puzzle-test-files");
        for (File file : folder.listFiles()) {
            String extension = file.getName().substring(file.getName().lastIndexOf('.') + 1);
            if (file.isFile() && extension.equals("txt")) {
                if (!file.getName().contains("unsolvable")) {
                    // If it's solvable, make sure that it is solved
                    // in the specified number of moves.
                    int numberOfMoves = Integer.parseInt(
                        file.getName().substring(
                            file.getName().length() - 6,
                            file.getName().length() - 4
                        )
                    );

                    if (numberOfMoves > MAX_MOVE_NUMBER) {
                        continue;
                    }

                    System.out.println("Testing file: " + file.getName());
                    Solver solver = generateSolver(file.getName());

                    assertEquals(solver.moves(), numberOfMoves);
                    assertTrue(solver.isSolvable());
                } else {
                    System.out.println("Testing file: " + file.getName());
                    Solver solver = generateSolver(file.getName());

                    // The amount of moves should be `-1` since it's unsolvable.
                    assertEquals(solver.moves(), -1);
                    assertFalse(solver.isSolvable());
                }
            }
        }
    }

    @Test
    public void testSolutionOnePuzzle() {
        Solver solver = generateSolver("puzzle2x2-01.txt");
        Iterable<Board> solution = solver.solution();
        Board solutionStep0 = new Board(new int[][] {{1, 2}, {0, 3}});
        Board solutionStep1 = new Board(new int[][] {{1, 2}, {3, 0}});

        int currentIndex = 0;
        for (Board board : solution) {
            if (currentIndex == 0) {
                assertTrue(board.equals(solutionStep0));
            } else if (currentIndex == 1) {
                assertTrue(board.equals(solutionStep1));
            } else {
                fail("It took too many tries to find a solution.");
            }

            currentIndex++;
        }
    }

    @Test
    public void testMovesSolvableOnePuzzle() {
        Solver solver = generateSolver("puzzle2x2-05.txt");

        assertEquals(solver.moves(), 5);
        assertEquals(solver.isSolvable(), true);
    }

    @Test
    public void testSolutionUnsolvableOnePuzzle() {
        Solver solver = generateSolver("puzzle2x2-unsolvable1.txt");
        Iterable<Board> solution = solver.solution();

        assertEquals(solution, null);
    }

    @Test
    public void testMovesSolvableUnsolvableOnePuzzle() {
        Solver solver = generateSolver("puzzle2x2-unsolvable3.txt");

        assertEquals(solver.moves(), -1);
        assertEquals(solver.isSolvable(), false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullException() {
        Solver solver = new Solver(null);
    }
}