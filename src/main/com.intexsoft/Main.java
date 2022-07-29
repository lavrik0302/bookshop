import controler.*;
import controler.dao.*;
import controler.findRequest.*;
import controler.findRequest.fromTo.BookFromToCostInByns;
import controler.findRequest.fromTo.BookFromToCountInStocks;


import java.sql.Connection;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        ConnectToDb dbFunctions = new ConnectToDb();
        Connection connection = dbFunctions.connect_to_db("bookshop", "postgres", "postgrespw");
        BookDAO bookDAO = new BookDAO(connection);
        PersonDAO personDAO = new PersonDAO(connection);
        CartDAO cartDAO = new CartDAO(connection);
        PersonOrderHasBookDAO personOrderHasBook = new PersonOrderHasBookDAO(connection);
        PersonOrderDAO personOrderDAO = new PersonOrderDAO(connection);
        PersonOrderHasBookDAO personOrderHasBookDAO = new PersonOrderHasBookDAO(connection);

    }
}
