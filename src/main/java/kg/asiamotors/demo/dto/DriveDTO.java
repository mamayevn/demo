package kg.asiamotors.demo.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DriveDTO {
    private int id;
    private String name;

    public DriveDTO(String name) {
        this.name = name;
    }

    public DriveDTO() {}

    public DriveDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

}
