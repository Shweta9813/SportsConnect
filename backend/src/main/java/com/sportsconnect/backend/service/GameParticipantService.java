package com.sportsconnect.backend.service;

import com.sportsconnect.backend.dto.GameParticipantResponse;
import com.sportsconnect.backend.entity.Game;
import com.sportsconnect.backend.entity.GameParticipant;
import com.sportsconnect.backend.entity.User;
import com.sportsconnect.backend.repository.GameParticipantRepository;
import com.sportsconnect.backend.repository.GameRepository;
import com.sportsconnect.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GameParticipantService {

    private final GameParticipantRepository gameParticipantRepository;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    public GameParticipantService(
            GameParticipantRepository gameParticipantRepository,
            GameRepository gameRepository,
            UserRepository userRepository) {

        this.gameParticipantRepository = gameParticipantRepository;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    public String joinGame(Long gameId, String userEmail) {

        // 1. Find the game
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        // 2. Find the logged-in user
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 3. Check if user already joined
        if (gameParticipantRepository.existsByGameIdAndUserId(gameId, user.getId())) {
            throw new RuntimeException("You have already joined this game");
        }

        // 4. Check whether game is full
        long participantCount = gameParticipantRepository.countByGameId(gameId);

        if (participantCount >= game.getMaxPlayers()) {
            throw new RuntimeException("Game is full");
        }

        // 5. Create participant
        GameParticipant participant = new GameParticipant();

        participant.setGame(game);
        participant.setUser(user);
        participant.setJoinedAt(LocalDateTime.now());

        // 6. Save participant
        gameParticipantRepository.save(participant);

        return "Game joined successfully";
    }

    public List<GameParticipantResponse> getParticipants(Long gameId) {

        // Check whether the game exists
        gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        // Find all participants of the game
        List<GameParticipant> participants =
                gameParticipantRepository.findByGameId(gameId);

        // Convert entities into response DTOs
        return participants.stream()
                .map(participant -> new GameParticipantResponse(
                        participant.getUser().getId(),
                        participant.getUser().getName(),
                        participant.getUser().getEmail(),
                        participant.getUser().getLocation(),
                        participant.getUser().getSkillLevel()
                ))
                .toList();
    }

    @Transactional
    public String leaveGame(Long gameId, String userEmail) {

        // 1. Check whether the game exists
        gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        // 2. Find the logged-in user
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 3. Check whether the user has joined this game
        if (!gameParticipantRepository.existsByGameIdAndUserId(
                gameId, user.getId())) {

            throw new RuntimeException("You have not joined this game");
        }

        // 4. Remove the participant record
        gameParticipantRepository.deleteByGameIdAndUserId(
                gameId, user.getId());

        return "You have left the game successfully";
    }
}