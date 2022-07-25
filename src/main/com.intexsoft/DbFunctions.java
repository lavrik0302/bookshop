import com.sun.jdi.event.ExceptionEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbFunctions {
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

    public void createTable(Connection connection, String tableName) {
        Statement statement;
        try {
            String query = "create table " + tableName + "(personid SERIAL, name varchar(255), surname varchar(255), mobileNumber varchar(13), primary key (personid))";
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Table created");
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void insert_row(Connection connection, String tableName, String name, String surname, String mobileNumber) {
        Statement statement;
        try {
            String query = String.format("insert into %s(name, surname, mobileNumber) values ('%s','%s', '%s');", tableName, name, surname, mobileNumber);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Insert success");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void read_data(Connection connection, String tableName) {
        Statement statement;
        ResultSet rs = null;
        try {
            String query = String.format("select * from %s", tableName);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                System.out.print(rs.getString("personid") + " ");
                System.out.print(rs.getString("name") + " ");
                System.out.print(rs.getString("surname") + " ");
                System.out.println(rs.getString("mobileNumber") + " ");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void update_name(Connection connection, String tableName, String old_name, String new_name) {
        Statement statement;
        try {
            String query = String.format("update %s set name='%s' where name='%s'", tableName, new_name, old_name);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void search_by_name(Connection connection, String tableName, String name) {
        Statement statement;
        ResultSet rs = null;

        try {
            String query = String.format("select * from %s where name= '%s'", tableName, name);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                System.out.print(rs.getString("personid") + " ");
                System.out.print(rs.getString("name") + " ");
                System.out.print(rs.getString("surname") + " ");
                System.out.println(rs.getString("mobileNumber"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void search_by_id(Connection connection, String tableName, int id) {
        Statement statement;
        ResultSet rs = null;

        try {
            String query = String.format("select * from %s where personid= '%s'", tableName, id);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                System.out.print(rs.getString("personid") + " ");
                System.out.print(rs.getString("name") + " ");
                System.out.print(rs.getString("surname") + " ");
                System.out.println(rs.getString("mobileNumber"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void delete_row_by_name(Connection connection, String tableName, String name) {
        Statement statement;
        try {
            String query = String.format("delete from %s where name= '%s'", tableName, name);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Data deleted");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void delete_row_by_id(Connection connection, String tableName, int id) {
        Statement statement;
        try {
            String query = String.format("delete from %s where personid= '%s'", tableName, id);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Data deleted");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void delete_table(Connection connection, String tableName) {
        Statement statement;
        try {
            String query = String.format("drop table %s", tableName);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("table deleted");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
