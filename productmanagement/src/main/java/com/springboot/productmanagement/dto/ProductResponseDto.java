package com.springboot.productmanagement.dto;

/*
public record ProductResponseDto (
    Long id,
    String name,
    String shortDescription,
    String longDescription,
    Double price
){}
*/

public class ProductResponseDto {
    private Long id;
    private String name;
    private String shortDescription;
    private String longDescription;
    private Double price;

    public ProductResponseDto() {}

    public ProductResponseDto(Long id, String name, String shortDescription, String longDescription, Double price) {
        this.id = id;
        this.name = name;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}