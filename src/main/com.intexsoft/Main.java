import java.sql.Connection;
import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        ConnectToDb dbFunctions = new ConnectToDb();
        Connection connection = dbFunctions.connect_to_db("bookshop", "postgres", "postgrespw");
        PersonCRUD personCRUD = new PersonCRUD();
        CartCrud cartCrud = new CartCrud();
        //UUID uuid=personCRUD.select_person_id_by_surname(connection,"Doe");
        // cartCrud.insert_row_by_person_mobileNumber(connection, "+375298691234", "Lavrenchuk cart");
        UUID uuid1 = UUID.fromString("7aecde76-319d-4826-8ac9-55460391fe7e");
        // System.out.println(cartCrud.select_cart_id_by_person_id(connection,uuid1));
        String[] excepted = {"name"};
        String[] columnNames = {"surname", "mobilenumber"};
        String[] values = {"Doe", "+375298690771"};
        List val = personCRUD.select_custom(connection, excepted, columnNames, values);
        System.out.println(val.get(0));
        // System.out.println(val.get(1));
    }
}
