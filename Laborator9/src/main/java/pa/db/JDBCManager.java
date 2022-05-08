package pa.db;

import pa.entity.City;
import pa.entity.Continent;
import pa.entity.Country;
import pa.repository.JDBCContinentRepository;
import pa.repository.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//NOTE(Al-Andrew): Singleton
public class JDBCManager {
    private static JDBCManager instance = null;
    private Connection inner = null;

    private JDBCManager() {
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

    public static JDBCManager the() {
        // Lazy instantiation
        if (instance == null)
            instance = new JDBCManager();

        return instance;
    }

    public static Connection getConnection() {
        return the().inner;
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
}
