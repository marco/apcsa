import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Describe your basic strategy here.
 * @author skunkmb
 *
 */
public class MyAgent extends Agent {
  /**
   * A random number generator to randomly decide where to place a token.
   */
  private Random random;

  /**
   * Constructs a new agent, giving it the game and telling it whether it is Red or Yellow.
   *
   * @param game The game the agent will be playing.
   * @param iAmRed True if the agent is Red, False if the agent is Yellow.
   */
  public MyAgent(Connect4Game game, boolean iAmRed) {
    super(game, iAmRed);
    random = new Random();
  }

  /**
   * The move method is run every time it is this agent's turn in the game. You may assume that
   * when move() is called, the game has at least one open slot for a token, and the game has not
   * already been won.
   *
   * <p>By the end of the move method, the agent should have placed one token into the game at some
   * point.</p>
   *
   * <p>After the move() method is called, the game engine will check to make sure the move was
   * valid. A move might be invalid if:
   * - No token was place into the game.
   * - More than one token was placed into the game.
   * - A previous token was removed from the game.
   * - The color of a previous token was changed.
   * - There are empty spaces below where the token was placed.</p>
   *
   * <p>If an invalid move is made, the game engine will announce it and the game will be ended.</p>
   *
   * <p>In order to move, `MyAgent` follows the following hierarchy of options:
   *  - Check if it can win (has 3 in a row already).
   *  - Check if they can win (they have 3 in a row to stop).
   *  - Use a negamax function to check six moves in advance for the best move.
   *  - Find any losing moves that allow the other bot to win after we move (e.g. placing underneath a final
   *    slot for a diagonal) and avoid those for all following options.
   *  - Make a diagonal 3-in-a-row.
   *  - Make a horizontal 3-in-a-row.
   *  - Make a vertical 3-in-a-row.
   *  - Stop them from making a horizontal 3-in-a-row.
   *  - Stop them from making a diagonal 3-in-a-row.
   *  - Stop them from making a vertical 3-in-a-row.
   *  - Make a random move, if all else fails.
   * </p>
   */
  @Override
  public void move() {
    // Handle initial outputting.

    if (filledCount(myGame) == 0) {
        System.out.println("\n\nNew game, playing first.\n");
    }

    if (filledCount(myGame) == 1) {
        System.out.println("\n\nNew game, playing second.\n");
    }

    // Check if I can win.

    int myWinningSlot = iCanWin();

    if (myWinningSlot != -1) {
        System.out.println("We are playing a winning move.");
        moveOnColumn(myWinningSlot);
        return;
    }

    // Check if they can win.

    int theirWinningSlot = theyCanWin();

    if (theirWinningSlot != -1) {
        System.out.println("We are preventing their winning move.");
        moveOnColumn(theirWinningSlot);
        return;
    }

    // Check if there is a negamax move and find any "worst moves."

    Map<String, Object> negamaxResult = useNegamax(6);
    int negamaxMove = (int) negamaxResult.get("bestColumn");

    if (negamaxMove != -1) {
        System.out.println("We are playing a negamax move.");
        moveOnColumn(negamaxMove);
        return;
    }

    List<Integer> worstMoves = findWorstMovesNow(negamaxResult);
    String worstMovesString = Arrays.toString(worstMoves.toArray());

    // Check if I can make a 3-in-a-row.

    int my3Slot = iCanMake3(worstMoves);

    if (my3Slot != -1) {
        System.out.println("We are making a 3-in-a-row. Worst moves: " + worstMovesString);
        moveOnColumn(my3Slot);
        return;
    }

    // Check if they can make a 3-in-a-row.

    int their3Slot = theyCanMake3(worstMoves);

    if (their3Slot != -1) {
        System.out.println("We are preventing their 3-in-a-row. Worst moves: " + worstMovesString);
        moveOnColumn(their3Slot);
        return;
    }

    // Make a loosely "best" move, if all else fails.

    System.out.println("We are playing the \"best\" non-losing move. Worst moves: " + worstMovesString);

    moveOnColumn(findBestMoveNow(worstMoves));
  }

