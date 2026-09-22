package com.sportsconnect.backend.controller;

import com.sportsconnect.backend.dto.GameRequest;
import com.sportsconnect.backend.dto.GameResponse;
import com.sportsconnect.backend.dto.GameUpdateRequest;
import com.sportsconnect.backend.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<GameResponse> createGame(
            @RequestBody GameRequest request,
            Authentication authentication) {

        String userEmail = authentication.getName();

        GameResponse game = gameService.createGame(request, userEmail);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(game);
    }

    @GetMapping
    public ResponseEntity<List<GameResponse>> getAllGames() {
        return ResponseEntity.ok(gameService.getAllGames());
    }

    @PutMapping("/{gameId}")
    public ResponseEntity<GameResponse> updateGame(
            @PathVariable Long gameId,
            @RequestBody GameUpdateRequest request,
            Authentication authentication) {

        String userEmail = authentication.getName();

        GameResponse response =
                gameService.updateGame(gameId, request, userEmail);

        return ResponseEntity.ok(response);
    }
}