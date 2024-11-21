package kg.asiamotors.demo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sales_person")
@Data
@NoArgsConstructor
public class SalesPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name", nullable = false)
    @NotEmpty(message = "Введите имя")
    @Size(min = 2, max = 30, message = "Имя должно быть > 2 символов")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotEmpty(message = "Введите фамилию")
    @Size(min = 2, max = 30, message = "Фамилия должна быть > 2 символов")
    private String lastName;

    @Column(name = "phone_number", nullable = false)
    @NotEmpty(message = "Введите номер телефона")
    private String phoneNumber;

    @Column(name = "email", nullable = false, unique = true)
    @NotEmpty(message = "Введите почту")
    @Email(message = "Введите настоящую почту")
    private String email;

    @Column(name = "position", nullable = false)
    @NotEmpty(message = "Введите должность")
    private String position;
}
