import controler.*;
import controler.dao.*;
import controler.findRequests.*;
import model.*;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        ConnectToDb dbFunctions = new ConnectToDb();
        Connection connection = dbFunctions.connect_to_db("bookshop", "postgres", "postgrespw");
        BookDAO bookDAO = new BookDAO();
        PersonDAO personDAO = new PersonDAO();
        CartDAO cartDAO = new CartDAO();
        PersonOrderDAO personOrderDAO = new PersonOrderDAO();

        PersonOrderHasBookDAO personOrderHasBookDAO = new PersonOrderHasBookDAO();
        PersonOrderHasBook personOrderHasBook = new PersonOrderHasBook();
        System.out.println(cartDAO.findWithBooks(connection,new FindCartRequest().setCartCartname("Doe cart")));
    }
}
