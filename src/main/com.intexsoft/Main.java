import java.sql.Connection;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        ConnectToDb dbFunctions = new ConnectToDb();
        Connection connection = dbFunctions.connect_to_db("bookshop", "postgres", "postgrespw");
        BookCRUD bookCRUD=new BookCRUD();
       UUID uuid=UUID.fromString("88744eb7-4616-4844-9789-82a549b2ed51");
        System.out.println(bookCRUD.select_count_in_stock_by_book_id(connection,uuid));
    }
}
