package pa.repository;

import pa.db.JDBCManager;
import pa.entity.Continent;
import pa.entity.Country;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JDBCCountryRepository extends Repository<Country> {
    @Override
    public List<Country> getAll() {
        List<Country> res = new ArrayList<>();
        Statement stmt;
        try {
            stmt = JDBCManager.getConnection().createStatement();

            ResultSet rs = stmt.executeQuery("select id, name, code, continent_id from countries");
            while (rs.next()) {
                Repository<Continent> cd = new JDBCContinentRepository();
                Continent continent = cd.findById(rs.getInt("CONTINENT_ID"));
                res.add(new Country(rs.getLong("ID"), rs.getString("NAME"), rs.getString("CODE"), continent));
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    public Country findById(long id) {
        Country res = null;
        Statement stmt;
        try {
            stmt = JDBCManager.getConnection().createStatement();

            ResultSet rs = stmt.executeQuery("select id, name, code, continent_id from countries where id = " + id);
            if(rs.next()) {
                Repository<Continent> cd = new JDBCContinentRepository();
                Continent continent = cd.findById(rs.getInt("CONTINENT_ID"));
                res = new Country(rs.getLong("ID"), rs.getString("NAME"), rs.getString("CODE"), continent);
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    public Country findByName(String name) {
        Country res = null;
        Statement stmt;
        try {
            stmt = JDBCManager.getConnection().createStatement();

            ResultSet rs = stmt.executeQuery("select * from countries where name like '" + name + "'");
            if(rs.next()) {
                Repository<Continent> cd = new JDBCContinentRepository();
                Continent continent = cd.findById(rs.getInt("CONTINENT_ID"));
                res = new Country(rs.getLong("ID"), rs.getString("NAME"), rs.getString("CODE"), continent);
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    public boolean create(Country country) {
        Statement stmt;
        try {
            stmt = JDBCManager.getConnection().createStatement();
            stmt.executeQuery("insert into countries values(0" +
                    ",'" + country.getName() + "'" +
                    ",'" + country.getCode() + "'" +
                    ","   + country.getContinent().getId() + ")"); //NOTE(Al-Andrew): the id is handled by the db seq here
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean update(long id, Country country) {
        Statement stmt;
        try {
            stmt = JDBCManager.getConnection().createStatement();

            stmt.executeQuery("update continents set id =" + country.getId() +
                    ", name = '" + country.getName() + "'" +
                    ", code = '" + country.getCode() + "'" +
                    ", continent_id = " + country.getContinent().getId() +
                    "where id = " + id);

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean delete(Country country) {
        Statement stmt;
        try {
            stmt = JDBCManager.getConnection().createStatement();

            stmt.executeQuery("delete from continents where id = " + country.getId());

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
