package kg.asiamotors.demo.dto;

import java.time.LocalDate;

public class SaleDTO {
    private Long id;
    private Long customerId;
    private Long salesPersonId;
    private Long carModelId;
    private LocalDate saleDate;
    private Double salePrice;
    private String fileName;

    public SaleDTO() {}

    public SaleDTO(Long id, Long customerId, Long salesPersonId, Long carModelId, LocalDate saleDate, Double salePrice, String fileName) {
        this.id = id;
        this.customerId = customerId;
        this.salesPersonId = salesPersonId;
        this.carModelId = carModelId;
        this.saleDate = saleDate;
        this.salePrice = salePrice;
        this.fileName = fileName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getSalesPersonId() {
        return salesPersonId;
    }

    public void setSalesPersonId(Long salesPersonId) {
        this.salesPersonId = salesPersonId;
    }

    public Long getCarModelId() {
        return carModelId;
    }

    public void setCarModelId(Long carModelId) {
        this.carModelId = carModelId;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
