package simulator.shopper;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import config.Configuration;
import simulator.bigbrother.BigBrother;
import simulator.store.AbstractGroceryStore;
import simulator.world.SimpleWorld;

public class SocialNetworkTest {
	@Test(timeout = 5000)
    public void testSocialNetwork() {
        SimpleWorld.destroyWorld();
        BigBrother.resetBigBrother();
        new SimpleWorld(15);

        // Selects the store for the simulation
        AbstractGroceryStore store = Configuration.getProfitableStore();

        while (!BigBrother.getBigBrother().tick());

        SocialNetwork socialNetwork = SocialNetwork.getSocialNetwork();
        double rating = socialNetwork.getRatingForStore(store);
        System.out.println("Ratings: " + socialNetwork.getNumberOfVotesForStore(store));
        System.out.println("Rating received: " + rating);
        assertTrue(0.75 < rating);
	}
}
