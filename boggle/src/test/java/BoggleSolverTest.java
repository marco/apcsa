import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;

import edu.princeton.cs.algs4.In;

public class BoggleSolverTest {
    final int RUNTIME_COUNT = 100;

    @Test(expected = IllegalArgumentException.class)
    public void testBoggleSolver() {
        new BoggleSolver(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidWordNull() {
        In input = new In("boggle-test-files/dictionary-yawl.txt");
        String[] dictionary = input.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        solver.getAllValidWords(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testScoreOfNull() {
        In input = new In("boggle-test-files/dictionary-yawl.txt");
        String[] dictionary = input.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        solver.scoreOf(null);
    }

    @Test
    public void testGetAllValidWords() {
        In input = new In("boggle-test-files/dictionary-yawl.txt");
        String[] dictionary = input.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        ArrayList<String> expectedValidWords = new ArrayList<String>();
        expectedValidWords.add("NTH");
        expectedValidWords.add("PHT");
        for (String actualWord: solver.getAllValidWords(new BoggleBoard("boggle-test-files/board-points2.txt"))) {
            assertTrue(actualWord + " is not in the expected list", expectedValidWords.contains(actualWord));
        }
    }

    @Test
    public void testGetAllValidWordsDiagonal() {
        In input = new In("boggle-test-files/dictionary-yawl.txt");
        String[] dictionary = input.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        ArrayList<String> expectedValidWords = new ArrayList<String>();
        expectedValidWords.add("HEN");
        expectedValidWords.add("THE");
        expectedValidWords.add("HEX");
        expectedValidWords.add("THEN");
        for (String actualWord: solver.getAllValidWords(new BoggleBoard("boggle-test-files/board-diagonal.txt"))) {
            assertTrue(actualWord + " is not in the expected list", expectedValidWords.contains(actualWord));
        }
    }

    @Test
    public void testGetAllValidWordsHorizontal() {
        In input = new In("boggle-test-files/dictionary-yawl.txt");
        String[] dictionary = input.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        ArrayList<String> expectedValidWords = new ArrayList<String>();
        expectedValidWords.add("DATA");
        expectedValidWords.add("TAD");
        expectedValidWords.add("TAJ");
        expectedValidWords.add("TYPE");
        for (String actualWord: solver.getAllValidWords(new BoggleBoard("boggle-test-files/board-horizontal.txt"))) {
            assertTrue(actualWord + " is not in the expected list", expectedValidWords.contains(actualWord));
        }
    }

    @Test
    public void testGetAllValidWordsVertical() {
        In input = new In("boggle-test-files/dictionary-yawl.txt");
        String[] dictionary = input.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        ArrayList<String> expectedValidWords = new ArrayList<String>();
        expectedValidWords.add("EXERT");
        expectedValidWords.add("DON");
        expectedValidWords.add("NOD");
        expectedValidWords.add("REX");
        expectedValidWords.add("DEX");
        expectedValidWords.add("REE");
        expectedValidWords.add("TREE");
        expectedValidWords.add("OXER");
        expectedValidWords.add("ODE");
        expectedValidWords.add("NODE");
        expectedValidWords.add("EXODE");
        expectedValidWords.add("NOX");
        expectedValidWords.add("EXON");
        for (String actualWord: solver.getAllValidWords(new BoggleBoard("boggle-test-files/board-vertical.txt"))) {
            assertTrue(actualWord + " is not in the expected list", expectedValidWords.contains(actualWord));
        }
    }

    @Test
    public void testGetAllValidWords0() {
        In input = new In("boggle-test-files/dictionary-yawl.txt");
        String[] dictionary = input.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        ArrayList<String> expectedValidWords = new ArrayList<String>();
        for (String actualWord: solver.getAllValidWords(new BoggleBoard("boggle-test-files/board-points0.txt"))) {
            assertTrue(actualWord + " is not in the expected list", expectedValidWords.contains(actualWord));
        }
    }

    @Test
    public void testScoreOf() {
        In input = new In("boggle-test-files/dictionary-yawl.txt");
        String[] dictionary = input.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        assertEquals(1, solver.scoreOf("NTH"));
        assertEquals(0, solver.scoreOf("PTH"));
        assertEquals(3, solver.scoreOf("YELLOW"));
        assertEquals(3, solver.scoreOf("QUAINT"));
        assertEquals(11, solver.scoreOf("QUINTESSENTIAL"));
    }

    @Test
    public void testInitNoException() {
        In input = new In("boggle-test-files/dictionary-algs4.txt");
        String[] dictionary = input.readAllStrings();
        new BoggleSolver(dictionary);
        new BoggleBoard("boggle-test-files/board-q.txt");
    }

    @Test(timeout = 10000)
    public void testRunTime() {
        In input = new In("boggle-test-files/dictionary-yawl.txt");
        String[] dictionary = input.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard("boggle-test-files/board-estrangers.txt");

        for (int i = 0; i < RUNTIME_COUNT; i++) {
            solver.getAllValidWords(board);
        }
    }

    @Test
    public void testAllPointCount() {
        In input = new In("boggle-test-files/dictionary-yawl.txt");
        String[] dictionary = input.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        File folder = new File("boggle-test-files");

        for (File file : folder.listFiles()) {
            if (file.isFile() &&
                (file.getName().substring(file.getName().lastIndexOf('.')+1).equals("txt"))) {
                if (file.getName().startsWith("board-points")) {
                    int correctPoints = Integer.parseInt(file.getName().substring(12, file.getName().length() - 4));
                    BoggleBoard board = new BoggleBoard("boggle-test-files/" + file.getName());
                    int scoreSum = 0;

                    for (String word : solver.getAllValidWords(board)) {
                        scoreSum += solver.scoreOf(word);
                    }

                    assertEquals(correctPoints, scoreSum);
                }
            }
        }
    }
}
