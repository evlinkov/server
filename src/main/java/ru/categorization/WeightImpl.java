package ru.categorization;

public class WeightImpl implements Weight  {

    @Override
    public double standardWeight(double costProduct, double costDataBase) {
        return 1.0;
    }

    @Override
    public double attitudeWeight(double costProduct, double costDataBase) {
        // возвращаем отношение меньшей цены к большей
        return (costProduct > costDataBase) ?
                costDataBase / costProduct :
                costProduct / costDataBase;
    }

}
