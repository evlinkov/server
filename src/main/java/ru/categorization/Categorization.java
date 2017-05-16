package ru.categorization;

import ru.model.Receipt;
import ru.model.RequestProduct;
import java.util.function.BinaryOperator;

public interface Categorization {

    void categorize(Receipt receipt);

    void categorize(RequestProduct requestProduct);

    void categorize(BinaryOperator<Double> weight, RequestProduct product);

}
