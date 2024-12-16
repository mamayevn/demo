package kg.asiamotors.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CarModelDTO {
    private String name;
    private int brandId;
    private int volumeId;
    private int transmissionId;
    private int driveId;
    private int fuelTypeId;

    public CarModelDTO() {}

}
