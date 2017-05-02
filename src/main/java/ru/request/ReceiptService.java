package ru.request;

import ru.dao.ProductDao;
import ru.dao.CategoryDao;

public interface ReceiptService {

    //String getCategories(InputStream inputStream) throws Exception;
    CategoryDao getCategoryDao();
    ProductDao getProductDao();

}
