package ru.request;

import ru.model.Receipt;
import ru.dao.ProductDao;
import ru.dao.CategoryDao;
import javax.inject.Inject;
import ru.model.RequestProduct;
import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;

@ManagedBean
@ApplicationScoped
public class ReceiptServiceImpl implements ReceiptService {

    @Inject
    ProductDao productDao;
    @Inject
    CategoryDao categoryDao;

    private int getCategoryId(String productName) {
        return productDao.getCategoryIdByName(productName);
    }

    private String getCategoryName(int id) {
        return categoryDao.getCategoryById(id).getName();
    }

    private String getCategoryName(String productName) {
        int id = getCategoryId(productName);
        return getCategoryName(id);
    }

    @Override
    public void getCategories(Receipt receipt) {
        for (RequestProduct product : receipt.getProducts()) {
            product.setCategory(getCategoryName(product.getName()));
        }
    }

}
