import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        ConnectToDb dbFunctions = new ConnectToDb();
        Connection connection = dbFunctions.connect_to_db("bookshop", "postgres", "postgrespw");
        BookCRUD bookCRUD=new BookCRUD();
        bookCRUD.insert_row(connection,"1984", "Orwell", 29, 16);
    }
}
