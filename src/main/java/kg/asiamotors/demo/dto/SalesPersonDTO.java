package kg.asiamotors.demo.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesPersonDTO {

    private int id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String position;

    public SalesPersonDTO() {}

    public SalesPersonDTO(String firstName, String lastName, String phoneNumber, String email, String position) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.position = position;
    }

    public SalesPersonDTO(int id, String firstName, String lastName, String phoneNumber, String email, String position) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.position = position;
    }

}
