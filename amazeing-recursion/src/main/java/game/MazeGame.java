package game;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import maze.Maze;
import maze.Room;
import maze.SimpleMazeBuilder;
import structure.ListInterface;
import structures.LinkedList;

/**
 * A maze game that can be played through a GUI by a user.
 *
 * @author skunkmb
 */
public class MazeGame implements ActionListener {
    /**
     * A frame for the first "start" screen of the game.
     */
    private JFrame startFrame = new JFrame("Start MazeGame");

    /**
     * A frame for the game itself.
     */
    private JFrame gameFrame = new JFrame("MazeGame");

    /**
     * A frame for the win screen.
     */
    private JFrame winFrame = new JFrame("You won!");

    /**
     * A frame for the losing screen.
     */
    private JFrame lossFrame = new JFrame("You lost!");

    /**
     * A text field for the user to enter a difficulty rating.
     */
    private JTextField difficultyTextField;

    /**
     * A text field for the user to enter the percent of one-way rooms.
     */
    private JTextField oneWayTextField;

    /**
     * A label for the current game state, including the current room
     * that the player is in.
     */
    private JLabel gameTitle = new JLabel();

    /**
     * A `ButtonGroup` for radio buttons for the next room to enter.
     */
    private ButtonGroup roomOptionsButtonGroup = new ButtonGroup();

    /**
     * A list of radio buttons for the next room to enter.
     */
    private ListInterface<JRadioButton> roomOptionButtons
        = new LinkedList<JRadioButton>();

    /**
     * A panel to house the radio buttons for the next room to enter.
     */
    private JPanel roomOptionsContainer = new JPanel();

    /**
     * The current room that a player is in.
     */
    private Room currentRoom;

    /**
     * The open maze.
     */
    private Maze maze;

    /**
     * The number of moves made by the player.
     */
    private int currentMovementCount = 0;

    /**
     * Starts the game.
     *
     * @param args Command line arguments, which will be ignored.
     */
    public static void main(String[] args) {
        MazeGame mazeGame = new MazeGame();
        mazeGame.play();
    }

    /**
     * Opens the starting screen of the game.
     */
    public void play() {
        final int TITLE_WIDTH = 200;
        final int TITLE_HEIGHT = 20;
        final int LABELS_WIDTH = 50;
        final int LABELS_HEIGHT = 10;
        final int FONT_SIZE = 15;
        final int FIELDS_WIDTH = 20;
        final int FRAME_WIDTH = 450;
        final int FRAME_HEIGHT = 400;

        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("Welcome to MazeGame");
        title.setFont(new Font("TimesRoman", Font.BOLD, FONT_SIZE));
        title.setSize(TITLE_WIDTH, TITLE_HEIGHT);
        startFrame.getContentPane().add(title, BorderLayout.CENTER);

        JLabel difficultyLabel = new JLabel("Difficulty: (1 or more)");
        difficultyLabel.setSize(LABELS_WIDTH, LABELS_HEIGHT);

        difficultyTextField = new JTextField(FIELDS_WIDTH);
        difficultyTextField.setMaximumSize(
            difficultyTextField.getPreferredSize()
        );

        JLabel oneWayLabel = new JLabel("One-way amount: (0 to 1)");
        oneWayLabel.setSize(LABELS_WIDTH, LABELS_HEIGHT);

        oneWayTextField = new JTextField(FIELDS_WIDTH);
        oneWayTextField.setMaximumSize(
            oneWayTextField.getPreferredSize()
        );

        JButton goButton = new JButton("Start");
        goButton.addActionListener(this);

        JPanel difficultySubPanel = new JPanel(new FlowLayout());
        difficultySubPanel.add(difficultyLabel);
        difficultySubPanel.add(difficultyTextField);
        JPanel oneWaySubPanel = new JPanel(new FlowLayout());
        oneWaySubPanel.add(oneWayLabel);
        oneWaySubPanel.add(oneWayTextField);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.add(difficultySubPanel);
        optionsPanel.add(oneWaySubPanel);
        optionsPanel.add(goButton);
        startFrame.getContentPane().add(optionsPanel, BorderLayout.PAGE_END);

        startFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        startFrame.setVisible(true);
    }

