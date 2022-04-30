package pa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CityDAODatabase implements CityDAO {
    @Override
    public List<City> getAll() {
        List<City> res = new ArrayList<>();
        Statement stmt;
        try {
            stmt = DatabaseConnection.getConnection().createStatement();

            ResultSet rs = stmt.executeQuery("select id, name, is_capital, latitude, longitude, country_id from cities");
            while (rs.next()) {
                Country country = (new CountryDAODatabase()).findById(rs.getInt("COUNTRY_ID"));
                boolean is_capital = rs.getString("IS_CAPITAL") != "N";
                res.add(new City(rs.getInt("ID"), rs.getString("NAME"), country, is_capital,
                        rs.getFloat("LATITUDE"), rs.getFloat("LONGITUDE")));
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
            stmt = DatabaseConnection.getConnection().createStatement();

            ResultSet rs = stmt.executeQuery("select id, name, is_capital, latitude, longitude, country_id from cities where id = " + id);
            if(rs.next()){
                Country country = (new CountryDAODatabase()).findById(rs.getInt("COUNTRY_ID"));
                boolean is_capital = rs.getString("IS_CAPITAL") == "Y";
                res = (new City(rs.getInt("ID"), rs.getString("NAME"), country, is_capital,
                        rs.getFloat("LATITUDE"), rs.getFloat("LONGITUDE")));
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
            stmt = DatabaseConnection.getConnection().createStatement();

            ResultSet rs = stmt.executeQuery("select id, name, is_capital, latitude, longitude, country_id from cities where name like '" + name + "'");
            if(rs.next()){
                Country country = (new CountryDAODatabase()).findById(rs.getInt("COUNTRY_ID"));
                boolean is_capital = rs.getString("IS_CAPITAL") == "Y";
                res = (new City(rs.getInt("ID"), rs.getString("NAME"), country, is_capital,
                        rs.getFloat("LATITUDE"), rs.getFloat("LONGITUDE")));
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    public boolean insertCity(City city) {
        Statement stmt;
        try {
            stmt = DatabaseConnection.getConnection().createStatement();

            stmt.executeQuery("insert into cities values(0" +
                    ",'" + city.getName() + "'" +
                    ",'" + (city.isCapital()?"Y":"N") + "'" +
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
    public boolean updateCity(long id, City city) {
        Statement stmt;
        try {
            stmt = DatabaseConnection.getConnection().createStatement();

            stmt.executeQuery("update continents set" +
                    "id =" + city.getId() +
                    ",name = '" + city.getName() + "'" +
                    ",is_capital = '" + (city.isCapital()?"Y":"N") + "'" +
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
    public boolean deleteCity(City city) {
        Statement stmt;
        try {
            stmt = DatabaseConnection.getConnection().createStatement();

            stmt.executeQuery("delete from cities where id = " + city.getId());

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
