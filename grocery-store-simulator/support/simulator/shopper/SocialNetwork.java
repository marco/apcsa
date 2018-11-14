package simulator.shopper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import simulator.store.AbstractGroceryStore;

/**
 * A social network that manages votes and reviews for stores.
 *
 * @author skunkmb
 */
public class SocialNetwork {
    /**
     * The current social network instance.
     */
    private static SocialNetwork instance = new SocialNetwork();

    /**
     * A map of all votes placed in this social network. Keys are grocery stores, and values are
     * lists of votes, where upvotes are `true` and downvotes are `false`.
     */
    private final Map<AbstractGroceryStore, List<Boolean>> votes
        = new HashMap<AbstractGroceryStore, List<Boolean>>();

    /**
     * Returns the current social network instance.
     *
     * @return The current social network instance.
     */
    public static SocialNetwork getSocialNetwork() {
        return instance;
    }

    /**
     * Upvotes a store, increasing its rating.
     *
     * @param store The store to upvote.
     */
    public void upvote(AbstractGroceryStore store) {
        if (!votes.containsKey(store)) {
            votes.put(store, new ArrayList<Boolean>());
        }

        votes.get(store).add(true);
    }

    /**
     * Downvotes a store, decreasing its rating.
     *
     * @param store The store to downvote.
     */
    public void downvote(AbstractGroceryStore store) {
        if (!votes.containsKey(store)) {
            votes.put(store, new ArrayList<Boolean>());
        }

        votes.get(store).add(false);
    }

    /**
     * Returns the number of votes counted for a store.
     *
     * @param store The store to count votes for.
     * @return The number of votes for the store.
     */
    public int getNumberOfVotesForStore(AbstractGroceryStore store) {
        if (votes.containsKey(store)) {
            return votes.get(store).size();
        }

        return 0;
    }

    /**
     * Returns the rating for a store based on its votes.
     *
     * @param store The store to get the rating for.
     * @return The rating for the store.
     */
    public double getRatingForStore(AbstractGroceryStore store) {
        if (!votes.containsKey(store)) {
            throw new NullPointerException("There are no ratings for that store.");
        }

        List<Boolean> votesForStore = votes.get(store);
        double points = 0;

        for (int i = 0; i < votesForStore.size(); i++) {
            if (votesForStore.get(i)) {
                points++;
            }
        }

        return points / votesForStore.size();
    }

    /**
     * Finds the store with the highest rating out of a list of stores.
     *
     * @param stores The stores to find the highest-rated store from.
     * @return The store with the highest rating, or `null`.
     */
    public AbstractGroceryStore selectStore(List<AbstractGroceryStore> stores) {
        double currentHighestRating = -1;
        int currentHighestRatedIndex = 0;

        for (int i = 0; i < stores.size(); i++) {
            try {
                double ratingForStore = getRatingForStore(stores.get(i));

                if (currentHighestRating < ratingForStore) {
                    currentHighestRating = ratingForStore;
                    currentHighestRatedIndex = i;
                }
            } catch (NullPointerException exception) {
            }
        }

        return stores.get(currentHighestRatedIndex);
    }
}
