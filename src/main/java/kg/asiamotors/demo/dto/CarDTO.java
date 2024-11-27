package kg.asiamotors.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CarDTO {
    private String brand;
    private String model;
    private double engineVolume;
    private int year;
    private double price;

    public CarDTO() {
    }
}
