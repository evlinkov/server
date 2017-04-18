package ru.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

public class DatabaseConfig {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);

    private static final String HOST = "localhost";
    private static final String SCHEMA = "categories";
    private static final String PORT = "3306";
    private static final String USER = "root";
    private static final String PASSWORD = "rfhvf123";

    private static final String TABLE_PRODUCT = "product";
    private static final String TABLE_PRODUCT_NAME_PRODUCT = "name_product";
    private static final String TABLE_PRODUCT_CATEGORY_ID = "category_id";

    private static final String TABLE_CATEGORY = "category";
    private static final String TABLE_CATEGORY_CATEGORY_ID = "category_id";
    private static final String TABLE_CATEGORY_NAME_CATEGORY = "name_category";

    public String getHost() {
        return HOST;
    }

    public String getSchema() {
        return SCHEMA;
    }

    public String getPort() {
        return PORT;
    }

    public String getUser() {
        return USER;
    }

    public String getPassword() {
        return PASSWORD;
    }

    public String getTableProduct() {
        return TABLE_PRODUCT;
    }

    public String getTableProductNameProduct() {
        return TABLE_PRODUCT_NAME_PRODUCT;
    }

    public String getTableProductCategoryId() {
        return TABLE_PRODUCT_CATEGORY_ID;
    }

    public String getTableCategory() {
        return TABLE_CATEGORY;
    }

    public String getTableCategoryCategoryId() {
        return TABLE_CATEGORY_CATEGORY_ID;
    }

    public String getTableCategoryNameCategory() {
        return TABLE_CATEGORY_NAME_CATEGORY;
    }

    public static String getURL() {
        return "jdbc:mysql://" + HOST + ":" + PORT + "/" + SCHEMA + "?useSSL=false";
    }

    public static DataSource getDataSource() {
        logger.info("Received a request for shipment a DataSource");
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUsername(USER);
        driverManagerDataSource.setPassword(PASSWORD);
        driverManagerDataSource.setUrl(getURL());
        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        logger.info("DataSource is sent");
        return driverManagerDataSource;
    }

}
