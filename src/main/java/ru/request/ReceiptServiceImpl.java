package ru.request;

import ru.dao.ProductDao;
import ru.dao.CategoryDao;
import ru.entities.Category;

import javax.inject.Inject;
import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;

@ManagedBean
@ApplicationScoped
public class ReceiptServiceImpl implements ReceiptService {

    @Inject
    ProductDao productDao;
    @Inject
    CategoryDao categoryDao;

    /*@Override
    public String getCategories(InputStream inputStream) throws Exception {
        Receipts receipts = new ObjectCreator().getReceipts(inputStream);
        return dataBaseImpl.getCategories(receipts);
    }*/

    @Override
    public CategoryDao getCategoryDao() {
        return categoryDao;
    }

    @Override
    public ProductDao getProductDao() {
        return productDao;
    }

}
