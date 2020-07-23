package com.lighthouse.kalah.service;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class GameController {

    @RequestMapping("/")
    public String index() {
        return "Welkom naar Kalah Game!";
    }

    @PostMapping( path = "/game", produces = MediaType.APPLICATION_JSON_VALUE)
    public String create(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @DeleteMapping(path = "/game")
    public String endGame(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Game %s ended",name);
    }

    @PutMapping( path = "/game/{gameId}/pits/{pitId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String move(@RequestParam(value = "gameId", defaultValue = "0") Long gameId,
                               @RequestParam(value = "pitId", defaultValue = "0") Long pitId) {
        return String.format("Move %d :: %d!", gameId, pitId);
    }
}
