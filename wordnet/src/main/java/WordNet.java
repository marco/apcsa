import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.princeton.cs.algs4.DepthFirstOrder;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

/**
 * A class representing a collection of synonyms ("synsets") and
 * their relationships ("hypernyms). These can be used to find
 * the similarity and ancestors of words.
 *
 * @author skunkmb
 */
public class WordNet {
    /**
     * The digraph used by the WordNet.
     */
    private Digraph hypernymGraph;

    /**
     * An `SAP` containing all of the nouns in the WordNet and their
     * relationships.
     */
    private SAP hypernymSap;

    /**
     * A list of all synsets in the WordNet, where the index is the "ID"
     * of the synset and the value is an array of its synonyms.
     */
    private List<String[]> synsets = new ArrayList<String[]>();

    /**
     * A list of all of the hypernyms in a Wordnet, where the index
     * is the "ID"  of a word and the value is an array of IDs of
     * hypernyms.
     */
    private List<Integer[]> hypernyms = new ArrayList<Integer[]>();

    /**
     * A map of all of the hyponyms in a Wordnet, where the key
     * is the "ID"  of a word and the value is a list of IDs of
     * hyponyms.
     */
    private Map<Integer, List<Integer>> hyponyms = new HashMap<Integer, List<Integer>>();

    /**
     * A map of nouns and all of the synsets that they are within. The
     * keys are the noun strings, and the values are lists of synset IDs.
     */
    private Map<String, List<Integer>> nounIDs = new HashMap<String, List<Integer>>();

    /**
     * A set of all nouns present in this WordNet.
     */
    private Set<String> uniqueNouns = new HashSet<String>();

    /**
     * A map storing cached distance values between nouns. The keys
     * are in the format "nounA-nounB" as defined by `getCacheKey`, and
     * the values are the distances between the nouns.
     */
    private Map<String, Integer> cachedDistances = new HashMap<String, Integer>();

    /**
     * A map storing cached synset values for the common ancestors
     * of nouns. The keys are in the format "nounA-nounB" as defined by
     * `getCacheKey`, and the values are the distances between the nouns.
     */
    private Map<String, String> cachedSaps = new HashMap<String, String>();

    /**
     * Constructs a `WordNet`.
     *
     * @param synsetsFilename The CSV file containing synsets.
     * @param hypernymsFilename The CSV file containing hypernyms.
     */
    public WordNet(String synsetsFilename, String hypernymsFilename) {
        In synsetIn = new In(synsetsFilename);
        String synsetsInput = synsetIn.readAll();
        In hypernymIn = new In(hypernymsFilename);
        String hypernymsInput = hypernymIn.readAll();

        String[] synsetLines = synsetsInput.split("\\n");
        String[] hypernymLines = hypernymsInput.split("\\n");
        hypernymGraph = new Digraph(synsetLines.length);

        for (int i = 0; i < synsetLines.length; i++) {
            String[] synonyms = synsetLines[i].split(",")[1].split(" ");
            synsets.add(synonyms);

            for (int j = 0; j < synonyms.length; j++) {
                uniqueNouns.add(synonyms[j]);

                if (!nounIDs.containsKey(synonyms[j])) {
                    nounIDs.put(synonyms[j], new ArrayList<Integer>());
                }

                nounIDs.get(synonyms[j]).add(i);
            }
        }

        for (int i = 0; i < hypernymLines.length; i++) {
            String[] hypernyms = hypernymLines[i].split(",");

            for (int j = 1; j < hypernyms.length; j++) {
                hypernymGraph.addEdge(Integer.parseInt(hypernyms[0]), Integer.parseInt(hypernyms[j]));

                if (this.hyponyms.containsKey(hypernyms[j])) {
                    this.hyponyms.get(Integer.parseInt(hypernyms[j])).add(Integer.parseInt(hypernyms[0]));
                } else {
                    List<Integer> hyponymList = new ArrayList<Integer>();
                    hyponymList.add(Integer.parseInt(hypernyms[0]));
                    this.hyponyms.put(Integer.parseInt(hypernyms[j]), hyponymList);
                }
            }

            if (hypernyms.length > 1) {
                Integer[] hypernymContents = new Integer[hypernyms.length - 1];

                for (int k = 1; k < hypernyms.length; k++) {
                    hypernymContents[k - 1] = Integer.parseInt(hypernyms[k]);
                }

                this.hypernyms.add(hypernymContents);
            }
        }

        Iterable<Integer> vertices = new DepthFirstOrder(hypernymGraph).reversePost();
        boolean currentHasRoot = false;

        for (Integer vertex : vertices) {
            if (hypernymGraph.outdegree(vertex) == 0) {
                if (currentHasRoot) {
                    throw new IllegalArgumentException("Only one root can be supplied.");
                }

                currentHasRoot = true;
            }
        }

        if (!currentHasRoot) {
            throw new IllegalArgumentException("One root must be supplied.");
        }

        hypernymSap = new SAP(hypernymGraph);
    }