  /**
   * Drops a token into a particular column so that it will fall to the bottom of the column.
   * If the column is already full, nothing will change.
   *
   * @param columnNumber The column into which to drop the token.
   */
  public void moveOnColumn(int columnNumber) {
    // Find the top empty slot in the column
    // If the column is full, lowestEmptySlot will be -1
    int lowestEmptySlotIndex = getLowestEmptyIndex(myGame.getColumn(columnNumber));
    // if the column is not full
    if (lowestEmptySlotIndex > -1) {
      // get the slot in this column at this index
      Connect4Slot lowestEmptySlot = myGame.getColumn(columnNumber).getSlot(lowestEmptySlotIndex);
      // If the current agent is the Red player...
      if (iAmRed) {
        lowestEmptySlot.addRed(); // Place a red token into the empty slot
      } else {
        lowestEmptySlot.addYellow(); // Place a yellow token into the empty slot
      }
    }
  }

  /**
   * Adds a token to the bottom open slot of a column for a game.
   *
   * @param game The game to move in.
   * @param columnNumber The column index to move in.
   * @param colorIsRed Whether or not the color to move as is red.
   */
  public void moveGameOnColumn(Connect4Game game, int columnNumber, boolean colorIsRed) {
    // Find the top empty slot in the column
    // If the column is full, lowestEmptySlot will be -1
    int lowestEmptySlotIndex = getLowestEmptyIndex(game.getColumn(columnNumber));
    // if the column is not full
    if (lowestEmptySlotIndex > -1) {
      // get the slot in this column at this index
      Connect4Slot lowestEmptySlot = game.getColumn(columnNumber).getSlot(lowestEmptySlotIndex);
      if (colorIsRed) {
        lowestEmptySlot.addRed(); // Place a red token into the empty slot
      } else {
        lowestEmptySlot.addYellow(); // Place a yellow token into the empty slot
      }
    }
  }

  /**
   * Returns the index of the top empty slot in a particular column.
   *
   * @param column The column to check.
   * @return
   *      the index of the top empty slot in a particular column;
   *      -1 if the column is already full.
   */
  public int getLowestEmptyIndex(Connect4Column column) {
    int lowestEmptySlot = -1;
    for  (int i = 0; i < column.getRowCount(); i++) {
      if (!column.getSlot(i).getIsFilled()) {
        lowestEmptySlot = i;
      }
    }
    return lowestEmptySlot;
  }

  /**
   * Returns a random valid move. If your agent doesn't know what to do, making a random move
   * can allow the game to go on anyway.
   *
   * @return a random valid move.
   */
  public int randomMove() {
    int i = random.nextInt(myGame.getColumnCount());
    while (getLowestEmptyIndex(myGame.getColumn(i)) == -1) {
      i = random.nextInt(myGame.getColumnCount());
    }
    return i;
  }

  /**
   * Checks if a color can win a certain game with a vertical 4-in-a-row.
   *
   * @param game The game to check.
   * @param colorIsRed Whether or not the color of the player to check is red.
   * @return The column that the player can win in, or -1 if there is no winning column.
   */
  private int colorCanWinVertical(Connect4Game game, boolean colorIsRed) {
      // Check if there is a vertical victory possible. Loop through rows except for the last three. This
      // index will represent the bottom-most slot in the vertical row, so it has to start not in the last
      // three.
      for (int r = 3; r < game.getRowCount(); r++) {
          for (int c = 0; c < game.getColumnCount(); c++) {
              if (
                  checkSlotIsColor(game, c, r, colorIsRed)
                      && checkSlotIsColor(game, c, r - 1, colorIsRed)
                      && checkSlotIsColor(game, c, r - 2, colorIsRed)
              ) {
                  if (checkSlotIsPlayable(game, c, r - 3)) {
                      return c;
                  }

                  if (r < game.getRowCount() - 1 && checkSlotIsPlayable(game, c, r + 1)) {
                      return c;
                  }
              }
          }
      }

      // Check if there is a vertical victory possible with a gap 2nd.
      for (int r = 3; r < game.getRowCount(); r++) {
          for (int c = 0; c < game.getColumnCount(); c++) {
              if (
                  checkSlotIsColor(game, c, r, colorIsRed)
                      && checkSlotIsColor(game, c, r - 2, colorIsRed)
                      && checkSlotIsColor(game, c, r - 3, colorIsRed)
              ) {
                  if (checkSlotIsPlayable(game, c, r - 1)) {
                      return c;
                  }
              }
          }
      }

      // Check if there is a vertical victory possible with a gap 3rd.
      for (int r = 3; r < game.getRowCount(); r++) {
          for (int c = 0; c < game.getColumnCount(); c++) {
              if (
                  checkSlotIsColor(game, c, r, colorIsRed)
                      && checkSlotIsColor(game, c, r - 1, colorIsRed)
                      && checkSlotIsColor(game, c, r - 3, colorIsRed)
              ) {
                  if (checkSlotIsPlayable(game, c, r - 2)) {
                      return c;
                  }
              }
          }
      }

      return -1;
  }

