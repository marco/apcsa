import static spark.Spark.get;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * A server to provide cards for a game of blackjack.
 *
 * @author skunkmb
 */
public class BlackJack {
    /**
     * The current queue of cards.
     */
    private static RandomizedQueue<String> cards = new RandomizedQueue<String>();

    /**
     * Possible faces to use for cards.
     */
    private static final String[] FACES = new String[] {"1", "2", "3", "4", "5", "6", "7", "8"};

    /**
     * Possible suits to use for cards.
     */
    private static final String[] SUITS = new String[] {"of Spades", "of Hearts", "of Diamonds", "of Clubs"};

    /**
     * Starts the server on port 4567.
     *
     * @param args Command-line arguments, which are ignored.
     */
    public static void main(String[] args) {
        makeCards();

        get("/", (request, response) -> {
            try {
                byte[] file = Files.readAllBytes(Paths.get("./src/main/java/BlackJack.html"));
                String index = new String(file, "utf-8");
                return index;
            } catch (Exception error) {
                System.out.println(error);
                return error;
            }
        });

        get("/get-next-card", (request, response) -> {
            if (cards.size() == 0) {
                makeCards();
            }

            return cards.dequeue();
        });
    }

    /**
     * Updates `cards` with a full deck of cards.
     */
    private static void makeCards() {
        for (int i = 0; i < SUITS.length; i++) {
            for (int j = 0; j < FACES.length; j++) {
                cards.enqueue(FACES[j] + " " + SUITS[i]);
            }
        }
    }
}
