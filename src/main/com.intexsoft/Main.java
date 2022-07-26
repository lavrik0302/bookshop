import controler.ConnectToDb;
import controler.FindPersonRequest;
import controler.PersonDAO;
import model.Person;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        ConnectToDb dbFunctions = new ConnectToDb();
        Connection connection = dbFunctions.connect_to_db("bookshop", "postgres", "postgrespw");
        PersonDAO personDAO = new PersonDAO();
        UUID uuid=UUID.fromString("46b42e1c-3d77-4b29-be35-ca62b41cb311");
        personDAO.delete(connection, new FindPersonRequest().setPersonSurname("Colt").setPersonId(uuid));
    }
}
