package controler.dao;

import controler.findRequests.FindPersonOrderHasBookRequest;
import controler.updateRequests.UpdatePersonOrderHasBookRequest;
import model.PersonOrderHasBook;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PersonOrderHasBookDAO {
    public PersonOrderHasBook createRow(Connection connection, UUID orderId, UUID bookId, int bookCount) {
        Statement statement;
        PersonOrderHasBook personOrderHasBook=new PersonOrderHasBook();
        try {
            personOrderHasBook.setOrderId(orderId);
            personOrderHasBook.setBookId(bookId);
            personOrderHasBook.setBookCount(bookCount);
            String query = "insert into person_order_has_book values ('" + orderId + "', '" + bookId + "', '" + bookCount + "');";
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Insert success");
        } catch (Exception e) {
            System.out.println(e);
        }
        return personOrderHasBook;
    }
    public PersonOrderHasBook createPersonOrderHasBook(Connection connection, PersonOrderHasBook personOrderHasBook) {
        Statement statement;
        try {
            String query = "insert into person_order_has_book values ('" + personOrderHasBook.getOrderId() + "', '" + personOrderHasBook.getBookId() + "', '" + personOrderHasBook.getBookCount() + "');";
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Insert success");
        } catch (Exception e) {
            System.out.println(e);
        }
        return personOrderHasBook;
    }
    public List<PersonOrderHasBook> readAll(Connection connection) {
        List<PersonOrderHasBook> list = new ArrayList<>();
        Statement statement;
        ResultSet rs = null;
        try {
            String query = "select * from person_order_has_book";
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                PersonOrderHasBook personOrderHasBook=new PersonOrderHasBook();
                personOrderHasBook.setOrderId(rs.getObject(1, UUID.class));
                personOrderHasBook.setBookId(rs.getObject(2, UUID.class));
                personOrderHasBook.setBookCount(rs.getInt(3));
                list.add(personOrderHasBook);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }
    public List<PersonOrderHasBook> find(Connection connection, FindPersonOrderHasBookRequest findPersonOrderHasBookRequest) {
        Statement statement;
        List<PersonOrderHasBook> list=new ArrayList<>();
        ResultSet rs = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select * from person_order_has_book where ");
            sb.append(findPersonOrderHasBookRequest.toSQLStringStatement());
            System.out.println(sb.toString());
            statement = connection.createStatement();
            rs = statement.executeQuery(sb.toString());
            while (rs.next()) {
                PersonOrderHasBook personOrderHasBook=new PersonOrderHasBook();
                personOrderHasBook.setOrderId(rs.getObject(1,UUID.class));
                personOrderHasBook.setBookId(rs.getObject(2, UUID.class));
                personOrderHasBook.setBookCount(rs.getInt(3));
                list.add(personOrderHasBook);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }
    public void update(Connection connection, UpdatePersonOrderHasBookRequest updateCartHasBookRequest, FindPersonOrderHasBookRequest findPersonOrderHasBookRequest) {
        Statement statement;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("update person_order_has_book set ");

            if (updateCartHasBookRequest.getOrderIds() != null)
                sb.append("order_id" + "='" + updateCartHasBookRequest.getOrderIds() + "', ");
            if (updateCartHasBookRequest.getBookIds() != null)
                sb.append("book_id" + "='" + updateCartHasBookRequest.getBookIds() + "', ");
            if (updateCartHasBookRequest.getBookCounts() != null)
                sb.append("book_count" + "='" + updateCartHasBookRequest.getBookCounts() + "', ");
            sb.deleteCharAt(sb.lastIndexOf(","));
            sb.append("where ");
            sb.append(findPersonOrderHasBookRequest.toSQLStringStatement());
            System.out.println(sb.toString());
            statement = connection.createStatement();
            statement.executeUpdate(sb.toString());
            System.out.println("Data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public PersonOrderHasBook updateCartHasBook(Connection connection, PersonOrderHasBook personOrderHasBook, FindPersonOrderHasBookRequest findPersonOrderHasBookRequest) {
        UpdatePersonOrderHasBookRequest updatePersonOrderHasBookRequest=new UpdatePersonOrderHasBookRequest();
        updatePersonOrderHasBookRequest.setOrderId(personOrderHasBook.getOrderId()).setBookId(personOrderHasBook.getBookId()).setBookCounts(personOrderHasBook.getBookCount());
        update(connection, updatePersonOrderHasBookRequest, findPersonOrderHasBookRequest);
        return personOrderHasBook;
    }

    public void delete(Connection connection, FindPersonOrderHasBookRequest findPersonOrderHasBookRequest) {
        Statement statement;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("delete from person_order_has_book where ");
            sb.append(findPersonOrderHasBookRequest.toSQLStringStatement());
            System.out.println(sb.toString());
            statement = connection.createStatement();
            statement.executeUpdate(sb.toString());
            System.out.println("Data deleted");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