  /**
   * Checks if a color can win a certain game with a horizontal 4-in-a-row.
   *
   * @param game The game to check.
   * @param colorIsRed Whether or not the color of the player to check is red.
   * @return The column that the player can win in, or -1 if there is no winning column.
   */
  private int colorCanWinHorizontal(Connect4Game game, boolean colorIsRed) {
      // Check if there is a horizontal victory possible. Loop through columns except for the last three.
      // This index will represent the left-most slot in the horizontal row, so it has to start not in the
      // last three.
      for (int c = 0; c < game.getColumnCount() - 3; c++) {
          for (int r = 0; r < game.getRowCount(); r++) {
              if (
                  checkSlotIsColor(game, c, r, colorIsRed)
                      && checkSlotIsColor(game, c + 1, r, colorIsRed)
                      && checkSlotIsColor(game, c + 2, r, colorIsRed)
              ) {
                  if (checkSlotIsPlayable(game, c + 3, r)) {
                      return c + 3;
                  }

                  if (0 < c && checkSlotIsPlayable(game, c - 1, r)) {
                      return c - 1;
                  }
              }
          }
      }

      // Check if there is a horizontal victory possible with a gap 2nd.
      for (int c = 0; c < game.getColumnCount() - 3; c++) {
          for (int r = 0; r < game.getRowCount(); r++) {
              if (
                  checkSlotIsColor(game, c, r, colorIsRed)
                      && checkSlotIsColor(game, c + 2, r, colorIsRed)
                      && checkSlotIsColor(game, c + 3, r, colorIsRed)
              ) {
                  if (checkSlotIsPlayable(game, c + 1, r)) {
                      return c + 1;
                  }
              }
          }
      }

      // Check if there is a horizontal victory possible with a gap 3rd.
      for (int c = 0; c < game.getColumnCount() - 3; c++) {
          for (int r = 0; r < game.getRowCount(); r++) {
              if (
                  checkSlotIsColor(game, c, r, colorIsRed)
                      && checkSlotIsColor(game, c + 1, r, colorIsRed)
                      && checkSlotIsColor(game, c + 3, r, colorIsRed)
              ) {
                  if (checkSlotIsPlayable(game, c + 2, r)) {
                      return c + 2;
                  }
              }
          }
      }

      return -1;
  }

  /**
   * Checks if a color can win a certain game with a diagonal upwards 4-in-a-row (from bottom-left to
   * top-right).
   *
   * @param game The game to check.
   * @param colorIsRed Whether or not the color of the player to check is red.
   * @return The column that the player can win in, or -1 if there is no winning column.
   */
  private int colorCanWinDiagonalUpwards(Connect4Game game, boolean colorIsRed) {
      // Check if there is an upwards diagonal victory possible. Loop through columns and rows except for the
      // last three. These indexes will represent the left-bottom-most slot in the diagonal, so it has to
      // start not in the last three.
      for (int c = 0; c < game.getColumnCount() - 3; c++) {
          for (int r = 3; r < game.getRowCount(); r++) {
              if (
                  checkSlotIsColor(game, c, r, colorIsRed)
                      && checkSlotIsColor(game, c + 1, r - 1, colorIsRed)
                      && checkSlotIsColor(game, c + 2, r - 2, colorIsRed)
              ) {
                  if (checkSlotIsPlayable(game, c + 3, r - 3)) {
                      return c + 3;
                  }

                  if (0 < c && r < game.getRowCount() - 1 && checkSlotIsPlayable(game, c - 1, r + 1)) {
                      return c - 1;
                  }
              }
          }
      }

      // Check if there is an upwards diagonal with a gap 2nd.
      for (int c = 0; c < game.getColumnCount() - 3; c++) {
          for (int r = 3; r < game.getRowCount(); r++) {
              if (
                  checkSlotIsColor(game, c, r, colorIsRed)
                      && checkSlotIsColor(game, c + 2, r - 2, colorIsRed)
                      && checkSlotIsColor(game, c + 3, r - 3, colorIsRed)
              ) {
                  if (checkSlotIsPlayable(game, c + 1, r - 1)) {
                      return c + 1;
                  }
              }
          }
      }

      // Check if there is an upwards diagonal with a gap 3rd.
      for (int c = 0; c < game.getColumnCount() - 3; c++) {
          for (int r = 3; r < game.getRowCount(); r++) {
              if (
                  checkSlotIsColor(game, c, r, colorIsRed)
                      && checkSlotIsColor(game, c + 1, r - 1, colorIsRed)
                      && checkSlotIsColor(game, c + 3, r - 3, colorIsRed)
              ) {
                  if (checkSlotIsPlayable(game, c + 2, r - 2)) {
                      return c + 2;
                  }
              }
          }
      }

      return -1;
  }

