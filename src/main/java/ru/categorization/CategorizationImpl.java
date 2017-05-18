package ru.categorization;

import java.util.List;
import ru.bktree.BKTree;
import ru.dao.ProductDao;
import ru.dao.CategoryDao;
import ru.entities.Product;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;

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
    private final double MINIMUM_MATCH_QUALITY = 0.1;

    // число категорий в базе данных
    private final int NUMBER_OF_CATEGORIES = 22;

    // номер категории, обозначающей категорию "другое"
    private final int DEFAULT_CATEGORY = 0;

    // число продуктов из базы, которые мы учитываем
    private final int MAXIMUM_NUMBER_OF_VALUES = 5;

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
        if (word.length() <= 5) {
            return 1;
        }
        return 2;
    }

    private List<String> getSimilarWordsFromDictionary(String partProductName) {
        int numberOfMistakes = getNumberOfMistakes(partProductName);
        List<String> similarWordsFromDictionary = new ArrayList<>();
        for (int mistakes = 0; mistakes < numberOfMistakes; ++mistakes) {
            similarWordsFromDictionary.addAll(bkTree.query(partProductName, mistakes).keySet());
            if (similarWordsFromDictionary.size() > 0) {
                break;
            }
        }
        return similarWordsFromDictionary;
    }

    private double getValue(double costProduct, List<Double> values) {
        if (costProduct < WeightImpl.EPS) {
            // берем сумму MAXIMUM_NUMBER_OF_VALUES значений похожести
            Collections.sort(values);
            int cnt = 0;
            double sum = 0;
            for (int i = values.size() - 1; i >= 0; --i) {
                sum += values.get(i);
                cnt++;
                if (cnt == MAXIMUM_NUMBER_OF_VALUES) {
                    break;
                }
            }
            return cnt;
        } else {
            // цена плохо определилась, берем число позиций в базе
            return values.size();
        }
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
        double probabilities[]; // вероятность каждой категории для названия в целом
        double values[]; // значения вероятностей для слов по отдельности
        int frequency[]; // частота встречания категорий в словах
        double costProduct = product.getPrice() / product.getAmount();
        probabilities = new double[NUMBER_OF_CATEGORIES];
        frequency = new int[NUMBER_OF_CATEGORIES];
        values = new double[NUMBER_OF_CATEGORIES];
        for (int i = 0; i < NUMBER_OF_CATEGORIES; ++i) {
            probabilities[i] = 1.0;
            frequency[i] = 0;
        }
        List<String> partsProductName = getWords(product.getName());
        for (String partProductName : partsProductName) {
            List<String> similarWordsFromDictionary = getSimilarWordsFromDictionary(partProductName);
            if (similarWordsFromDictionary.size() > 0) {
                List<Product> productsDataBase = new ArrayList<>();
                for (String wordFromDictionary : similarWordsFromDictionary) {
                    productsDataBase.addAll(productDao.getProductsByName(wordFromDictionary));
                }
                List< List<Double> > valuesOfLikenesses = new ArrayList<>();
                for (int i = 0; i < NUMBER_OF_CATEGORIES; ++i) {
                    valuesOfLikenesses.add(new ArrayList<>());
                }
                for (Product productDataBase : productsDataBase) {
                    double value = weight.apply(costProduct, productDataBase.getCost());
                    valuesOfLikenesses.get(productDataBase.getCategoryId()).add(value);
                }
                double sum = 0; // нормировка полученных значений
                for (int i = 1; i < NUMBER_OF_CATEGORIES; ++i) {
                    values[i] = getValue(costProduct, valuesOfLikenesses.get(i));
                    sum += values[i];
                }
                for (int i = 1; i < NUMBER_OF_CATEGORIES; ++i) {
                    if (values[i] > WeightImpl.EPS) {
                        // часть слова связана с категорией номер i
                        frequency[i]++;
                        probabilities[i] *= values[i] / sum;
                    }
                }
            }
        }

        int resultCategory = 0, maximumFrequency = 0;
        for (int i = 1; i < NUMBER_OF_CATEGORIES; ++i) {
            if (maximumFrequency < frequency[i]) {
                maximumFrequency = frequency[i];
            }
        }
        // рассматриваем только категории, которые связаны с наибольшим количеством слов
        if (maximumFrequency > 0) {
            for (int i = 0; i < maximumFrequency; ++i) {
                probabilities[DEFAULT_CATEGORY] *= MINIMUM_MATCH_QUALITY;
            }
            for (int i = 1; i < NUMBER_OF_CATEGORIES; ++i) {
                if (frequency[i] == maximumFrequency && probabilities[i] > probabilities[resultCategory]) {
                    resultCategory = i;
                }
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
