import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class WordNetTest {

    private WordNet wordNet6TwoAncestors;

    @Before
    public void setUp() throws Exception {
        wordNet6TwoAncestors = new WordNet("wordnet-test-files/synsets6.txt", "wordnet-test-files/hypernyms6TwoAncestors.txt");
    }

    @Test (timeout = 5000, expected = IllegalArgumentException.class)
    public void testWordNetNullSysnets() {
        WordNet w = new WordNet(null, "wordnet-test-files/hypernyms.txt");
    }

    @Test (timeout = 5000, expected = IllegalArgumentException.class)
    public void testWordNetNullHypernyms() {
        WordNet w = new WordNet("wordnet-test-files/sysnets.txt", null);
    }

    @Test (timeout = 5000, expected = IllegalArgumentException.class)
    public void testWordNetCyclicError() {
        WordNet w = new WordNet("wordnet-test-files/synsets.txt", "wordnet-test-files/hypernyms3InvalidCycle.txt");
    }

    @Test (timeout = 5000, expected = IllegalArgumentException.class)
    public void testWordNetTwoRoots() {
        WordNet w = new WordNet("wordnet-test-files/synsets.txt", "wordnet-test-files/hypernyms6InvalidTwoRoots.txt");
    }

    @Test
    public void testNouns() {
        String[] expectedNounsArray = {"a", "b", "c", "d", "e", "f"};
        ArrayList<String> expectedNouns = new ArrayList<String>(Arrays.asList(expectedNounsArray));
        for(String actualNoun: wordNet6TwoAncestors.nouns()) {
            assertTrue(expectedNouns.contains(actualNoun));
        }
    }

    @Test
    public void testNouns3() {
        WordNet w = new WordNet("wordnet-test-files/synsets3.txt", "wordnet-test-files/hypernyms3.txt");
        String[] expectedNounsArray = {"a", "b", "c"};
        ArrayList<String> expectedNouns = new ArrayList<String>(Arrays.asList(expectedNounsArray));
        for(String actualNoun: w.nouns()) {
            assertTrue(expectedNouns.contains(actualNoun));
        }
    }

    @Test
    public void testNouns15() {
        WordNet w = new WordNet("wordnet-test-files/synsets15.txt", "wordnet-test-files/hypernyms15.txt");
        String[] expectedNounsArray = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o"};
        ArrayList<String> expectedNouns = new ArrayList<String>(Arrays.asList(expectedNounsArray));
        for(String actualNoun: w.nouns()) {
            assertTrue(expectedNouns.contains(actualNoun));
        }
    }

    @Test
    public void testIsNoun() {
        assertTrue(wordNet6TwoAncestors.isNoun("a"));
    }

    @Test
    public void testIsNounTop() {
        WordNet wordnet =  new WordNet("wordnet-test-files/synsets.txt", "wordnet-test-files/hypernyms.txt");
        assertTrue(wordnet.isNoun("Agrostis"));
    }

    @Test
    public void testIsNounMiddle() {
        WordNet wordnet =  new WordNet("wordnet-test-files/synsets.txt", "wordnet-test-files/hypernyms.txt");
        assertTrue(wordnet.isNoun("passenger_pigeon"));
    }
    @Test
    public void testIsNounBottom() {
        WordNet wordnet =  new WordNet("wordnet-test-files/synsets.txt", "wordnet-test-files/hypernyms.txt");
        assertTrue(wordnet.isNoun("yellowtail_flounder"));
    }

    @Test
    public void testIsNounFalseClose() {
        WordNet wordnet =  new WordNet("wordnet-test-files/synsets.txt", "wordnet-test-files/hypernyms.txt");
        assertFalse(wordnet.isNoun("yellowtale_flounder"));
    }

    @Test
    public void testIsNounFalseFake() {
        WordNet wordnet =  new WordNet("wordnet-test-files/synsets.txt", "wordnet-test-files/hypernyms.txt");
        assertFalse(wordnet.isNoun("NotARealWord"));
    }

    @Test
    public void testIsNounFalseEmpty() {
        WordNet wordnet =  new WordNet("wordnet-test-files/synsets.txt", "wordnet-test-files/hypernyms.txt");
        assertFalse(wordnet.isNoun(""));
    }

    @Test
    public void testDistance() {
        assertEquals(3, wordNet6TwoAncestors.distance("e", "b"));
    }

    @Test
    public void testDistanceWords() {
        WordNet wordNet= new WordNet("wordnet-test-files/synsets.txt", "wordnet-test-files/hypernyms.txt");
        assertEquals(11, wordNet.distance("expanse", "vegetable_ivory"));
    }

    @Test
    public void testDistanceWordsAmbiguous1() {
        WordNet wordNet= new WordNet("wordnet-test-files/synsets11.txt", "wordnet-test-files/hypernyms11AmbiguousAncestor.txt");
        assertEquals(2, wordNet.distance("e", "b"));
        assertEquals(1, wordNet.distance("g", "f"));
    }

    @Test
    public void testDistanceWordsAmbiguous2() {
        WordNet wordNet= new WordNet("wordnet-test-files/synsets11.txt", "wordnet-test-files/hypernyms11AmbiguousAncestor.txt");
        assertEquals(2, wordNet.distance("j", "h"));
        assertEquals(0, wordNet.distance("g", "g"));
    }

    @Test
    public void testDistanceWordsMany1() {
        WordNet wordNet= new WordNet("wordnet-test-files/synsets11.txt", "wordnet-test-files/hypernymsManyPathsOneAncestor.txt");
        assertEquals(1, wordNet.distance("a", "d"));
        assertEquals(1, wordNet.distance("g", "f"));
    }

    @Test
    public void testDistanceWordsMany2() {
        WordNet wordNet= new WordNet("wordnet-test-files/synsets11.txt", "wordnet-test-files/hypernymsManyPathsOneAncestor.txt");
        assertEquals(3, wordNet.distance("a", "j"));
        assertEquals(2, wordNet.distance("b", "i"));
    }

    @Test
    public void testSapAmbiguous1() {
        WordNet wordNet = new WordNet("wordnet-test-files/synsets11.txt", "wordnet-test-files/hypernyms11AmbiguousAncestor.txt");
        assertEquals("c", wordNet.sap("e", "b"));
        assertEquals("f", wordNet.sap("g", "f"));
    }

    @Test
    public void testSapAmbiguous2() {
        WordNet wordNet = new WordNet("wordnet-test-files/synsets11.txt", "wordnet-test-files/hypernyms11AmbiguousAncestor.txt");
        assertEquals("h", wordNet.sap("j", "h"));
        assertEquals("g", wordNet.sap("g", "g"));
    }

    @Test
    public void testSapManyPaths1() {
        WordNet wordNet = new WordNet("wordnet-test-files/synsets11.txt", "wordnet-test-files/hypernymsManyPathsOneAncestor.txt");
        assertEquals(1, wordNet.distance("a", "d"));
        assertEquals(1, wordNet.distance("g", "f"));
    }

    @Test
    public void testSapManyPaths2() {
        WordNet wordNet = new WordNet("wordnet-test-files/synsets11.txt", "wordnet-test-files/hypernymsManyPathsOneAncestor.txt");
        assertEquals(3, wordNet.distance("a", "j"));
        assertEquals(2, wordNet.distance("b", "i"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowNullDistanceString() {
        wordNet6TwoAncestors.distance(null, "apple");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowFakeDistanceString() {
        wordNet6TwoAncestors.distance("apple", "notrealword");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowNullHypernym() {
        wordNet6TwoAncestors.getHypernyms(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowNullHyponym() {
        wordNet6TwoAncestors.getHyponyms(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowNullSynsets() {
        wordNet6TwoAncestors.getSynset(null);
    }

    @Test
    public void testEmptyFakeHypernym() {
        assertEquals(wordNet6TwoAncestors.getHypernyms("notarealword").size(), 0);
    }

    @Test
    public void testEmptyFakeHyponym() {
        assertEquals(wordNet6TwoAncestors.getHyponyms("notarealword").size(), 0);
    }

    @Test
    public void testEmptyFakeSynsets() {
        assertEquals(wordNet6TwoAncestors.getSynset("notarealword").size(), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowIsNounNull() {
        wordNet6TwoAncestors.isNoun(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowSapNull() {
        wordNet6TwoAncestors.sap("apple", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowSapFake() {
        wordNet6TwoAncestors.sap("apple", "notarealword");
    }
}
