import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.algs4.In;

public class BoardTest {
    Board board;
    @Before
    public void setUp() throws Exception {
        board = generateBoard("puzzle10.txt");
    }

    private Board generateBoard(String filename) {
        // The board from file.
        In in = new In("8puzzle-test-files/" + filename);
        int n = in.readInt();
        int[][] blocks = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocks[i][j] = in.readInt();
            }
        }

        return new Board(blocks);
    }

    @Test
    public void testAllConstructable() {
        // This test checks that each test case board is constructable.
        File folder = new File("8puzzle-test-files");
        for (File file : folder.listFiles()) {
            String extension = file.getName().substring(file.getName().lastIndexOf('.') + 1);
            if (file.isFile() && extension.equals("txt")) {
                // This should never throw an error.
                Board solver = generateBoard(file.getName());
            }
        }
    }

    @Test
    public void testDimension() {
        board = generateBoard("puzzle3x3-01.txt");
        assertEquals(board.dimension(), 3);
    }

    @Test
    public void testHamming() {
        board = generateBoard("puzzle3x3-01.txt");
        assertEquals(board.hamming(), 1);
    }

    @Test
    public void testManhattan() {
        board = generateBoard("puzzle3x3-01.txt");
        assertEquals(board.manhattan(), 1);
    }

    @Test
    public void testIsGoal() {
        board = generateBoard("puzzle3x3-01.txt");
        assertEquals(board.isGoal(), false);
    }

    @Test
    public void testTwin() {
        board = generateBoard("puzzle3x3-01.txt");
        Iterable<Board> toIterate = board.neighbors();

        for (Board b: toIterate) {
            // No neighbor should equal a twin.
            assertTrue(b != board.twin());
        }

        Board twin = board.twin();

        for (int i = 0; i < 5; i++) {
            // The twin must always be the same.
            assertEquals(board.twin(), twin);
        }
    }

    @Test
    public void testNeighbors() {
        board = generateBoard("puzzle3x3-01.txt");
        Iterable<Board> neighbors = board.neighbors();
        int currentCount = 0;

        Board lastNeighbor = null;
        for (Board neighbor : neighbors) {
            assertNotEquals(neighbor, lastNeighbor);
            lastNeighbor = neighbor;
            currentCount++;
        }

        assertEquals(currentCount, 3);
    }

    @Test
    public void testToString() {
        board = generateBoard("puzzle3x3-01.txt");
        String s = (
            "3\n"
                + " 1  2  3 \n"
                + " 4  5  0 \n"
                + " 7  8  6 "
        );
        assertEquals(s, board.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNull1DSizeException() {
        Board board = new Board(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotSquareWideException() {
        Board board = new Board(
            new int[][] {
                new int[] {0, 1, 2, 3}, new int[] {0, 1, 2, 3},
                new int[] {0, 1, 2, 3}, new int[] {0, 1, 2, 3},
                new int[] {0, 1, 2, 3}, new int[] {0, 1, 2, 3},
            }
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotSquareTallException() {
        Board board = new Board(new int[][] {
            new int[] {0, 1, 2}, new int[] {0, 1, 2},
            new int[] {0, 1, 2}, new int[] {0, 1, 2},
            new int[] {0, 1, 2}, new int[] {0, 1, 2},
            new int[] {0, 1, 2}, new int[] {0, 1, 2},
        });
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSmallException() {
        Board board = new Board(new int[][] {new int[] {0}});
    }
}
