package org.vinn.dto;

// DTO -> Data Transfer Object, Data Transfer Layer
// Why DTO? -> 1. I dont't want to give database model to UI, 2. Best for Security
public class GenreRequestDto {
    private String name;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public GenreRequestDto(String name) {
        this.name = name;
    }
}
