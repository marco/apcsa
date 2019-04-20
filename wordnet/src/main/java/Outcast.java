/**
 * A class that identifies "outcasts" within an array of "WordNet" words.
 * An "outcast" is the word with the greatest hypernym path distance
 * to the other words in the array.
 *
 * @author skunkmb
 */
public class Outcast {
    /**
     * The WordNet to use for finding hypernyms between words.
     */
    private WordNet wordnet;

    /**
     * Constructs an `Outcast`.
     *
     * @param wordnet The WordNet to use for finding hypernyms between
     * words.
     */
    public Outcast(WordNet wordnet) {
        if (wordnet == null) {
            throw new IllegalArgumentException("The WordNet cannot be null.");
        }

        this.wordnet = wordnet;
    }

    /**
     * Finds an outcast within an array of WordNet nouns.
     *
     * @param nouns The WordNet nouns to find an outcast within.
     * @return The noun that has the lowest connection (or greatest
     * hypernym distance) to the other nouns, also known as the outcast.
     */
    public String outcast(String[] nouns) {
        if (nouns == null) {
            throw new IllegalArgumentException("The nouns cannot be null");
        }

        int currentMaxSum = -1;
        String currentBestOutcast = null;

        for (int i = 0; i < nouns.length; i++) {
            int currentSum = 0;

            for (String otherNoun : nouns) {
                currentSum += wordnet.distance(otherNoun, nouns[i]);
            }

            if (currentSum > currentMaxSum) {
                currentMaxSum = currentSum;
                currentBestOutcast = nouns[i];
            }
        }

        return currentBestOutcast;
    }
}