import static spark.Spark.get;

import java.util.Set;

/**
 * A web server that can respond with synonyms, hypernyms, or hyponyms
 * for any noun.
 *
 * @author skunkmb
 */
public class NetServe {
    /**
     * The main WordNet to use.
     */
    private static WordNet mainWordNet = new WordNet("wordnet-test-files/synsets.txt", "wordnet-test-files/hypernyms.txt");;

    /**
     * Starts the server.
     *
     * @param args Command line arguments, which are ignored.
     */
    public static void main(String[] args) {
        get("/api/get-synonyms", (req, res) -> {
            String noun = req.queryParams("word");
            Set<String> synonyms = mainWordNet.getSynset(noun);
            return "[" + String.join(", ", synonyms) + "]";
        });

        get("/api/get-hypernyms", (req, res) -> {
            String noun = req.queryParams("word");
            Set<String> hypernyms = mainWordNet.getHypernyms(noun);
            return "[" + String.join(", ", hypernyms) + "]";
        });

        get("/api/get-hyponyms", (req, res) -> {
            String noun = req.queryParams("word");
            Set<String> hyponyms = mainWordNet.getHyponyms(noun);
            return "[" + String.join(", ", hyponyms) + "]";
        });
    }
}