  /**
   * Checks if a color can win a certain game with a diagonal downwards 4-in-a-row (from bottom-right to
   * top-left).
   *
   * @param game The game to check.
   * @param colorIsRed Whether or not the color of the player to check is red.
   * @return The column that the player can win in, or -1 if there is no winning column.
   */
  private int colorCanWinDiagonalDownwards(Connect4Game game, boolean colorIsRed) {
      // Check if there is an downwards diagonal victory possible. Loop through columns and rows except for
      // the last and first three respectively. These indexes will represent the left-top-most slot in the
      // diagonal, so it has to start not in the top right.
      for (int c = 0; c < game.getColumnCount() - 3; c++) {
          for (int r = 0; r < game.getRowCount() - 3; r++) {
              if (
                  checkSlotIsColor(game, c, r, colorIsRed)
                      && checkSlotIsColor(game, c + 1, r + 1, colorIsRed)
                      && checkSlotIsColor(game, c + 2, r + 2, colorIsRed)
              ) {
                  if (checkSlotIsPlayable(game, c + 3, r + 3)) {
                      return c + 3;
                  }

                  if (0 < c && 0 < r && checkSlotIsPlayable(game, c - 1, r - 1)) {
                      return c - 1;
                  }
              }
          }
      }

      // Check if there is a downwards diagonal with a gap 2nd.
      for (int c = 0; c < game.getColumnCount() - 3; c++) {
          for (int r = 0; r < game.getRowCount() - 3; r++) {
              if (
                  checkSlotIsColor(game, c, r, colorIsRed)
                      && checkSlotIsColor(game, c + 2, r + 2, colorIsRed)
                      && checkSlotIsColor(game, c + 3, r + 3, colorIsRed)
              ) {
                  if (checkSlotIsPlayable(game, c + 1, r + 1)) {
                      return c + 1;
                  }
              }
          }
      }

      // Check if there is a downwards diagonal with a gap 3rd.
      for (int c = 0; c < game.getColumnCount() - 3; c++) {
          for (int r = 0; r < game.getRowCount() - 3; r++) {
              if (
                  checkSlotIsColor(game, c, r, colorIsRed)
                      && checkSlotIsColor(game, c + 1, r + 1, colorIsRed)
                      && checkSlotIsColor(game, c + 3, r + 3, colorIsRed)
              ) {
                  if (checkSlotIsPlayable(game, c + 2, r + 2)) {
                      return c + 2;
                  }
              }
          }
      }

      return -1;
  }

  /**
   * Checks if a color can win a certain game with a diagonal 4-in-a-row.
   *
   * @param game The game to check.
   * @param colorIsRed Whether or not the color of the player to check is red.
   * @return The column that the player can win in, or -1 if there is no winning column.
   */
  private int colorCanWinDiagonal(Connect4Game game, boolean colorIsRed) {
      int upwardsWinningSlot = colorCanWinDiagonalUpwards(game, colorIsRed);

      if (upwardsWinningSlot != -1) {
          return upwardsWinningSlot;
      }

      int downwardsWinningSlot = colorCanWinDiagonalDownwards(game, colorIsRed);

      if (downwardsWinningSlot != -1) {
          return downwardsWinningSlot;
      }

      return -1;
  }

