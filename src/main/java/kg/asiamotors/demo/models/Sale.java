package kg.asiamotors.demo.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "salesperson_id", nullable = false)
    private SalesPerson salesPerson;

    @ManyToOne
    @JoinColumn(name = "car_model_id", nullable = false)
    private CarModel carModel;
    private LocalDate saleDate;
    private Double salePrice;
    private String fileName;
}