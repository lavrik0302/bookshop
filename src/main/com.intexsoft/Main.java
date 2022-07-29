import controler.*;
import controler.dao.*;
import controler.findRequest.*;
import model.*;

import java.sql.Connection;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        ConnectToDb dbFunctions = new ConnectToDb();
        Connection connection = dbFunctions.connect_to_db("bookshop", "postgres", "postgrespw");
        BookDAO bookDAO = new BookDAO(connection);
        PersonDAO personDAO = new PersonDAO(connection);
        CartDAO cartDAO = new CartDAO(connection);
        PersonOrderDAO personOrderDAO = new PersonOrderDAO(connection);
        PersonOrderHasBookDAO personOrderHasBookDAO=new PersonOrderHasBookDAO(connection);
Book book=new Book();
book.setBookname("Bible");
book.setBookId(UUID.randomUUID());
book.setAuthor("NULL");
book.setCostInByn(23);
book.setCountInStock(56);
bookDAO.createBook(book);
    }
}
