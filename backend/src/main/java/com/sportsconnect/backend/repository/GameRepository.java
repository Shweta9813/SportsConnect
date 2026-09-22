package com.sportsconnect.backend.repository;

import com.sportsconnect.backend.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}