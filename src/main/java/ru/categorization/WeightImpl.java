package ru.categorization;

import javax.annotation.ManagedBean;
import java.util.function.BinaryOperator;
import javax.enterprise.context.ApplicationScoped;

@ManagedBean
@ApplicationScoped
public class WeightImpl implements Weight  {

    private final double EPS = 0.0001;

    /*
     *
     * @param costProduct
     *        цена продукта, которая была в чеке
     *
     * @param costDataBase
     *        цена продукта, с которым сравниваем из базы данных
     *
     * @return число (0,1], которое характеризует насколько товар подходит под товар из БД
    */

    @Override
    public BinaryOperator<Double> standardWeight() {
        return new BinaryOperator<Double>() {
            @Override
            public Double apply(Double costProduct, Double costDataBase) {
                return 1.0;
            }
        };
    }

    @Override
    public BinaryOperator<Double> attitudeWeight() {
        return new BinaryOperator<Double>() {
            @Override
            public Double apply(Double costProduct, Double costDataBase) {
                if (costProduct < EPS || costDataBase < EPS) {
                    // случай плохого распознавания цены
                    return 1.0;
                }
                // возвращаем отношение меньшей цены к большей
                return (costProduct > costDataBase) ?
                        costDataBase / costProduct :
                        costProduct / costDataBase;
            }
        };
    }

}
