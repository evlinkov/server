package ru.database;

import com.google.gson.annotations.SerializedName;

public class SimpleProduct {

    @SerializedName(value = "name")
    private String nameProduct;

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    @SerializedName(value = "category")
    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    SimpleProduct() { }

    SimpleProduct(String nameProduct, String category) {
        this.nameProduct = nameProduct;
        this.category = category;
    }

}
