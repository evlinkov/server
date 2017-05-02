package ru.dao;

import ru.entities.Category;

import java.util.List;
import java.sql.ResultSet;
import javax.inject.Inject;
import java.sql.SQLException;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

@ManagedBean
public class CategoryDaoImpl implements CategoryDao {

    @Inject
    DataBaseConfig dataBase;

    private JdbcTemplate jdbcTemplate;

    public CategoryDaoImpl() { }

    public CategoryDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void initialization() {
        jdbcTemplate = new JdbcTemplate(dataBase.getDataSource());
    }

    private RowMapper<Category> rowMapperCategory = new RowMapper<Category>() {
        @Override
        public Category mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Category category = new Category();
            category.setId(resultSet.getInt("id"));
            category.setName(resultSet.getString("name"));
            return category;
        }
    };

    @Override
    public void insertCategory(Category category) {
        String sql = "INSERT INTO category (id, name) VALUES (?, ?)";
        jdbcTemplate.update(sql, category.getId(), category.getName());
    }

    @Override
    public void updateCategory(Category category) {
        String sql = "UPDATE category SET name=? WHERE id=?";
        jdbcTemplate.update(sql, category.getName(), category.getId());
    }

    @Override
    public void deleteCategory(Integer id) {
        String sql = "DELETE FROM category WHERE id=?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Category getCategoryById(Integer id)  {
        String sql = "SELECT * FROM category WHERE id=?";
        List<Category> categories = jdbcTemplate.query(sql, rowMapperCategory, id);
        if (categories.size() > 1) {
            throw new RuntimeException();
        }
        return categories.isEmpty() ? null : categories.get(0);
    }

    @Override
    public List<Category> getAllCategories() {
        String sql = "SELECT * FROM category";
        return jdbcTemplate.query(sql, rowMapperCategory);
    }

}
