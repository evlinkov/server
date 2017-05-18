package ru.categorization;

import org.junit.Test;
import org.junit.Ignore;
import org.slf4j.Logger;
import ru.dao.ProductDao;
import ru.dao.CategoryDao;
import java.io.FileReader;
import ru.distance.Distance;
import ru.bktree.BKTreeImpl;
import ru.dao.ProductDaoImpl;
import ru.dao.CategoryDaoImpl;
import java.io.BufferedReader;
import org.slf4j.LoggerFactory;
import ru.model.RequestProduct;
import ru.distance.LevenshteinDistance;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class CategorizationTest {

    private static final Logger logger = LoggerFactory.getLogger(CategorizationTest.class);

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

        Categorization categorization = new CategorizationImpl(weight, bkTree, productDao, categoryDao);

        BufferedReader reader = new BufferedReader(new FileReader("./src/main/resources/products.txt"));
        String line;
        int cnt = 0;
        logger.info("start");
        while ((line = reader.readLine()) != null) {
            cnt++;
            String names[] = line.split(", ");
            RequestProduct requestProduct = new RequestProduct();
            requestProduct.setName(names[0]);
            requestProduct.setPrice(Double.valueOf(names[1]));
            requestProduct.setAmount(1);
            requestProduct.setCategory("");
            categorization.categorize(requestProduct);
            if (!requestProduct.getCategory().equals("Groceries & Gourmet Food")) {
                System.out.print(requestProduct.getName() + " - ");
                System.out.println(requestProduct.getCategory());
            }
        }
        logger.info("end");
        System.out.println(cnt);
        reader.close();
    }

}
