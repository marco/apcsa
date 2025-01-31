package maze;

import structure.ListInterface;

/**
 * A {@link Room} in a {@link Maze}.
 *
 * @author jcollard, jddevaug
 *
 */
public interface Room {

  /**
   * Returns a full description of this {@link Room}. This is a description
   * seen when someone is inside of this {@link Room}.
   *
   * @return a full description of this {@link Room}
   */
  String getFullDescription();

  /**
   * Returns a short description of this {@link Room}. This is a description
   * seen when someone is in an adjacent {@link Room}.
   *
   * @return a short description of this {@link Room}.
   */
  String getShortDescription();

  /**
   * Returns {@code true} if this {@link Room} is an exit and {@code false}
   * otherwise. When someone is in this {@link Room} they may exit
   * the {@link Maze}
   *
   * @return {@code true} if this {@link Room} is an exit and {@code false}
   *         otherwise.
   */
  boolean isExit();

  /**
   * Returns a {@link ListInterface} of the {@link Room}s which can be reached
   * from this {@link Room}.
   *
   * @return a {@link ListInterface} of the {@link Room}s which can be reached
   * from this {@link Room}
   */
  ListInterface<Room> getRooms();

    /**
     * Adds a room as an adjacent room, if it is not already included as
     * an adjacent room. `true` is returned if it was added, or `false`
     * is returned if it was already included.
     *
     * @param room The room to add.
     * @return Whether or not the room needed to be added.
     */
    boolean addAdjacentRoom(Room room);
}

