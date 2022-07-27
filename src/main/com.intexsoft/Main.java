import controler.*;
import controler.DAOs.BookDAO;
import controler.DAOs.CartDAO;
import controler.DAOs.PersonDAO;
import controler.FindRequests.FindBookRequest;
import controler.FindRequests.FindCartRequest;
import controler.FindRequests.FindPersonRequest;

import java.sql.Connection;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        ConnectToDb dbFunctions = new ConnectToDb();
        Connection connection = dbFunctions.connect_to_db("bookshop", "postgres", "postgrespw");
        BookDAO bookDAO = new BookDAO();
        PersonDAO personDAO = new PersonDAO();
        CartDAO cartDAO = new CartDAO();
    }
}
