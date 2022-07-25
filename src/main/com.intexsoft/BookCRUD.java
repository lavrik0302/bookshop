import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

public class BookCRUD {

    public void insert_row(Connection connection, String bookname, String author, int cost_in_byn, int count_in_stock) {
        Statement statement;
        try {
            UUID uuid = UUID.randomUUID();
            String query = "insert into book values ('" + uuid + "', '" + bookname + "', '" + author + "', " + cost_in_byn + ", " + count_in_stock + ");";
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
            String query = "select * from book";
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


    public void search_by_bookname(Connection connection, String bookname) {
        Statement statement;
        ResultSet rs = null;
        try {
            String query = String.format("select * from book where bookname= '%s'", bookname);
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

    public void search_by_author(Connection connection, String author) {
        Statement statement;
        ResultSet rs = null;
        try {
            String query = String.format("select * from book where author= '%s'", author);
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

    public void search_by_id(Connection connection, UUID id) {
        Statement statement;
        ResultSet rs = null;
        try {
            String query = String.format("select * from book where book_id= '%s'", id);
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

    public void update_bookname(Connection connection, String old_bookname, String new_bookname) {
        Statement statement;
        try {
            String query = String.format("update book set bookname='%s' where bookname='%s'", new_bookname, old_bookname);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void update_author(Connection connection, String old_author, String new_author) {
        Statement statement;
        try {
            String query = String.format("update book set surname='%s' where surname='%s'", new_author, old_author);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void update_cost_in_byn(Connection connection, String bookname, int new_cost_in_byn) {
        Statement statement;
        try {
            String query = String.format("update person set mobilenumber='%s' where mobilenumber='%s'", bookname, new_cost_in_byn);
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
