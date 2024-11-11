package kg.asiamotors.demo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String brand;
    private String model;
    private double engineVolume;

    @Min(value = 1901, message = "Минимальный возраст 1901 год")
    private int year;
    @Min(value = 0, message = "Цена должна быть > 0")
    private double price;

    public Car() {
    }

    public Car(int year, String model, String brand) {
        this.year = year;
        this.model = model;
        this.brand = brand;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getEngineVolume() {
        return engineVolume;
    }

    public void setEngineVolume(double engineVolume) {
        this.engineVolume = engineVolume;
    }

    @Min(value = 1901, message = "Минимальный возраст 1901 год")
    public int getYear() {
        return year;
    }

    public void setYear(@Min(value = 1901, message = "Минимальный возраст 1901 год") int year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
