import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.algs4.In;

public class PercolationTest {

  private Percolation grid10;
  private static final int TIMING_COUNT = 1000;
  private static final double RANDOM_OPEN_AMOUNT = 0.5;

  @Before
  public void setup() {
    grid10 = new Percolation(10);
  }

  @Test
  public void testCloseSites() {
    for(int i = 1; i <= 10; i++) {
      for(int j = 1; j <= 10; j++) {
        assertFalse("Site " + i + "," + j + " should start closed",
            grid10.isOpen(i, j));
      }
    }
  }

  @Test
  public void testOpen() {
    grid10.open(1, 1);
    assertTrue(grid10.isOpen(1, 1));
  }

  @Test
  public void testOpenAdjacentClosed() {
    grid10.open(1, 1);
    assertFalse("You should not open an adjacent site, you should union them",
        grid10.isOpen(1, 2));
  }

  @Test
  public void testNumberOfOpenSites() {
    grid10.open(1, 1);
    assertEquals("Should have 1 site open", 1, grid10.numberOfOpenSites());
  }

  @Test
  public void testIsFull() {
    grid10.open(1, 1);
    assertTrue("1,1 should translate to the top left corner (traditionally 0,0)"
        + "and be full if it is opened", grid10.isFull(1, 1));
  }

  @Test
  public void testIsFullFalse() {
    grid10.open(1, 1);
    grid10.open(3, 1);
    assertFalse("3,1 should not be full as it is not connected to top",
        grid10.isFull(3, 1));
  }

  @Test
  public void testIsFullConnection() {
    grid10.open(1, 1);
    grid10.open(2, 1);
    grid10.open(3, 1);
    assertTrue("3, 1 is connected to the top and thus should be full",
        grid10.isFull(3, 1));
  }

  private Percolation generatePercolation(String filename) {
    In in = new In("percolation-test-files/" + filename);      // input file
    int n = in.readInt();         // n-by-n percolation system
    // repeatedly read in sites to open
    Percolation perc = new Percolation(n);
    while (!in.isEmpty()) {
      int i = in.readInt();
      int j = in.readInt();
      perc.open(i, j);
    }
    return perc;
  }

  @Test
  public void testPercolatesFalse() {
    File folder = new File("percolation-test-files");

    for (File file : folder.listFiles()) {
      // check that it's a valid txt file
      if (file.isFile() &&
          (file.getName().substring(file.getName().lastIndexOf('.')+1).equals("txt"))) {
        // check to verify that it is a system that does not percolate
        if (file.getName().contains("no") || file.getName().equals("greeting57.txt")
            || file.getName().equals("heart25.txt")) {
          Percolation perc = generatePercolation(file.getName());
          assertFalse(file.getName() + " should not percolate", perc.percolates());
        }
      }
    }
  }

  @Test
  public void testPercolates() {
    File folder = new File("percolation-test-files");
    for (File file : folder.listFiles()) {
      // check that it's a valid txt file
      if (file.isFile() &&
          (file.getName().substring(file.getName().lastIndexOf('.')+1).equals("txt"))) {
        // check to verify that it is a system that percolates
        if (!file.getName().contains("no") && !file.getName().equals("greeting57.txt") &&
            !file.getName().equals("heart25.txt")) {
          Percolation perc = generatePercolation(file.getName());
          assertTrue(file.getName() + " should percolate", perc.percolates());
        }
      }
    }
  }

  @Test
  public void testBackwash() {
    Percolation input10 = generatePercolation("input10.txt");
    assertFalse("Bottom left site is not connected to the top so should not be full",
                  input10.isFull(10, 1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOpenIndexOutOfBounds() {
      grid10.open(0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOpenBigNumber() {
      // Grid 10 is 10x10, so 11 is out of bounds.
      grid10.open(11, 11);
  }

  @Test
  public void testOpenMultiple() {
      grid10.open(1, 1);
      grid10.open(1, 2);
      assertTrue(grid10.isOpen(1, 1));
      assertTrue(grid10.isOpen(1, 2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOpenNegative() {
      grid10.open(-100, 100);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsFullNegative() {
      grid10.isFull(-100, 100);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalid() {
      new Percolation(-100);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorSize0() {
      new Percolation(0);
  }

  @Test(timeout = 500)
  public void testOpenTime() {
      Percolation percolation = new Percolation(TIMING_COUNT);
      for (int i = 1; i <= TIMING_COUNT; i++) {
          for (int j = 1; j <= TIMING_COUNT; j++) {
              percolation.open(i, j);
          }
      }
  }

  @Test(timeout = 500)
  public void testIsOpenTime() {
      Percolation percolation = new Percolation(TIMING_COUNT);

      for (int i = 1; i <= TIMING_COUNT; i++) {
          for (int j = 1; j <= TIMING_COUNT; j++) {
              percolation.open(i, j);
          }
      }

      for (int i = 1; i <= TIMING_COUNT; i++) {
          for (int j = 1; j <= TIMING_COUNT; j++) {
              percolation.isOpen(i, j);
          }
      }
  }

  @Test(timeout = 500)
  public void testIsFullTime() {
      Percolation percolation = new Percolation(TIMING_COUNT);

      for (int i = 1; i <= TIMING_COUNT; i++) {
          for (int j = 1; j <= TIMING_COUNT; j++) {
              percolation.open(i, j);
          }
      }

      for (int i = 1; i <= TIMING_COUNT; i++) {
          for (int j = 1; j <= TIMING_COUNT; j++) {
              percolation.isFull(i, j);
          }
      }
  }

  @Test(timeout = 500)
  public void testNumberOfOpenSitesTime() {
      Percolation percolation = new Percolation(TIMING_COUNT);

      for (int i = 1; i <= TIMING_COUNT; i++) {
          for (int j = 1; j <= TIMING_COUNT; j++) {
              percolation.open(i, j);
          }
      }

      percolation.numberOfOpenSites();
  }

  @Test(timeout = 500)
  public void testPercolatesTime() {
      Percolation percolation = new Percolation(TIMING_COUNT);

      for (int i = 1; i <= TIMING_COUNT; i++) {
          for (int j = 1; j <= TIMING_COUNT; j++) {
              if (Math.random() < RANDOM_OPEN_AMOUNT) {
                  percolation.open(i, j);
              }
          }
      }

      percolation.percolates();
  }
}
