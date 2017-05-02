package ru.dao;

import org.junit.Test;
import org.junit.Ignore;
import ru.entities.Product;
import ru.entities.Category;
import static org.junit.Assert.assertEquals;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DatabaseTest {

    @Test
    @Ignore
    public void testTableCategory() {

        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUsername("root");
        driverManagerDataSource.setPassword("rfhvf123");
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/categories?useSSL=false");
        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");

        CategoryDaoImpl categoryDaoImpl = new CategoryDaoImpl(new JdbcTemplate(driverManagerDataSource));
        assertEquals(categoryDaoImpl.getAllCategories().size(), 4);
        assertEquals(categoryDaoImpl.getCategoryById(0).getName(), "other");
        assertEquals(categoryDaoImpl.getCategoryById(1).getName(), "drink");
        assertEquals(categoryDaoImpl.getCategoryById(2).getName(), "car");
        assertEquals(categoryDaoImpl.getCategoryById(3).getName(), "eat");
        assertEquals(categoryDaoImpl.getCategoryById(4), null);
        Category category = new Category();
        category.setId(4);
        category.setName("test");
        categoryDaoImpl.insertCategory(category);
        assertEquals(categoryDaoImpl.getAllCategories().size(), 5);
        assertEquals(categoryDaoImpl.getCategoryById(4).getName(), "test");
        category.setName("testAfterUpdate");
        categoryDaoImpl.updateCategory(category);
        assertEquals(categoryDaoImpl.getAllCategories().size(), 5);
        assertEquals(categoryDaoImpl.getCategoryById(4).getName(), "testAfterUpdate");
        categoryDaoImpl.deleteCategory(4);
        assertEquals(categoryDaoImpl.getAllCategories().size(), 4);
    }

    @Test
    @Ignore
    public void testTableProduct() {

        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUsername("root");
        driverManagerDataSource.setPassword("rfhvf123");
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/categories?useSSL=false");
        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");

        ProductDaoImpl productDaoImpl = new ProductDaoImpl(new JdbcTemplate(driverManagerDataSource));
        assertEquals(productDaoImpl.getAllProducts().size(), 4);
        assertEquals(productDaoImpl.getProductByName("fanta").getId().toString(), "1");
        assertEquals(productDaoImpl.getProductByName("sprite").getCost().toString(), "12.3");
        assertEquals(productDaoImpl.getProductByName("strawberry").getCost().toString(), "11.1");
        assertEquals(productDaoImpl.getProductByName("audi").getCategoryId().toString(), "2");
        assertEquals(productDaoImpl.getProductByName("cola"), null);
        Product product = new Product();
        product.setId(5);
        product.setName("cola");
        product.setCost(13.37);
        product.setCategoryId(2);
        productDaoImpl.insertProduct(product);
        assertEquals(productDaoImpl.getAllProducts().size(), 5);
        assertEquals(productDaoImpl.getProductByName("cola").getCost().toString(), "13.37");
        product.setCost(10.00);
        productDaoImpl.updateProduct(product);
        assertEquals(productDaoImpl.getAllProducts().size(), 5);
        assertEquals(productDaoImpl.getProductByName("cola").getCost().toString(), "10.0");
        productDaoImpl.deleteProduct("cola");
        assertEquals(productDaoImpl.getAllProducts().size(), 4);

        assertEquals(String.valueOf(productDaoImpl.getCategoryIdByName("fanta")), "1");
        assertEquals(String.valueOf(productDaoImpl.getCategoryIdByName("sprite")), "1");
        assertEquals(String.valueOf(productDaoImpl.getCategoryIdByName("strawberry")), "3");
        assertEquals(String.valueOf(productDaoImpl.getCategoryIdByName("audi")), "2");
    }

}
