import java.sql.Connection;
import java.sql.DriverManager;


public class ConnectToDb {
    public Connection connect_to_db(String dbname, String username, String password) {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:55001/" + dbname, username, password);
            if (connection != null) {
                System.out.println("Connection established");
            } else {
                System.out.println("Connection failed");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return connection;
    }
}
