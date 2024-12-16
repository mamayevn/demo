package kg.asiamotors.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
public class CustomerDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String occupation;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    public CustomerDTO() {}

    public CustomerDTO(String firstName, String lastName, String phoneNumber, String email, String occupation, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.occupation = occupation;
        this.birthDate = birthDate;
    }
}
