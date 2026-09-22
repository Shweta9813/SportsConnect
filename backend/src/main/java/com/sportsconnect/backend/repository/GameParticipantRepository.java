package com.sportsconnect.backend.repository;

import com.sportsconnect.backend.entity.GameParticipant;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GameParticipantRepository extends JpaRepository<GameParticipant, Long> {

    boolean existsByGameIdAndUserId(Long gameId, Long userId);

    long countByGameId(Long gameId);

    List<GameParticipant> findByGameId(Long gameId);

    void deleteByGameIdAndUserId(Long gameId, Long userId);
}