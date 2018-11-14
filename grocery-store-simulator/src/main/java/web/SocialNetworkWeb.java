package web;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import simulator.bigbrother.BigBrother;
import simulator.shopper.SocialNetwork;
import simulator.store.AbstractGroceryStore;
import spark.Request;
import spark.Response;
import spark.Spark;

/**
 * A web server that uses Spark to host `SocialNetwork`.
 *
 * @author skunkmb
 */
public class SocialNetworkWeb {
    /**
     * A list of available names to randomly assign to stores.
     */
    private static final String[] AVAILABLE_NAMES = {
        "Ralphs", "Trader Joe's", "Kroger", "Safeway",
        "99 Cents Only", "Albertsons", "Gelsons", "Walmart",
        "Target", "Vons", "Whole Foods"
    };

    /**
     * A list of unused names to randomly assign to stores.
     */
    private static List<String> unusedNames = new LinkedList<String>(Arrays.asList(AVAILABLE_NAMES));

    /**
     * A `Random` that is used for assigning names to stores.
     */
    private static Random random = new Random();

    /**
     * Runs the server using Spark. By default, there website is hosted on `http://localhost:4567/`.
     */
    public static void runServer() {
        Spark.get("/", (Request req, Response res) -> {
            try {
                BigBrother bigBrother = BigBrother.getBigBrother();
                int storesAmount = bigBrother.getStoresAmount();

                StringBuilder stringBuilder = new StringBuilder(getBodyPrefix());
                stringBuilder.append(
                    "<h1>Welcome to SocialNetwork!</h1>"
                        + "<p>Here are the stores we have registered:</p>"
                        + "<ul>"
                );

                for (int i = 0; i < storesAmount; i++) {
                    stringBuilder.append("<li><a href=\"/rating/");
                    stringBuilder.append(String.valueOf(i));
                    stringBuilder.append("\"><strong>");
                    stringBuilder.append(getRandomName());
                    stringBuilder.append("</strong> (Store #");
                    stringBuilder.append(String.valueOf(i));
                    stringBuilder.append(")</a></li>");
                }

                stringBuilder.append("</ul>");
                stringBuilder.append(getBodySuffix());

                return stringBuilder.toString();
            } catch (Exception exception) {
                System.out.println(exception);
                return "An unexpected error occurred.";
            }
        });

        Spark.get("/rating/:id", (Request req, Response res) -> {
            try {
                String id = req.params(":id");
                int idInt = Integer.valueOf(id);

                AbstractGroceryStore store = BigBrother.getBigBrother().getStore(idInt);
                SocialNetwork socialNetwork = SocialNetwork.getSocialNetwork();

                if (socialNetwork.getNumberOfVotesForStore(store) == 0) {
                    return getBodyPrefix()
                        + "<h1>" + getRandomName()
                        + " (Store #" + id + ")"
                        + "</h1><p>There are no ratings for this store.</p>"
                        + getBodySuffix();
                }

                return (
                    getBodyPrefix()
                        + "<h1>" + getRandomName()
                        + " (Store #" + id + ")</h1>"
                        + "<p>Rating: <strong>"
                        + getStringForRating(socialNetwork.getRatingForStore(store))
                        + "%</strong></p>"
                        + "<p>out of <strong>" + socialNetwork.getNumberOfVotesForStore(store) + "</strong> votes.<p>"
                        + "<a href=\"/\">Go back.</a>"
                        + getBodySuffix()
                );
            } catch (Exception exception) {
                System.out.println(exception);
                return "An unexpected error occurred.";
            }
        });
    }

    /**
     * Returns a `String` representing a percentage for a rating.
     *
     * @param rating The rating to return a percent for.
     * @return The `String` percent representation.
     */
    private static String getStringForRating(double rating) {
        final double RATING_MULTIPLIER = 10000.0;
        final double RATING_DENOMINATOR = 100.0;

        return String.valueOf(Math.round(rating * RATING_MULTIPLIER) / RATING_DENOMINATOR);
    }

    /**
     * Returns a prefix to use for any HTML body.
     *
     * @return The prefix to use for any HTML body.
     */
    private static String getBodyPrefix() {
        return "<!doctype html><head><title>SocialNetwork</title>"
            + "<link rel=\"stylesheet\" "
            + "href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css\" "
            + "integrity=\"sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO\" "
            + "crossorigin=\"anonymous\">"
            + "</head><body class=\"m-4\">";
    }

    /**
     * Returns a suffix to use for any HTML body.
     *
     * @return The suffix to use for any HTML body.
     */
    private static String getBodySuffix() {
        return "</body>";
    }

    /**
     * Randomly chooses a store name from `unusedNames`, removes it from the list, and returns it.
     *
     * @return The randomly chosen name.
     */
    private static String getRandomName() {
        if (unusedNames.size() == 0) {
            unusedNames = new LinkedList<String>(Arrays.asList(AVAILABLE_NAMES));
        }

        int nameIndex = random.nextInt(unusedNames.size());
        String name = unusedNames.get(nameIndex);
        unusedNames.remove(nameIndex);
        return name;
    }
}
