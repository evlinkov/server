package ru.categorization;

import java.util.function.BinaryOperator;

public interface Weight {

    BinaryOperator<Double> standardWeight();

    BinaryOperator<Double> attitudeWeight();

}
