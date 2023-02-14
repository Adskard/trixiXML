package cz.trixi.application.parser;

import cz.trixi.application.element.District;
import cz.trixi.application.element.Town;
import lombok.Getter;
import lombok.extern.java.Log;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.*;
import java.io.IOException;
import java.util.ArrayList;

@Log
@Getter
public class Parser {
    private final XMLHandler handler = new XMLHandler();

    private String filePath;
    private final SAXParserFactory parserFactory = SAXParserFactory.newInstance();

    public void loadXML(String sourcePath) {
        log.info(String.format("Loading xml file %s", sourcePath));
        filePath = sourcePath;
    }

    /**
     * Parses xml
     * @throws ParserConfigurationException SAX parser creation exception
     * @throws SAXException errors encountered during parsing
     * @throws IOException error loading XML
     */
    public void parse() throws ParserConfigurationException, SAXException, IOException {
        log.info("Parsing start");
        parserFactory.newSAXParser().parse(filePath, handler);
    }

    public ArrayList<Town> getTowns(){
        return handler.getTowns();
    }

    public ArrayList<District> getDistricts(){
        return handler.getDistricts();
    }

    @Getter
    class XMLHandler extends DefaultHandler{
        private StringBuilder value = new StringBuilder();

        private boolean parsingDistrict = false;
        private District district = new District();
        private Town town = new Town();

        private final ArrayList<Town> towns = new ArrayList<>();
        private final ArrayList<District> districts = new ArrayList<>();

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes){
            if(XmlTag.DISTRICT_CODE.getValue().equals(qName)){
                value = new StringBuilder();
            }
            if(XmlTag.DISTRICT_TAG.getValue().equals(qName)){
                district = new District();
                parsingDistrict = true;
            }
            if(XmlTag.DISTRICT_NAME.getValue().equals(qName)){
                value = new StringBuilder();
            }
            if(XmlTag.TOWN_TAG.getValue().equals(qName)){
                town = new Town();
            }
            if(XmlTag.TOWN_CODE.getValue().equals(qName)){
                value = new StringBuilder();
            }
            if(XmlTag.TOWN_NAME.getValue().equals(qName)){
                value = new StringBuilder();
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName){
            if(XmlTag.DISTRICT_TAG.getValue().equals(qName)){
                districts.add(district);
                district = new District();
            }
            if(XmlTag.DISTRICT_CODE.getValue().equals(qName)){
                district.setCode(value.toString());
            }
            if(XmlTag.DISTRICT_NAME.getValue().equals(qName)){
                district.setName(value.toString());
            }

            if(XmlTag.TOWN_TAG.getValue().equals(qName)){
                towns.add(town);
                town = new Town();
            }
            if(XmlTag.TOWN_CODE.getValue().equals(qName)){
                if(parsingDistrict){
                    district.setTownCode(value.toString());
                }
                else{
                    town.setCode(value.toString());
                }
            }
            if(XmlTag.TOWN_NAME.getValue().equals(qName)){
                town.setName(value.toString());
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            value.append(ch, start, length);
        }

    }
}
