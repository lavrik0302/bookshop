package com.intexsoft.controler.dao;




import com.intexsoft.controler.findRequest.FindPersonRequest;
import com.intexsoft.controler.updateRequest.UpdatePersonRequest;
import com.intexsoft.model.Book;
import com.intexsoft.model.Cart;
import com.intexsoft.model.Person;
import com.intexsoft.model.PersonOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PersonDAO {
    Connection connection;

    public Person createRow(String name, String surname, String mobileNumber) {
        PreparedStatement preparedStatement;
        Person person = new Person();
        try {
            UUID uuid = UUID.randomUUID();
            person.setPersonId(uuid);
            person.setName(name);
            person.setSurname(surname);
            person.setMobilenumber(mobileNumber);
            preparedStatement=connection.prepareStatement("insert into person values (?, ?, ?, ?)");
            preparedStatement.setObject(1, uuid);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, surname);
            preparedStatement.setString(4, mobileNumber);
            preparedStatement.executeUpdate();
            System.out.println("Insert success");
        } catch (Exception e) {
            System.out.println(e);
        }
        return person;
    }

    public Person createPerson(Person person) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement=connection.prepareStatement("insert into person values (?, ?, ?, ?)");
            preparedStatement.setObject(1, person.getPersonId());
            preparedStatement.setString(2, person.getName());
            preparedStatement.setString(3, person.getSurname());
            preparedStatement.setString(4, person.getMobilenumber());
            preparedStatement.executeUpdate();
            System.out.println("Insert success");
        } catch (Exception e) {
            System.out.println(e);
        }
        return person;
    }

    public List<Person> findAll() {
        List<Person> list = new ArrayList<>();
        Statement statement;
        ResultSet rs = null;
        try {
            String query = "select * from person";
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                Person person = new Person();
                person.setPersonId(rs.getObject("person_id", UUID.class));
                person.setName(rs.getString("name"));
                person.setSurname(rs.getString("surname"));
                person.setMobilenumber(rs.getString("mobilenumber"));

                list.add(person);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public List<Person> findAllWithCart() {
        List<Person> list = new ArrayList<>();
        Statement statement;
        ResultSet rs = null;
        try {
            String query = "select p.*, c.* from person AS p left join cart AS c on p.person_id=c.person_id;";
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            System.out.println(query);
            while (rs.next()) {
                Person person = new Person();
                person.setPersonId(rs.getObject("person_id", UUID.class));
                person.setName(rs.getString("name"));
                person.setSurname(rs.getString("surname"));
                person.setMobilenumber(rs.getString("mobilenumber"));
                Cart cart = new Cart();
                cart.setCartId(rs.getObject("cart_id", UUID.class));
                cart.setPersonId(rs.getObject("person_id", UUID.class));
                cart.setCartname(rs.getString("cart_name"));
                person.setPersonCart(cart);
                list.add(person);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public List<Person> find(FindPersonRequest findPersonRequest) {
        Statement statement;
        List<Person> list = new ArrayList<>();
        ResultSet rs = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select * from person where ");
            sb.append(toSQLStringStatement(findPersonRequest));
            System.out.println(sb.toString());
            statement = connection.createStatement();
            rs = statement.executeQuery(sb.toString());
            while (rs.next()) {
                Person person = new Person();
                person.setPersonId(rs.getObject("person_id", UUID.class));
                person.setName(rs.getString("name"));
                person.setSurname(rs.getString("surname"));
                person.setMobilenumber(rs.getString("mobilenumber"));
                list.add(person);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public Person findWithCartWithBooks(FindPersonRequest findPersonRequest) {
        Statement statement;
        Person person = new Person();
        ResultSet rs = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select p.*, c.*, b.* from person AS p left join cart AS c on p.person_id=c.person_id left join cart_has_book AS chs on c.cart_id=chs.cart_id left join book AS b on chs.book_id=b.book_id where ");
            if (!findPersonRequest.getPersonIds().isEmpty()) {
                sb.append("p.person_id ");
                if (findPersonRequest.getPersonIds().size() > 1) {
                    sb.append("in (");
                    for (UUID person_id : findPersonRequest.getPersonIds()) {
                        sb.append("'").append(person_id).append("', ");
                    }
                    sb.deleteCharAt(sb.lastIndexOf(","));
                    sb.append(") AND ");
                } else {
                    sb.append("='").append(findPersonRequest.getPersonIds().get(0)).append("' AND ");
                }
            }
            if (!findPersonRequest.getPersonNames().isEmpty()) {
                sb.append("p.name ");
                if (findPersonRequest.getPersonNames().size() > 1) {
                    sb.append("in (");
                    for (String name : findPersonRequest.getPersonNames()) {
                        sb.append("'").append(name).append("', ");
                    }
                    sb.deleteCharAt(sb.lastIndexOf(","));
                    sb.append(") AND ");
                } else {
                    sb.append("='").append(findPersonRequest.getPersonNames().get(0)).append("' AND ");
                }
            }
            if (!findPersonRequest.getPersonSurnames().isEmpty()) {
                sb.append("p.surname ");
                if (findPersonRequest.getPersonSurnames().size() > 1) {
                    sb.append("in (");
                    for (String surname : findPersonRequest.getPersonSurnames()) {
                        sb.append("'").append(surname).append("', ");
                    }
                    sb.deleteCharAt(sb.lastIndexOf(","));
                    sb.append(") AND ");
                } else {
                    sb.append("='").append(findPersonRequest.getPersonSurnames().get(0)).append("' AND ");
                }
            }
            if (!findPersonRequest.getPersonMobilenumbers().isEmpty()) {
                sb.append("p.mobilenumber ");
                if (findPersonRequest.getPersonMobilenumbers().size() > 1) {
                    sb.append("in (");
                    for (String mobilenumber : findPersonRequest.getPersonMobilenumbers()) {
                        sb.append("'").append(mobilenumber).append("', ");
                    }
                    sb.deleteCharAt(sb.lastIndexOf(","));
                    sb.append(") AND ");
                } else {
                    sb.append("='").append(findPersonRequest.getPersonMobilenumbers().get(0)).append("' AND ");
                }
            }
            sb.delete(sb.length() - 4, sb.length());
            sb.append(";");
            System.out.println(sb.toString());
            statement = connection.createStatement();
            rs = statement.executeQuery(sb.toString());
            Cart cart = new Cart();
            while (rs.next()) {
                person.setPersonId(rs.getObject("person_id", UUID.class));
                person.setName(rs.getString("name"));
                person.setSurname(rs.getString("surname"));
                person.setMobilenumber(rs.getString("mobilenumber"));
                cart.setCartId(rs.getObject("cart_id", UUID.class));
                cart.setPersonId(rs.getObject("person_id", UUID.class));
                cart.setCartname(rs.getString("cart_name"));
                Book book = new Book();
                book.setBookId(rs.getObject("book_id", UUID.class));
                book.setBookname(rs.getString("bookname"));
                book.setAuthor(rs.getString("author"));
                book.setCostInByn(rs.getInt("cost_in_byn"));
                book.setCountInStock(rs.getInt("count_in_stock"));
                cart.getBooks().add(book);
                person.setPersonCart(cart);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return person;
    }

    public Person findWithPersonOrdersWithBooks(FindPersonRequest findPersonRequest) {
        Statement statement;
        Person person = new Person();
        ResultSet rs = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select p.*, po.*, pohb.*, b.* from person AS p left join person_order AS po on p.person_id=po.person_id left join person_order_has_book AS pohb on po.order_id=pohb.order_id left join book AS b on pohb.book_id=b.book_id where ");
            if (!findPersonRequest.getPersonIds().isEmpty()) {
                sb.append("p.person_id ");
                if (findPersonRequest.getPersonIds().size() > 1) {
                    sb.append("in (");
                    for (UUID person_id : findPersonRequest.getPersonIds()) {
                        sb.append("'").append(person_id).append("', ");
                    }
                    sb.deleteCharAt(sb.lastIndexOf(","));
                    sb.append(") AND ");
                } else {
                    sb.append("='").append(findPersonRequest.getPersonIds().get(0)).append("' AND ");
                }
            }
            if (!findPersonRequest.getPersonNames().isEmpty()) {
                sb.append("p.name ");
                if (findPersonRequest.getPersonNames().size() > 1) {
                    sb.append("in (");
                    for (String name : findPersonRequest.getPersonNames()) {
                        sb.append("'").append(name).append("', ");
                    }
                    sb.deleteCharAt(sb.lastIndexOf(","));
                    sb.append(") AND ");
                } else {
                    sb.append("='").append(findPersonRequest.getPersonNames().get(0)).append("' AND ");
                }
            }
            if (!findPersonRequest.getPersonSurnames().isEmpty()) {
                sb.append("p.surname ");
                if (findPersonRequest.getPersonSurnames().size() > 1) {
                    sb.append("in (");
                    for (String surname : findPersonRequest.getPersonSurnames()) {
                        sb.append("'").append(surname).append("', ");
                    }
                    sb.deleteCharAt(sb.lastIndexOf(","));
                    sb.append(") AND ");
                } else {
                    sb.append("='").append(findPersonRequest.getPersonSurnames().get(0)).append("' AND ");
                }
            }
            if (!findPersonRequest.getPersonMobilenumbers().isEmpty()) {
                sb.append("p.mobilenumber ");
                if (findPersonRequest.getPersonMobilenumbers().size() > 1) {
                    sb.append("in (");
                    for (String mobilenumber : findPersonRequest.getPersonMobilenumbers()) {
                        sb.append("'").append(mobilenumber).append("', ");
                    }
                    sb.deleteCharAt(sb.lastIndexOf(","));
                    sb.append(") AND ");
                } else {
                    sb.append("='").append(findPersonRequest.getPersonMobilenumbers().get(0)).append("' AND ");
                }
            }
            sb.delete(sb.length() - 4, sb.length());
            sb.append(";");
            System.out.println(sb.toString());
            statement = connection.createStatement();
            rs = statement.executeQuery(sb.toString());
            int orders_counter = 0;
            UUID temp = null;
            while (rs.next()) {
                person.setPersonId(rs.getObject("person_id", UUID.class));
                person.setName(rs.getString("name"));
                person.setSurname(rs.getString("surname"));
                person.setMobilenumber(rs.getString("mobilenumber"));
                Book book = new Book();
                if (!rs.getObject("order_id", UUID.class).equals(temp)) {
                    PersonOrder personOrder = new PersonOrder();
                    personOrder.setOrderId((rs.getObject("order_id", UUID.class)));
                    personOrder.setPersonId(rs.getObject("person_id", UUID.class));
                    personOrder.setAdress(rs.getString("adress"));
                    personOrder.setStatusId(rs.getInt("status_id"));
                    book.setBookId(rs.getObject("book_id", UUID.class));
                    book.setBookname(rs.getString("bookname"));
                    book.setAuthor(rs.getString("author"));
                    book.setCostInByn(rs.getInt("cost_in_byn"));
                    book.setCountInStock(rs.getInt("count_in_stock"));
                    personOrder.getBooks().add(book);
                    person.getPersonOrders().add(personOrder);
                    orders_counter++;
                } else {
                    book.setBookId(rs.getObject("book_id", UUID.class));
                    book.setBookname(rs.getString("bookname"));
                    book.setAuthor(rs.getString("author"));
                    book.setCostInByn(rs.getInt("cost_in_byn"));
                    book.setCountInStock(rs.getInt("count_in_stock"));
                    person.getPersonOrders().get(orders_counter - 1).getBooks().add(book);
                }
                temp = rs.getObject("order_id", UUID.class);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return person;
    }


    public void update(UpdatePersonRequest updatePersonRequest, FindPersonRequest findPersonRequest) {
        Statement statement;
        Person person;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("update person set ");

            if (updatePersonRequest.getPersonIds() != null)
                sb.append("person_id='").append(updatePersonRequest.getPersonIds()).append("', ");

            if (updatePersonRequest.getPersonNames() != null)
                sb.append("name='").append(updatePersonRequest.getPersonNames()).append("', ");
            if (updatePersonRequest.getPersonSurnames() != null)
                sb.append("surname='").append(updatePersonRequest.getPersonSurnames()).append("', ");
            if (updatePersonRequest.getPersonMobilenumbers() != null)
                sb.append("mobilenumber='").append(updatePersonRequest.getPersonMobilenumbers()).append("', ");
            sb.deleteCharAt(sb.lastIndexOf(","));
            sb.append("where ");

            sb.append(toSQLStringStatement(findPersonRequest));
            System.out.println(sb.toString());
            statement = connection.createStatement();
            statement.executeUpdate(sb.toString());
            System.out.println("Data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Person updatePerson(Person person) {
        FindPersonRequest findPersonRequest = new FindPersonRequest().setPersonId(person.getPersonId());
        UpdatePersonRequest updatePersonRequest = new UpdatePersonRequest();
        updatePersonRequest.setPersonId(person.getPersonId()).setPersonName(person.getName()).setPersonSurname(person.getSurname()).setPersonMobileNumber(person.getMobilenumber());
        update(updatePersonRequest, findPersonRequest);
        return person;
    }

    public void delete(FindPersonRequest findPersonRequest) {
        Statement statement;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("delete from person where ");
            sb.append(toSQLStringStatement(findPersonRequest));
            System.out.println(sb.toString());
            statement = connection.createStatement();
            statement.executeUpdate(sb.toString());
            System.out.println("Data deleted");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public String toSQLStringStatement(FindPersonRequest findPersonRequest) {
        StringBuilder sb=new StringBuilder();
        if (!findPersonRequest.getPersonIds().isEmpty()) {
            sb.append("person_id ");
            if (findPersonRequest.getPersonIds().size() > 1) {
                sb.append("in (");
                for (UUID person_id : findPersonRequest.getPersonIds()) {
                    sb.append("'").append(person_id).append("', ");
                }
                sb.deleteCharAt(sb.lastIndexOf(","));
                sb.append(") AND ");
            } else {
                sb.append("='").append(findPersonRequest.getPersonIds().get(0)).append("' AND ");
            }
        }
        if (!findPersonRequest.getPersonNames().isEmpty()) {
            sb.append("name ");
            if (findPersonRequest.getPersonNames().size() > 1) {
                sb.append("in (");
                for (String name : findPersonRequest.getPersonNames()) {
                    sb.append("'").append(name).append("', ");
                }
                sb.deleteCharAt(sb.lastIndexOf(","));
                sb.append(") AND ");
            } else {
                sb.append("='").append(findPersonRequest.getPersonNames().get(0)).append("' AND ");
            }
        }
        if (!findPersonRequest.getPersonSurnames().isEmpty()) {
            sb.append("surname ");
            if (findPersonRequest.getPersonSurnames().size() > 1) {
                sb.append("in (");
                for (String surname : findPersonRequest.getPersonSurnames()) {
                    sb.append("'").append(surname).append("', ");
                }
                sb.deleteCharAt(sb.lastIndexOf(","));
                sb.append(") AND ");
            } else {
                sb.append("='").append(findPersonRequest.getPersonSurnames().get(0)).append("' AND ");
            }
        }
        if (!findPersonRequest.getPersonMobilenumbers().isEmpty()) {
            sb.append("mobilenumber ");
            if (findPersonRequest.getPersonMobilenumbers().size() > 1) {
                sb.append("in (");
                for (String mobilenumber :findPersonRequest.getPersonMobilenumbers()) {
                    sb.append("'").append(mobilenumber).append("', ");
                }
                sb.deleteCharAt(sb.lastIndexOf(","));
                sb.append(") AND ");
            } else {
                sb.append("='").append(findPersonRequest.getPersonMobilenumbers().get(0)).append("' AND ");
            }
        }
        sb.delete(sb.length() - 4, sb.length());
        sb.append(";");
        return sb.toString();
    }
    public PersonDAO(Connection connection) {
        this.connection = connection;
    }
}