    /**
     * Opens the "game" screen, where a user sees the room they are in
     * and can choose future rooms.
     */
    private void startGame() {
        final int TITLE_WIDTH = 400;
        final int TITLE_HEIGHT = 400;
        final int FONT_SIZE = 15;
        final int FRAME_WIDTH = 400;
        final int ERROR_FRAME_WIDTH = 600;
        final int FRAME_HEIGHT = 400;

        startFrame.setVisible(false);
        startFrame.dispose();

        gameTitle.setFont(getEmojiFont());
        gameTitle.setSize(TITLE_WIDTH, TITLE_HEIGHT);
        roomOptionsContainer.setLayout(new BoxLayout(roomOptionsContainer, BoxLayout.Y_AXIS));
        gameFrame.getContentPane().add(gameTitle, BorderLayout.CENTER);
        gameFrame.getContentPane().add(roomOptionsContainer, BorderLayout.SOUTH);

        JButton giveUpButton = new JButton("Give Up");
        giveUpButton.addActionListener(this);
        gameFrame.getContentPane().add(giveUpButton, BorderLayout.EAST);

        try {
            int difficulty = Integer.parseInt(difficultyTextField.getText());
            double oneWayAmount = Double.parseDouble(oneWayTextField.getText());
            maze = SimpleMazeBuilder.getRandomSolvableMaze(difficulty, oneWayAmount);
            playRound(maze.getStart());

            gameFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
            gameFrame.setVisible(true);
        } catch (Exception exception) {
            gameTitle.setText("An error occurred when reading your settings. Please \"give up\" and try again.");

            gameFrame.setSize(ERROR_FRAME_WIDTH, FRAME_HEIGHT);
            gameFrame.setVisible(true);
        }
    }

    /**
     * Plays a single round of gameplay, and updates the `currentRoom`
     * to a new room.
     *
     * @param room The room to set as `currentRoom`.
     */
    private void playRound(Room room) {
        currentRoom = room;

        if (currentRoom.isExit()) {
            winGame();
            return;
        }

        if (currentRoom.getRooms().size() == 0) {
            loseGame();
            return;
        }

        gameTitle.setText(
            "<html>You are currently in: "
                + room.getFullDescription()
                + ". <br />You have: "
                + room.getRooms().size()
                + " rooms available to go to.</html>");
        removeAllRoomButtons();
        currentMovementCount++;

        for (int i = 0; i < currentRoom.getRooms().size(); i++) {
            JRadioButton radioButton = new JRadioButton(
                getRadioString(currentRoom, i)
            );
            radioButton.setFont(getEmojiFont());
            radioButton.setActionCommand(String.valueOf(i));
            radioButton.addActionListener(this);
            roomOptionButtons.insertFirst(radioButton);
            roomOptionsButtonGroup.add(radioButton);
            roomOptionsContainer.add(radioButton);
        }

        gameFrame.repaint();
    }

    /**
     * Gets the label text for a radio button for a given room.
     *
     * @param startRoom The room that the player is currently in.
     * @param i The index of the room to make a button for.
     * @return The button label `String`.
     */
    private String getRadioString(Room startRoom, int i) {
        Room roomOption = startRoom.getRooms().get(i);

        if (roomOption.getRooms().contains(startRoom) == -1) {
            return roomOption.getShortDescription() + ", one-way!";
        }

        return roomOption.getShortDescription();
    }

    /**
     * Removes all current room option buttons after a round.
     */
    private void removeAllRoomButtons() {
        roomOptionsContainer.removeAll();

        while (0 < roomOptionButtons.size()) {
            roomOptionsButtonGroup.remove(roomOptionButtons.getFirst());
            roomOptionButtons.removeFirst();
        }
    }

