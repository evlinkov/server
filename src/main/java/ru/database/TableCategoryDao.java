package ru.database;

import java.util.List;

public interface TableCategoryDao {

    void insertCategory(TableCategory tableCategory);

    void updateCategory(TableCategory tableCategory);

    void deleteCategory(Integer categoryId);

    List<TableCategory> getCategoryById(Integer categoryId);

    List<TableCategory> getAllCategories();

}
