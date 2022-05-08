package pa.repository;

import pa.db.JDBCManager;
import pa.entity.City;
import pa.entity.Country;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JDBCCityRepository extends Repository<City>{
    @Override
    public List<City> getAll() {
        List<City> res = new ArrayList<>();
        Statement stmt;
        try {
            stmt = JDBCManager.getConnection().createStatement();
            //City(long id, String name, Long isCapital, Float latitude, Float longitude, Country country, Long population)
            ResultSet rs = stmt.executeQuery("select id, name, is_capital, latitude, longitude, country_id, population from cities");
            while (rs.next()) {
                Country country = (new JDBCCountryRepository()).findById(rs.getInt("COUNTRY_ID"));
                boolean is_capital = rs.getString("IS_CAPITAL") != "N";
                res.add(new City(rs.getLong("ID"), rs.getString("NAME"), is_capital?1l:0l,
                        rs.getFloat("LATITUDE"), rs.getFloat("LONGITUDE"),
                        country, rs.getLong("POPULATION")));
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    public City findById(long id) {
        City res = null;
        Statement stmt;
        try {
            stmt = JDBCManager.getConnection().createStatement();

            ResultSet rs = stmt.executeQuery("select id, name, is_capital, latitude, longitude, country_id from cities where id = " + id);
            if(rs.next()){
                Country country = (new JDBCCountryRepository()).findById(rs.getInt("COUNTRY_ID"));
                boolean is_capital = rs.getString("IS_CAPITAL") == "Y";
                res = (new City(rs.getLong("ID"), rs.getString("NAME"), is_capital?1l:0l,
                        rs.getFloat("LATITUDE"), rs.getFloat("LONGITUDE"),
                        country, rs.getLong("POPULATION")));
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    public City findByName(String name) {
        City res = null;
        Statement stmt;
        try {
            stmt = JDBCManager.getConnection().createStatement();

            ResultSet rs = stmt.executeQuery("select id, name, is_capital, latitude, longitude, country_id from cities where name like '" + name + "'");
            if(rs.next()){
                Country country = (new JDBCCountryRepository()).findById(rs.getInt("COUNTRY_ID"));
                boolean is_capital = rs.getString("IS_CAPITAL") == "Y";
                res = (new City(rs.getLong("ID"), rs.getString("NAME"), is_capital?1l:0l,
                        rs.getFloat("LATITUDE"), rs.getFloat("LONGITUDE"),
                        country, rs.getLong("POPULATION")));
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    public boolean create(City city) {
        Statement stmt;
        try {
            stmt = JDBCManager.getConnection().createStatement();

            stmt.executeQuery("insert into cities values(0" +
                    ",'" + city.getName() + "'" +
                    ",'" + (city.getIsCapital() != 0?1:0) + "'" +
                    "," + city.getLatitude() +
                    "," + city.getLongitude() +
                    "," + city.getCountry().getId() +
                    ")"); //NOTE(Al-Andrew): the id is handled by the db seq here

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean update(long id, City city) {
        Statement stmt;
        try {
            stmt = JDBCManager.getConnection().createStatement();

            stmt.executeQuery("update continents set" +
                    "id =" + city.getId() +
                    ",name = '" + city.getName() + "'" +
                    ",'" + (city.getIsCapital() != 0?1:0) + "'" +
                    ",latitude = " + city.getLatitude() +
                    ",longitude = " + city.getLongitude() +
                    ",country_id = " + city.getCountry().getId() +
                    "where id = " + id);

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean delete(City city) {
        Statement stmt;
        try {
            stmt = JDBCManager.getConnection().createStatement();

            stmt.executeQuery("delete from cities where id = " + city.getId());

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
