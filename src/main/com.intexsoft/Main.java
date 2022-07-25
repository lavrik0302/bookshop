import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        ConnectToDb dbFunctions = new ConnectToDb();
        Connection connection = dbFunctions.connect_to_db("bookshop", "postgres", "postgrespw");
        PersonCRUD personCRUD = new PersonCRUD();
        personCRUD.read_data(connection);
       /* personCRUD.insert_row(connection, "Alexey", "Lavrenchuk", "+375298691234");
        personCRUD.insert_row(connection, "Jim", "Halpert", "+375292675618");
        personCRUD.insert_row(connection, "Sam", "Lawrence", "+375295876123");*/
    }
}