  /**
   * Checks if a color can win a certain game, based on whether they can create a vertical, horizontal, or
   * diagonal 4-in-a-row (in that order).
   *
   * @param game The game to check.
   * @param colorIsRed Whether or not the color of the player to check is red.
   * @return The column that the player can win in, or -1 if there is no winning column.
   */
  private int colorCanWin(Connect4Game game, boolean colorIsRed) {
      int verticalWin = colorCanWinVertical(game, colorIsRed);

      if (verticalWin != -1) {
          return verticalWin;
      }

      int horizontalWin = colorCanWinHorizontal(game, colorIsRed);

      if (horizontalWin != -1) {
          return horizontalWin;
      }

      int diagonalWin = colorCanWinDiagonal(game, colorIsRed);

      if (diagonalWin != -1) {
          return diagonalWin;
      }

      return -1;
  }

  /**
   * Checks if a color can make a horizontal 3-in-a-row without a loss.
   *
   * @param game The game to check
   * @param colorIsRed Whether or not the color of the player to check is red
   * @param losingMoves A list of assumed losing moves, that cannot be played regardless of a possible
   *                    3-in-a-row.
   * @return The column that the player can make the 3-in-a-row in, or -1 if there is no possible column.
   */
  private int colorCanMakeHorizontal3WithoutLoss(
      Connect4Game game,
      boolean colorIsRed,
      List<Integer> losingMoves
  ) {
      for (int c = 0; c < game.getColumnCount() - 2; c++) {
          for (int r = 0; r < game.getRowCount(); r++) {
              if (
                  checkSlotIsColor(game, c, r, colorIsRed)
                      && checkSlotIsColor(game, c + 1, r, colorIsRed)
              ) {
                  if (checkSlotIsPlayable(game, c + 2, r) && !losingMoves.contains(c + 2)) {
                      return c + 2;
                  }

                  if (0 < c && checkSlotIsPlayable(game, c - 1, r) && !losingMoves.contains(c - 1)) {
                      return c - 1;
                  }
              }
          }
      }

      return -1;
  }

  /**
   * Checks if a color can make a vertical 3-in-a-row without a loss.
   *
   * @param game The game to check
   * @param colorIsRed Whether or not the color of the player to check is red
   * @param losingMoves A list of assumed losing moves, that cannot be played regardless of a possible
   *                    3-in-a-row.
   * @return The column that the player can make the 3-in-a-row in, or -1 if there is no possible column.
   */
  private int colorCanMakeVertical3WithoutLoss(
      Connect4Game game,
      boolean colorIsRed,
      List<Integer> losingMoves
  ) {
      for (int r = 2; r < game.getRowCount(); r++) {
          for (int c = 0; c < game.getColumnCount(); c++) {
              if (
                  checkSlotIsColor(game, c, r, colorIsRed)
                      && checkSlotIsColor(game, c, r - 1, colorIsRed)
              ) {

                  if (checkSlotIsPlayable(game, c, r - 2) && !losingMoves.contains(c)) {
                      return c;
                  }

                  if (
                      r < game.getRowCount() - 1
                          && checkSlotIsPlayable(game, c, r + 1)
                          && !losingMoves.contains(c)
                  ) {
                      return c;
                  }
              }
          }
      }

      return -1;
  }

