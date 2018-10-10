import java.awt.event.KeyEvent;

/**
 * Provides the base model implementation for the Area class.
 * Represents a Window constructed out of 64 x 64 tiles.
 * @author jddevaughnbrown
 *
 */
@SuppressWarnings("serial")
public class OurArea extends Area {

    /*
     * Variables from the parent class we have access to

  // The trees that are scattered around the area.
  Tree[] trees;

  // The area tile map, with each tile represented as an integer.
  int[][] tiles;

  // The tile values for grass (0) and stone (1) tiles.
  int GRASS;
  int STONE;

  // The number of tiles on the x-axis, and y-axis.
  int NUM_TILES_X;
  int NUM_TILES_Y;

  // The maximum position of a tree on the x-axis and y-axis.
  // Note: The minimum is simply (0, 0).
  double MAX_TREE_X;
  double MAX_TREE_Y;
     */

    /**
     * The number of fires to show in the area.
     */
    private int numberOfFires;

    /**
     * The number of trees to show in the area.
     */
    private int numberOfTrees;

    /**
     * The number of water tiles to show in the area.
     */
    private int numberOfWaters;

    /**
     * Constructs the OurArea.
     *
     * @param numberOfTrees The number of trees to place in the area.
     * @param numberOfFires The number of fires to place in the area.
     * @param numberOfWaters The number of water tiles to place in the area.
     */
    public OurArea(int numberOfTrees, int numberOfFires, int numberOfWaters) {
        super();

        this.numberOfFires = numberOfFires;
        this.numberOfTrees = numberOfTrees;
        this.numberOfWaters = numberOfWaters;

        createTiles();
        createPlayer();
        createTrees();
        createDeathIndicator();
        createWinIndicator();
    }

    /**
     * Creates the trees in the `trees` array.
     */
    public void createTrees() {
        trees = new Tree[numberOfTrees];
        for (int i = 0; i < numberOfTrees; i++) {
            trees[i] = new Tree((int) (Math.random() * MAX_TREE_X),
                    (int) (Math.random() * MAX_TREE_Y),
                    "images/tree_1.png");
        }
    }

    /**
     * Creates the player in the `player` variable.
     */
    public void createPlayer() {
        // Don't put the player too close to the edge of the screen.
        int x = (int) (
            Math.random() * (Window.WIDTH - Player.MINIMUM_MARGIN * 2)
        ) + Player.MINIMUM_MARGIN;
        int y = (int) (
            Math.random() * (Window.WIDTH - Player.MINIMUM_MARGIN * 2)
        ) + Player.MINIMUM_MARGIN;
        player = new Player(x, y, Window.WIDTH, Window.HEIGHT);

        // A player should never spawn on a fire. If they do, re-create the player until
        // they don't.
        checkPlayerDeathFromFires();
        if (player.getIsDead()) {
            createPlayer();
        }
    }

    /**
     * Creates the death indicator in the `deathIndicator` variable.
     */
    public void createDeathIndicator() {
        deathIndicator = new DeathIndicator();
    }

    /**
     * Creates the win indicator in the `winIndicator` variable.
     */
    public void createWinIndicator() {
        winIndicator = new WinIndicator();
    }

    /**
     * Creates the tiles in the `tiles` array.
     */
    public void createTiles() {
        tiles = new Tile[NUM_TILES_X][NUM_TILES_Y];

        for (int i = 0; i < NUM_TILES_X; i++) {
            for (int j = 0; j < NUM_TILES_Y; j++) {
                // Default to grass everywhere.
                tiles[i][j] = new GrassTile(i, j);
            }
        }

        tiles[1][1] = new StoneTile(1, 1);
        tiles[2][3] = new StoneTile(2, 3);
        tiles[5][8] = new StoneTile(5, 8);
        tiles[3][4] = new StoneTile(3, 4);

        for (int i = 0; i < numberOfFires; i++) {
            int column = (int) (Math.random() * NUM_TILES_X);
            int row = (int) (Math.random() * NUM_TILES_Y);
            tiles[column][row] = new FireTile(column, row);
        }

        for (int i = 0; i < numberOfWaters; i++) {
            int column = (int) (Math.random() * NUM_TILES_X);
            int row = (int) (Math.random() * NUM_TILES_Y);
            tiles[column][row] = new WaterTile(column, row);
        }

        int column = (int) (Math.random() * NUM_TILES_X);
        int row = (int) (Math.random() * NUM_TILES_Y);
        tiles[column][row] = new TreasureTile(column, row);
    }

    @Override
    protected void onRepaint() {
        checkPlayerSwimFromWater();
        checkPlayerDeathFromFires();
        checkPlayerWinFromTreasures();
    }

    /**
     * Checks if the player should be marked as swimming, and if so, marks them
     * as swimming.
     */
    private void checkPlayerSwimFromWater() {
        // Walk by default.
        player.walk();

        for (int i = 0; i < NUM_TILES_X; i++) {
            for (int j = 0; j < NUM_TILES_Y; j++) {
                if (tiles[i][j] instanceof WaterTile) {
                    ((WaterTile) tiles[i][j]).checkPlayerSwim();
                }
            }
        }
    }

    /**
     * Checks if a player should be marked as dead, and if so, marks them as
     * dead.
     */
    private void checkPlayerDeathFromFires() {
        for (int i = 0; i < NUM_TILES_X; i++) {
            for (int j = 0; j < NUM_TILES_Y; j++) {
                if (tiles[i][j] instanceof FireTile) {
                    ((FireTile) tiles[i][j]).checkPlayerDeath();
                }
            }
        }
    }

    /**
     * Checks if a player should be marked as won, and if so, marks them as
     * won.
     */
    private void checkPlayerWinFromTreasures() {
        for (int i = 0; i < NUM_TILES_X; i++) {
            for (int j = 0; j < NUM_TILES_Y; j++) {
                if (tiles[i][j] instanceof TreasureTile) {
                    ((TreasureTile) tiles[i][j]).checkPlayerWin();
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        if (keyEvent.getKeyChar() == 'r') {
            createTiles();
            createPlayer();
            createTrees();
        }

        super.keyTyped(keyEvent);
    }
}
