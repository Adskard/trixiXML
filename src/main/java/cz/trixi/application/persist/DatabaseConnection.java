package cz.trixi.application.persist;

import cz.trixi.application.element.District;
import cz.trixi.application.element.Town;
import lombok.extern.java.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

@Log
public class DatabaseConnection {
    private int waitCycles = 4;

    private Connection connection;

    public DatabaseConnection() throws SQLException, InterruptedException, ClassNotFoundException {
        establishConnection();
    }

    /**
     * Creates a jdbc connection to a database.
     * Gets its params through environment.
     *
     * @throws SQLException errors encountered during SQL statement commits
     * @throws InterruptedException error waiting for connection
     * @throws ClassNotFoundException jdbc driver exception
     */
    public void establishConnection() throws SQLException, InterruptedException, ClassNotFoundException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            log.log(Level.SEVERE, "No PostgreSQL JDBC Driver in path!");
            throw ex;
        }

        log.info("PostgreSQL JDBC Driver registered!");

        //wait for db connection
        log.info("Establishing jdbc database connection");
        for (int i = 0; i < waitCycles; i++) {
            try{
                connection = DriverManager.getConnection(System.getenv("POSTGRES_URL"),
                        System.getenv("POSTGRES_USERNAME"),System.getenv("POSTGRES_PASSWORD"));
            }
            catch(SQLException ex){
                log.log(Level.WARNING, "Waiting for database");
                Thread.sleep(1000);
                if(i >= waitCycles-1){
                    throw ex;
                }
            }
        }
    }

    /**
     * Persists a town to database table
     * @param town town to be saved to db
     * @throws SQLException statement problem
     */
    public void persistTown(Town town) throws SQLException {
        String statement = String.format("INSERT INTO town (name, code) VALUES (\'%s\', \'%s\')"
        ,town.getName(), town.getCode());
        log.info("Executing statement " + statement);
        connection.createStatement().execute(statement);
    }

    public void persistTowns(List<Town> towns) throws SQLException {
        for(Town town : towns){
            persistTown(town);
        }
    }

    /**
     * Persists a district to database table
     * @param district district to be saved to db
     * @throws SQLException
     */
    public void persistDistrict(District district) throws SQLException {
        String statement = String.format("INSERT INTO district (name, code, town_code) VALUES (\'%s\', \'%s\', \'%s\')"
                ,district.getName(), district.getCode(), district.getTownCode());
        log.info("Executing statement " + statement);
        connection.createStatement().execute(statement);
    }

    public void persistDistricts(List<District> districts) throws SQLException {
        for(District district : districts){
            persistDistrict(district);
        }
    }

}
