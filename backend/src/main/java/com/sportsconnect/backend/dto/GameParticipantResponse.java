package com.sportsconnect.backend.dto;

public class GameParticipantResponse {

    private Long userId;
    private String name;
    private String email;
    private String location;
    private String skillLevel;

    public GameParticipantResponse(
            Long userId,
            String name,
            String email,
            String location,
            String skillLevel) {

        this.userId = userId;
        this.name = name;
        this.email = email;
        this.location = location;
        this.skillLevel = skillLevel;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getLocation() {
        return location;
    }

    public String getSkillLevel() {
        return skillLevel;
    }
}