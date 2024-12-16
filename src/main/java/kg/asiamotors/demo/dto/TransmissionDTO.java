package kg.asiamotors.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransmissionDTO {
    private int id;
    private String name;

    public TransmissionDTO() {
    }

    public TransmissionDTO(String name) {
        this.name = name;
    }

    public TransmissionDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
