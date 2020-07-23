package com.lighthouse.kalah.game;

import java.util.concurrent.ConcurrentHashMap;

public class Kalahah {

    private Long gameId;
    private Integer stones;
    private Player turn;
    private ConcurrentHashMap<Integer, Integer> status;
    public static final Integer KALAH_PLAYER_SOUTH = 7;
    public static final Integer KALAH_PLAYER_NORTH = 14;

    public Kalahah(final Integer stones) {
        this.stones = stones;
        this.turn = Player.SOUTH;
        this.status = new ConcurrentHashMap<>();
        initStonesBoard(this.status, this.stones);
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(final Long gameId) {
        this.gameId = gameId;
    }

    public ConcurrentHashMap<Integer, Integer> getStatus() {
        return status;
    }

    public void setStatus(ConcurrentHashMap<Integer, Integer> status) {
        this.status = status;
    }

    public void move(final Integer pitId) {
        if (isValidPit(pitId)) {
            if (pitHasItems(pitId)) {
                int pitStones = this.status.get(pitId);
                //pickup the stones
                this.status.put(pitId, 0);
                switch (this.turn) {
                    case SOUTH:
                        //place stones counter-clockwise
                        int pitDestination = pitId;
                        //do this while I have stones in my hand
                        while (pitStones > 0) {
                            pitDestination = pitDestination % 14 + 1;
                            //only on my valid pits
                            if (pitDestination != KALAH_PLAYER_NORTH)
                                this.status.put(pitDestination, this.status.get(pitDestination) + 1);
                            if (pitStones == 1) {
                                processEmptyPitToPullOpponentStones(pitDestination, this.turn);
                            }
                            pitStones--;
                        }
                        // rule, if last stone is placed on kalah you have another turn
                        if (isLastStonePlacedOnKalah(pitDestination, this.turn)) {
                            this.turn = Player.SOUTH;
                        } else {
                            this.turn = Player.NORTH;
                        }
                        break;
                    case NORTH:
                        break;
                }
            } else {
                throw new RuntimeException("Pit doesn't have items");
            }
        } else {
            throw new IllegalArgumentException("Illegal pitId: " + pitId);
        }
    }

    public boolean isValidPit(final Integer pitId) {
        return pitId != KALAH_PLAYER_SOUTH && pitId != KALAH_PLAYER_NORTH;
    }

    private boolean pitHasItems(final Integer pitId) {
        return this.status.get(pitId) > 0;
    }

    private boolean isLastStonePlacedOnKalah(final Integer pitId, final Player turn) {
        if (turn == Player.SOUTH)
            return pitId == KALAH_PLAYER_SOUTH;
        if (turn == Player.NORTH)
            return pitId == KALAH_PLAYER_NORTH;
        return false;
    }

    private void processEmptyPitToPullOpponentStones(final Integer pitId, final Player turn) {
        switch (turn) {
            case SOUTH:
                if (pitId >= 1 && pitId <= 6) {
                    this.status.put(KALAH_PLAYER_SOUTH, this.status.get(KALAH_PLAYER_SOUTH) + this.status.get(14 - pitId) + 1);
                    //clear the pits
                    this.status.put(14 - pitId, 0);
                    this.status.put(pitId, 0);
                }
                break;
            case NORTH:
                if (pitId >= 8 && pitId <= 13) {
                    this.status.put(KALAH_PLAYER_NORTH, this.status.get(KALAH_PLAYER_NORTH) + this.status.get(14 - pitId) + 1);
                    //clear the pits
                    this.status.put(14 - pitId, 0);
                    this.status.put(pitId, 0);
                }
                break;
        }
    }

    private void initStonesBoard(ConcurrentHashMap<Integer, Integer> board, final Integer stones) {
        for (Integer pitId = 1; pitId < 15; pitId++) {
            if (isValidPit(pitId)) {
                board.put(pitId, stones);
            } else {
                board.put(pitId, 0);
            }
        }
    }
}
