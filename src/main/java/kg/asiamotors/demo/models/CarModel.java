package kg.asiamotors.demo.models;

import jakarta.persistence.*;
import kg.asiamotors.demo.repasitories.BrandRepository;

@Entity
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "volume_id")
    private Volume volume;  // Изменили тип с String на Volume

    @ManyToOne
    @JoinColumn(name = "transmission_id")
    private Transmission transmission;  // Изменили тип с String на Transmission

    @ManyToOne
    @JoinColumn(name = "drive_id")
    private Drive drive;  // Изменили тип с String на Drive

    @ManyToOne
    @JoinColumn(name = "fuel_type_id")
    private FuelType fuelType;  // Изменили тип с String на FuelType

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Volume getVolume() {
        return volume;
    }

    public void setVolume(Volume volume) {
        this.volume = volume;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    public Drive getDrive() {
        return drive;
    }

    public void setDrive(Drive drive) {
        this.drive = drive;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
