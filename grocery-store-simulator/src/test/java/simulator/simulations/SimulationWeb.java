package simulator.simulations;

import config.Configuration;
import simulator.bigbrother.BigBrother;
import simulator.world.SimpleWorld;
import web.SocialNetworkWeb;

public class SimulationWeb {
	public static void main(String ... args){
        final int NUMBER_OF_STORES = 10;

		// Creates the world
        new SimpleWorld(10);

        // Make stores for the server.
        for (int i = 0; i < NUMBER_OF_STORES / 2; i++) {
            Configuration.getProfitableStore();
            Configuration.getSimpleStore();
        }

		while(!BigBrother.getBigBrother().tick());

        SocialNetworkWeb.runServer();
        System.out.println("Done. Check the server. (localhost:4567)");
	}
}
