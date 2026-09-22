package com.sportsconnect.backend.controller;

import com.sportsconnect.backend.dto.GameParticipantResponse;
import com.sportsconnect.backend.service.GameParticipantService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/games")
public class GameParticipantController {

    private final GameParticipantService gameParticipantService;

    public GameParticipantController(GameParticipantService gameParticipantService) {
        this.gameParticipantService = gameParticipantService;
    }

    @PostMapping("/{gameId}/join")
    public ResponseEntity<String> joinGame(
            @PathVariable Long gameId,
            Authentication authentication) {

        String userEmail = authentication.getName();

        String response =
                gameParticipantService.joinGame(gameId, userEmail);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{gameId}/participants")
    public ResponseEntity<List<GameParticipantResponse>> getParticipants(
            @PathVariable Long gameId) {

        List<GameParticipantResponse> participants =
                gameParticipantService.getParticipants(gameId);

        return ResponseEntity.ok(participants);
    }
    
    @DeleteMapping("/{gameId}/leave")
    public ResponseEntity<String> leaveGame(
            @PathVariable Long gameId,
            Authentication authentication) {

        String userEmail = authentication.getName();

        String response =
                gameParticipantService.leaveGame(gameId, userEmail);

        return ResponseEntity.ok(response);
    }

}