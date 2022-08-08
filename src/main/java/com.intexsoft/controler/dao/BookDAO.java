package com.intexsoft.controler.dao;


import com.intexsoft.controler.ConnectionPool;
import com.intexsoft.controler.findRequest.FindBookRequest;
import com.intexsoft.controler.updateRequest.UpdateBookRequest;
import com.intexsoft.model.Book;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookDAO {

    Connection connection;


    public Book createRow(String bookname, String author, int costInByn, int countInStock) throws SQLException {
        PreparedStatement preparedStatement;
        Book book = new Book();
        try {
            UUID uuid = UUID.randomUUID();
            book.setBookId(uuid);
            book.setBookname(bookname);
            book.setAuthor(author);
            book.setCostInByn(costInByn);
            book.setCountInStock(countInStock);
            connection = ConnectionPool.getInstance().getConnection();
            System.out.println("Used connection: " + connection);
            preparedStatement = connection.prepareStatement("insert into book values (?, ?, ?, ?, ?)");
            preparedStatement.setObject(1, uuid);
            preparedStatement.setString(2, bookname);
            preparedStatement.setString(3, author);
            preparedStatement.setInt(4, costInByn);
            preparedStatement.setInt(5, countInStock);
            preparedStatement.executeUpdate();
            ConnectionPool.getInstance().releaseConnection(connection);
            System.out.println("Insert success");
        } catch (Exception e) {
            System.out.println(e);
        }


        return book;
    }

    public Book createBook(Book book) {
        PreparedStatement preparedStatement;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            System.out.println("Used connection: " + connection);
            preparedStatement = connection.prepareStatement("insert into book values (?, ?, ?, ?, ?)");
            preparedStatement.setObject(1, book.getBookId());
            preparedStatement.setString(2, book.getBookname());
            preparedStatement.setString(3, book.getAuthor());
            preparedStatement.setInt(4, book.getCostInByn());
            preparedStatement.setInt(5, book.getCountInStock());
            preparedStatement.executeUpdate();
            ConnectionPool.getInstance().releaseConnection(connection);
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
            connection = ConnectionPool.getInstance().getConnection();
            System.out.println("Used connection: " + connection);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            ConnectionPool.getInstance().releaseConnection(connection);
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

    public List<Book> find(FindBookRequest findBookRequest) throws SQLException {
        Statement statement;
        List<Book> list = new ArrayList<>();
        ResultSet rs = null;
        System.out.println("start of find");
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select * from book where ");
            sb.append(toSQLStringStatement(findBookRequest));
            System.out.println(sb.toString());
            connection = ConnectionPool.getInstance().getConnection();
            System.out.println("Used connection: " + connection);
            statement = connection.createStatement();
            rs = statement.executeQuery(sb.toString());
            ConnectionPool.getInstance().releaseConnection(connection);
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
            sb.append(toSQLStringStatement(findBookRequest));
            System.out.println(sb.toString());
            connection = ConnectionPool.getInstance().getConnection();
            System.out.println("Used connection: " + connection);
            statement = connection.createStatement();
            statement.executeUpdate(sb.toString());
            ConnectionPool.getInstance().releaseConnection(connection);
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

            sb.append(toSQLStringStatement(findBookRequest));
            System.out.println(sb.toString());
            connection = ConnectionPool.getInstance().getConnection();
            System.out.println("Used connection: " + connection);
            statement = connection.createStatement();
            statement.executeUpdate(sb.toString());
            ConnectionPool.getInstance().releaseConnection(connection);
            System.out.println("Data deleted");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String toSQLStringStatement(FindBookRequest findBookRequest) {
        StringBuilder sb = new StringBuilder();
        if (!findBookRequest.getBookIds().isEmpty()) {
            sb.append("book_id ");
            if (findBookRequest.getBookIds().size() > 1) {
                sb.append("in (");
                for (UUID book_id : findBookRequest.getBookIds()) {
                    sb.append("'").append(book_id).append("', ");
                }
                sb.deleteCharAt(sb.lastIndexOf(","));
                sb.append(") AND ");
            } else {
                sb.append("='").append(findBookRequest.getBookIds().get(0)).append("' AND ");
            }
        }
        if (!findBookRequest.getBookBooknames().isEmpty()) {
            sb.append("bookname ");
            if (findBookRequest.getBookBooknames().size() > 1) {
                sb.append("in (");
                for (String bookname : findBookRequest.getBookBooknames()) {
                    sb.append("'").append(bookname).append("', ");
                }
                sb.deleteCharAt(sb.lastIndexOf(","));
                sb.append(") AND ");
            } else {
                sb.append("='").append(findBookRequest.getBookBooknames().get(0)).append("' AND ");
            }
        }
        if (!findBookRequest.getBookAuthors().isEmpty()) {
            sb.append("author ");
            if (findBookRequest.getBookAuthors().size() > 1) {
                sb.append("in (");
                for (String author : findBookRequest.getBookAuthors()) {
                    sb.append("'").append(author).append("', ");
                }
                sb.deleteCharAt(sb.lastIndexOf(","));
                sb.append(") AND ");
            } else {
                sb.append("='").append(findBookRequest.getBookAuthors().get(0)).append("' AND ");
            }
        }
        if (findBookRequest.getBookFromToCostInByns().getFrom() != null) {
            sb.append("cost_in_byn ");

            sb.append(">='").append(findBookRequest.getBookFromToCostInByns().getFrom()).append("' AND ");
        }
        if (findBookRequest.getBookFromToCostInByns().getTo() != null) {
            sb.append("cost_in_byn ");

            sb.append("<='").append(findBookRequest.getBookFromToCostInByns().getTo()).append("' AND ");
        }
        if (findBookRequest.getBookCostInByns() != null) {
            sb.append("cost_in_byn='").append(findBookRequest.getBookCostInByns()).append("' AND ");
        }

        if (findBookRequest.getBookFromToCountInStocks().getFrom() != null) {
            sb.append("count_in_stock ");
            sb.append(">='").append(findBookRequest.getBookFromToCountInStocks().getFrom()).append("' AND ");
        }
        if (findBookRequest.getBookFromToCountInStocks().getTo() != null) {
            sb.append("count_in_stock ");
            sb.append("<='").append(findBookRequest.getBookFromToCountInStocks().getTo()).append("' AND ");
        }
        if (findBookRequest.getBookCountInStocks() != null) {
            sb.append("count_in_stock='").append(findBookRequest.getBookCountInStocks()).append("' AND ");
        }

        sb.delete(sb.length() - 4, sb.length());
        sb.append(";");
        return sb.toString();
    }


}