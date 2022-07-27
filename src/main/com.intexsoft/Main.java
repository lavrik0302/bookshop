import controler.*;
import controler.DAOs.BookDAO;
import controler.DAOs.CartDAO;
import controler.DAOs.PersonDAO;
import controler.FindRequests.FindBookRequest;
import controler.FindRequests.FindCartRequest;
import controler.FindRequests.FindPersonRequest;
import controler.UpdateRequests.UpdateBookRequest;
import controler.UpdateRequests.UpdatePersonRequest;
import model.Book;
import model.Person;

import java.sql.Connection;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        ConnectToDb dbFunctions = new ConnectToDb();
        Connection connection = dbFunctions.connect_to_db("bookshop", "postgres", "postgrespw");
        BookDAO bookDAO = new BookDAO();
        PersonDAO personDAO = new PersonDAO();
        CartDAO cartDAO = new CartDAO();
        Book book = new Book();
        book.setBookId(UUID.randomUUID());
        book.setBookname("HP");
        book.setAuthor("Rowling");
        book.setCostInByn(38);
        book.setCountInStock(66);
        System.out.println(personDAO.findWithCart(connection, new FindPersonRequest().setPersonName("John")));    }
}
