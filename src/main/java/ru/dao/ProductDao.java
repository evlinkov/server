package ru.dao;

import java.util.List;
import ru.entities.Product;

public interface ProductDao {

    void insertProduct(Product product);

    void updateProduct(Product product);

    void deleteProduct(String name);

    void deleteProduct(Integer id);

    Product getProductByName(String name);

    Product getProductById(Integer id);

    List<Product> getAllProducts();

    List<Product> getProductsByName(String name);

}
