package org.vinn.dto;

public class CategoryDto {
    private Long id;
    private String name;
    private Long createdBy;
    private String createdByUsername;

    public CategoryDto() {}

    public CategoryDto(Long id, String name, Long createdBy) {
        this.id = id;
        this.name = name;
        this.createdBy = createdBy;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name; // ""
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public String getCreatedByUsername() {
        return createdByUsername;
    }

    public void setCreatedByUsername(String createdByUsername) {
        System.out.println("Pass 6");
        this.createdByUsername = createdByUsername;
    }
}
