package controler.dao;

import controler.findRequest.FindPersonOrderHasBookRequest;
import controler.findRequest.toStringSqlStatement.ToSqlStringStatementForPersonOrderHasBook;
import controler.updateRequest.UpdatePersonOrderHasBookRequest;
import model.PersonOrderHasBook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PersonOrderHasBookDAO {
    Connection connection;

    public PersonOrderHasBook createRow(UUID orderId, UUID bookId, int bookCount) {
        PreparedStatement preparedStatement;
        PersonOrderHasBook personOrderHasBook = new PersonOrderHasBook();
        try {
            personOrderHasBook.setOrderId(orderId);
            personOrderHasBook.setBookId(bookId);
            personOrderHasBook.setBookCount(bookCount);
            preparedStatement=connection.prepareStatement("insert into person_order_has_book values (?, ?, ?)");
            preparedStatement.setObject(1, orderId);
            preparedStatement.setObject(2, bookId);
            preparedStatement.setInt(3,bookCount);
            preparedStatement.executeUpdate();

            System.out.println("Insert success");
        } catch (Exception e) {
            System.out.println(e);
        }
        return personOrderHasBook;
    }

    public PersonOrderHasBook createPersonOrderHasBook(PersonOrderHasBook personOrderHasBook) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement=connection.prepareStatement("insert into person_order_has_book values (?, ?, ?)");
            preparedStatement.setObject(1, personOrderHasBook.getOrderId());
            preparedStatement.setObject(2, personOrderHasBook.getBookId());
            preparedStatement.setInt(3,personOrderHasBook.getBookCount());
            preparedStatement.executeUpdate();
            System.out.println("Insert success");
        } catch (Exception e) {
            System.out.println(e);
        }
        return personOrderHasBook;
    }

    public List<PersonOrderHasBook> findAll(Connection connection) {
        List<PersonOrderHasBook> list = new ArrayList<>();
        Statement statement;
        ResultSet rs = null;
        try {
            String query = "select * from person_order_has_book";
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                PersonOrderHasBook personOrderHasBook = new PersonOrderHasBook();
                personOrderHasBook.setOrderId(rs.getObject("order_id", UUID.class));
                personOrderHasBook.setBookId(rs.getObject("book_id", UUID.class));
                personOrderHasBook.setBookCount(rs.getInt("book_count"));
                list.add(personOrderHasBook);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public List<PersonOrderHasBook> find(FindPersonOrderHasBookRequest findPersonOrderHasBookRequest) {
        Statement statement;
        List<PersonOrderHasBook> list = new ArrayList<>();
        ResultSet rs = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select * from person_order_has_book where ");
            ToSqlStringStatementForPersonOrderHasBook toSqlStringStatementForPersonOrderHasBook = new ToSqlStringStatementForPersonOrderHasBook();
            sb.append(toSqlStringStatementForPersonOrderHasBook.toSQLStringStatement(findPersonOrderHasBookRequest));
            System.out.println(sb.toString());
            statement = connection.createStatement();
            rs = statement.executeQuery(sb.toString());
            while (rs.next()) {
                PersonOrderHasBook personOrderHasBook = new PersonOrderHasBook();
                personOrderHasBook.setOrderId(rs.getObject("order_id", UUID.class));
                personOrderHasBook.setBookId(rs.getObject("book_id", UUID.class));
                personOrderHasBook.setBookCount(rs.getInt("book_count"));
                list.add(personOrderHasBook);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public void update(UpdatePersonOrderHasBookRequest updateCartHasBookRequest, FindPersonOrderHasBookRequest findPersonOrderHasBookRequest) {
        Statement statement;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("update person_order_has_book set ");

            if (updateCartHasBookRequest.getOrderIds() != null)
                sb.append("order_id='").append(updateCartHasBookRequest.getOrderIds()).append("', ");
            if (updateCartHasBookRequest.getBookIds() != null)
                sb.append("book_id='").append(updateCartHasBookRequest.getBookIds()).append("', ");
            if (updateCartHasBookRequest.getBookCounts() != null)
                sb.append("book_count='").append(updateCartHasBookRequest.getBookCounts()).append("', ");
            sb.deleteCharAt(sb.lastIndexOf(","));
            sb.append("where ");
            ToSqlStringStatementForPersonOrderHasBook toSqlStringStatementForPersonOrderHasBook = new ToSqlStringStatementForPersonOrderHasBook();
            sb.append(toSqlStringStatementForPersonOrderHasBook.toSQLStringStatement(findPersonOrderHasBookRequest));
            System.out.println(sb.toString());
            statement = connection.createStatement();
            statement.executeUpdate(sb.toString());
            System.out.println("Data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public PersonOrderHasBook updateCartHasBook(PersonOrderHasBook personOrderHasBook) {
        FindPersonOrderHasBookRequest findPersonOrderHasBookRequest = new FindPersonOrderHasBookRequest().setOrderId(personOrderHasBook.getOrderId());
        UpdatePersonOrderHasBookRequest updatePersonOrderHasBookRequest = new UpdatePersonOrderHasBookRequest();
        updatePersonOrderHasBookRequest.setOrderId(personOrderHasBook.getOrderId()).setBookId(personOrderHasBook.getBookId()).setBookCounts(personOrderHasBook.getBookCount());
        update(updatePersonOrderHasBookRequest, findPersonOrderHasBookRequest);
        return personOrderHasBook;
    }

    public void delete(FindPersonOrderHasBookRequest findPersonOrderHasBookRequest) {
        Statement statement;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("delete from person_order_has_book where ");
            ToSqlStringStatementForPersonOrderHasBook toSqlStringStatementForPersonOrderHasBook = new ToSqlStringStatementForPersonOrderHasBook();
            sb.append(toSqlStringStatementForPersonOrderHasBook.toSQLStringStatement(findPersonOrderHasBookRequest));
            System.out.println(sb.toString());
            statement = connection.createStatement();
            statement.executeUpdate(sb.toString());
            System.out.println("Data deleted");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public PersonOrderHasBookDAO(Connection connection) {
        this.connection = connection;
    }
}
