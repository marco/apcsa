package maze;

import structure.ListInterface;

/**
 * A simple room as part of a maze.
 *
 * @author skunkmb
 */
public class SimpleRoom implements Room {
    /**
     * A long description for this room.
     */
    private String fullDescription;

    /**
     * A short description for this room.
     */
    private String shortDescription;

    /**
     * Whether or not this room is an exit.
     */
    private boolean isExit;

    /**
     * A list of adjacent `Room`s to this room.
     */
    private ListInterface<Room> adjacentRooms;

    /**
     * Constructs a `SimpleRoom`.
     *
     * @param fullDescription A long description for this room.
     * @param shortDescription A short description for this room.
     * @param isExit Whether or not this room is an exit.
     * @param adjacentRooms A list of adjacent `Room`s to this room.
     */
    public SimpleRoom(
        String fullDescription,
        String shortDescription,
        boolean isExit,
        ListInterface<Room> adjacentRooms
    ) {
        if (fullDescription == null) {
            throw new NullPointerException(
                "The full description cannot be `null`."
            );
        }

        if (shortDescription == null) {
            throw new NullPointerException(
                "The short description cannot be `null`."
            );
        }

        if (adjacentRooms == null) {
            throw new NullPointerException(
                "The adjacent rooms cannot be `null`."
            );
        }
        this.fullDescription = fullDescription;
        this.shortDescription = shortDescription;
        this.isExit = isExit;
        this.adjacentRooms = adjacentRooms;
    }

    @Override
    public String getFullDescription() {
        return fullDescription;
    }

    @Override
    public String getShortDescription() {
        return shortDescription;
    }

    @Override
    public boolean isExit() {
        return isExit;
    }

    @Override
    public ListInterface<Room> getRooms() {
        return adjacentRooms;
    }

    @Override
    public boolean addAdjacentRoom(Room room) {
        if (room == null) {
            throw new NullPointerException(
                "The adjacent room cannot be `null`."
            );
        }
        if (adjacentRooms.contains(room) != -1) {
            return false;
        }

        adjacentRooms.insertLast(room);
        return true;
    }
}
