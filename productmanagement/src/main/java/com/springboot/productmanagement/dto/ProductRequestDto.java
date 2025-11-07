package com.springboot.productmanagement.dto;

/*
public record ProductRequestDto (
        String name,
        String shortDescription,
        String longDescription,
        Double price
) {


}
*/
public class ProductRequestDto {
    private String name;
    private String shortDescription;
    private String longDescription;
    private Double price;

    public ProductRequestDto() {}

    public ProductRequestDto(String name, String shortDescription, String longDescription, Double price) {
        this.name = name;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.price = price;
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