package simulator.bigbrother;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import simulator.checkout.AbstractRegister;
import simulator.checkout.Transaction;
import simulator.shopper.Shopper;
import simulator.store.AbstractGroceryStore;
import simulator.world.World;

/**
 * {@link BigBrother} is a fictional character in George Orwell's novel Nineteen
 * Eighty-Four. He is the enigmatic dictator of Oceania, a totalitarian state
 * taken to its utmost logical consequence – where the ruling Party wields total
 * power for its own sake over the inhabitants.
 *
 * @author jcollard, jddevaug
 *
 */
public final class BigBrother {
    /**
     * The current `BigBrother` instance.
     */
    private static BigBrother bigBrotherInstance = new BigBrother();

    /**
     * The current time in the game.
     */
  private int time = 0;

    /**
     * A set of all stores in the game.
     */
    private final List<AbstractGroceryStore> stores;

    /**
     * A set of all registers in the game.
     */
  private final Set<AbstractRegister> registers;

    /**
     * A set of all shoppers in the game.
     */
  private final Set<Shopper> shoppers;

    /**
     * A map of all grocery stores with a set of their transactions in the game.
     */
  private final Map<AbstractGroceryStore, Set<Transaction>> transMap;

    /**
     * Constructs a BigBrother instance.
     */
    private BigBrother() {
        if (bigBrotherInstance != null) {
            throw new BigBrotherIsWatchingYouException("Nice Try!");
        }
        registers = new HashSet<AbstractRegister>();
        stores = new LinkedList<AbstractGroceryStore>();
        shoppers = new HashSet<Shopper>();
        transMap = new HashMap<AbstractGroceryStore, Set<Transaction>>();
    }

  /**
   * Registers an {@link Shopper} with {@link BigBrother}.
   *
   * @param s
   *            the {@link Shopper} to register
   */
  public void registerShopper(final Shopper s) {
    if (s == null) {
      throw new NullPointerException();
    }
    shoppers.add(s);
  }

  /**
   * Registers an {@link AbstractRegister} with {@link BigBrother}.
   *
   * @param register
   *            the {@link AbstractRegister} to register
   */
  public void registerRegister(final AbstractRegister register) {
    if (register == null) {
      throw new NullPointerException();
    }
    registers.add(register);
  }

  /**
   * Registers an {@link AbstractGroceryStore} with {@link BigBrother}.
   *
   * @param store
   *            the {@link AbstractGroceryStore} to register
   */
  public void registerStore(final AbstractGroceryStore store) {
    if (store == null) {
      throw new NullPointerException();
    }
    if (stores.contains(store)) {
      throw new IllegalArgumentException("Each store should be registered exactly once.");
    }
    stores.add(store);
    transMap.put(store, new HashSet<Transaction>());
  }

    /**
     * Returns the registered store with a given ID.
     *
     * @param id
     *            The ID to return the store for.
     * @return The store with the given ID.
     */
    public AbstractGroceryStore getStore(int id) {
        return stores.get(id);
    }

    /**
     * Returns the number of registered stores.
     *
     * @return The number of registered stores.
     */
    public int getStoresAmount() {
        return stores.size();
    }

  /**
   * Returns the current time.
   *
   * @return the current time
   */
  public int getTime() {
    return time;
  }

  /**
   * Causes the universe to advance a single time step. The world ticks, all
   * registers tick, all shoppers tick. If possible, shoppers select a store
   *
   * @return {@code true} if the simulation is over and {@code false}
   *         otherwise.
   */
  public boolean tick() {
      final int MAXIMUM_TIME = 43200;

    if (time >= MAXIMUM_TIME) {
      return true;
    }

    time++;

    World.getWorld().tick();

    List<AbstractGroceryStore> possibleStores = new LinkedList<AbstractGroceryStore>(
        stores);

    for (AbstractRegister r : registers) {
      r.tick();
    }

    for (Shopper s : shoppers) {
      s.tick(transMap);
      if (!s.isInStore()) {
        s.goShopping(possibleStores);
      }
    }

    for (AbstractGroceryStore store : stores) {
      store.tick();
    }

    return false;
  }

  /**
   * Returns the {@link BigBrother} object which watches what you're doing.
   *
   * @return the {@link BigBrother} object which watches what you're doing.
   */
  public static BigBrother getBigBrother() {
    return bigBrotherInstance;
  }

    /**
     * Destroys the `BIG_BROTHER_INSTANCE` and creates a new one.
     */
    public static void resetBigBrother() {
        // A `BigBrotherIsWatchingYouException` is thrown if a new `BigBrother` is constructed when
        // `BIG_BROTHER_INSTANCE` is not null, so make it null before constructing a new one.
        bigBrotherInstance = null;
        bigBrotherInstance = new BigBrother();
    }
}
