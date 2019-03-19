
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    final static String DRIVER = "org.postgresql.Driver";
    final static String URL = "jdbc:postgresql://localhost:5432/kawasakio";
    final static String USER = "postgres";
    final static String PASS = "";

    public static Connection getConnection()
            throws SQLException {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(
                    "fail to class load : "
                    + e.getMessage());
        }
        Connection con = DriverManager.getConnection(URL, USER, PASS);
        return con;
    }
}