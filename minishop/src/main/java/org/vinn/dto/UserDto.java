package org.vinn.dto;

import java.time.LocalDateTime;

public class UserDto {
    private Long id;

    private String username;
    private String password;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserDto(){}

    public UserDto initialize(String username, String password){
        this.username = username;
        this.password = password;
        this.createdAt = LocalDateTime.now();
        return this;
    }

    public UserDto(Long id, String username, String password, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
