package structure;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import maze.Maze;
import maze.Room;
import maze.SimpleRoom;
import structures.LinkedList;

public class MazeTest {
    @Test
    public void testStart() {
        Room room = new SimpleRoom(
            "a room",
            "a big, empty room",
            false,
            new LinkedList<Room>()
        );
        Maze maze = new Maze(room);

        assertEquals(maze.getStart(), room);
    }
}
