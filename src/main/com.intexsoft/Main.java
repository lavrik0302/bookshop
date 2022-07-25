import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        DbFunctions dbFunctions = new DbFunctions();
        Connection connection = dbFunctions.connect_to_db("bookshop", "postgres", "postgrespw");
        //dbFunctions.createTable(connection, "person");
        //dbFunctions.insert_row(connection, "person", "John", "Doe", "+375292675618");
        //dbFunctions.update_name(connection, "person", "Alexey", "Alex");
        //dbFunctions.search_by_name(connection, "person", "Joseph");

        dbFunctions.read_data(connection, "person");
        dbFunctions.delete_table(connection,"person");
        dbFunctions.read_data(connection, "person");
    }
}
