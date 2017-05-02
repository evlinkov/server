package ru.entities;

public class Product {

    private Integer id;
    private String name;
    private Double cost;
    private Integer categoryId;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getCost() {
        return cost;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

}
