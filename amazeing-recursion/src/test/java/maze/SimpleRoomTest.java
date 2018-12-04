package maze;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import structures.LinkedList;;

public class SimpleRoomTest {
    @Test
    public void testCreateNotExit() {
        SimpleRoom room = new SimpleRoom(
            "orange room",
            "a big, furnished room",
            false,
            new LinkedList<Room>()
        );

        assertEquals(room.getFullDescription(), "orange room");
        assertEquals(room.getShortDescription(), "a big, furnished room");
        assertEquals(room.isExit(), false);
    }

    @Test
    public void testCreateExit() {
        SimpleRoom room = new SimpleRoom(
            "blue room",
            "a small, ugly room",
            true,
            new LinkedList<Room>()
        );

        assertEquals(room.getFullDescription(), "blue room");
        assertEquals(room.getShortDescription(), "a small, ugly room");
        assertEquals(room.isExit(), true);
    }

    @Test
    public void testAddAdjacent() {
        SimpleRoom orangeRoom = new SimpleRoom(
            "orange room",
            "a big, furnished room",
            false,
            new LinkedList<Room>()
        );

        SimpleRoom blueRoom = new SimpleRoom(
            "blue room",
            "a small, ugly room",
            false,
            new LinkedList<Room>()
        );

        SimpleRoom greenRoom = new SimpleRoom(
            "green room",
            "a medium room",
            false,
            new LinkedList<Room>()
        );

        greenRoom.addAdjacentRoom(blueRoom);

        assertEquals(greenRoom.getRooms().size(), 1);
        assertEquals(greenRoom.getRooms().getFirst(), blueRoom);

        blueRoom.addAdjacentRoom(orangeRoom);
        orangeRoom.addAdjacentRoom(blueRoom);

        assertEquals(blueRoom.getRooms().size(), 1);
        assertEquals(blueRoom.getRooms().getFirst(), orangeRoom);
        assertEquals(orangeRoom.getRooms().size(), 1);
        assertEquals(orangeRoom.getRooms().getFirst(), blueRoom);
    }
}
