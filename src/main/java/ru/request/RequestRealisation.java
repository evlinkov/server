package ru.request;

import ru.database.DataBaseImpl;
import ru.database.DatabaseConfig;
import ru.xmlparser.Receipts;
import ru.xmlparser.ObjectCreator;

import java.io.InputStream;

public class RequestRealisation implements RequestDao {

    private static DataBaseImpl dataBaseImpl = new DataBaseImpl(new DatabaseConfig());

    @Override
    public String getCategories(InputStream inputStream) throws Exception {
        Receipts receipts = new ObjectCreator().getReceipts(inputStream);
        return dataBaseImpl.getCategories(receipts);
    }

}
