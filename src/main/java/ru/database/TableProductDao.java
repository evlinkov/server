package ru.database;

import java.util.List;

public interface TableProductDao {

    void insertProduct(TableProduct tableProduct);

    void updateProduct(TableProduct tableProduct);

    void deleteProduct(String nameProduct);

    List<TableProduct> getProductByNameProduct(String nameProduct);

    List<TableProduct> getAllProducts();

}