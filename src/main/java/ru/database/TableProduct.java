package ru.database;

public class TableProduct {

    private String nameProduct;
    private Integer categoryId;

    public TableProduct() { }

    public TableProduct(String nameProduct, Integer categoryId) {
        this.nameProduct = nameProduct;
        this.categoryId = categoryId;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

}
