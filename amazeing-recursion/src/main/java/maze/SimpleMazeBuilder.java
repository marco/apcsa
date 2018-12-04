package maze;

import java.util.Random;

import structure.ListInterface;
import structures.LinkedList;

/**
 * A maze builder that can create rooms, exits, and passages.
 *
 * @author skunkmb
 */
public class SimpleMazeBuilder implements MazeBuilder {
    @Override
    public Room createRoom(String fullDescription, String shortDescription) {
        return new SimpleRoom(
            fullDescription,
            shortDescription,
            false,
            new LinkedList<Room>()
        );
    }

    @Override
    public Room createExit(String fullDescription, String shortDescription) {
        return new SimpleRoom(
            fullDescription,
            shortDescription,
            true,
            new LinkedList<Room>()
        );
    }

    @Override
    public MazeBuilder addPassage(Room room0, Room room1) {
        room0.addAdjacentRoom(room1);
        room1.addAdjacentRoom(room0);
        return this;
    }

    @Override
    public MazeBuilder addOneWayPassage(Room fromRoom, Room toRoom) {
        fromRoom.addAdjacentRoom(toRoom);
        return this;
    }

    /**
     * Creates a random maze that may or may not be solvable.
     *
     * @param difficulty How "difficult" this maze should be. Each
     * difficulty level corresponds to an extra room.
     * @param oneWayChance The chance that any given path is one-way.
     * @param makeOneExit Whether or not to include an exit to the maze.
     * @return The randomly generated `Maze`.
     */
    private static Maze getRandomMaze(int difficulty, double oneWayChance, boolean makeOneExit) {
        final int DIFFICULTY_BOOST = 4;

        if (difficulty < 1) {
            throw new IllegalArgumentException(
                "Difficulty cannot be less than 1, but found " + difficulty + "."
            );
        }

        if (oneWayChance < 0) {
            throw new IllegalArgumentException(
                "One-way chance cannot be lower than 0, but found " + oneWayChance + "."
            );
        }

        if (1 < oneWayChance) {
            throw new IllegalArgumentException(
                "One-way chance cannot be greater than 1, but found " + oneWayChance + "."
            );
        }

        ListInterface<Room> currentRooms = new LinkedList<Room>();
        Room startRoom = new SimpleRoom(
            "the starting room",
            "start room",
            false,
            new LinkedList<Room>()
        );
        currentRooms.insertFirst(startRoom);
        Random random = new Random();

        if (difficulty < 1) {
            throw new IllegalArgumentException(
                "Difficulty must be greater than zero."
            );
        }

        for (int i = 0; i < difficulty + DIFFICULTY_BOOST; i++) {
            Room roomToAddTo = currentRooms.get(
                random.nextInt(currentRooms.size())
            );

            boolean shouldBeExit = i == difficulty - 1 && makeOneExit;

            Room randomRoom = getRandomRoom(shouldBeExit, i);

            if (random.nextDouble() < oneWayChance) {
                new SimpleMazeBuilder().addOneWayPassage(roomToAddTo, randomRoom);
            } else {
                new SimpleMazeBuilder().addPassage(roomToAddTo, randomRoom);
            }

            currentRooms.insertFirst(randomRoom);
        }

        return new Maze(startRoom);
    }

    /**
     * Creates a random solvable `Maze`.
     *
     * @param difficulty How "difficult" to make the maze. Each
     * difficulty level is an additional room.
     * @param oneWayChance The chance that any given room is a one-way
     * room.
     * @return The randomly generated `Maze`.
     */
    public static Maze getRandomSolvableMaze(int difficulty, double oneWayChance) {
        Maze randomMaze = getRandomMaze(difficulty, oneWayChance, true);

        // It should be solvable no matter what, but in case it isn't,
        // do a manual check.
        if (new SimpleMazeSolver(randomMaze).getIsSolvable()) {
            return randomMaze;
        }

        return getRandomSolvableMaze(difficulty, oneWayChance);
    }

    /**
     * Creates a random unsolvable `Maze`.
     *
     * @param difficulty How "difficult" to make the maze. Each
     * difficulty level is an additional room.
     * @param oneWayChance The chance that any given room is a one-way
     * room.
     * @return The randomly generated `Maze`.
     */
    public static Maze getRandomUnsolvableMaze(int difficulty, double oneWayChance) {
        Maze randomMaze = getRandomMaze(difficulty, oneWayChance, false);

        // It should be unsolvable no matter what, since there won't
        // be an exit, but in case it isn't, do a manual check.
        if (new SimpleMazeSolver(randomMaze).getIsSolvable()) {
            return getRandomUnsolvableMaze(difficulty, oneWayChance);
        }

        return randomMaze;
    }

    /**
     * Creates a random room.
     *
     * @param shouldBeExit Whether or not the room should be an exit.
     * @param index The index for this room, which is included in its
     * short description.
     * @return The randomly generated `Room`.
     */
    private static Room getRandomRoom(boolean shouldBeExit, int index) {
        final String[] colors = new String[] {
            "red", "orange", "yellow", "green", "blue", "purple",
            "indigo", "violet", "pink", "black", "white", "grey"
        };

        final String[] adjectives = new String[] {
            "small", "big", "great", "ugly", "fancy", "special",
            "unique", "crowded", "empty"
        };

        final String[] possibleEmoji = new String[] {
            "😀", "😃", "😎", "😜", "😳", "🤠", "🤡", "😐", "🤓", "😫", "👾", "👀",
            "🧠", "👁", "🤖", "🎩", "🐸", "🦊", "🐱", "🐻", "🐼", "🐨", "🦁", "🐵",
            "🐧", "🐔", "🐛", "🐠", "🌎", "🍎", "🍏", "🥑", "🍍", "🥐", "🏀", "⚽️",
            "⚾", "🎻", "🎯", "🚗", "🚕", "✈️", "🎇", "🌠", "🏙", "🖨", "💰", "💎",
            "🛠", "🎊", "🎉", "⚔️", "📬", "🥨", "🔔️"
        };

        Random random = new Random();

        String randomWallColor = colors[random.nextInt(colors.length)];
        String randomChairColor = colors[random.nextInt(colors.length)];
        String randomAdjective = adjectives[random.nextInt(adjectives.length)];
        String randomEmoji = possibleEmoji[random.nextInt(possibleEmoji.length)];

        return new SimpleRoom(
            randomEmoji + " a " + randomAdjective + " room with " + randomWallColor
                + " walls and a " + randomChairColor + " chair",
            "Room #" + index + " " + randomEmoji + " (" + randomWallColor + " room)",
            shouldBeExit,
            new LinkedList<Room>()
        );
    }
}