  /**
   * Checks if a color can make a diagonal 3-in-a-row without a loss.
   *
   * @param game The game to check
   * @param colorIsRed Whether or not the color of the player to check is red
   * @param losingMoves A list of assumed losing moves, that cannot be played regardless of a possible
   *                    3-in-a-row.
   * @return The column that the player can make the 3-in-a-row in, or -1 if there is no possible column.
   */
  private int colorCanMakeDiagonal3WithoutLoss(
      Connect4Game game,
      boolean colorIsRed,
      List<Integer> losingMoves
  ) {
      for (int c = 0; c < game.getColumnCount() - 2; c++) {
          for (int r = 2; r < game.getRowCount(); r++) {
              if (
                  checkSlotIsColor(game, c, r, colorIsRed)
                      && checkSlotIsColor(game, c + 1, r - 1, colorIsRed)
              ) {
                  if (checkSlotIsPlayable(game, c + 2, r - 2) && !losingMoves.contains(c + 2)) {
                      return c + 2;
                  }

                  if (
                      0 < c && r < game.getRowCount() - 1
                          && checkSlotIsPlayable(game, c - 1, r + 1)
                          && !losingMoves.contains(c - 1)
                  ) {
                      return c - 1;
                  }
              }
          }
      }

      for (int c = 0; c < game.getColumnCount() - 2; c++) {
          for (int r = 0; r < game.getRowCount() - 2; r++) {
              if (
                  checkSlotIsColor(game, c, r, colorIsRed)
                      && checkSlotIsColor(game, c + 1, r + 1, colorIsRed)
              ) {
                  if (checkSlotIsPlayable(game, c + 2, r + 2) && !losingMoves.contains(c + 2)) {
                      return c + 2;
                  }

                  if (
                      0 < c && 0 < r
                          && checkSlotIsPlayable(game, c - 1, r - 1)
                          && !losingMoves.contains(c - 1)
                  ) {
                      return c - 1;
                  }
              }
          }
      }

      return -1;
  }

  /**
   * Finds current losing moves.
   *
   * @return A list of moves that, if taken by the agent, would allow the opponent to win.
   */
  public List<Integer> findLosingMoves() {
      List<Integer> losingMoves = new ArrayList<Integer>();

      for (int i = 0; i < myGame.getColumnCount(); i++) {
          Connect4Game testGame = new Connect4Game(myGame);
          moveGameOnColumn(testGame, i, iAmRed);

          if (colorCanWin(testGame, !iAmRed) != -1) {
              losingMoves.add(i);
          }
      }

      return losingMoves;
  }

  /**
   * Finds the number of filled slots in a game.
   *
   * @param game The game to check.
   * @return The number of filled slots.
   */
  private int filledCount(Connect4Game game) {
      int filledCount = 0;

      for (int i = 0; i < game.getColumnCount(); i++) {
          for (int j = 0; j < game.getRowCount(); j++) {
              if (game.getColumn(i).getSlot(j).getIsFilled()) {
                  filledCount++;
              }
          }
      }

      return filledCount;
  }

  /**
   * Calculates a score based on a game's current position for a player, to be used for a negamax algorithm.
   * Positive results are more favorable and negative ones favor the other player.
   *
   * @param game The game to analyze.
   * @param colorIsRed Whether or not to color to find a score for is red.
   * @param weight How much this position should be weighted.
   * @return The score.
   */
  private int findNegamaxScoreAtEndPosition(Connect4Game game, boolean colorIsRed, int weight) {
      int maxScore = game.getColumnCount();

      if (game.gameWon() == 'R') {
          if (!colorIsRed) {
              return -maxScore * weight;
          }

          return maxScore * weight;
      }

      if (game.gameWon() == 'Y') {
          if (!colorIsRed) {
              return maxScore * weight;
          }

          return -maxScore * weight;
      }

      return 0;
  }

  /**
   * Uses a negamax algorithm to find a score for a game's current position and future possible positions.
   * Positive results are more favorable and negative ones favor the other player.
   *
   * @param game The root game to analyze.
   * @param roundsAmount The number of future possible moves to analyze. Higher values lead to higher
   *                     accuracy and slower times.
   * @param colorIsRed Whether or not the color to find a score for is red.
   * @return The score.
   */
  private int loopNegamax(Connect4Game game, int roundsAmount, boolean colorIsRed) {
      if (roundsAmount == 0 || game.boardFull() || game.gameWon() != 'N') {
          if (game.boardFull()) {
              return 0;
          }

          return findNegamaxScoreAtEndPosition(game, colorIsRed, 1);
      }

      Integer value = null;

      for (int i = 0; i < game.getColumnCount(); i++) {
          Connect4Game innerGame = new Connect4Game(game);

          if (!checkIfAnySlotAvailable(innerGame, i)) {
              continue;
          }

          moveGameOnColumn(innerGame, i, colorIsRed);
          int newNegamax = -loopNegamax(innerGame, roundsAmount - 1, !colorIsRed);

          if (value == null || value < newNegamax) {
              value = newNegamax;
          }
      }

      return value;
  }

