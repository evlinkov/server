package ru.xmlparser;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "receipts")
public class Receipts {

    private List<Receipt> receipts;

    @XmlElement(name = "receipt")
    public List<Receipt> getReceipts() {
        return receipts;
    }

    public void setReceipts(List<Receipt> receipts) {
        this.receipts = receipts;
    }

    public Receipts() { }

    public Receipts(List<Receipt> receipts) {
        this.receipts = receipts;
    }

}
