package ru.dao;

import ru.entities.Category;

import java.util.List;

public interface CategoryDao {

    void insertCategory(Category category);

    void updateCategory(Category category);

    void deleteCategory(Integer id);

    Category getCategoryById(Integer id);

    List<Category> getAllCategories();

}
