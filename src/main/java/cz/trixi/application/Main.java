package cz.trixi.application;

import cz.trixi.application.file.Downloader;
import cz.trixi.application.file.Unzipper;
import cz.trixi.application.parser.Parser;
import cz.trixi.application.persist.DatabaseConnection;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Application entry point
 */
public class Main {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, SQLException, InterruptedException, ClassNotFoundException {
        String xmlSource = "https://www.smartform.cz/download/kopidlno.xml.zip";
        String zipFilename = "kopidlno.xml.zip";
        String xmlName = "kopidlno.xml";

        Downloader.download(xmlSource, zipFilename);
        Unzipper.unzipFirstEntry(zipFilename, xmlName);

        Parser xmlParser = new Parser();
        xmlParser.loadXML(xmlName);
        xmlParser.parse();

        DatabaseConnection db = new DatabaseConnection();
        db.persistTowns(xmlParser.getTowns());
        db.persistDistricts(xmlParser.getDistricts());
    }
}