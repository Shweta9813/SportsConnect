package com.sportsconnect.backend.repository;

import com.sportsconnect.backend.entity.Sport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SportRepository extends JpaRepository<Sport, Long> {
}