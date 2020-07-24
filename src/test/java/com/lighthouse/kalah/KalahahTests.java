package com.lighthouse.kalah;

import com.lighthouse.kalah.game.Kalahah;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class KalahahTests {

    @Test
    public void setupBoard() {
        Kalahah board = new Kalahah(6);
        for (int pitId = 1; pitId < 15; pitId++) {
            if (pitId != Kalahah.KALAH_PLAYER_SOUTH && pitId != Kalahah.KALAH_PLAYER_NORTH) {
                assertEquals(6, board.getStatus().get(pitId));
            } else {
                assertEquals(0, board.getStatus().get(pitId));
            }
        }
    }

    @Test
    public void simpleMove() {
        //  13 12 11 10  9  8
        //   6  6  6  6  6  6
        //14 0              1 7
        //   0  7  7  7  7  7
        //   1  2  3  4  5  6
        Kalahah board = new Kalahah(6);
        board.move(1);
        assertEquals(0, board.getStatus().get(1));
        assertEquals(7, board.getStatus().get(2));
        assertEquals(7, board.getStatus().get(3));
        assertEquals(7, board.getStatus().get(4));
        assertEquals(7, board.getStatus().get(5));
        assertEquals(7, board.getStatus().get(6));
        assertEquals(1, board.getStatus().get(7));
        assertEquals(6, board.getStatus().get(8));
        assertEquals(6, board.getStatus().get(9));
        assertEquals(6, board.getStatus().get(10));
        assertEquals(6, board.getStatus().get(11));
        assertEquals(6, board.getStatus().get(12));
        assertEquals(6, board.getStatus().get(13));
        assertEquals(0, board.getStatus().get(14));
    }

    @Test
    public void invalidKalahMove() {
        assertThrows(IllegalArgumentException.class, () -> {
            Kalahah board = new Kalahah(6);
            board.move(Kalahah.KALAH_PLAYER_NORTH);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Kalahah board = new Kalahah(6);
            board.move(Kalahah.KALAH_PLAYER_SOUTH);
        });
    }

    @Test
    public void invalidPitIdMoveForSouthTurn() {
        assertThrows(RuntimeException.class, () -> {
            Kalahah board = new Kalahah(6);
            board.move(11);
        });
    }

//    @Test
//    public void invalidPitIdMoveForNorthTurn() {
//        assertThrows(RuntimeException.class, () -> {
//            Kalahah board = new Kalahah(6);
//            board.move(11);
//        });
//    }

    @Test
    public void invalidPitMoveAndSouthGetsAnotherTurn() {
        Kalahah board = new Kalahah(6);
        //  13 12 11 10  9  8
        //   6  6  6  6  6  6
        //14 0              1 7
        //   0  7  7  7  7  7
        //   1  2  3  4  5  6
        board.move(1); //south player
        assertEquals(0, board.getStatus().get(1));
        assertEquals(7, board.getStatus().get(2));
        assertEquals(7, board.getStatus().get(3));
        assertEquals(7, board.getStatus().get(4));
        assertEquals(7, board.getStatus().get(5));
        assertEquals(7, board.getStatus().get(6));
        assertEquals(1, board.getStatus().get(7));
        assertEquals(6, board.getStatus().get(8));
        assertEquals(6, board.getStatus().get(9));
        assertEquals(6, board.getStatus().get(10));
        assertEquals(6, board.getStatus().get(11));
        assertEquals(6, board.getStatus().get(12));
        assertEquals(6, board.getStatus().get(13));
        assertEquals(0, board.getStatus().get(14));
        assertThrows(RuntimeException.class, () -> {
            board.move(11); // move pieces from north player when still south turn
        });
        //  13 12 11 10  9  8
        //   6  6  6  6  7  7
        //14 0              2 7
        //   0  0  8  8  8  8
        //   1  2  3  4  5  6
        board.move(2);
        assertEquals(0, board.getStatus().get(1));
        assertEquals(0, board.getStatus().get(2));
        assertEquals(8, board.getStatus().get(3));
        assertEquals(8, board.getStatus().get(4));
        assertEquals(8, board.getStatus().get(5));
        assertEquals(8, board.getStatus().get(6));
        assertEquals(2, board.getStatus().get(7));
        assertEquals(7, board.getStatus().get(8));
        assertEquals(7, board.getStatus().get(9));
        assertEquals(6, board.getStatus().get(10));
        assertEquals(6, board.getStatus().get(11));
        assertEquals(6, board.getStatus().get(12));
        assertEquals(6, board.getStatus().get(13));
        assertEquals(0, board.getStatus().get(14));
    }

    @Test
    public void moveItemsFromEmptyPit() {
        //  13 12 11 10  9  8
        //   6  6  6  6  6  6
        //14 0              6 7
        //   6  6  6  6  0  6
        //   1  2  3  4  5  6
        assertThrows(RuntimeException.class, () -> {
            Kalahah board = new Kalahah(6);
            ConcurrentHashMap<Integer, Integer> tempBoard = board.getStatus();
            tempBoard.put(5, 0);
            tempBoard.put(7, 6);
            board.setStatus(tempBoard);
            board.move(5);
        });
    }

    //you can steal stones in front of you from empty spots, and you take the opponent stones to your kalah.
    @Test
    public void captureStonesFromEmptyCupOne() {
        // before
        //  13 12 11 10  9  8
        //   6  6  6  6  6  6
        //14 2              6 7
        //   4  6  6  6  0  6
        //   1  2  3  4  5  6

        // after
        //  13 12 11 10  9  8
        //   6  6  6  6  0  6
        //14 2             13 7
        //   0  7  7  7  0  6
        //   1  2  3  4  5  6

        Kalahah board = new Kalahah(6);
        ConcurrentHashMap<Integer, Integer> tempBoard = board.getStatus();
        tempBoard.put(1, 4);
        tempBoard.put(5, 0);
        tempBoard.put(7, 6);
        tempBoard.put(14, 2);
        board.setStatus(tempBoard);
        board.move(1);
        assertEquals(7, board.getStatus().get(2));
        assertEquals(7, board.getStatus().get(3));
        assertEquals(7, board.getStatus().get(4));
        assertEquals(0, board.getStatus().get(5));
        assertEquals(6, board.getStatus().get(6));
        assertEquals(13, board.getStatus().get(7));
        assertEquals(6, board.getStatus().get(8));
        assertEquals(0, board.getStatus().get(9));

    }

    @Test
    public void captureStonesFromEmptyCupTwo() {

    }

    @Test
    public void captureStonesFromEmptyCupThree() {

    }

    @Test
    public void captureStonesFromEmptyCupFour() {

    }

    @Test
    public void captureStonesFromEmptyCupFive() {

    }

    @Test
    public void captureStonesFromEmptyCupSix() {

    }

    @Test
    public void moreStonesThanSixDoNotFillOpponentKalah() {

    }

    @Test
    public void endOfGameGetStonesToPlayerX() {

    }

}
