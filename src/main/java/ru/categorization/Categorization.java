package ru.categorization;

import ru.model.RequestProduct;
import java.util.function.BinaryOperator;

public interface Categorization {

    void categorize(BinaryOperator<Double> weight, RequestProduct product);

}