    /**
     * Returns all WordNet nouns.
     * @return An `Iterable` of all nouns in the WordNet.
     */
    public Iterable<String> nouns() {
        return uniqueNouns;
    }

    /**
     * Returns whether or not a word is a noun within the WordNet.
     *
     * @param word The word to check.
     * @return Whether or not it is a noun.
     */
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException("The word cannot be null.");
        }

        return uniqueNouns.contains(word);
    }

    /**
     * Returns the shortest ancestral path distance between two
     * nouns. The shortest ancestral path is the
     * sum of the distances between a common ancestor and two vertices.
     *
     * @param nounA The first noun to check.
     * @param nounB The second noun to check.
     * @return The shortest ancestral path distance.
     */
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException("The nouns cannot be null.");
        }

        if (cachedDistances.containsKey(getCacheKey(nounA, nounB))) {
            return cachedDistances.get(getCacheKey(nounA, nounB));
        }

        List<Integer> idsA = nounIDs.get(nounA);
        List<Integer> idsB = nounIDs.get(nounB);

        int distanceResult = hypernymSap.length(idsA, idsB);
        cachedDistances.put(getCacheKey(nounA, nounB), distanceResult);
        return distanceResult;
    }

    /**
     * Returns the closest common ancestor's synset for two nouns.
     *
     * @param nounA The first noun to check.
     * @param nounB The second noun to check.
     * @return The synonyms, separated by spaces, of the nouns' closest
     * common ancestor.
     */
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException("The nouns cannot be null.");
        }

        if (cachedSaps.containsKey(getCacheKey(nounA, nounB))) {
            return cachedSaps.get(getCacheKey(nounA, nounB));
        }

        List<Integer> idsA = nounIDs.get(nounA);
        List<Integer> idsB = nounIDs.get(nounB);

        String sapResult = String.join(" ", synsets.get(hypernymSap.ancestor(idsA, idsB)));
        cachedSaps.put(getCacheKey(nounA, nounB), sapResult);
        return sapResult;
    }

    /**
     * Returns a set of synonyms for a noun.
     *
     * @param noun The noun to return a synset for.
     * @return The synset.
     */
    public Set<String> getSynset(String noun) {
        if (noun == null) {
            throw new IllegalArgumentException("The noun cannot be null.");
        }

        Set<String> synsetSum =  new HashSet<String>();
        List<Integer> nounIDsForNoun = nounIDs.get(noun);

        if (nounIDsForNoun == null) {
            return new HashSet<String>();
        }

        for (Integer nounID : nounIDsForNoun) {
            synsetSum.addAll(Arrays.asList(synsets.get(nounID)));
        }

        return synsetSum;
    }

    /**
     * Returns a set of hypernyms for a noun.
     *
     * @param noun The noun to return hypernyms for.
     * @return The hypernyms.
     */
    public Set<String> getHypernyms(String noun) {
        if (noun == null) {
            throw new IllegalArgumentException("The noun cannot be null.");
        }

        Set<Integer> hypernymIDsSum =  new HashSet<Integer>();
        List<Integer> nounIDsForNoun = nounIDs.get(noun);

        if (nounIDsForNoun == null) {
            return new HashSet<String>();
        }

        for (Integer nounID : nounIDsForNoun) {
            hypernymIDsSum.addAll(Arrays.asList(hypernyms.get(nounID)));
        }

        Set<String> hypernymsSum = new HashSet<String>();

        for (Integer hypernymID : hypernymIDsSum) {
            hypernymsSum.addAll(new HashSet(Arrays.asList(synsets.get(hypernymID))));
        }

        return hypernymsSum;
    }

    /**
     * Returns a set of hyponyms for a noun.
     *
     * @param noun The noun to return hyponyms for.
     * @return The hyponyms.
     */
    public Set<String> getHyponyms(String noun) {
        if (noun == null) {
            throw new IllegalArgumentException("The noun cannot be null.");
        }
        Set<Integer> hyponymsIDsSum =  new HashSet<Integer>();
        List<Integer> nounIDsForNoun = nounIDs.get(noun);

        if (nounIDsForNoun == null) {
            return new HashSet<String>();
        }

        for (Integer nounID : nounIDsForNoun) {
            if (!hyponyms.containsKey(nounID)) {
                continue;
            }

            hyponymsIDsSum.addAll(new HashSet<Integer>(hyponyms.get(nounID)));
        }

        Set<String> hyponymsSum = new HashSet<String>();

        for (Integer hyponymID : hyponymsIDsSum) {
            hyponymsSum.addAll(new HashSet(Arrays.asList(synsets.get(hyponymID))));
        }

        return hyponymsSum;
    }

    /**
     * Returns a key used in maps for caching values for two nouns. The
     * key is in the format "nounA-nounB."
     *
     * @param nounA The first noun to cache.
     * @param nounB The second noun to cache.
     * @return The cache key.
     */
    private String getCacheKey(String nounA, String nounB) {
        return nounA + "-" + nounB;
    }
}