import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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

    public void search_by_person_id(Connection connection, UUID person_id) {
        Statement statement;
        ResultSet rs = null;
        try {
            String query = String.format("select * from person where person_id= '%s'", person_id);
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

    public List select_custom(Connection connection, String exceptedColumns[], String[] tableColumns, String[] values) {
        Statement statement;
        List list = new ArrayList<>();
        ResultSet rs = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select ");
            for (String exceptedColumn : exceptedColumns) {
                sb.append(exceptedColumn + ", ");
            }
            sb.deleteCharAt(sb.lastIndexOf(","));
            sb.append(" from person where ");
            int i = 0;
            for (String tableColumn : tableColumns) {
                sb.append(tableColumn + "='" + values[i] + "' AND ");
                i++;
            }
            sb.delete(sb.length() - 4, sb.length());
            sb.append(";");
            System.out.println(sb.toString());
            statement = connection.createStatement();
            rs = statement.executeQuery(sb.toString());
            rs.next();
            for (int j = 1; j <= rs.getMetaData().getColumnCount(); j++) {
                list.add(j - 1, rs.getObject(j));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;

    }

    public UUID select_person_id_by_name(Connection connection, String name) {
        Statement statement;
        UUID uuid = null;
        ResultSet rs = null;
        try {
            String query = String.format("select person_id from person where name= '%s'", name);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            uuid = rs.getObject(1, UUID.class);
        } catch (Exception e) {
            System.out.println(e);
        }
        return uuid;
    }

    public UUID select_person_id_by_surname(Connection connection, String surname) {
        Statement statement;
        UUID uuid = null;
        ResultSet rs = null;
        try {
            String query = String.format("select person_id from person where surname= '%s'", surname);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            uuid = rs.getObject(1, UUID.class);
        } catch (Exception e) {
            System.out.println(e);
        }
        return uuid;
    }

    public UUID select_person_id_by_mobileNumber(Connection connection, String mobileNumber) {
        Statement statement;
        UUID uuid = null;
        ResultSet rs = null;
        try {
            String query = String.format("select person_id from person where mobilenumber= '%s'", mobileNumber);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            uuid = rs.getObject(1, UUID.class);
        } catch (Exception e) {
            System.out.println(e);
        }
        return uuid;
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

    public String select_name_by_person_id(Connection connection, UUID person_id) {
        Statement statement;
        String name = null;
        ResultSet rs = null;
        try {
            String query = String.format("select name from person where person_id= '%s'", person_id);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            name = rs.getString(1);
        } catch (Exception e) {
            System.out.println(e);
        }
        return name;
    }

    public String select_name_by_surname(Connection connection, String surname) {
        Statement statement;
        String name = null;
        ResultSet rs = null;
        try {
            String query = String.format("select name from person where surname= '%s'", surname);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            name = rs.getString(1);
        } catch (Exception e) {
            System.out.println(e);
        }
        return name;
    }

    public String select_name_by_mobileNumber(Connection connection, String mobileNumber) {
        Statement statement;
        String name = null;
        ResultSet rs = null;
        try {
            String query = String.format("select name from person where mobilenumber= '%s'", mobileNumber);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            name = rs.getString(1);
        } catch (Exception e) {
            System.out.println(e);
        }
        return name;
    }

    public void search_by_surname(Connection connection, String surname) {
        Statement statement;
        ResultSet rs = null;
        try {
            String query = String.format("select * from person where surname= '%s'", surname);
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

    public String select_surname_by_person_id(Connection connection, UUID person_id) {
        Statement statement;
        String surname = null;
        ResultSet rs = null;
        try {
            String query = String.format("select surname from person where person_id= '%s'", person_id);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            surname = rs.getString(1);
        } catch (Exception e) {
            System.out.println(e);
        }
        return surname;
    }

    public String select_surname_by_name(Connection connection, String name) {
        Statement statement;
        String surname = null;
        ResultSet rs = null;
        try {
            String query = String.format("select surname from person where name= '%s'", name);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            surname = rs.getString(1);
        } catch (Exception e) {
            System.out.println(e);
        }
        return surname;
    }

    public String select_surname_by_mobileNumber(Connection connection, String mobileNumber) {
        Statement statement;
        String surname = null;
        ResultSet rs = null;
        try {
            String query = String.format("select surname from person where mobilenumber= '%s'", mobileNumber);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            surname = rs.getString(1);
        } catch (Exception e) {
            System.out.println(e);
        }
        return surname;
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

    public String select_mobileNumber_by_person_id(Connection connection, UUID person_id) {
        Statement statement;
        String mobileNumber = null;
        ResultSet rs = null;
        try {
            String query = String.format("select mobilenumber from person where person_id= '%s'", person_id);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            mobileNumber = rs.getString(1);
        } catch (Exception e) {
            System.out.println(e);
        }
        return mobileNumber;
    }

    public String select_mobileNumber_by_name(Connection connection, String name) {
        Statement statement;
        String mobileNumber = null;
        ResultSet rs = null;
        try {
            String query = String.format("select mobilenumber from person where name= '%s'", name);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            mobileNumber = rs.getString(1);
        } catch (Exception e) {
            System.out.println(e);
        }
        return mobileNumber;
    }

    public String select_mobileNumber_by_surname(Connection connection, String surname) {
        Statement statement;
        String mobileNumber = null;
        ResultSet rs = null;
        try {
            String query = String.format("select mobilenumber from person where surname= '%s'", surname);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            mobileNumber = rs.getString(1);
        } catch (Exception e) {
            System.out.println(e);
        }
        return mobileNumber;
    }

    public void update_person_id_by_person_id(Connection connection, UUID old_person_uuid, UUID new_person_id) {
        Statement statement;
        try {
            String query = String.format("update person set person_id='%s' where person_id='%s'", new_person_id, old_person_uuid);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void update_name_by_person_id(Connection connection, UUID person_id, String new_name) {
        Statement statement;
        try {
            String query = String.format("update person set name='%s' where person_id='%s'", new_name, person_id);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void update_name_by_name(Connection connection, String old_name, String new_name) {
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

    public void update_name_by_surname(Connection connection, String surname, String new_name) {
        Statement statement;
        try {
            String query = String.format("update person set name='%s' where surname='%s'", new_name, surname);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void update_name_by_mobileNumber(Connection connection, String mobileNumber, String new_name) {
        Statement statement;
        try {

            String query = String.format("update person set name='%s' where mobilenumber='%s'", new_name, mobileNumber);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void update_surname_by_person_id(Connection connection, UUID person_id, String new_surname) {

        Statement statement;
        try {
            String query = String.format("update person set surname='%s' where person_id='%s'", new_surname, person_id);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void update_surname_by_name(Connection connection, String name, String new_surname) {
        Statement statement;
        try {
            String query = String.format("update person set surname='%s' where name='%s'", new_surname, name);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void update_surname_by_surname(Connection connection, String old_surname, String new_surname) {
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

    public void update_surname_by_mobileNumber(Connection connection, String mobileNumber, String new_surname) {
        Statement statement;
        try {
            String query = String.format("update person set surname='%s' where mobilenumber='%s'", new_surname, mobileNumber);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void update_mobileNumber_by_person_id(Connection connection, UUID person_id, String new_mobileNumber) {
        Statement statement;
        try {
            String query = String.format("update person set mobilenumber='%s' where person_id='%s'", new_mobileNumber, person_id);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void update_mobileNumber_by_name(Connection connection, String name, String new_mobileNumber) {
        Statement statement;
        try {
            String query = String.format("update person set mobilenumber='%s' where name='%s'", new_mobileNumber, name);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void update_mobileNumber_by_surname(Connection connection, String surname, String new_mobileNumber) {
        Statement statement;
        try {
            String query = String.format("update person set mobilenumber='%s' where surname='%s'", new_mobileNumber, surname);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void update_mobileNumber_by_mobileNumber(Connection connection, String old_mobileNumber, String new_mobileNumber) {
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


    public void delete_row_by_person_id(Connection connection, UUID person_id) {
        Statement statement;
        try {
            String query = String.format("delete from person where person_id= '%s'", person_id);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Data deleted");
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
