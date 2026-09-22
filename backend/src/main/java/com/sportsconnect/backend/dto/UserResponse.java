package com.sportsconnect.backend.dto;

public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private String location;
    private String skillLevel;

    public UserResponse(Long id, String name, String email,
                        String location, String skillLevel) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.location = location;
        this.skillLevel = skillLevel;
    }

    public Long getId() {
        return id;
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