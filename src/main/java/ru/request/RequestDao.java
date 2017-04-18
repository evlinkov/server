package ru.request;

import java.io.InputStream;

public interface RequestDao {

    String getCategories(InputStream inputStream) throws Exception;

}
