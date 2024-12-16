package kg.asiamotors.demo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "person")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Заполните строку имя")
    @Size(min = 2, max = 30, message = "Имя должно быть между 2 и 30 символами")
    @Column(name = "first_name")
    private String name;

    @Min(value = 18, message = "Возраст должен быть > 18 лет")
    @Column(name = "age")
    private int age;

    @Column(name = "email")
    @NotEmpty(message = "Заполните почту")
    @Email
    private String email;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Car> cars;

    public Person(int id, String name, int age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }
}

