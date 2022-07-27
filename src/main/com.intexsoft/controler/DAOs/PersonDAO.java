package controler.DAOs;

import controler.FindRequests.FindPersonRequest;
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
            String query = "select person.person_id, person.name, person.surname, person.mobilenumber, cart.cart_id, cart.cart_name from person left join cart on person.person_id=cart.person_id;";
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
                cart.setCart_id(rs.getObject(5, UUID.class));
                cart.setPerson_id(rs.getObject(1, UUID.class));
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

            if (!findPersonRequest.getPersonIds().isEmpty()) {
                sb.append("person_id ");
                if (findPersonRequest.getPersonIds().size() > 1) {
                    sb.append("in (");
                    for (UUID person_id : findPersonRequest.getPersonIds()) {
                        sb.append("'" + person_id + "', ");
                    }
                    sb.deleteCharAt(sb.lastIndexOf(","));
                    sb.append(") AND ");
                } else {
                    sb.append("='" + findPersonRequest.getPersonIds().get(0) + "' AND ");
                }
            }
            if (!findPersonRequest.getPersonNames().isEmpty()) {
                sb.append("name ");
                if (findPersonRequest.getPersonNames().size() > 1) {
                    sb.append("in (");
                    for (String name : findPersonRequest.getPersonNames()) {
                        sb.append("'" + name + "', ");
                    }
                    sb.deleteCharAt(sb.lastIndexOf(","));
                    sb.append(") AND ");
                } else {
                    sb.append("='" + findPersonRequest.getPersonNames().get(0) + "' AND ");
                }
            }
            if (!findPersonRequest.getPersonSurnames().isEmpty()) {
                sb.append("surname ");
                if (findPersonRequest.getPersonSurnames().size() > 1) {
                    sb.append("in (");
                    for (String surname : findPersonRequest.getPersonSurnames()) {
                        sb.append("'" + surname + "', ");
                    }
                    sb.deleteCharAt(sb.lastIndexOf(","));
                    sb.append(") AND ");
                } else {
                    sb.append("='" + findPersonRequest.getPersonSurnames().get(0) + "' AND ");
                }
            }
            if (!findPersonRequest.getPersonMobilenumbers().isEmpty()) {
                sb.append("mobilenumber ");
                if (findPersonRequest.getPersonMobilenumbers().size() > 1) {
                    sb.append("in (");
                    for (String mobilenumber : findPersonRequest.getPersonMobilenumbers()) {
                        sb.append("'" + mobilenumber + "', ");
                    }
                    sb.deleteCharAt(sb.lastIndexOf(","));
                    sb.append(") AND ");
                } else {
                    sb.append("='" + findPersonRequest.getPersonMobilenumbers().get(0) + "' AND ");
                }
            }

            sb.delete(sb.length() - 4, sb.length());
            sb.append(";");
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
            sb.append("select person.person_id, person.name, person.surname, person.mobilenumber, cart.cart_id, cart.cart_name from person left join cart on person.person_id=cart.person_id where ");

            if (!findPersonRequest.getPersonIds().isEmpty()) {
                sb.append("person.person_id ");
                if (findPersonRequest.getPersonIds().size() > 1) {
                    sb.append("in (");
                    for (UUID person_id : findPersonRequest.getPersonIds()) {
                        sb.append("'" + person_id + "', ");
                    }
                    sb.deleteCharAt(sb.lastIndexOf(","));
                    sb.append(") AND ");
                } else {
                    sb.append("='" + findPersonRequest.getPersonIds().get(0) + "' AND ");
                }
            }
            if (!findPersonRequest.getPersonNames().isEmpty()) {
                sb.append("person.name ");
                if (findPersonRequest.getPersonNames().size() > 1) {
                    sb.append("in (");
                    for (String name : findPersonRequest.getPersonNames()) {
                        sb.append("'" + name + "', ");
                    }
                    sb.deleteCharAt(sb.lastIndexOf(","));
                    sb.append(") AND ");
                } else {
                    sb.append("='" + findPersonRequest.getPersonNames().get(0) + "' AND ");
                }
            }
            if (!findPersonRequest.getPersonSurnames().isEmpty()) {
                sb.append("person.surname ");
                if (findPersonRequest.getPersonSurnames().size() > 1) {
                    sb.append("in (");
                    for (String surname : findPersonRequest.getPersonSurnames()) {
                        sb.append("'" + surname + "', ");
                    }
                    sb.deleteCharAt(sb.lastIndexOf(","));
                    sb.append(") AND ");
                } else {
                    sb.append("='" + findPersonRequest.getPersonSurnames().get(0) + "' AND ");
                }
            }
            if (!findPersonRequest.getPersonMobilenumbers().isEmpty()) {
                sb.append("person.mobilenumber ");
                if (findPersonRequest.getPersonMobilenumbers().size() > 1) {
                    sb.append("in (");
                    for (String mobilenumber : findPersonRequest.getPersonMobilenumbers()) {
                        sb.append("'" + mobilenumber + "', ");
                    }
                    sb.deleteCharAt(sb.lastIndexOf(","));
                    sb.append(") AND ");
                } else {
                    sb.append("='" + findPersonRequest.getPersonMobilenumbers().get(0) + "' AND ");
                }
            }
            sb.delete(sb.length() - 4, sb.length());
            sb.append("");

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
                cart.setCart_id(rs.getObject(5, UUID.class));
                cart.setPerson_id(rs.getObject(1, UUID.class));
                cart.setCartname(rs.getString(6));
                person.setPersonCart(cart);
                list.add(person);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }


    public void update(Connection connection, FindPersonRequest updatePersonRequest, FindPersonRequest findPersonRequest) {
        Statement statement;
        Person person;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("update person set ");

            if (!updatePersonRequest.getPersonIds().isEmpty())
                sb.append("person_id" + "='" + updatePersonRequest.getPersonIds().get(0) + "', ");

            if (!updatePersonRequest.getPersonNames().isEmpty())
                sb.append("name" + "='" + updatePersonRequest.getPersonNames().get(0) + "', ");
            if (!updatePersonRequest.getPersonSurnames().isEmpty())
                sb.append("surname" + "='" + updatePersonRequest.getPersonSurnames().get(0) + "', ");
            if (!updatePersonRequest.getPersonMobilenumbers().isEmpty())
                sb.append("mobilenumber" + "='" + updatePersonRequest.getPersonMobilenumbers().get(0) + "', ");
            sb.deleteCharAt(sb.lastIndexOf(","));
            sb.append("where ");
            if (!findPersonRequest.getPersonIds().isEmpty()) {
                sb.append("person_id ");
                if (findPersonRequest.getPersonIds().size() > 1) {
                    sb.append("in (");
                    for (UUID person_id : findPersonRequest.getPersonIds()) {
                        sb.append("'" + person_id + "', ");
                    }
                    sb.deleteCharAt(sb.lastIndexOf(","));
                    sb.append(") AND ");
                } else {
                    sb.append("='" + findPersonRequest.getPersonIds().get(0) + "' AND ");
                }
            }
            if (!findPersonRequest.getPersonNames().isEmpty()) {
                sb.append("name ");
                if (findPersonRequest.getPersonNames().size() > 1) {
                    sb.append("in (");
                    for (String name : findPersonRequest.getPersonNames()) {
                        sb.append("'" + name + "', ");
                    }
                    sb.deleteCharAt(sb.lastIndexOf(","));
                    sb.append(") AND ");
                } else {
                    sb.append("='" + findPersonRequest.getPersonNames().get(0) + "' AND ");
                }
            }
            if (!findPersonRequest.getPersonSurnames().isEmpty()) {
                sb.append("surname ");
                if (findPersonRequest.getPersonSurnames().size() > 1) {
                    sb.append("in (");
                    for (String surname : findPersonRequest.getPersonSurnames()) {
                        sb.append("'" + surname + "', ");
                    }
                    sb.deleteCharAt(sb.lastIndexOf(","));
                    sb.append(") AND ");
                } else {
                    sb.append("='" + findPersonRequest.getPersonSurnames().get(0) + "' AND ");
                }
            }
            if (!findPersonRequest.getPersonMobilenumbers().isEmpty()) {
                sb.append("mobilenumber ");
                if (findPersonRequest.getPersonMobilenumbers().size() > 1) {
                    sb.append("in (");
                    for (String mobilenumber : findPersonRequest.getPersonMobilenumbers()) {
                        sb.append("'" + mobilenumber + "', ");
                    }
                    sb.deleteCharAt(sb.lastIndexOf(","));
                    sb.append(") AND ");
                } else {
                    sb.append("='" + findPersonRequest.getPersonMobilenumbers().get(0) + "' AND ");
                }
            }
            sb.delete(sb.length() - 4, sb.length());
            sb.append(";");
            System.out.println(sb.toString());
            statement = connection.createStatement();
            statement.executeUpdate(sb.toString());
            System.out.println("Data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Person updatePerson(Connection connection, Person person, FindPersonRequest findPersonRequest) {
        FindPersonRequest updatePersonRequest = new FindPersonRequest();
        updatePersonRequest.setPersonId(person.getPersonId()).setPersonName(person.getName()).setPersonSurname(person.getSurname()).setPersonMobileNumber(person.getMobilenumber());
        update(connection, updatePersonRequest, findPersonRequest);
        return person;
    }

    public void delete(Connection connection, FindPersonRequest findPersonRequest) {
        Statement statement;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("delete from person where ");
            if (!findPersonRequest.getPersonIds().isEmpty()) {
                sb.append("person_id ");
                if (findPersonRequest.getPersonIds().size() > 1) {
                    sb.append("in (");
                    for (UUID person_id : findPersonRequest.getPersonIds()) {
                        sb.append("'" + person_id + "', ");
                    }
                    sb.deleteCharAt(sb.lastIndexOf(","));
                    sb.append(") AND ");
                } else {
                    sb.append("='" + findPersonRequest.getPersonIds().get(0) + "' AND ");
                }
            }
            if (!findPersonRequest.getPersonNames().isEmpty()) {
                sb.append("name ");
                if (findPersonRequest.getPersonNames().size() > 1) {
                    sb.append("in (");
                    for (String name : findPersonRequest.getPersonNames()) {
                        sb.append("'" + name + "', ");
                    }
                    sb.deleteCharAt(sb.lastIndexOf(","));
                    sb.append(") AND ");
                } else {
                    sb.append("='" + findPersonRequest.getPersonNames().get(0) + "' AND ");
                }
            }
            if (!findPersonRequest.getPersonSurnames().isEmpty()) {
                sb.append("surname ");
                if (findPersonRequest.getPersonSurnames().size() > 1) {
                    sb.append("in (");
                    for (String surname : findPersonRequest.getPersonSurnames()) {
                        sb.append("'" + surname + "', ");
                    }
                    sb.deleteCharAt(sb.lastIndexOf(","));
                    sb.append(") AND ");
                } else {
                    sb.append("='" + findPersonRequest.getPersonSurnames().get(0) + "' AND ");
                }
            }
            if (!findPersonRequest.getPersonMobilenumbers().isEmpty()) {
                sb.append("mobilenumber ");
                if (findPersonRequest.getPersonMobilenumbers().size() > 1) {
                    sb.append("in (");
                    for (String mobilenumber : findPersonRequest.getPersonMobilenumbers()) {
                        sb.append("'" + mobilenumber + "', ");
                    }
                    sb.deleteCharAt(sb.lastIndexOf(","));
                    sb.append(") AND ");
                } else {
                    sb.append("='" + findPersonRequest.getPersonMobilenumbers().get(0) + "' AND ");
                }
            }
            sb.delete(sb.length() - 4, sb.length());
            sb.append(";");
            System.out.println(sb.toString());
            statement = connection.createStatement();
            statement.executeUpdate(sb.toString());
            System.out.println("Data deleted");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
