package ru.categorization;

import org.junit.Test;
import org.junit.Ignore;
import ru.dao.ProductDao;
import ru.dao.CategoryDao;
import java.io.FileReader;
import ru.distance.Distance;
import ru.bktree.BKTreeImpl;
import ru.dao.ProductDaoImpl;
import ru.dao.CategoryDaoImpl;
import java.io.BufferedReader;
import ru.model.RequestProduct;
import ru.distance.LevenshteinDistance;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


public class CategorizationTest {

    @Test
    @Ignore
    public void testCategorize() throws Exception {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUsername("root");
        driverManagerDataSource.setPassword("rfhvf123");
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/categories?useSSL=false");
        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");

        ProductDao productDao = new ProductDaoImpl(new JdbcTemplate(driverManagerDataSource));
        CategoryDao categoryDao = new CategoryDaoImpl(new JdbcTemplate(driverManagerDataSource));
        Distance distance = new LevenshteinDistance();
        Weight weight = new WeightImpl();

        BKTreeImpl bkTree = new BKTreeImpl(distance, productDao);
        bkTree.initialization();

        System.out.println(bkTree.query("bottom", 0));

        Categorization categorization = new CategorizationImpl(weight, bkTree, productDao, categoryDao);

        BufferedReader reader = new BufferedReader(new FileReader("./src/main/resources/food.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            String names[] = line.split(", ");
            RequestProduct requestProduct = new RequestProduct();
            requestProduct.setName(names[0]);
            requestProduct.setPrice(Double.valueOf(names[1]));
            requestProduct.setAmount(1);
            requestProduct.setCategory("");
            categorization.categorize(requestProduct);
            System.out.println(requestProduct.getCategory());
        }

        reader.close();
    }

}
