package maze;

import structure.ListInterface;
import structures.LinkedList;

/**
 * A maze solver that can find solutions to mazes, or detect if they
 * are unsolvable.
 *
 * @author skunkmb
 */
public class SimpleMazeSolver implements MazeSolution {
    /**
     * The maze to be solved.
     */
    private Maze mazeToSolve;

    /**
     * A list of the current already visited rooms, so that they will
     * not be visited a second time.
     */
    private ListInterface<Room> visitedRooms = new LinkedList<Room>();

    /**
     * Constructs a `SimpleMazeSolver`.
     *
     * @param mazeToSolve The `Maze` to be solved.
     */
    public SimpleMazeSolver(Maze mazeToSolve) {
        this.mazeToSolve = mazeToSolve;
    }

    @Override
    public ListInterface<Room> getSolution() {
        ListInterface<Room> solution = getSolution(mazeToSolve.getStart());

        if (solution == null) {
            throw new UnsolvableMazeException(
                "This maze cannot be solved."
            );
        }

        return solution;
    }

    /**
     * Gets whether or not the maze is solvable. This is done by checking
     * if `getSolution` throws an error.
     *
     * @return Whether or not the maze is solvable.
     */
    public boolean getIsSolvable() {
        try {
            getSolution();
        } catch (UnsolvableMazeException exception) {
            // If there's an `UnsolvableMazeException`, then it's not solvable.
            return false;
        }

        return true;
    }

    /**
     * Finds a solution for a maze with a given starting room.
     *
     * @param startRoom The room to start with.
     * @return A list of rooms to visit, in order, to solve the maze, or
     * `null` if there is no solution.
     */
    private ListInterface<Room> getSolution(Room startRoom) {
        if (startRoom.isExit()) {
            ListInterface<Room> solution = new LinkedList<Room>();
            solution.insertFirst(startRoom);
            return solution;
        }

        return getAdjacentSolution(startRoom, 0);
    }

    /**
     * Finds a solution for any room adjacent to a given room. Adjacent
     * rooms are recursively tried, in order, for solutions. Updates
     * `visitedRooms` when a room is visited.
     *
     * @param startRoom The room to start with.
     * @param startIndex The index to start with.
     * @return A list of rooms to visit, in order, to solve the maze
     * (starting with an adjacent room), or `null` if there is no
     * solution.
     */
    private ListInterface<Room> getAdjacentSolution(Room startRoom, int startIndex) {
        // If every adjacent room has already been tried, then there is
        // no solution.
        if (startRoom.getRooms().size() <= startIndex) {
            return null;
        }

        Room adjacentRoom = startRoom.getRooms().get(startIndex);

        // If it's already been visited, try the next room.
        if (visitedRooms.contains(adjacentRoom) != -1) {
            return getAdjacentSolution(startRoom, startIndex + 1);
        }

        visitedRooms.insertLast(adjacentRoom);
        ListInterface<Room> solution = getSolution(adjacentRoom);

        if (solution != null) {
            solution.insertFirst(startRoom);
            return solution;
        }

        return getAdjacentSolution(startRoom, startIndex + 1);
    }

    @Override
    public Maze getMaze() {
        return mazeToSolve;
    }
}
