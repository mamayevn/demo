package kg.asiamotors.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
public class CustomerDTO {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String occupation;
    private LocalDate birthDate;

    public CustomerDTO() {}
    }
