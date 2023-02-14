package cz.trixi.application.element;

import lombok.Getter;
import lombok.Setter;

/**
 * Town table representation
 */
@Getter
@Setter
public class Town {
    public Town(){

    }
    public Town(String code, String name) {
        this.code = code;
        this.name = name;
    }

    private String code;
    private String name;

    @Override
    public String toString() {
        return "Town{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