  /**
   * Uses a negamax algorithm to find the best column to move in for this agent's game.
   *
   * @param roundsAmount The number of future possible moves to analyze. Higher values lead to higher
   *                     accuracy and slower times.
   * @return A map containing `bestColumn` and `negativeColumns`. `bestColumn` is the ideal column to play,
   *         or -1 if there is no good column. `negativeColumns` are columns with negative scores that should
   *         not be played.
   */
  public Map<String, Object> useNegamax(int roundsAmount) {
      int bestColumn = -1;
      int bestValue = 0;
      List<Integer> negativeColumns = new ArrayList<Integer>();

      for (int i = 0; i < myGame.getColumnCount(); i++) {
          Connect4Game innerGame = new Connect4Game(myGame);

          if (!checkIfAnySlotAvailable(innerGame, i)) {
              continue;
          }

          moveGameOnColumn(innerGame, i, iAmRed);

          int newNegamax = -loopNegamax(innerGame, roundsAmount, !iAmRed);

          if (bestValue < newNegamax) {
              bestValue = newNegamax;
              bestColumn = i;
          }

          if (newNegamax < 0) {
              negativeColumns.add(i);
          }
      }

      Map<String, Object> result = new HashMap<String, Object>();
      result.put("bestColumn", bestColumn);
      result.put("negativeColumns", negativeColumns);

      return result;
  }

  /**
   * Creates a list combining the negamax result for the "negative columns" along with moves that immediately
   * allow the opponent to win, in order to have a list of "worst moves" that should not be made.
   *
   * @param negamaxResult A negamax result map from `useNegamax`.
   * @return
   */
  private List<Integer> findWorstMovesNow(Map<String, Object> negamaxResult) {
      // Combine the negative negamax columns with the auto-losing moves to find all unsatisfactory options.
      List<Integer> negativeColumns = (List<Integer>) negamaxResult.get("negativeColumns");
      List<Integer> losingMoves = findLosingMoves();
      List<Integer> worstMoves = new ArrayList<Integer>();
      worstMoves.addAll(negativeColumns);
      worstMoves.addAll(losingMoves);
      return worstMoves;
  }

  /**
   * Finds a move to play if all other checks fail, based on a move's proximity to the bottom of the board
   * and the center column. If all else fails, generates a random move.
   *
   * @param worstMoves A list of assumed losing moves, that cannot be played by this agent regardless.
   * @return The best move found.
   */
  private int findBestMoveNow(List<Integer> worstMoves) {
      int middleColumn = (int) Math.ceil(myGame.getColumnCount() / 2);

      // Loop the distance away from the middle column. Return the column closest to the middle where a
      // bottom slot is available and isn't a losing move, if there is one.
      for (int i = 0; i <= middleColumn; i++) {
          if (!worstMoves.contains(middleColumn - i)) {
              if (checkIfBottomSlotAvailable(myGame, middleColumn - i)) {
                  return middleColumn - i;
              }
          }

          if (!worstMoves.contains(middleColumn + i)) {
              if (checkIfBottomSlotAvailable(myGame, middleColumn + i)) {
                  return middleColumn + i;
              }
          }
      }

      // If bottom slots aren't open, do any slot closest to the middle that isn't a losing move.
      for (int i = 0; i <= middleColumn; i++) {
          if (!worstMoves.contains(middleColumn - i)) {
              if (checkIfAnySlotAvailable(myGame, middleColumn - i)) {
                  return middleColumn - i;
              }
          }

          if (!worstMoves.contains(middleColumn + i)) {
              if (checkIfAnySlotAvailable(myGame, middleColumn + i)) {
                  return middleColumn + i;
              }
          }
      }

      // If all else fails, move randomly.
      return randomMove();
  }

  /**
   * Checks if the bottom slot of a game's column is untaken.
   *
   * @param game The game to check.
   * @param column The column index to check.
   * @return Whether or not it is untaken.
   */
  private boolean checkIfBottomSlotAvailable(Connect4Game game, int column) {
      return getLowestEmptyIndex(game.getColumn(column)) == game.getRowCount() - 1;
  }

  /**
   * Checks if any slot of a game's column is untaken.
   *
   * @param game THe game to check.
   * @param column The column index to check.
   * @return Whether or not it is untaken.
   */
  private boolean checkIfAnySlotAvailable(Connect4Game game, int column) {
      return getLowestEmptyIndex(game.getColumn(column)) != -1;
  }

