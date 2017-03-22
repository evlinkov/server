package ru.xmlparser;

import java.util.ArrayList;
import java.util.List;

public class ContextCheck {

    private Double totalSum;
    private List<ContextItem> items = new ArrayList<ContextItem>();

    public Double getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(Double totalSum) {
        this.totalSum = totalSum;
    }

    public List<ContextItem> getItems() {
        return items;
    }

    public void setItems(List<ContextItem> items) {
        this.items = items;
    }

    public void addItem(ContextItem item) {
        this.items.add(item);
    }

}
