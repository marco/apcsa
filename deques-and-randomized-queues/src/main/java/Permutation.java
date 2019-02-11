import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;

/**
 * A permutator that can take a file as input and output randomized
 * strings.
 *
 * @author skunkmb
 */
public class Permutation {
    /**
     * Runs the permutator.
     *
     * @param args Command-line arguments. The first argument is the
     * number of random strings to output.
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException(
                "At least one argument is required."
            );
        }

        int printAmount = Integer.valueOf(args[0]);
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            randomizedQueue.enqueue(StdIn.readString());
        }

        Iterator<String> randomizedIterator = randomizedQueue.iterator();

        for (int i = 0; i < printAmount; i++) {
            System.out.println(randomizedIterator.next());
        }
    }
}
