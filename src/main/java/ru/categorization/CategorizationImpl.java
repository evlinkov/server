package ru.categorization;

import java.util.List;
import ru.dao.ProductDao;
import ru.dao.CategoryDao;
import ru.entities.Product;
import javax.inject.Inject;
import ru.model.RequestProduct;
import javax.annotation.ManagedBean;
import java.util.function.BinaryOperator;
import javax.enterprise.context.ApplicationScoped;

@ManagedBean
@ApplicationScoped
public class CategorizationImpl implements Categorization {

    @Inject
    ProductDao productDao;
    @Inject
    CategoryDao categoryDao;

    // минимальное значение, чтобы признать категорию подходящей
    private final double MINIMUM_MATCH_QUALITY = 0.25;

    // номер категории, обозначающей категорию "другое"
    private final int DEFAULT_CATEGORY = 0;

    // число категорий в базе данных
    private final int NUMBER_OF_CATEGORIES = 22;

    // метод будет заменен на соответствущий из библиотеки для работы со словарем
    private String changeProductName(String name) {
        return "";
    }

    /**
     *
     * @param weight оператор, который возвращает число из (0,1] характеризующее
     *               насколько товар из чека и товар из базы данных похожи в зависимости от их цен
     *
     * @param product продукт из чека, который нужно категоризировать
     *
     */

    @Override
    public void categorize(BinaryOperator<Double> weight, RequestProduct product) {
        double weights[];
        weights = new double[NUMBER_OF_CATEGORIES];
        for (int i = 1; i < NUMBER_OF_CATEGORIES; ++i) {
            weights[i] = 0;
        }
        weights[DEFAULT_CATEGORY] = MINIMUM_MATCH_QUALITY;
        for (String partProductName : product.getName().split(" ")) {
            List<Product> productsDataBase = productDao.getProductsByName(changeProductName(partProductName));
            double sum = 0; // нужно для нормировки полученных значений
            for (Product productDataBase : productsDataBase) {
                double costProduct = product.getPrice() / product.getAmount();
                sum += weight.apply(costProduct, productDataBase.getCost());
            }
            for (Product productDataBase : productsDataBase) {
                double costProduct = product.getPrice() / product.getAmount();
                weights[productDataBase.getCategoryId()] += weight.apply(costProduct, productDataBase.getCost()) / sum;
            }
        }
        int resultCategory = 0;
        for (int i = 1; i < NUMBER_OF_CATEGORIES; ++i) {
            if (weights[i] > weights[resultCategory]) {
                resultCategory = i;
            }
        }
        product.setCategory(categoryDao.getCategoryById(resultCategory).getName());
    }

}
