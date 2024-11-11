package kg.asiamotors.demo.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @Column(name = "person_id")
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

    public Person() {
    }
    public Person(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

@OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Car> cars;

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotEmpty(message = "Заполните строку Имя") @Size(min = 2, max = 30, message = "Имя должно быть между 2 и 30 символами") String getName() {
        return name;
    }

    public void setName(@NotEmpty(message = "Заполните строку Имя") @Size(min = 2, max = 30, message = "Имя должно быть между 2 и 30 символами") String name) {
        this.name = name;
    }

    @Min(value = 18, message = "Возраст должен быть > 18 лет")
    public int getAge() {
        return age;
    }

    public void setAge(@Min(value = 18, message = "Возраст должен быть > 18 лет") int age) {
        this.age = age;
    }

    public @NotEmpty(message = "Заполните почту") @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotEmpty(message = "Заполните почту") @Email String email) {
        this.email = email;
    }
}
