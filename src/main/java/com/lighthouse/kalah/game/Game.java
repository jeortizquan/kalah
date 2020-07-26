package com.lighthouse.kalah.game;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public class Game {

    private static final int NEW_GAME_ATTEMPT_LIMIT = 5;
    private static final int STONES_LIMIT = 6;
    public ConcurrentHashMap<Integer, Kalahah> games;

    public Game(ConcurrentHashMap<Integer, Kalahah> gameList) {
        this.games = gameList;
    }

    public Integer create() {
        Kalahah board = new Kalahah(STONES_LIMIT);
        int attempt = 0;
        while (exists(board.getGameId()) && attempt < NEW_GAME_ATTEMPT_LIMIT) {
            board.setGameId(ThreadLocalRandom.current().nextInt(Kalahah.GAMES_ORIGIN, Kalahah.GAMES_LIMIT));
            attempt++;
        }
        games.put(board.getGameId(), board);
        return board.getGameId();
    }

    private boolean exists(Integer gameId) {
        return this.games.get(gameId) != null;
    }

    public Kalahah move(Integer gameId, Integer pitId) {
        if (exists(gameId)) {
            Kalahah board = this.games.get(gameId);
            board.move(pitId);
            this.games.put(gameId, board);
            return this.games.get(gameId);
        } else {
            throw new RuntimeException("Invalid gameId!");
        }
    }
}
