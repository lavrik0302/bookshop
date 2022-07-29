package controler.dao;

import controler.findRequest.FindBookRequest;
import controler.findRequest.toStringSqlStatement.ToSqlStringStatementForBook;
import controler.updateRequest.UpdateBookRequest;
import model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookDAO {
    Connection connection;

    public Book createRow(String bookname, String author, int costInByn, int countInStock) {
        PreparedStatement preparedStatement;
        Book book = new Book();
        try {
            UUID uuid = UUID.randomUUID();
            book.setBookId(uuid);
            book.setBookname(bookname);
            book.setAuthor(author);
            book.setCostInByn(costInByn);
            book.setCountInStock(countInStock);
            preparedStatement = connection.prepareStatement("insert into book values (?, ?, ?, ?, ?)");
            preparedStatement.setObject(1, uuid);
            preparedStatement.setString(2, bookname);
            preparedStatement.setString(3, author);
            preparedStatement.setInt(4, costInByn);
            preparedStatement.setInt(5, countInStock);
            preparedStatement.executeUpdate();

            System.out.println("Insert success");
        } catch (Exception e) {
            System.out.println(e);
        }
        return book;
    }

    public Book createBook(Book book) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement("insert into book values (?, ?, ?, ?, ?)");
            preparedStatement.setObject(1, book.getBookId());
            preparedStatement.setString(2, book.getBookname());
            preparedStatement.setString(3, book.getAuthor());
            preparedStatement.setInt(4, book.getCostInByn());
            preparedStatement.setInt(5, book.getCountInStock());
            preparedStatement.executeUpdate();
            System.out.println("Insert success");
        } catch (Exception e) {
            System.out.println(e);
        }
        return book;
    }

    public List<Book> findAll() {
        List<Book> list = new ArrayList<>();
        Statement statement;
        ResultSet rs = null;
        try {
            String query = "select * from book";
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getObject("book_id", UUID.class));
                book.setBookname(rs.getString("bookname"));
                book.setAuthor(rs.getString("author"));
                book.setCostInByn(rs.getInt("cost_in_byn"));
                book.setCountInStock(rs.getInt("count_in_stock"));
                list.add(book);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public List<Book> find(FindBookRequest findBookRequest) {
        Statement statement;
        List<Book> list = new ArrayList<>();
        ResultSet rs = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select * from book where ");
            ToSqlStringStatementForBook toSqlStringStatementForBook = new ToSqlStringStatementForBook();
            sb.append(toSqlStringStatementForBook.toSQLStringStatement(findBookRequest));
            System.out.println(sb.toString());
            statement = connection.createStatement();
            rs = statement.executeQuery(sb.toString());
            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getObject("book_id", UUID.class));
                book.setBookname(rs.getString("bookname"));
                book.setAuthor(rs.getString("author"));
                book.setCostInByn(rs.getInt("cost_in_byn"));
                book.setCountInStock(rs.getInt("count_in_stock"));
                list.add(book);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public void update(UpdateBookRequest updateBookRequest, FindBookRequest findBookRequest) {
        Statement statement;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("update book set ");

            if (updateBookRequest.getBookIds() != null)
                sb.append("book_id='").append(updateBookRequest.getBookIds()).append("', ");
            if (updateBookRequest.getBookBooknames() != null)
                sb.append("bookname='").append(updateBookRequest.getBookBooknames()).append("', ");
            if (updateBookRequest.getBookAuthors() != null)
                sb.append("author='").append(updateBookRequest.getBookAuthors()).append("', ");
            if (updateBookRequest.getBookCostInByns() != null)
                sb.append("cost_in_byn='").append(updateBookRequest.getBookCostInByns()).append("', ");
            if (updateBookRequest.getBookCountInStocks() != null)
                sb.append("count_in_stock='").append(updateBookRequest.getBookCountInStocks()).append("', ");
            sb.deleteCharAt(sb.lastIndexOf(","));
            sb.append("where ");
            ToSqlStringStatementForBook toSqlStringStatementForBook = new ToSqlStringStatementForBook();
            sb.append(toSqlStringStatementForBook.toSQLStringStatement(findBookRequest));
            System.out.println(sb.toString());
            statement = connection.createStatement();
            statement.executeUpdate(sb.toString());
            System.out.println("Data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Book updateBook(Book book) {
        FindBookRequest findBookRequest = new FindBookRequest().setBookId(book.getBookId());
        UpdateBookRequest updateBookRequest = new UpdateBookRequest();
        updateBookRequest.setBookId(book.getBookId()).setBookBookname(book.getBookname()).setBookAuthor(book.getAuthor()).setBookCostInByn(book.getCostInByn()).setBookCountInStock(book.getCountInStock());
        update(updateBookRequest, findBookRequest);
        return book;
    }

    public void delete(FindBookRequest findBookRequest) {
        Statement statement;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("delete from book where ");
            ToSqlStringStatementForBook toSqlStringStatementForBook = new ToSqlStringStatementForBook();
            sb.append(toSqlStringStatementForBook.toSQLStringStatement(findBookRequest));
            System.out.println(sb.toString());
            statement = connection.createStatement();
            statement.executeUpdate(sb.toString());
            System.out.println("Data deleted");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public BookDAO(Connection connection) {
        this.connection = connection;
    }
}