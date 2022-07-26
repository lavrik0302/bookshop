package controler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

public class CartCrud {
    public void insert_row_by_person_id(Connection connection, UUID person_id, String cart_name) {
        Statement statement;
        try {
            UUID uuid = UUID.randomUUID();
            String query = "insert into cart values ('" + uuid + "', '" + person_id + "', '" + cart_name + "');";
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Insert success");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void insert_row_by_person_name(Connection connection, String name, String cart_name) {
        Statement statement;
        try {
            UUID uuid = UUID.randomUUID();
            PersonCRUD personCRUD=new PersonCRUD();
            //UUID person_id = personCRUD.select_person_id_by_name(connection,name);
           // String query = "insert into cart values ('" + uuid + "', '" + person_id + "', '" + cart_name + "');";
            statement = connection.createStatement();
           // statement.executeUpdate(query);
            System.out.println("Insert success");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void insert_row_by_person_surname(Connection connection, String surnname, String cart_name) {
        Statement statement;
        try {
            UUID uuid = UUID.randomUUID();
            PersonCRUD personCRUD=new PersonCRUD();
            UUID person_id = personCRUD.select_person_id_by_surname(connection,surnname);
            String query = "insert into cart values ('" + uuid + "', '" + person_id + "', '" + cart_name + "');";
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Insert success");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void insert_row_by_person_mobileNumber(Connection connection, String mobilenumber, String cart_name) {
        Statement statement;
        try {
            UUID uuid = UUID.randomUUID();
            PersonCRUD personCRUD=new PersonCRUD();
            UUID person_id = personCRUD.select_person_id_by_mobileNumber(connection,mobilenumber);
            String query = "insert into cart values ('" + uuid + "', '" + person_id + "', '" + cart_name + "');";
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
            String query = "select * from cart";
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
    public void search_by_cart_id(Connection connection, UUID cart_id) {
        Statement statement;
        ResultSet rs = null;
        try {
            String query = String.format("select * from cart where cart_id= '%s'", cart_id);
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
    public UUID select_cart_id_by_person_id(Connection connection, UUID person_id) {
        Statement statement;
        UUID uuid = null;
        ResultSet rs = null;
        try {
            String query = String.format("select cart_id from cart where person_id= '%s'", person_id);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            uuid = rs.getObject(1, UUID.class);
        } catch (Exception e) {
            System.out.println(e);
        }
        return uuid;
    }
}
