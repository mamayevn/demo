package kg.asiamotors.demo;

import kg.asiamotors.demo.models.SalesPerson;

public class SalesPersonSalesDTO {
    private SalesPerson salesPerson;
    private int carsSold;

    public SalesPerson getSalesPerson() {
        return salesPerson;
    }
    public void setSalesPerson(SalesPerson salesPerson) {
        this.salesPerson = salesPerson;
    }

    public int getCarsSold() {
        return carsSold;
    }

    public void setCarsSold(int carsSold) {
        this.carsSold = carsSold;
    }
}
