package pa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ContinentDAODatabase implements ContinentDAO {

    @Override
    public List<Continent> getAll() {
        List<Continent> res = new ArrayList<>();
        Statement stmt;
        try {
            stmt = DatabaseConnection.getConnection().createStatement();

            ResultSet rs = stmt.executeQuery("select id, name from continents");
            while (rs.next())
                res.add(new Continent(rs.getInt("ID"), rs.getString("NAME")));
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    public Continent findById(long id) {
        Continent res = null;
        Statement stmt;
        try {
            stmt = DatabaseConnection.getConnection().createStatement();

            ResultSet rs = stmt.executeQuery("select * from continents where id = " + id);
            if(rs.next())
                res = new Continent(rs.getInt(1), rs.getString(2));
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    public Continent findByName(String name) {
        Continent res = null;
        Statement stmt;
        try {
            stmt = DatabaseConnection.getConnection().createStatement();

            ResultSet rs = stmt.executeQuery("select * from continents where name like '" + name + "'");
            if(rs.next())
                res = new Continent(rs.getInt(1), rs.getString(2));
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    public boolean insertContinent(Continent continent) {
        Statement stmt;
        try {
            stmt = DatabaseConnection.getConnection().createStatement();

            stmt.executeQuery("insert into continents values(0, '" + continent.getName() + "')"); //NOTE(Al-Andrew): the id is handled by the db seq here

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean updateContinent(long id, Continent newContinent) {
        Statement stmt;
        try {
            stmt = DatabaseConnection.getConnection().createStatement();

            stmt.executeQuery("update continents set id =" + newContinent.getId() +
                    ", name = '" + newContinent.getName() + "' + where id = " + id);

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean deleteContinent(Continent continent) {
        Statement stmt;
        try {
            stmt = DatabaseConnection.getConnection().createStatement();

            stmt.executeQuery("delete from continents where id = " + continent.getId());

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
