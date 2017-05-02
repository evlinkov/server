package ru.model;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElementWrapper;

@XmlRootElement(name = "receipt")
public class Receipt {

    private double total;
    private String vendor;
    private List<RequestProduct> products;

    @XmlElement(name = "vendor")
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getVendor() {
        return vendor;
    }


    @XmlElement(name="total")
    public void setTotal(double total) {
        this.total = total;
    }

    public double getTotal() {
        return total;
    }


    @XmlElementWrapper(name = "products")
    @XmlElement(name = "product")
    public void setProducts(List <RequestProduct> products) {
        this.products = products;
    }

    public List<RequestProduct> getProducts() {
        return products;
    }

}
