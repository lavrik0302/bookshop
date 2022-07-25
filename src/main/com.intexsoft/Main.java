import java.sql.Connection;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        ConnectToDb dbFunctions = new ConnectToDb();
        Connection connection = dbFunctions.connect_to_db("bookshop", "postgres", "postgrespw");
        BookCRUD bookCRUD = new BookCRUD();
        // bookCRUD.insert_row(connection, "Tom Sawyer", "Twain", 24,13);
        bookCRUD.read_data(connection);
        UUID uuid = UUID.fromString("38dd1d53-4561-477b-be8c-0c0f87ce2a26");
        System.out.println("-----------------------------------");
        bookCRUD.delete_row_by_count_in_stock(connection, 233);
        bookCRUD.read_data(connection);
    }
}
