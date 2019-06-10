import java.util.HashSet;
import java.util.Set;

/**
 * A class for solving a game of Boggle given a dictionary and a board.
 *
 * @author skunkmb
 */
public class BoggleSolver {
    /**
     * The trie of valid dictionary words.
     */
    private BoggleTrie dictionaryTrie;

    /**
     * The "R" value for the `dictionaryTrie`, which is equal to the number
     * of letters in the alphabet.
     */
    private static final int DICTIONARY_TRIE_R = 26;

    /**
     * The ASCII offset for a letter in the alphabet, so that the
     * value of 'A' - LETTER_OFFSET equals 0. This is used when creating
     * and accessing the dictionary trie node elements.
     */
    private static final int LETTER_OFFSET = 65;

    /**
     * Constructs a `BoggleSolver`.
     *
     * @param dictionary The array of dictionary words to allow in the
     * Boggle board.
     */
    public BoggleSolver(String[] dictionary) {
        if (dictionary == null) {
            throw new IllegalArgumentException("The dictionary cannot be null.");
        }

        dictionaryTrie = new BoggleTrie();

        for (int i = 0; i < dictionary.length; i++) {
            int initialScore = getInitialScore(dictionary[i]);

            if (initialScore > 0) {
                dictionaryTrie.put(dictionary[i], initialScore);
            }
        }
    }

    /**
     * Returns all of the valid scoring words for a given `BoggleBoard`.
     * Words that are valid but do not earn points, i.e. words less than
     * three characters, are not included.
     *
     * @param board The `BoggleBoard` used to find valid words.
     * @return The `Iterable` of valid words.
     */
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        if (board == null) {
            throw new IllegalArgumentException("The board cannot be null.");
        }

