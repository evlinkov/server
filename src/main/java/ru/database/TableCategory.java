package ru.database;

public class TableCategory {

    private Integer categoryId;
    private String nameCategory;

    public TableCategory() { }

    public TableCategory (Integer categoryId, String nameCategory) {
        this.categoryId = categoryId;
        this.nameCategory = nameCategory;

    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public String getNameCategory() {
        return nameCategory;
    }

}
