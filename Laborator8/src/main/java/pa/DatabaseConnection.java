package pa;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//NOTE(Al-Andrew): Singleton
public class DatabaseConnection {
    private static DatabaseConnection instance = null;
    private static Connection inner = null; //NOTE(Al-Andrew): we actually wrap this, so it should be static

    private DatabaseConnection() {
        //TODO(Al-Andrew): error-checking
        try {
            //step1 load the driver class
            Class.forName("oracle.jdbc.driver.OracleDriver");

            //step2 create  the connection object
            inner = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe", "STUDENT_PA", "STUDENT_PA");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static DatabaseConnection getInstance() {
        // Lazy instantiation
        if (instance == null)
            instance = new DatabaseConnection();

        return instance;
    }

    public static Connection getConnection() {
        // Lazy instantiation
        if (instance == null)
            instance = new DatabaseConnection();

        return inner;
    }

    public void resetConnection() {
        instance = null;
        try {
            if (inner != null)
                inner.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        inner = null;
    }

    public void constructDB() {
        Statement stmt;
        try {
            stmt = DatabaseConnection.getConnection().createStatement();

            List<String> lines = Files.readAllLines(Path.of("./create_tables.sql"));
            for(var query : lines) {
                query = query.strip();
                query = query.substring(0, query.length() - 1);
                try {
                    stmt.executeQuery(query);
                } catch (SQLSyntaxErrorException ex) {
                    //Nothing to do here. occurs when trying to delete the tables that don't exist
                    //System.out.println(ex.toString());
                }
                //System.out.println(query);
            }

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void populateDB() {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Path.of("./dataset.csv"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        lines.remove(0); //We don't need the header of the csv file
        Set<String> continents = new HashSet<>();
        List<Country> countries = new ArrayList<>();
        List<City> cities = new ArrayList<>();

        for(var line : lines) {
            String[] parts = line.split(",");
            continents.add(parts[5]);
        }
        ContinentDAO continentDOA = new ContinentDAODatabase();
        for(var continent : continents)
            continentDOA.insertContinent(new Continent(continent));

        for(var line : lines) {
            String[] parts = line.split(",");
            countries.add(new Country(parts[0], parts[4], continentDOA.findByName(parts[5])));
        }
        CountryDAO countryDAO = new CountryDAODatabase();
        for(var country : countries)
            countryDAO.insertCountry(country);

        for(var line : lines) {
            String[] parts = line.split(",");
            cities.add(new City(parts[1], countryDAO.findByName(parts[0]), true,  Float.parseFloat(parts[2]), Float.parseFloat(parts[3])));
        }

        CityDAO cityDAO = new CityDAODatabase();
        for(var city : cities)
            cityDAO.insertCity(city);

    }


}
