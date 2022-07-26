import controler.BookDAO;
import controler.ConnectToDb;
import controler.FindPersonRequest;
import controler.PersonDAO;
import model.Book;
import model.Person;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        ConnectToDb dbFunctions = new ConnectToDb();
        Connection connection = dbFunctions.connect_to_db("bookshop", "postgres", "postgrespw");
        BookDAO bookDAO = new BookDAO();
        UUID uuid = UUID.fromString("269acb18-99b3-4a76-b21f-19669c6f8db6");
        Book book = new Book();
        book.setBookId(UUID.randomUUID());
        book.setBookname("Black Tower");
        book.setAuthor("Akunin");
        book.setCostInByn(29);
        book.setCountInStock(6);
        bookDAO.createBook(connection, book);
        System.out.println(bookDAO.readAll(connection).size());
    }
}
