import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * An application that plays a percolation guessing game with the player.
 *
 * @author skunkmb
 */
public class PercoGuess {
    /**
     * The player's current score.
     */
    private static int currentScore = 0;

    /**
     * A `Random` object for generating percolations.
     */
    private static Random random = new Random();

    /**
     * The current `JFrame` for the game.
     */
    private static JFrame frame;

    /**
     * The `JLabel` showing the game information.
     */
    private static JLabel currentInformation;

    /**
     * The `JLabel` showing the score.
     */
    private static JLabel currentScoreLabel;

    /**
     * The current percolation object.
     */
    private static Percolation currentPercolation;

    /**
     * The maximum size for a percolation grid.
     */
    private static final int MAX_SIZE = 20;

    /**
     * The number of vertical components within the frame.
     */
    private static final int GRID_LENGTH = 7;

    /**
     * The amount of padding between elements of the frame.
     */
    private static final int GRID_PADDING = 5;

    /**
     * The width and height of the grid.
     */
    private static final int GRID_SIZE = 500;

    /**
     * Starts the game by opening a new window and asking questions.
     *
     * @param args Command line arguments, which are ignored.
     */
    public static void main(String[] args) {
        frame = new JFrame("PercoGuess");
        frame.getContentPane().setLayout(new GridLayout(GRID_LENGTH, 1, GRID_PADDING, GRID_PADDING));

        JLabel title = new JLabel("Welcome to PercoGuess!");
        JLabel instructions = new JLabel("In this game, you have to guess whether or not a system will percolate.");
        currentScoreLabel = new JLabel("Current Score: 0");
        JLabel currentRound = new JLabel("Current Round:");
        currentInformation = new JLabel();

        JButton yesButton = new JButton("I think it Percolates!");
        yesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onYesChosen();
            }
        });

        JButton noButton = new JButton("I think it doesn't Percolate!");
        noButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onNoChosen();
            }
        });

        frame.add(title);
        frame.add(instructions);
        frame.add(currentScoreLabel);
        frame.add(currentRound);
        frame.add(currentInformation);
        frame.add(yesButton);
        frame.add(noButton);

        doNextRound();

        frame.setSize(GRID_SIZE, GRID_SIZE);
        frame.setVisible(true);
    }

    /**
     * Checks if the answer "yes" is correct or not, if the player
     * pressed the "yes" option.
     */
    private static void onYesChosen() {
        if (currentPercolation.percolates()) {
            currentScore++;
            currentScoreLabel.setText("Current Score: " + currentScore);
            doNextRound();
        } else {
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            JOptionPane.showMessageDialog(null, "No, actually, it doesn't percolate! Your score was: " + currentScore);
        }
    }

    /**
     * Checks if the answer "no" is correct or not, if the player
     * pressed the "no" option.
     */
    private static void onNoChosen() {
        if (!currentPercolation.percolates()) {
            currentScore++;
            currentScoreLabel.setText("Current Score: " + currentScore);
            doNextRound();
        } else {
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            JOptionPane.showMessageDialog(null, "No, actually, it does percolate! Your score was: " + currentScore);
        }
    }

    /**
     * Performs the next round of gameplay by generating a new grid
     * and asking the question.
     */
    private static void doNextRound() {
        int randomSize = random.nextInt(MAX_SIZE) + 1;
        int randomAmountFilled = random.nextInt(randomSize * randomSize) + 1;
        currentPercolation = new Percolation(randomSize);

        while (currentPercolation.numberOfOpenSites() < randomAmountFilled) {
            int x = random.nextInt(randomSize) + 1;
            int y = random.nextInt(randomSize) + 1;

            if (currentPercolation.isOpen(x, y)) {
                continue;
            }

            currentPercolation.open(x, y);
        }

        currentInformation.setText(
            "<html>The grid is " + randomSize + "x" + randomSize + ".<br />"
                + randomAmountFilled + " grids are filled.<br />"
                + "Do you think it percolates?</html>"
        );
    }
}
