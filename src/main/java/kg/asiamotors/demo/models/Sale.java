package kg.asiamotors.demo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "sales")
@Data
@NoArgsConstructor
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "salesperson_id", nullable = false)
    @NotNull(message = "Продажник не может быть пустым")
    private SalesPerson salesPerson;

    @ManyToOne
    @JoinColumn(name = "car_model_id", nullable = false)
    @NotNull(message = "Модель не может быть пустой")
    private CarModel carModel;

    @Column(name = "sale_date")
    @NotNull(message = "Дата не может быть пустым")
    private LocalDate saleDate;

    @Column(name = "sale_price")
    @NotNull(message = "Данное поле должно быть заполненным")
    @Min(value = 0, message = "> 0 сом")
    private Double salePrice;

    @Column(name = "file_name")
    private String fileName;
}