  /**
   * Checks if a game's slot is untaken and playable.
   *
   * @param game The game to check.
   * @param column The column index to check.
   * @param slot The slot index to check.
   * @return Whether or not it is untaken and playable.
   */
  private boolean checkSlotIsPlayable(Connect4Game game, int column, int slot) {
      return getLowestEmptyIndex(game.getColumn(column)) == slot;
  }

  /**
   * Checks if a game's slot is taken by a specific color.
   *
   * @param game The game to check.
   * @param column The column index to check.
   * @param slot The slot index to check.
   * @param colorIsRed Whether or not the color to check is red.
   * @return Whether or not the color of the spot is the specified color.
   */
  private boolean checkSlotIsColor(Connect4Game game, int column, int slot, boolean colorIsRed) {
      return (
          game.getColumn(column).getSlot(slot).getIsFilled()
              && game.getColumn(column).getSlot(slot).getIsRed() == colorIsRed
      );
  }

  /**
   * Returns the column that would allow the agent to win.
   *
   * <p>You might want your agent to check to see if it has a winning move available to it so that
   * it can go ahead and make that move. Implement this method to return what column would
   * allow the agent to win.</p>
   *
   * @return the column that would allow the agent to win.
   */
  public int iCanWin() {
    return colorCanWin(myGame, iAmRed);
  }

  /**
   * Returns the column that would allow the opponent to win.
   *
   * <p>You might want your agent to check to see if the opponent would have any winning moves
   * available so your agent can block them. Implement this method to return what column should
   * be blocked to prevent the opponent from winning.</p>
   *
   * @return the column that would allow the opponent to win.
   */
  public int theyCanWin() {
      return colorCanWin(myGame, !iAmRed);
  }

  /**
   * Checks if this agent can make a 3-in-a-row diagonally, horizontally, or vertically.
   *
   * @param worstMoves A list of assumed losing moves, that cannot be played by this agent regardless of a
   *                   possible 3-in-a-row.
   * @return The column that would lead to the 3-in-a-row (implying a location for this agent to move), or
   *         -1 if there is no possible column.
   */
  public int iCanMake3(List<Integer> worstMoves) {
      int myDiagonal3Slot = colorCanMakeDiagonal3WithoutLoss(myGame, iAmRed, worstMoves);

      if (myDiagonal3Slot != -1) {
          return myDiagonal3Slot;
      }

      int ourHorizontal3Slot = colorCanMakeHorizontal3WithoutLoss(myGame, iAmRed, worstMoves);

      if (ourHorizontal3Slot != -1) {
          return ourHorizontal3Slot;
      }

      int ourVertical3Slot = colorCanMakeVertical3WithoutLoss(myGame, iAmRed, worstMoves);

      if (ourVertical3Slot != -1) {
          return ourVertical3Slot;
      }

      return -1;
  }

  /**
   * Checks if this agent's opponent can make a 3-in-a-row horizontally, diagonally, or vertically.
   *
   * @param worstMoves A list of assumed losing moves, that cannot be played by this agent regardless of a
   *                   possible 3-in-a-row by the opponent.
   * @return The column that would lead to the opponent's 3-in-a-row (implying a location for this agent to
   *         move), or -1 if there is no possible column.
   */
  public int theyCanMake3(List<Integer> worstMoves) {
      int theirHorizontal3Slot = colorCanMakeHorizontal3WithoutLoss(myGame, !iAmRed, worstMoves);

      if (theirHorizontal3Slot != -1) {
          return theirHorizontal3Slot;
      }

      int theirDiagonal3Slot = colorCanMakeDiagonal3WithoutLoss(myGame, !iAmRed, worstMoves);

      if (theirDiagonal3Slot != -1) {
          return theirDiagonal3Slot;
      }

      int theirVertical3Slot = colorCanMakeVertical3WithoutLoss(myGame, !iAmRed, worstMoves);

      if (theirVertical3Slot != -1) {
          return theirVertical3Slot;
      }

      return -1;
  }

  /**
   * Returns the name of this agent.
   *
   * @return the agent's name
   */
  @Override
  public String getName() {
    return "H&M's Agent";
  }
}