    /**
     * Shows the win screen to a player.
     */
    private void winGame() {
        final int FRAME_WIDTH = 400;
        final int FRAME_HEIGHT = 400;

        gameFrame.setVisible(false);
        gameFrame.dispose();

        JLabel winLabel = new JLabel(
            "<html>You win, congratulations!<br />"
                + "It took you " + currentMovementCount + " moves.<br />"
                + "Play again?"
        );

        winFrame.getContentPane().add(winLabel, BorderLayout.CENTER);

        JButton goButton = new JButton("Restart");
        goButton.addActionListener(this);

        winFrame.getContentPane().add(goButton, BorderLayout.SOUTH);

        winFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        winFrame.setVisible(true);
    }

    /**
     * Shows a loss screen to a player.
     */
    private void loseGame() {
        final int FRAME_WIDTH = 400;
        final int FRAME_HEIGHT = 400;

        gameFrame.setVisible(false);
        gameFrame.dispose();

        JLabel lossLabel = new JLabel(
            "<html>You lost! You lost because you went through a "
                + "one-way passage into a room without any adjacent rooms...<br />"
                + "You played for " + currentMovementCount + " moves.<br />"
                + "Play again?"
        );

        lossFrame.getContentPane().add(lossLabel, BorderLayout.CENTER);

        JButton goButton = new JButton("Restart");
        goButton.addActionListener(this);

        lossFrame.getContentPane().add(goButton, BorderLayout.SOUTH);

        lossFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        lossFrame.setVisible(true);
    }

    /**
     * Shows the resignation screen to the player.
     */
    private void giveUpGame() {
        final int FRAME_WIDTH = 400;
        final int FRAME_HEIGHT = 400;

        gameFrame.setVisible(false);
        gameFrame.dispose();

        JLabel lossLabel = new JLabel(
            "<html>You Gave Up!<br />"
                + "You played for " + currentMovementCount + " moves.<br />"
                + "Play again?</html>"
        );

        lossFrame.getContentPane().add(lossLabel, BorderLayout.CENTER);

        JButton goButton = new JButton("Restart");
        goButton.addActionListener(this);

        lossFrame.getContentPane().add(goButton, BorderLayout.SOUTH);

        lossFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        lossFrame.setVisible(true);
    }

    /**
     * Restarts the game, by hiding frames and starting a new game.
     */
    private void restart() {
        winFrame.setVisible(false);
        winFrame.dispose();
        lossFrame.setVisible(false);
        lossFrame.dispose();

        MazeGame.main(new String[0]);
    }

    /**
     * Returns a font that can display emoji properly.
     * @return The emoji `Font`.
     */
    private Font getEmojiFont() {
        final int EMOJI_FONT_SIZE = 12;

        InputStream stream = MazeGame.class.getResourceAsStream("OpenSansEmoji.ttf");
        Font font;

        try {
            font = Font.createFont(Font.TRUETYPE_FONT, stream);
        } catch (FontFormatException | IOException exception) {
            System.out.println("An error occurred when reading font.");
            exception.printStackTrace();
            System.exit(1);
            return null;
        }

        // Casting to `double` and then constructing a `Float` seems
        // inefficient, but it's literally the only way that Checkstyle
        // will accept it, so I'm keeping it this way. (No matter how
        // else I do it, Checkstyle says to "use primitive `double`
        // instead of `float`, but I have to use a `float` because
        // that's what `deriveFont` uses as a parameter.)
        return font.deriveFont(new Float((double) EMOJI_FONT_SIZE));
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getActionCommand().equals("Start")) {
            startGame();
            return;
        }

        if (event.getActionCommand().equals("Restart")) {
            restart();
            return;
        }

        if (event.getActionCommand().equals("Give Up")) {
            giveUpGame();
            return;
        }

        // If it wasn't `Start`, then it has to be the index for a button
        // pressed.
        int buttonPressedIndex = Integer.parseInt(event.getActionCommand());
        playRound(currentRoom.getRooms().get(buttonPressedIndex));
    }
}
