package ru.dao;

import javax.sql.DataSource;
import javax.annotation.ManagedBean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@ManagedBean
public class DataBaseConfigImpl implements DataBaseConfig {

    private final String USERNAME = "root";
    private final String PASSWORD = "rfhvf123";
    private final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
    private final String URL = "jdbc:mysql://localhost:3306/categories?useSSL=false";

    @Override
    public DataSource getDataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUsername(USERNAME);
        driverManagerDataSource.setPassword(PASSWORD);
        driverManagerDataSource.setUrl(URL);
        driverManagerDataSource.setDriverClassName(DRIVER_CLASS);
        return driverManagerDataSource;
    }

}
