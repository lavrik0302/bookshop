package controler.dao;

import controler.findRequests.FindPersonRequest;
import controler.updateRequests.UpdatePersonRequest;
import model.Cart;
import model.Person;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PersonDAO {

    public Person createRow(Connection connection, String name, String surname, String mobileNumber) {
        Statement statement;
        Person person = new Person();
        try {
            UUID uuid = UUID.randomUUID();
            person.setPersonId(uuid);
            person.setName(name);
            person.setSurname(surname);
            person.setMobilenumber(mobileNumber);
            String query = "insert into person values ('" + uuid + "', '" + name + "', '" + surname + "', '" + mobileNumber + "');";
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Insert success");
        } catch (Exception e) {
            System.out.println(e);
        }
        return person;
    }

    public Person createPerson(Connection connection, Person person) {
        Statement statement;
        try {
            String query = "insert into person values ('" + person.getPersonId() + "', '" + person.getName() + "', '" + person.getSurname() + "', '" + person.getMobilenumber() + "');";
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Insert success");
        } catch (Exception e) {
            System.out.println(e);
        }
        return person;
    }

    public List<Person> readAll(Connection connection) {
        List<Person> list = new ArrayList<>();
        Statement statement;
        ResultSet rs = null;
        try {
            String query = "select * from person";
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                Person person = new Person();
                person.setPersonId(rs.getObject(1, UUID.class));
                person.setName(rs.getString(2));
                person.setSurname(rs.getString(3));
                person.setMobilenumber(rs.getString(4));

                list.add(person);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public List<Person> readAllWithCart(Connection connection) {
        List<Person> list = new ArrayList<>();
        Statement statement;
        ResultSet rs = null;
        try {
            String query = "select p.person_id, p.name, p.surname, p.mobilenumber, c.cart_id, c.cart_name from person AS p left join cart AS c on p.person_id=c.person_id;";
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            System.out.println(query);
            while (rs.next()) {
                Person person = new Person();
                person.setPersonId(rs.getObject(1, UUID.class));
                person.setName(rs.getString(2));
                person.setSurname(rs.getString(3));
                person.setMobilenumber(rs.getString(4));
                Cart cart = new Cart();
                cart.setCartId(rs.getObject(5, UUID.class));
                cart.setPersonId(rs.getObject(1, UUID.class));
                cart.setCartname(rs.getString(6));
                person.setPersonCart(cart);
                list.add(person);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public List<Person> find(Connection connection, FindPersonRequest findPersonRequest) {
        Statement statement;
        List<Person> list = new ArrayList<>();
        ResultSet rs = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select * from person where ");
            sb.append(findPersonRequest.toSQLStringStatement());
            System.out.println(sb.toString());
            statement = connection.createStatement();
            rs = statement.executeQuery(sb.toString());
            while (rs.next()) {
                Person person = new Person();
                person.setPersonId(rs.getObject(1, UUID.class));
                person.setName(rs.getString(2));
                person.setSurname(rs.getString(3));
                person.setMobilenumber(rs.getString(4));
                list.add(person);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public List<Person> findWithCart(Connection connection, FindPersonRequest findPersonRequest) {
        Statement statement;
        List<Person> list = new ArrayList<>();
        ResultSet rs = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select p.person_id, p.name, p.surname, p.mobilenumber, c.cart_id, c.cart_name from person AS p left join cart AS c on p.person_id=c.person_id where ");
            sb.append(findPersonRequest.toSQLStringStatement());
            System.out.println(sb.toString());
            statement = connection.createStatement();
            rs = statement.executeQuery(sb.toString());
            while (rs.next()) {
                Person person = new Person();
                person.setPersonId(rs.getObject(1, UUID.class));
                person.setName(rs.getString(2));
                person.setSurname(rs.getString(3));
                person.setMobilenumber(rs.getString(4));
                Cart cart = new Cart();
                cart.setCartId(rs.getObject(5, UUID.class));
                cart.setPersonId(rs.getObject(1, UUID.class));
                cart.setCartname(rs.getString(6));
                person.setPersonCart(cart);
                list.add(person);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }


    public void update(Connection connection, UpdatePersonRequest updatePersonRequest, FindPersonRequest findPersonRequest) {
        Statement statement;
        Person person;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("update person set ");

            if (updatePersonRequest.getPersonIds() != null)
                sb.append("person_id" + "='" + updatePersonRequest.getPersonIds() + "', ");

            if (updatePersonRequest.getPersonNames() != null)
                sb.append("name" + "='" + updatePersonRequest.getPersonNames() + "', ");
            if (updatePersonRequest.getPersonSurnames() != null)
                sb.append("surname" + "='" + updatePersonRequest.getPersonSurnames() + "', ");
            if (updatePersonRequest.getPersonMobilenumbers() != null)
                sb.append("mobilenumber" + "='" + updatePersonRequest.getPersonMobilenumbers() + "', ");
            sb.deleteCharAt(sb.lastIndexOf(","));
            sb.append("where ");
            sb.append(findPersonRequest.toSQLStringStatement());
            System.out.println(sb.toString());
            statement = connection.createStatement();
            statement.executeUpdate(sb.toString());
            System.out.println("Data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Person updatePerson(Connection connection, Person person, FindPersonRequest findPersonRequest) {
        UpdatePersonRequest updatePersonRequest = new UpdatePersonRequest();
        updatePersonRequest.setPersonId(person.getPersonId()).setPersonName(person.getName()).setPersonSurname(person.getSurname()).setPersonMobileNumber(person.getMobilenumber());
        update(connection, updatePersonRequest, findPersonRequest);
        return person;
    }

    public void delete(Connection connection, FindPersonRequest findPersonRequest) {
        Statement statement;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("delete from person where ");
            sb.append(findPersonRequest.toSQLStringStatement());
            System.out.println(sb.toString());
            statement = connection.createStatement();
            statement.executeUpdate(sb.toString());
            System.out.println("Data deleted");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
