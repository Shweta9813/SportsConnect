package com.sportsconnect.backend.dto;

import java.time.LocalDateTime;

public class GameResponse {

    private Long id;
    private String title;
    private String description;
    private String location;
    private LocalDateTime gameDate;
    private Integer maxPlayers;
    private LocalDateTime createdAt;

    private Long creatorId;
    private String creatorName;
    private String creatorEmail;

    private Long sportId;
    private String sportName;

    public GameResponse(
            Long id,
            String title,
            String description,
            String location,
            LocalDateTime gameDate,
            Integer maxPlayers,
            LocalDateTime createdAt,
            Long creatorId,
            String creatorName,
            String creatorEmail,
            Long sportId,
            String sportName) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.gameDate = gameDate;
        this.maxPlayers = maxPlayers;
        this.createdAt = createdAt;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.creatorEmail = creatorEmail;
        this.sportId = sportId;
        this.sportName = sportName;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public LocalDateTime getGameDate() {
        return gameDate;
    }

    public Integer getMaxPlayers() {
        return maxPlayers;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public String getCreatorEmail() {
        return creatorEmail;
    }

    public Long getSportId() {
        return sportId;
    }

    public String getSportName() {
        return sportName;
    }
}