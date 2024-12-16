package kg.asiamotors.demo.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FuelTypeDTO {
    private int id;
    private String name;

    public FuelTypeDTO() {}

    public FuelTypeDTO(String name) {
        this.name = name;
    }

    public FuelTypeDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
