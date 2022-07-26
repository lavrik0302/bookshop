package controler;

import model.Book;
import model.Person;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookDAO {
    public void createRow(Connection connection, String bookname, String author, int costInByn, int countInStock) {
        Statement statement;
        try {
            UUID uuid = UUID.randomUUID();
            String query = "insert into book values ('" + uuid + "', '" + bookname + "', '" + author + "', " + costInByn + ", "+countInStock+");";
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Insert success");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void createBook(Connection connection, Book book){
        Statement statement;
        try {
            String query = "insert into book values ('" + book.getBookId() + "', '" + book.getBookname()+ "', '" + book.getAuthor() + "', " + book.getCostInByn() + ", "+book.getCountInStock()+");";
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Insert success");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public List<Book> readAll(Connection connection) {
        List<Book> list = new ArrayList<>();
        Statement statement;
        ResultSet rs = null;
        try {
            String query = "select * from book";
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getObject(1, UUID.class));
                book.setBookname(rs.getString(2));
                book.setAuthor(rs.getString(3));
                book.setCostInByn(rs.getInt(4));
                book.setCountInStock(rs.getInt(5));
                list.add(book);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }
}
