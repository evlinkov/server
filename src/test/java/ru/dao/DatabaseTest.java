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

    /*@Test
    @Ignore
    public void testTableProduct() {
        DatabaseConfig databaseConfig = new DatabaseConfig();
        DataBaseImpl dataBaseImpl = new DataBaseImpl(databaseConfig);
        assertEquals(dataBaseImpl.getAllProducts().size(), 4);
        assertEquals(dataBaseImpl.getProductByNameProduct("audi").get(0).getCategoryId().toString(), "2");
        assertEquals(dataBaseImpl.getProductByNameProduct("biscuit").get(0).getCategoryId().toString(), "3");
        assertEquals(dataBaseImpl.getProductByNameProduct("cola").get(0).getCategoryId().toString(), "1");
        assertEquals(dataBaseImpl.getProductByNameProduct("fanta").get(0).getCategoryId().toString(), "1");
        assertEquals(dataBaseImpl.getProductByNameProduct("sprite").size(), 0);
        TableProduct tableProduct = new TableProduct("sprite", 4);
        dataBaseImpl.insertProduct(tableProduct);
        assertEquals(dataBaseImpl.getAllProducts().size(), 5);
        assertEquals(dataBaseImpl.getProductByNameProduct("sprite").get(0).getCategoryId().toString(), "4");
        tableProduct.setCategoryId(3);
        dataBaseImpl.updateProduct(tableProduct);
        assertEquals(dataBaseImpl.getAllProducts().size(), 5);
        assertEquals(dataBaseImpl.getProductByNameProduct("sprite").get(0).getCategoryId().toString(), "3");
        dataBaseImpl.deleteProduct("sprite");
        assertEquals(dataBaseImpl.getAllCategories().size(), 4);
    }

    @Test
    @Ignore
    public void testGetCategories() {
        List<Receipt> receiptList = new ArrayList<Receipt>();

        List<Item> items1 = new ArrayList<Item>();
        Item audi = new Item(new Name("audi"));
        Item cola = new Item(new Name("cola"));
        items1.add(audi);
        items1.add(cola);
        RecognizedItems recognizedItems1 = new RecognizedItems(items1);
        Receipt receipt1 = new Receipt(recognizedItems1);
        receiptList.add(receipt1);

        List<Item> items2 = new ArrayList<Item>();
        Item sprite = new Item(new Name("sprite"));
        Item fanta = new Item(new Name("fanta"));
        Item biscuit = new Item(new Name("biscuit"));
        items2.add(sprite);
        items2.add(fanta);
        items2.add(biscuit);
        RecognizedItems recognizedItems2 = new RecognizedItems(items2);
        Receipt receipt2 = new Receipt(recognizedItems2);
        receiptList.add(receipt2);

        Receipts receipts = new Receipts(receiptList);

        DatabaseConfig databaseConfig = new DatabaseConfig();
        DataBaseImpl dataBaseImpl = new DataBaseImpl(databaseConfig);

        System.out.println(dataBaseImpl.getCategories(receipts));
    }

    private void addProducts(DataBaseImpl dataBaseImpl) {
        TableProduct tableProduct = new TableProduct();
        for (int i = 1; i <= 500000; ++i) {
            tableProduct.setNameProduct("name" + i);
            tableProduct.setCategoryId(i);
            dataBaseImpl.insertProduct(tableProduct);
        }
        System.out.println(dataBaseImpl.getAllProducts().size());
    }

    private void deleteProducts(DataBaseImpl dataBaseImpl) {
        for (int i = 1; i <= 11111; ++i) {
            dataBaseImpl.deleteProduct("name" + i);
        }
        System.out.println(dataBaseImpl.getAllProducts().size());
    }

    @Test
    @Ignore
    public void testTimeSequential() {
        DatabaseConfig databaseConfig = new DatabaseConfig();
        DataBaseImpl dataBaseImpl = new DataBaseImpl(databaseConfig);
        addProducts(dataBaseImpl);
        logger.info("start");
        for (int i = 1; i <= 10000; ++i) {
            TableProduct tableProduct = dataBaseImpl.getObjectProductByNameProduct("name" + i);
            assertEquals(tableProduct.getCategoryId().toString(), String.valueOf(i));
        }
        logger.info("end");
        deleteProducts(dataBaseImpl);
    }

    @Test
    @Ignore
    public void testTimeParallel() {
        DatabaseConfig databaseConfig = new DatabaseConfig();
        DataBaseImpl dataBaseImpl = new DataBaseImpl(databaseConfig);
        addProducts(dataBaseImpl);
        List<String> names = new ArrayList<String>();
        for (int i = 1; i <= 10000; ++i) {
            names.add("name" + i);
        }
        logger.info("start");
        List<TableProduct> products = names.parallelStream().map(nameProduct -> dataBaseImpl.getObjectProductByNameProduct(nameProduct))
                                           .collect(Collectors.toList());
        System.out.println(products.size());
        logger.info("end");
        deleteProducts(dataBaseImpl);
    }*/

}
