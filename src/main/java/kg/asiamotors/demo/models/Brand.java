package kg.asiamotors.demo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Brand", indexes = @Index(name = "idx_brand_name", columnList = "name"))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Brand implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<CarModel> models = new ArrayList<>();
}