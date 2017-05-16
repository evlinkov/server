package ru.categorization;

import java.util.List;
import ru.bktree.BKTree;
import ru.dao.ProductDao;
import ru.dao.CategoryDao;
import ru.entities.Product;
import javax.inject.Inject;
import java.util.ArrayList;

import ru.model.Receipt;
import ru.model.RequestProduct;
import javax.annotation.ManagedBean;
import java.util.function.BinaryOperator;
import javax.enterprise.context.ApplicationScoped;

@ManagedBean
@ApplicationScoped
public class CategorizationImpl implements Categorization {

    @Inject
    Weight weight;
    @Inject
    BKTree bkTree;
    @Inject
    ProductDao productDao;
    @Inject
    CategoryDao categoryDao;

    // минимальное значение, чтобы признать категорию подходящей
    private final double MINIMUM_MATCH_QUALITY = 0.15;

    // число категорий в базе данных
    private final int NUMBER_OF_CATEGORIES = 22;

    // номер категории, обозначающей категорию "другое"
    private final int DEFAULT_CATEGORY = 0;

    public CategorizationImpl() {

    }

    public CategorizationImpl(Weight weight, BKTree bkTree, ProductDao productDao, CategoryDao categoryDao) {
        this.weight = weight;
        this.bkTree = bkTree;
        this.productDao = productDao;
        this.categoryDao = categoryDao;
    }

    private boolean digit(char symbol) {
        return symbol >= '0' && symbol <= '9';
    }

    private boolean letter(char symbol) {
        return symbol >= 'a' && symbol <= 'z';
    }

    private List<String> getWords(String part) {
        part = part.toLowerCase();
        List<String> words = new ArrayList<>();
        StringBuilder word = new StringBuilder("");
        for (int i = 0; i < part.length(); ++i) {
            char symbol = part.charAt(i);
            if (digit(symbol) || letter(symbol)) {
                word.append(symbol);
            } else {
                if (word.length() > 0) {
                    words.add(word.toString());
                    word = new StringBuilder("");
                }
            }
        }
        if (word.length() > 0) {
            words.add(word.toString());
        }
        return words;
    }

    private int getNumberOfMistakes(String word) {
        // здесь подразумевается, что чем меньше букв в слове, тем меньше в нем возможно ошибок
        if (word.length() <= 4) {
            return 1;
        }
        if (word.length() <= 8) {
            return 2;
        }
        return 3;
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
        List<String> partsProductName = getWords(product.getName());
        for (String partProductName : partsProductName) {
            int numberOfMistakes = getNumberOfMistakes(partProductName);
            List<String> similarWordsFromDictionary = new ArrayList<>();
            for (int mistakes = 0; mistakes < numberOfMistakes; ++mistakes) {
                similarWordsFromDictionary.addAll(bkTree.query(partProductName, mistakes).keySet());
                if (similarWordsFromDictionary.size() > 0) {
                    break;
                }
            }
            /* нормировка такая, что суммарный вклад от 1-ого слова должен быть равен 1 и
                он равномерен от каждого похожего слова из словаря */
            if (similarWordsFromDictionary.size() > 0) {
                double sum = 0; // нормировка полученных значений
                List<Product> productsDataBase = new ArrayList<>();
                for (String wordFromDictionary : similarWordsFromDictionary) {
                    productsDataBase.addAll(productDao.getProductsByName(wordFromDictionary));
                }
                for (Product product1 : productsDataBase) {
                    System.out.println(product1.getCategoryId());
                }
                for (Product productDataBase : productsDataBase) {
                    double costProduct = product.getPrice() / product.getAmount();
                    sum += weight.apply(costProduct, productDataBase.getCost());
                }
                for (Product productDataBase : productsDataBase) {
                    double costProduct = product.getPrice() / product.getAmount();
                    weights[productDataBase.getCategoryId()] +=
                            weight.apply(costProduct, productDataBase.getCost()) / sum;
                }
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

    @Override
    public void categorize(Receipt receipt) {
        for (RequestProduct requestProduct : receipt.getProducts()) {
            categorize(weight.attitudeWeight(),requestProduct);
        }
    }

    @Override
    public void categorize(RequestProduct requestProduct) {
        categorize(weight.attitudeWeight(), requestProduct);
    }

}
