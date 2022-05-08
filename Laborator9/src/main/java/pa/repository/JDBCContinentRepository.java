package pa.repository;

import pa.db.JDBCManager;
import pa.entity.Continent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JDBCContinentRepository extends Repository<Continent> {

    @Override
    public List<Continent> getAll() {
        List<Continent> res = new ArrayList<>();
        Statement stmt;
        try {
            stmt = JDBCManager.getConnection().createStatement();

            ResultSet rs = stmt.executeQuery("select id, name from continents");
            while (rs.next())
                res.add(new Continent(rs.getLong("ID"), rs.getString("NAME")));
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
            stmt = JDBCManager.getConnection().createStatement();

            ResultSet rs = stmt.executeQuery("select * from continents where id = " + id);
            if(rs.next())
                res = new Continent(rs.getLong(1), rs.getString(2));
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
            stmt = JDBCManager.getConnection().createStatement();

            ResultSet rs = stmt.executeQuery("select * from continents where name like '" + name + "'");
            if(rs.next())
                res = new Continent(rs.getLong(1), rs.getString(2));
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    public boolean create(Continent toCreate) {
        Statement stmt;
        try {
            stmt = JDBCManager.getConnection().createStatement();

            stmt.executeQuery("insert into continents values(0, '" + toCreate.getName() + "')"); //NOTE(Al-Andrew): the id is handled by the db seq here

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean update(long id, Continent replacement) {
        Statement stmt;
        try {
            stmt = JDBCManager.the().getConnection().createStatement();

            stmt.executeQuery("update continents set id =" + replacement.getId() +
                    ", name = '" + replacement.getName() + "' + where id = " + id);

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean delete(Continent toDelete) {
        Statement stmt;
        try {
            stmt = JDBCManager.the().getConnection().createStatement();

            stmt.executeQuery("delete from continents where id = " + toDelete.getId());

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
