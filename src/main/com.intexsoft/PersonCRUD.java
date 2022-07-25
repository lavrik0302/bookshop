import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

public class PersonCRUD {

    public void insert_row(Connection connection, String name, String surname, String mobileNumber) {
        Statement statement;
        try {
            UUID uuid = UUID.randomUUID();
            String query = "insert into person values ('" + uuid + "', '" + name + "', '" + surname + "', '" + mobileNumber + "');";
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Insert success");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void read_data(Connection connection) {
        Statement statement;
        ResultSet rs = null;
        try {
            String query = "select * from person";
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    System.out.print(rs.getString(i) + " ");
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void search_by_name(Connection connection, String name) {
        Statement statement;
        ResultSet rs = null;
        try {
            String query = String.format("select * from person where name= '%s'", name);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    System.out.print(rs.getString(i) + " ");
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void search_by_surname(Connection connection, String surname) {
        Statement statement;
        ResultSet rs = null;
        try {
            String query = String.format("select * from person where name= '%s'", surname);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    System.out.print(rs.getString(i) + " ");
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void search_by_id(Connection connection, int id) {
        Statement statement;
        ResultSet rs = null;
        try {
            String query = String.format("select * from person where personid= '%s'", id);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    System.out.print(rs.getString(i) + " ");
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void search_by_mobileNumber(Connection connection, String mobileNumber) {
        Statement statement;
        ResultSet rs = null;
        try {
            String query = String.format("select * from person where mobilenumber= '%s'", mobileNumber);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    System.out.print(rs.getString(i) + " ");
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void update_name(Connection connection, String old_name, String new_name) {
        Statement statement;
        try {
            String query = String.format("update person set name='%s' where name='%s'", new_name, old_name);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void update_surname(Connection connection, String old_surname, String new_surname) {
        Statement statement;
        try {
            String query = String.format("update person set surname='%s' where surname='%s'", new_surname, old_surname);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void update_mobileNumber(Connection connection, String old_mobileNumber, String new_mobileNumber) {
        Statement statement;
        try {
            String query = String.format("update person set mobilenumber='%s' where mobilenumber='%s'", new_mobileNumber, old_mobileNumber);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void delete_row_by_name(Connection connection, String name) {
        Statement statement;
        try {
            String query = String.format("delete from person where name= '%s'", name);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Data deleted");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void delete_row_by_id(Connection connection, UUID id) {
        Statement statement;
        try {
            String query = String.format("delete from person where personid= '%s'", id);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Data deleted");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void delete_row_by_surname(Connection connection, String surname) {
        Statement statement;
        try {
            String query = String.format("delete from person where surname= '%s'", surname);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Data deleted");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void delete_row_by_mobileNumber(Connection connection, String mobileNumber) {
        Statement statement;
        try {
            String query = String.format("delete from person where mobilenumber= '%s'", mobileNumber);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Data deleted");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
