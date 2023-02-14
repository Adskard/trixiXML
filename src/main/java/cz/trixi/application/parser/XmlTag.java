package cz.trixi.application.parser;

import lombok.Getter;

@Getter
public enum XmlTag {
    TOWN_CODE("obi:Kod"),
    TOWN_NAME("obi:Nazev"),
    TOWN_TAG("vf:Obec"),
    DISTRICT_TAG("vf:CastObce"),
    DISTRICT_NAME("coi:Nazev"),
    DISTRICT_CODE("coi:Kod");
    private final String value;
    XmlTag(String value){
        this.value = value;
    }
}
