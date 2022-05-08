package pa.db;

import pa.entity.City;
import pa.entity.Continent;
import pa.entity.Country;
import pa.repository.JDBCContinentRepository;
import pa.repository.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Maker {
    private static Maker instance;

    static public Maker the() {
        if(instance == null)
            instance = new Maker();
        return instance;
    }

    public void  constructDB() {
        Statement stmt;
        try {
            stmt = JDBCManager.getConnection().createStatement();

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

    public <ContinentRepo extends Repository<Continent>,
            CountryRepo extends Repository<Country>,
            CityRepo extends Repository<City>> void populateDB(Class<ContinentRepo> cnt, Class<CountryRepo> cry, Class<CityRepo> cty) {
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

        Repository<Continent> continentDOA = null;
        try {
            continentDOA = cnt.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        for(var continent : continents)
            continentDOA.create(new Continent(continent));

        for(var line : lines) {
            String[] parts = line.split(",");
            countries.add(new Country(parts[0], parts[4], continentDOA.findByName(parts[5])));
        }
        Repository<Country> countryDAO = null;
        try {
            countryDAO = cry.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        for(var country : countries)
            countryDAO.create(country);

        for(var line : lines) {
            String[] parts = line.split(",");
            cities.add(new City(parts[1], countryDAO.findByName(parts[0]), 1l,  Float.parseFloat(parts[2]), Float.parseFloat(parts[3])));
        }

        Repository<City> cityDAO = null;
        try {
            cityDAO = cty.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        for(var city : cities)
            cityDAO.create(city);

    }
}
