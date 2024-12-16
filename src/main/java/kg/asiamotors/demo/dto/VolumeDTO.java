package kg.asiamotors.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class VolumeDTO implements Serializable{
    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;

    public VolumeDTO() {}

    public VolumeDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public VolumeDTO(String name) {
        this.name = name;
    }

}
