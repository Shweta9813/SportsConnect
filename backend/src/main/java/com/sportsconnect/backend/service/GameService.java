package com.sportsconnect.backend.service;

import com.sportsconnect.backend.dto.GameRequest;
import com.sportsconnect.backend.dto.GameResponse;
import com.sportsconnect.backend.dto.GameUpdateRequest;
import com.sportsconnect.backend.entity.Game;
import com.sportsconnect.backend.entity.Sport;
import com.sportsconnect.backend.entity.User;
import com.sportsconnect.backend.repository.GameRepository;
import com.sportsconnect.backend.repository.SportRepository;
import com.sportsconnect.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final SportRepository sportRepository;
    private final UserRepository userRepository;

    public GameService(GameRepository gameRepository,
                       SportRepository sportRepository,
                       UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.sportRepository = sportRepository;
        this.userRepository = userRepository;
    }

    public GameResponse createGame(GameRequest request, String userEmail) {

        User creator = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Sport sport = sportRepository.findById(request.getSportId())
                .orElseThrow(() ->
                        new RuntimeException("Sport not found"));

        Game game = new Game();

        game.setTitle(request.getTitle());
        game.setDescription(request.getDescription());
        game.setLocation(request.getLocation());
        game.setGameDate(request.getGameDate());
        game.setMaxPlayers(request.getMaxPlayers());
        game.setCreatedAt(LocalDateTime.now());

        game.setCreator(creator);
        game.setSport(sport);

        Game savedGame = gameRepository.save(game);

        return new GameResponse(
            savedGame.getId(),
            savedGame.getTitle(),
            savedGame.getDescription(),
            savedGame.getLocation(),
            savedGame.getGameDate(),
            savedGame.getMaxPlayers(),
            savedGame.getCreatedAt(),
            savedGame.getCreator().getId(),
            savedGame.getCreator().getName(),
            savedGame.getCreator().getEmail(),
            savedGame.getSport().getId(),
            savedGame.getSport().getName()
        );
    }

    public List<GameResponse> getAllGames() {

        return gameRepository.findAll()
            .stream()
            .map(game -> new GameResponse(
                    game.getId(),
                    game.getTitle(),
                    game.getDescription(),
                    game.getLocation(),
                    game.getGameDate(),
                    game.getMaxPlayers(),
                    game.getCreatedAt(),
                    game.getCreator().getId(),
                    game.getCreator().getName(),
                    game.getCreator().getEmail(),
                    game.getSport().getId(),
                    game.getSport().getName()
            ))
            .toList();
    }

    public GameResponse updateGame(
                Long gameId,
                GameUpdateRequest request,
                String userEmail) {

        // 1. Find the game
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() ->
                        new RuntimeException("Game not found"));

        // 2. Find the logged-in user
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        // 3. Check whether the logged-in user is the creator
        if (!game.getCreator().getId().equals(user.getId())) {
                throw new RuntimeException(
                        "Only the game creator can update this game");
        }

        // 4. Update allowed fields
        game.setTitle(request.getTitle());
        game.setDescription(request.getDescription());
        game.setLocation(request.getLocation());
        game.setGameDate(request.getGameDate());
        game.setMaxPlayers(request.getMaxPlayers());

        // 5. Save updated game
        Game updatedGame = gameRepository.save(game);

        // 6. Return safe response DTO
        return new GameResponse(
                updatedGame.getId(),
                updatedGame.getTitle(),
                updatedGame.getDescription(),
                updatedGame.getLocation(),
                updatedGame.getGameDate(),
                updatedGame.getMaxPlayers(),
                updatedGame.getCreatedAt(),
                updatedGame.getCreator().getId(),
                updatedGame.getCreator().getName(),
                updatedGame.getCreator().getEmail(),
                updatedGame.getSport().getId(),
                updatedGame.getSport().getName()
        );
    }

}