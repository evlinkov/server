package ru.database;

import com.google.gson.Gson;
import ru.xmlparser.Receipts;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class DataBaseImpl implements TableCategoryDao, TableProductDao {

    private static final Logger logger = LoggerFactory.getLogger(DataBaseImpl.class);

    private static final Integer DEFAULT_CATEGORY_ID = 0;

    private JdbcTemplate jdbcTemplate;
    private DatabaseConfig databaseConfig;

    public DataBaseImpl(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
        DataSource dataSource = DatabaseConfig.getDataSource();
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<TableCategory> rowMapperCategory = new RowMapper<TableCategory>() {
        @Override
        public TableCategory mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            TableCategory tableCategory = new TableCategory();
            tableCategory.setCategoryId(resultSet.getInt(databaseConfig.getTableCategoryCategoryId()));
            tableCategory.setNameCategory(resultSet.getString(databaseConfig.getTableCategoryNameCategory()));
            return tableCategory;
        }
    };

    private RowMapper<TableProduct> rowMapperProduct = new RowMapper<TableProduct>() {
        @Override
        public TableProduct mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            TableProduct tableProduct = new TableProduct();
            tableProduct.setNameProduct(resultSet.getString(databaseConfig.getTableProductNameProduct()));
            tableProduct.setCategoryId(resultSet.getInt(databaseConfig.getTableProductCategoryId()));
            return tableProduct;
        }
    };

    @Override
    public void insertCategory(TableCategory tableCategory) {
        String sql = "INSERT INTO " + databaseConfig.getTableCategory() + "(" +
                     databaseConfig.getTableCategoryCategoryId() + ", " +
                     databaseConfig.getTableCategoryNameCategory() + ")" +
                     "VALUES (?, ?)";
        jdbcTemplate.update(sql, tableCategory.getCategoryId(), tableCategory.getNameCategory());
    }

    @Override
    public void updateCategory(TableCategory tableCategory) {
        String sql = "UPDATE " + databaseConfig.getTableCategory() + " SET " +
                     databaseConfig.getTableCategoryNameCategory() + "=? WHERE " +
                     databaseConfig.getTableCategoryCategoryId() + "=?";
        jdbcTemplate.update(sql, tableCategory.getNameCategory(), tableCategory.getCategoryId());
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        String sql = "DELETE FROM " + databaseConfig.getTableCategory() + " WHERE " +
                     databaseConfig.getTableCategoryCategoryId() + "=?";
        jdbcTemplate.update(sql, categoryId);
    }

    @Override
    public List<TableCategory> getCategoryById(Integer categoryId) {
        String sql = "SELECT * FROM " + databaseConfig.getTableCategory() + " WHERE " +
                     databaseConfig.getTableCategoryCategoryId() + "=?";
        return jdbcTemplate.query(sql, rowMapperCategory, categoryId);
    }

    public TableCategory getObjectCategoryById (Integer categoryId) {
        List<TableCategory> categories = getCategoryById(categoryId);
        if (categories.size() == 0) {
            return null;
        } else {
            if (categories.size() == 1) {
                return categories.get(0);
            } else {
                logger.info("Method getCategoryById returned two or more results");
                return categories.get(0);
            }
        }
    }

    @Override
    public List<TableCategory> getAllCategories() {
        String sql = "SELECT * FROM " + databaseConfig.getTableCategory();
        return jdbcTemplate.query(sql, rowMapperCategory);
    }

    @Override
    public void insertProduct(TableProduct tableProduct) {
        String sql = "INSERT INTO " + databaseConfig.getTableProduct() + "(" +
                databaseConfig.getTableProductNameProduct() + ", " +
                databaseConfig.getTableProductCategoryId() + ")" +
                "VALUES (?, ?)";
        jdbcTemplate.update(sql, tableProduct.getNameProduct(), tableProduct.getCategoryId());
    }

    @Override
    public void updateProduct(TableProduct tableProduct) {
        String sql = "UPDATE " + databaseConfig.getTableProduct() + " SET " +
                databaseConfig.getTableProductCategoryId() + "=? WHERE " +
                databaseConfig.getTableProductNameProduct() + "=?";
        jdbcTemplate.update(sql, tableProduct.getCategoryId(), tableProduct.getNameProduct());
    }

    @Override
    public void deleteProduct(String nameProduct) {
        String sql = "DELETE FROM " + databaseConfig.getTableProduct() + " WHERE " +
                databaseConfig.getTableProductNameProduct() + "=?";
        jdbcTemplate.update(sql, nameProduct);
    }

    @Override
    public List<TableProduct> getProductByNameProduct(String nameProduct) {
        String sql = "SELECT * FROM " + databaseConfig.getTableProduct() + " WHERE " +
                databaseConfig.getTableProductNameProduct() + "=?";
        return jdbcTemplate.query(sql, rowMapperProduct, nameProduct);
    }

    public TableProduct getObjectProductByNameProduct (String nameProduct) {
        List<TableProduct> products = getProductByNameProduct(nameProduct);
        if (products.size() == 0) {
            return null;
        } else {
            if (products.size() == 1) {
                return products.get(0);
            } else {
                logger.info("Method getProductByNameProduct returned two or more results");
                return products.get(0);
            }
        }
    }

    @Override
    public List<TableProduct> getAllProducts() {
        String sql = "SELECT * FROM " + databaseConfig.getTableProduct();
        return jdbcTemplate.query(sql, rowMapperProduct);
    }

    private SimpleProduct createSimpleProduct(String nameProduct, String nameCategory) {
        SimpleProduct simpleProduct = new SimpleProduct();
        simpleProduct.setNameProduct(nameProduct);
        simpleProduct.setCategory(nameCategory);
        return simpleProduct;
    }

    // search algorithm
    private SimpleProduct getSimpleProduct(String nameProduct) {
        TableProduct tableProduct = getObjectProductByNameProduct(nameProduct);
        Integer categoryId = (tableProduct == null) ? DEFAULT_CATEGORY_ID : tableProduct.getCategoryId();
        return createSimpleProduct(nameProduct, getObjectCategoryById(categoryId).getNameCategory());
    }

    public String getCategories (Receipts receipts) {
        List<String> nameProducts = receipts.getReceipts()
                                       .parallelStream()
                                       .map(receipt -> receipt.getRecognizedItems()
                                                              .getItems()
                                                              .stream()
                                                              .map(item -> item.getName().getText())
                                                              .collect(Collectors.toList()))
                                       .flatMap(List::stream)
                                       .collect(Collectors.toList());
        // !!! compare parallelStream and Stream when we are working with a db !!!
        List<SimpleProduct> categories = nameProducts.parallelStream()
                                                     .map(this::getSimpleProduct)
                                                     .collect(Collectors.toList());
        return (new Gson().toJson(categories));
    }

}