        Set<String> wordSet = new HashSet<String>();

        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {
                Set<Integer> prefixPairs = new HashSet<Integer>();
                prefixPairs.add(get1DIndex(i, j, board.cols()));

                Node node;
                char startCharacter = board.getLetter(i, j);
                String startCharString = String.valueOf(startCharacter);
                String newUpdatedWord;

                if (startCharacter == 'Q') {
                    node = dictionaryTrie.getNodeObjectQU();
                    newUpdatedWord = "QU";
                } else {
                    node = dictionaryTrie.getNodeObject(startCharacter);
                    newUpdatedWord = startCharString;
                }

                getAllValidPrefixedWords(board, i, j, startCharString, newUpdatedWord, prefixPairs, wordSet, node);
            }
        }

        return wordSet;
    }

    /**
     * Returns a 1D index for a 2D row-column pair.
     *
     * @param row The row of the 2D index.
     * @param col The column of the 2D index.
     * @param width The width of the board.
     * @return The 1D index.
     */
    private int get1DIndex(int row, int col, int width) {
        return row * width + col;
    }

    /**
     * Recursively finds all valid words on a `BoggleBoard` that start
     * with a certain prefix.
     *
     * @param board The `BoggleBoard` to find words for.
     * @param startRow The last accessed row in the prefix string.
     * @param startCol The last accessed column in the prefix string.
     * @param prefixString The prefix string to use when recursively
     * finding words.
     * @param updatedWord A user-readable version of `prefixString`, where
     * all "Q"s have been replaced with "QU".
     * @param prefixPairs A set of already used 1D indices of letters on
     * the `BoggleBoard`. These spaces cannot be used again.
     * @param wordList The set to add to with new valid words.
     * @param node The last accessed `Node` in the prefix string.
     */
    private void getAllValidPrefixedWords(BoggleBoard board, int startRow, int startCol, String prefixString, String updatedWord, Set<Integer> prefixPairs, Set<String> wordList, Node node) {
        if (getInitialScore(updatedWord) > 0 && dictionaryTrie.get(updatedWord) != null) {
            wordList.add(updatedWord);
        }

        if (node == null) {
            return;
        }

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int row = startRow + i;
                int col = startCol + j;

                if (row < 0 || row >= board.rows() || col < 0 || col >= board.cols() || (i == 0 && j == 0)) {
                    continue;
                }

                int index1D = get1DIndex(row, col, board.cols());

                if (prefixPairs.contains(index1D)) {
                    continue;
                }

                Node next;
                char nextLetter = board.getLetter(row, col);
                String newUpdatedWord;

                if (nextLetter == 'Q') {
                    next = node.next['Q' - LETTER_OFFSET];
                    newUpdatedWord = updatedWord + "QU";

                    if (next != null) {
                        next = next.next['U' - LETTER_OFFSET];
                    }
                } else {
                    next = node.next[nextLetter - LETTER_OFFSET];
                    newUpdatedWord = updatedWord + nextLetter;
                }

                if (next == null) {
                    continue;
                }

                Set<Integer> newPrefixPairs = new HashSet<Integer>(prefixPairs);
                newPrefixPairs.add(index1D);
                getAllValidPrefixedWords(board, row, col, prefixString + nextLetter, newUpdatedWord, newPrefixPairs, wordList, next);
            }
        }
    }

    /**
     * Returns the score of a word, or 0 if the word is not a valid word.
     * Scores are found as follows based on length:
     *  - 0-2: 0 points
     *  - 3-4: 1 point
     *  - 5: 2 points
     *  - 6: 3 points
     *  - 7: 5 points
     *  - 8+: 11 points
     *
     * @param word The word to find a score for.
     * @return The word score.
     */
    public int scoreOf(String word) {
        if (word == null) {
            throw new IllegalArgumentException("The word cannot be null.");
        }

        Integer score = dictionaryTrie.get(word);

        if (score == null) {
            return 0;
        }

        return score;
    }

    /**
     * Returns a score for a word before entering it into a trie.
     * This is used before the trie is created, so `scoreOf` cannot be
     * used. Scores are found as follows based on length:
     *  - 0-2: 0 points
     *  - 3-4: 1 point
     *  - 5: 2 points
     *  - 6: 3 points
     *  - 7: 5 points
     *  - 8+: 11 points
     *
     * @param word The word to find a score for.
     * @return The word score.
     */
    private int getInitialScore(String word) {
        int length = word.length();

        if (length <= 2) {
            return 0;
        }

        if (length <= 4) {
            return 1;
        }

        if (length == 5) {
            return 2;
        }

        if (length == 6) {
            return 3;
        }

        if (length == 7) {
            return 5;
        }

        return 11;
    }

    /**
     * A trie node containing a value and child nodes.
     *
     * @author skunkmb
     */
    private class Node {
        private Integer value;
        private Node[] next = new Node[DICTIONARY_TRIE_R];
    }

    /**
     * An optimized dictionary trie to be used by `BoggleSolver`.
     *
     * @author skunkmb
     */
    private class BoggleTrie {
        /**
         * The root node of the trie.
         */
        private Node root = new Node();

        /**
         * Adds a new score value for a certain key.
         *
         * @param key The key to add the score for.
         * @param val The score value to use.
         */
        public void put(String key, Integer val) {
            root = put(root, key, val, 0);
        }

        /**
         * Recursively adds a new score value for a certain key, and
         * returns a new `Node` to use as the root node. The new root
         * may be the same as the old root.
         *
         * @param x The node to add the new node as a subchild of.
         * @param key The key to use for the new node.
         * @param val The score value for the new node.
         * @param depth The current depth of the `x` node.
         * @return The new root node.
         */
        private Node put(Node x, String key, Integer val, int depth) {
            if (x == null) {
                x = new Node();
            }

            if (depth == key.length()) {
                x.value = val;
                return x;
            }

            int c = key.charAt(depth) - LETTER_OFFSET;
            x.next[c] = put(x.next[c], key, val, depth + 1);
            return x;
        }

        /**
         * Returns the score value for a key.
         *
         * @param key The word key.
         * @return The value.
         */
        public Integer get(String key) {
            Node result = get(root, key, 0);

            if (result == null) {
                return null;
            }

            return result.value;
        }

        /**
         * Recursively finds the score value for a key.
         *
         * @param x The node to find the node as a subchild of.
         * @param key The key of the node to find.
         * @param depth The depth of the `x` node.
         * @return The `Node` with the given key.
         */
        private Node get(Node x, String key, int depth) {
            if (x == null) {
                return null;
            }

            if (depth == key.length()) {
                return x;
            }

            int c = key.charAt(depth) - LETTER_OFFSET;
            return get(x.next[c], key, depth + 1);
        }

        /**
         * Returns a `Node` with a given one-character prefix.
         *
         * @param prefix The character prefix.
         * @return The `Node`.
         */
        public Node getNodeObject(char prefix) {
            return root.next[prefix - LETTER_OFFSET];
        }

        /**
         * Returns the `Node` with the "QU" prefix.
         *
         * @return The `Node`.
         */
        public Node getNodeObjectQU() {
            return root.next['Q' - LETTER_OFFSET].next['U' - LETTER_OFFSET];
        }
    }
}