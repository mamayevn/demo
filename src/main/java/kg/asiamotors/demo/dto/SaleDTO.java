package kg.asiamotors.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Setter
@Getter
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

}
