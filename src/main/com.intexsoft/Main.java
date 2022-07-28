import controler.*;
import controler.DAOs.BookDAO;
import controler.DAOs.CartDAO;
import controler.DAOs.CartHasBookDAO;
import controler.DAOs.PersonDAO;
import controler.FindRequests.FindBookRequest;
import controler.FindRequests.FindCartHasBookRequest;
import controler.FindRequests.FindCartRequest;
import controler.FindRequests.FindPersonRequest;
import controler.UpdateRequests.UpdateBookRequest;
import controler.UpdateRequests.UpdateCartHasBookRequest;
import controler.UpdateRequests.UpdatePersonRequest;
import model.Book;
import model.CartHasBook;
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
        CartHasBookDAO cartHasBookDAO=new CartHasBookDAO();
        CartHasBook cartHasBook=new CartHasBook();
        cartHasBook.setCartId(UUID.fromString("f9ac8f48-0041-43de-a035-4ca8c1fe1d47"));
        cartHasBook.setBookId(UUID.fromString("e1a26463-9dd9-4dd0-a5b7-4b173f8fb7f6"));
        cartHasBook.setBookCount(4);
cartHasBookDAO.delete(connection, new FindCartHasBookRequest().setCartId(UUID.fromString("f9ac8f48-0041-43de-a035-4ca8c1fe1d47")));    }
}
