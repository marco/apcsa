import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class MyAgentTest {

  Connect4Game game;
  final int WIN_MINIMUM = 40;

  @Before
  public void setUp() throws Exception {
    game = new Connect4Game(7, 6);
  }

  @Test
  public void testICanWinVerticallySimple() {
    MyAgent redAgent = new MyAgent(game, true);
    MyAgent yellowAgent = new MyAgent(game, false);
    game.clearBoard();
    for (int i = 0; i < 3; i++) {
      redAgent.moveOnColumn(1);
      yellowAgent.moveOnColumn(2);
    }

    assertEquals(1, redAgent.iCanWin());
  }

  @Test
  public void testICanWinVerticallyTop4() {
    MyAgent redAgent = new MyAgent(game, true);
    MyAgent yellowAgent = new MyAgent(game, false);
    game.clearBoard();
    for (int i = 0; i < 2; i++) {
      redAgent.moveOnColumn(1);
      yellowAgent.moveOnColumn(2);
    }

    for (int i = 0; i < 3; i++) {
      redAgent.moveOnColumn(2);
      yellowAgent.moveOnColumn(1);
    }

    assertEquals(2, redAgent.iCanWin());
  }

  @Test
  public void testICanWinVerticallySide4() {
    MyAgent redAgent = new MyAgent(game, true);
    MyAgent yellowAgent = new MyAgent(game, false);
    game.clearBoard();

    yellowAgent.moveOnColumn(6);
    redAgent.moveOnColumn(6);
    yellowAgent.moveOnColumn(4);
    redAgent.moveOnColumn(6);
    yellowAgent.moveOnColumn(3);
    redAgent.moveOnColumn(6);

    assertEquals(6, yellowAgent.theyCanWin());
  }

  @Test
  public void testICanPreventHorizontalGap2nd() {
    MyAgent redAgent = new MyAgent(game, true);
    MyAgent yellowAgent = new MyAgent(game, false);
    game.clearBoard();

    redAgent.moveOnColumn(0);
    yellowAgent.moveOnColumn(0);
    redAgent.moveOnColumn(2);
    yellowAgent.moveOnColumn(2);
    redAgent.moveOnColumn(3);
    yellowAgent.moveOnColumn(3);
    redAgent.moveOnColumn(1);

    assertEquals(1, redAgent.theyCanWin());

  }

  @Test
  public void testICanWinHorizontally() {
      MyAgent redAgent = new MyAgent(game, true);
      MyAgent yellowAgent = new MyAgent(game, false);
      game.clearBoard();
      for (int i = 0; i < 3; i++) {
        redAgent.moveOnColumn(i);
        yellowAgent.moveOnColumn(i);
      }

      assertEquals(3, redAgent.iCanWin());
  }

  @Test
  public void testICanMake3() {
      MyAgent redAgent = new MyAgent(game, true);
      MyAgent yellowAgent = new MyAgent(game, false);
      game.clearBoard();
      for (int i = 0; i < 2; i++) {
        redAgent.moveOnColumn(i);
        yellowAgent.moveOnColumn(i);
      }

      assertEquals(2, redAgent.iCanMake3(new ArrayList<Integer>()));
  }

  @Test
  public void testTheyCanMake3() {
      MyAgent redAgent = new MyAgent(game, true);
      MyAgent yellowAgent = new MyAgent(game, false);
      game.clearBoard();
      for (int i = 0; i < 2; i++) {
        redAgent.moveOnColumn(i);
        yellowAgent.moveOnColumn(i);
      }

      assertEquals(2, yellowAgent.theyCanMake3(new ArrayList<Integer>()));
  }

  @Test
  public void testUseNegamax() {
      MyAgent redAgent = new MyAgent(game, true);
      MyAgent yellowAgent = new MyAgent(game, false);
      game.clearBoard();
      for (int i = 0; i < 3; i++) {
        redAgent.moveOnColumn(i);
        yellowAgent.moveOnColumn(i);
      }

      // The agent should recognize through a 5-depth negamax that 2 is the best column to move in.
      assertEquals(3, (int) redAgent.useNegamax(5).get("bestColumn"));
  }

  @Test
  public void testICanFindHorizontalLosingMoves() {
      MyAgent redAgent = new MyAgent(game, true);
      MyAgent yellowAgent = new MyAgent(game, false);
      game.clearBoard();
      for (int i = 0; i < 3; i++) {
        redAgent.moveOnColumn(i);
        yellowAgent.moveOnColumn(i);
      }

      assertEquals(3, (int) redAgent.findLosingMoves().get(0));
  }

  @Test
  public void testICanWinHorizontallyAlternate() {
      MyAgent redAgent = new MyAgent(game, true);
      MyAgent yellowAgent = new MyAgent(game, false);
      game.clearBoard();
      for (int i = 1; i < 4; i++) {
        redAgent.moveOnColumn(i);
        yellowAgent.moveOnColumn(i);
      }

      redAgent.moveOnColumn(4);

      assertEquals(4, yellowAgent.iCanWin());
    }

  @Test
  public void testTheyCanWinHorizontally() {
      MyAgent redAgent = new MyAgent(game, true);
      MyAgent yellowAgent = new MyAgent(game, false);
      game.clearBoard();
      for (int i = 0; i < 3; i++) {
        redAgent.moveOnColumn(i);
        yellowAgent.moveOnColumn(i);
      }

      assertEquals(3, yellowAgent.theyCanWin());
  }

  @Test
  public void testTheyCanWinHorizontallyAlternate() {
      MyAgent redAgent = new MyAgent(game, true);
      MyAgent yellowAgent = new MyAgent(game, false);
      game.clearBoard();
      for (int i = 1; i < 4; i++) {
        redAgent.moveOnColumn(i);
        yellowAgent.moveOnColumn(i);
      }

      redAgent.moveOnColumn(4);

      assertEquals(4, redAgent.theyCanWin());
    }

  @Test
  public void testICanWinDiagonally() {
      MyAgent redAgent = new MyAgent(game, true);
      MyAgent yellowAgent = new MyAgent(game, false);
      game.clearBoard();

      redAgent.moveOnColumn(1);
      yellowAgent.moveOnColumn(2);
      redAgent.moveOnColumn(2);
      yellowAgent.moveOnColumn(3);
      redAgent.moveOnColumn(3);
      yellowAgent.moveOnColumn(4);
      redAgent.moveOnColumn(3);
      yellowAgent.moveOnColumn(4);
      redAgent.moveOnColumn(4);
      yellowAgent.moveOnColumn(5);
      redAgent.moveOnColumn(5);

      assertEquals(4, redAgent.iCanWin());
  }

  @Test
  public void testICanWinDiagonallyBackwards() {
      MyAgent redAgent = new MyAgent(game, true);
      MyAgent yellowAgent = new MyAgent(game, false);
      game.clearBoard();

      yellowAgent.moveOnColumn(0);
      redAgent.moveOnColumn(0);
      yellowAgent.moveOnColumn(1);
      redAgent.moveOnColumn(0);
      yellowAgent.moveOnColumn(2);
      redAgent.moveOnColumn(0);
      yellowAgent.moveOnColumn(4);
      redAgent.moveOnColumn(2);
      yellowAgent.moveOnColumn(4);
      redAgent.moveOnColumn(1);
      yellowAgent.moveOnColumn(4);
      redAgent.moveOnColumn(1);
      yellowAgent.moveOnColumn(5);

      assertEquals(0, redAgent.iCanWin());
  }

  @Test
  public void testTheyCanWinDiagonally() {
      MyAgent redAgent = new MyAgent(game, true);
      MyAgent yellowAgent = new MyAgent(game, false);
      game.clearBoard();

      redAgent.moveOnColumn(1);
      yellowAgent.moveOnColumn(2);
      redAgent.moveOnColumn(2);
      yellowAgent.moveOnColumn(3);
      redAgent.moveOnColumn(3);
      yellowAgent.moveOnColumn(4);
      redAgent.moveOnColumn(3);
      yellowAgent.moveOnColumn(4);
      redAgent.moveOnColumn(4);
      yellowAgent.moveOnColumn(5);
      redAgent.moveOnColumn(5);

      assertEquals(4, yellowAgent.theyCanWin());
  }

  @Test
  public void testTheyCanWinDiagonallyBackwards() {
      MyAgent redAgent = new MyAgent(game, true);
      MyAgent yellowAgent = new MyAgent(game, false);
      game.clearBoard();

      yellowAgent.moveOnColumn(0);
      redAgent.moveOnColumn(0);
      yellowAgent.moveOnColumn(1);
      redAgent.moveOnColumn(0);
      yellowAgent.moveOnColumn(2);
      redAgent.moveOnColumn(0);
      yellowAgent.moveOnColumn(4);
      redAgent.moveOnColumn(2);
      yellowAgent.moveOnColumn(4);
      redAgent.moveOnColumn(1);
      yellowAgent.moveOnColumn(4);
      redAgent.moveOnColumn(1);
      yellowAgent.moveOnColumn(5);

      assertEquals(0, yellowAgent.theyCanWin());
  }

  @Test
  public void testTheyCanWin() {
    MyAgent redAgent = new MyAgent(game, true);
    MyAgent yellowAgent = new MyAgent(game, false);
    game.clearBoard();
    for (int i = 0; i < 3; i++) {
      redAgent.moveOnColumn(1);
      yellowAgent.moveOnColumn(2);
    }

    assertEquals(2, redAgent.theyCanWin());
  }

  // Tests you can win against a Beginner agent as Red
  @Test
  public void testRedWinningBeginnerAgent() {
    Agent redAgent = new MyAgent(game, true);
    Agent yellowAgent = new BeginnerAgent(game, false);
    int numberOfWins = 0;
    for (int i = 0; i < 50; i++) {
      game.clearBoard();
      while(!game.boardFull() && game.gameWon() == 'N') {
        redAgent.move();
        if (game.gameWon() != 'R') {
          yellowAgent.move();
        }
      }

      if (game.gameWon() == 'R') {
        numberOfWins++;
      }

      System.out.println("\nGames played: " + i + " / " + 50 + ".\n");
    }
    System.out.println("You won: " + numberOfWins + " games as Red against Beginner");
    // Test that you win over 90% of your games
    assertTrue(numberOfWins >= WIN_MINIMUM);
  }

  // Tests you can win against a Beginner agent as Yellow
  @Test
  public void testYellowWinningBeginnerAgent() {
    Agent redAgent = new BeginnerAgent(game, true);
    Agent yellowAgent = new MyAgent(game, false);
    int numberOfWins = 0;
    for (int i = 0; i < 50; i++) {
      game.clearBoard();
      while(!game.boardFull() && game.gameWon() == 'N') {
        redAgent.move();
        if (game.gameWon() != 'R') {
          yellowAgent.move();
        }
      }

      if (game.gameWon() == 'Y') {
        numberOfWins++;
      }

      System.out.println("\nGames played: " + i + " / " + 50 + ".\n");
    }
    System.out.println("You won: " + numberOfWins + " games as Yellow against Beginner");
    // Test that you win over 90% of your games
    assertTrue(numberOfWins >= WIN_MINIMUM);
  }

  // Tests you can win against a Random agent as Red
  @Test
  public void testRedWinningRandomAgent() {
    Agent redAgent = new MyAgent(game, true);
    Agent yellowAgent = new RandomAgent(game, false);
    int numberOfWins = 0;
    for (int i = 0; i < 50; i++) {
      game.clearBoard();
      while(!game.boardFull() && game.gameWon() == 'N') {
        redAgent.move();
        if (game.gameWon() != 'R') {
          yellowAgent.move();
        }
      }

      if (game.gameWon() == 'R') {
        numberOfWins++;
      }

      System.out.println("\nGames played: " + i + " / " + 50 + ".\n");
    }
    System.out.println("You won: " + numberOfWins + " games as Red against Random");
    // Test that you win over 90% of your games
    assertTrue(numberOfWins >= WIN_MINIMUM);
  }

  //Tests you can win against a Random agent as Red
  @Test
  public void testYellowWinningRandomAgent() {
    Agent redAgent = new RandomAgent(game, true);
    Agent yellowAgent = new MyAgent(game, false);
    int numberOfWins = 0;
    for (int i = 0; i < 50; i++) {
      game.clearBoard();
      while(!game.boardFull() && game.gameWon() == 'N') {
        redAgent.move();
        if (game.gameWon() != 'R') {
          yellowAgent.move();
        }
      }

      if (game.gameWon() == 'Y') {
        numberOfWins++;
      }

      System.out.println("\nGames played: " + i + " / " + 50 + ".\n");
    }
    System.out.println("You won: " + numberOfWins + " games as Yellow against Random");
    // Test that you win over 90% of your games
    assertTrue(numberOfWins >= WIN_MINIMUM);
  }

  // Tests you can win against a Intermediate agent as Red
  @Test
  public void testRedWinningIntermediateAgent() {
    Agent yellowAgent = new IntermediateAgent(game, false);
    Agent redAgent = new MyAgent(game, true);
    int numberOfWins = 0;
    for (int i = 0; i < 50; i++) {
      game.clearBoard();
      while(!game.boardFull() && game.gameWon() == 'N') {
        redAgent.move();
        if (game.gameWon() != 'R') {
          yellowAgent.move();
        }
      }

      if (game.gameWon() == 'R') {
        numberOfWins++;
      }

      System.out.println("\nGames played: " + i + " / " + 50 + ".\n");
    }
    System.out.println("You won: " + numberOfWins + " games as Red against Intermediate");
    // Test that you win over 90% of your games
    assertTrue(numberOfWins >= WIN_MINIMUM);
  }

  // Tests you can win against a Intermediate agent as Yellow
  @Test
  public void testYellowWinningIntermediateAgent() {
    Agent redAgent = new IntermediateAgent(game, true);
    Agent yellowAgent = new MyAgent(game, false);
    int numberOfWins = 0;
    for (int i = 0; i < 50; i++) {
      game.clearBoard();
      while(!game.boardFull() && game.gameWon() == 'N') {
        redAgent.move();
        if (game.gameWon() != 'R') {
          yellowAgent.move();
        }
      }

      if (game.gameWon() == 'Y') {
        numberOfWins++;
      }

      System.out.println("\nGames played: " + i + " / " + 50 + ".\n");
    }
    System.out.println("You won: " + numberOfWins + " games as Yellow against Intermediate");
    // Test that you win over 90% of your games
    assertTrue(numberOfWins >= WIN_MINIMUM);
  }

  // Tests you can win against a Advanced agent as Red
  @Test
  public void testRedWinningAdvancedAgent() {
    Agent yellowAgent = new AdvancedAgent(game, false);
    Agent redAgent = new MyAgent(game, true);
    int numberOfWins = 0;
    for (int i = 0; i < 50; i++) {
      game.clearBoard();
      while(!game.boardFull() && game.gameWon() == 'N') {
        redAgent.move();
        if (game.gameWon() != 'R') {
          yellowAgent.move();
        }
      }

      if (game.gameWon() == 'R') {
        numberOfWins++;
      }

      System.out.println("\nGames played: " + i + " / " + 50 + ".\n");
    }
    System.out.println("You won: " + numberOfWins + " games as Red against Advanced");
    // Test that you win over 90% of your games
    assertTrue(numberOfWins >= WIN_MINIMUM);
  }

  // Tests you can win against a Advanced agent as Yellow
  @Test
  public void testYellowWinningAdvancedAgent() {
    Agent redAgent = new AdvancedAgent(game, true);
    Agent yellowAgent = new MyAgent(game, false);
    int numberOfWins = 0;
    for (int i = 0; i < 50; i++) {
      game.clearBoard();
      while(!game.boardFull() && game.gameWon() == 'N') {
        redAgent.move();
        if (game.gameWon() != 'R') {
          yellowAgent.move();
        }
      }

      if (game.gameWon() == 'Y') {
        numberOfWins++;
      }

      System.out.println("\nGames played: " + i + " / " + 50 + ".\n");
    }
    System.out.println("You won: " + numberOfWins + " games as Yellow against Advanced");
    // Test that you win over 90% of your games
    assertTrue(numberOfWins >= WIN_MINIMUM);
  }

  // Tests you can win against a Brilliant agent as Red
  @Test
  public void testRedWinningBrilliantAgent() {
    Agent yellowAgent = new BrilliantAgent(game, false);
    Agent redAgent = new MyAgent(game, true);
    int numberOfWins = 0;
    for (int i = 0; i < 50; i++) {
      game.clearBoard();
      while(!game.boardFull() && game.gameWon() == 'N') {
        redAgent.move();
        if (game.gameWon() != 'R') {
          yellowAgent.move();
        }
      }

      if (game.gameWon() == 'R') {
        numberOfWins++;
      }

      System.out.println("\nGames played: " + i + " / " + 50 + ".\n");
    }
    System.out.println("You won: " + numberOfWins + " games as Red against Brilliant");
    // Test that you win over 90% of your games
    assertTrue(numberOfWins >= WIN_MINIMUM);
  }

  // Tests you can win against a Brilliant agent as Yellow
  @Test
  public void testYellowWinningBrilliantAgent() {
    Agent redAgent = new BrilliantAgent(game, true);
    Agent yellowAgent = new MyAgent(game, false);
    int numberOfWins = 0;
    for (int i = 0; i < 50; i++) {
      game.clearBoard();
      while(!game.boardFull() && game.gameWon() == 'N') {
        redAgent.move();
        if (game.gameWon() != 'R') {
          yellowAgent.move();
        }
      }

      if (game.gameWon() == 'Y') {
        numberOfWins++;
      }

      System.out.println("\nGames played: " + i + " / " + 50 + ".\n");
    }
    System.out.println("You won: " + numberOfWins + " games as Yellow against Brilliant");
    // Test that you win over 90% of your games
    assertTrue(numberOfWins >= WIN_MINIMUM);
  }

}
