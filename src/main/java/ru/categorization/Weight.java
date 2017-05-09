package ru.categorization;

public interface Weight {

    /**
     *
     * @param costProduct
     *        цена продукта, которая была в чеке
     *
     * @param costDataBase
     *        цена продукта, с которым сравниваем из базы данных
     *
     * @return число (0,1], которое характеризует насколько товар подходит под товар из БД
     */

    double standardWeight(double costProduct, double costDataBase);

    double attitudeWeight(double costProduct, double costDataBase);

}
