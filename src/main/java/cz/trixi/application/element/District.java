package cz.trixi.application.element;

import lombok.Getter;
import lombok.Setter;

/**
 * District table representation
 */
@Getter
@Setter
public class District {
    private String code;
    private String name;
    private String townCode;

    public District(String code, String name, String townCode) {
        this.code = code;
        this.name = name;
        this.townCode = townCode;
    }

    public District(){

    }

    @Override
    public String toString() {
        return "District{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", townCode='" + townCode + '\'' +
                '}';
    }
}
