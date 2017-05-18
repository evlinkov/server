package ru.dao;

import java.util.List;
import java.sql.ResultSet;
import ru.entities.Product;
import javax.inject.Inject;
import java.sql.SQLException;
import javax.annotation.PostConstruct;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class ProductDaoImpl implements ProductDao {

    @Inject
    DataBaseConfig dataBase;

    private JdbcTemplate jdbcTemplate;
    private final int DEFAULT_CATEGORY_ID = 0;

    public ProductDaoImpl() { }

    public ProductDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void initialization() {
        jdbcTemplate = new JdbcTemplate(dataBase.getDataSource());
    }

    private RowMapper<Product> rowMapperProduct = new RowMapper<Product>() {
        @Override
        public Product mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Product category = new Product();
            category.setId(resultSet.getInt("id"));
            category.setName(resultSet.getString("name"));
            category.setCost(resultSet.getDouble("cost"));
            category.setCategoryId(resultSet.getInt("category_id"));
            return category;
        }
    };

    @Override
    public void insertProduct(Product product) {
        String sql = "INSERT INTO product (id, name, cost, category_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, product.getId(), product.getName(), product.getCost(), product.getCategoryId());
    }

    @Override
    public void updateProduct(Product product) {
        String sql = "UPDATE product SET name=?, cost=?, category_id=? WHERE id=?";
        jdbcTemplate.update(sql, product.getName(), product.getCost(), product.getCategoryId(), product.getId());
    }

    @Override
    public void deleteProduct(String name) {
        String sql = "DELETE FROM product WHERE name=?";
        jdbcTemplate.update(sql, name);
    }

    @Override
    public void deleteProduct(Integer id) {
        String sql = "DELETE FROM product WHERE id=?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Product getProductByName(String name) {
        String sql = "SELECT * FROM product WHERE name=?";
        List<Product> products = jdbcTemplate.query(sql, rowMapperProduct, name);
        if (products.size() > 1) {
            throw new RuntimeException();
        }
        return products.isEmpty() ? null : products.get(0);
    }

    @Override
    public Product getProductById(Integer id) {
        String sql = "SELECT * FROM product WHERE id=?";
        List<Product> products = jdbcTemplate.query(sql, rowMapperProduct, id);
        if (products.size() > 1) {
            throw new RuntimeException();
        }
        return products.isEmpty() ? null : products.get(0);
    }

    @Override
    public List<Product> getAllProducts() {
        String sql = "SELECT * FROM product";
        return jdbcTemplate.query(sql, rowMapperProduct);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        String sql = "SELECT * FROM product WHERE name LIKE ?";
        return jdbcTemplate.query(sql, rowMapperProduct, "% " + name + " %");
    }

}
