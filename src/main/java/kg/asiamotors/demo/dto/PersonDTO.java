package kg.asiamotors.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class PersonDTO {
    private int id;

    @NotEmpty(message = "Заполните строку имя")
    @Size(min = 2, max = 30, message = "Имя должно быть между 2 и 30 символами")
    private String name;

    @Min(value = 18, message = "Возраст должен быть > 18 лет")
    private int age;

    @NotEmpty(message = "Заполните почту")
    @Email
    private String email;

    public PersonDTO() {
    }

    public PersonDTO(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public PersonDTO(int id, String name, int age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }
}
