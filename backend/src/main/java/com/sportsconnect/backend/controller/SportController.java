package com.sportsconnect.backend.controller;

import com.sportsconnect.backend.entity.Sport;
import com.sportsconnect.backend.service.SportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sports")
public class SportController {

    private final SportService sportService;

    public SportController(SportService sportService) {
        this.sportService = sportService;
    }

    @PostMapping
    public ResponseEntity<Sport> createSport(@RequestBody Sport sport) {
        Sport savedSport = sportService.createSport(sport);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSport);
    }

    @GetMapping
    public ResponseEntity<List<Sport>> getAllSports() {
        return ResponseEntity.ok(sportService.getAllSports());
    }
}