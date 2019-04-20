import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.algs4.In;

public class OutcastTest {

    private WordNet wordnet;

    @Before
    public void setup() {
        wordnet = new WordNet("wordnet-test-files/synsets.txt", "wordnet-test-files/hypernyms.txt");
    }

    @Test
    public void testOutcast10() {
        In in = new In("wordnet-test-files/outcast10.txt");
        String[] nouns = in.readAll().split("\n");
        Outcast outcast = new Outcast(wordnet);
        assertEquals("albatross", outcast.outcast(nouns));
    }

    @Test
    public void testOutcast29() {
        In in = new In("wordnet-test-files/outcast29.txt");
        String[] nouns = in.readAll().split("\n");
        Outcast outcast = new Outcast(wordnet);
        assertEquals("acorn", outcast.outcast(nouns));
    }

    @Test
    public void testOutcast11() {
        In in = new In("wordnet-test-files/outcast11.txt");
        String[] nouns = in.readAll().split("\n");
        Outcast outcast = new Outcast(wordnet);
        assertEquals("potato", outcast.outcast(nouns));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowNullNouns() {
        In in = new In("wordnet-test-files/outcast11.txt");
        String[] nouns = in.readAll().split("\n");
        Outcast outcast = new Outcast(wordnet);
        outcast.outcast(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowNullNoun() {
        In in = new In("wordnet-test-files/outcast11.txt");
        String[] nouns = in.readAll().split("\n");
        Outcast outcast = new Outcast(wordnet);
        outcast.outcast(new String[] {"apple", "orange", null, "banana"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowNonexistantNoun() {
        In in = new In("wordnet-test-files/outcast11.txt");
        String[] nouns = in.readAll().split("\n");
        Outcast outcast = new Outcast(wordnet);
        outcast.outcast(new String[] {"apple", "orange", "thiswordisfake", "banana"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowNullNet() {
        In in = new In("wordnet-test-files/outcast11.txt");
        String[] nouns = in.readAll().split("\n");
        Outcast outcast = new Outcast(null);
    }
}
