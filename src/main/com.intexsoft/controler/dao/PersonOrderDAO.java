package controler.dao;

import controler.findRequests.FindPersonOrderRequest;

import controler.updateRequests.UpdatePersonOrderRequest;
import model.PersonOrder;
import model.PersonOrderHasBook;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PersonOrderDAO {
    public PersonOrder createRow(Connection connection, UUID personId, String adress, int statusId) {
        Statement statement;
        PersonOrder personOrder = new PersonOrder();
        try {
            UUID uuid = UUID.randomUUID();
            personOrder.setOrderId(uuid);
            personOrder.setPersonId(personId);
            personOrder.setAdress(adress);
            personOrder.setStatusId(statusId);
            String query = "insert into person_order values ('" + uuid + "', '" + personId + "', '" + adress + "', '" + statusId + "');";
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Insert success");
        } catch (Exception e) {
            System.out.println(e);
        }
        return personOrder;
    }

    public PersonOrder createPersonOrder(Connection connection, PersonOrder personOrder) {
        Statement statement;
        try {
            String query = "insert into person_order values ('" + personOrder.getOrderId() + "', '" + personOrder.getPersonId() + "', '" + personOrder.getAdress() + "', '" + personOrder.getStatusId() + "');";
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Insert success");
        } catch (Exception e) {
            System.out.println(e);
        }
        return personOrder;
    }

    public List<PersonOrder> readAll(Connection connection) {
        List<PersonOrder> list = new ArrayList<>();
        Statement statement;
        ResultSet rs = null;
        try {
            String query = "select * from person_order";
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                PersonOrder personOrder = new PersonOrder();
                personOrder.setOrderId(rs.getObject(1, UUID.class));
                personOrder.setPersonId(rs.getObject(2, UUID.class));
                personOrder.setAdress(rs.getString(3));
                personOrder.setStatusId(rs.getInt(4));
                list.add(personOrder);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public List<PersonOrder> find(Connection connection, FindPersonOrderRequest findPersonOrderRequest) {
        Statement statement;
        List<PersonOrder> list = new ArrayList<>();
        PersonOrder personOrder = new PersonOrder();
        ResultSet rs = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select * from person_order where ");
            sb.append(findPersonOrderRequest.toSQLStringStatement());
            System.out.println(sb.toString());
            statement = connection.createStatement();
            rs = statement.executeQuery(sb.toString());
            while (rs.next()) {
                personOrder.setOrderId(rs.getObject(1, UUID.class));
                personOrder.setPersonId(rs.getObject(2, UUID.class));
                personOrder.setAdress(rs.getString(3));
                personOrder.setStatusId(rs.getInt(4));
                list.add(personOrder);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public PersonOrder findWithBooks(Connection connection, FindPersonOrderRequest findPersonOrderRequest) {
        Statement statement;
        PersonOrder personOrder = new PersonOrder();
        ResultSet rs = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select po.order_id, po.person_id, po.adress, po.status_id, pohb.book_id, pohb.book_count from person_order AS po left join person_order_has_book AS pohb on po.order_id=pohb.order_id where ");
            if (findPersonOrderRequest.getOrderIds() != null) {
                sb.append("po.order_id ");
                sb.append("='" + findPersonOrderRequest.getOrderIds() + "' AND ");
            }
            if (findPersonOrderRequest.getPersonIds() != null) {
                sb.append("po.person_id ");
                sb.append("='" + findPersonOrderRequest.getPersonIds() + "' AND ");
            }
            if (findPersonOrderRequest.getAdresses() != null) {
                sb.append("po.adress ");
                sb.append("='" + findPersonOrderRequest.getAdresses() + "' AND ");
            }
            if (findPersonOrderRequest.getStatusIds() != null) {
                sb.append("po.status_id ");
                sb.append("='" + findPersonOrderRequest.getStatusIds() + "' AND ");
            }
            sb.delete(sb.length() - 4, sb.length());
            sb.append(";");
            System.out.println(sb.toString());
            statement = connection.createStatement();
            rs = statement.executeQuery(sb.toString());
            while (rs.next()) {
                personOrder.setOrderId(rs.getObject(1, UUID.class));
                personOrder.setPersonId(rs.getObject(2, UUID.class));
                personOrder.setAdress(rs.getString(3));
                personOrder.setStatusId(rs.getInt(4));
                PersonOrderHasBook personOrderHasBook = new PersonOrderHasBook();
                personOrderHasBook.setOrderId(rs.getObject(1, UUID.class));
                personOrderHasBook.setBookId(rs.getObject(5, UUID.class));
                personOrderHasBook.setBookCount(rs.getInt(6));
                personOrder.getOrderHasBooks().add(personOrderHasBook);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return personOrder;
    }

    public void update(Connection connection, UpdatePersonOrderRequest updatePersonOrderRequest, FindPersonOrderRequest findPersonOrderRequest) {
        Statement statement;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("update person_order set ");

            if (updatePersonOrderRequest.getOrderIds() != null)
                sb.append("order_id" + "='" + updatePersonOrderRequest.getPersonIds() + "', ");
            if (updatePersonOrderRequest.getPersonIds() != null)
                sb.append("person_id" + "='" + updatePersonOrderRequest.getPersonIds() + "', ");
            if (updatePersonOrderRequest.getAdresses() != null)
                sb.append("adress" + "='" + updatePersonOrderRequest.getAdresses() + "', ");
            if (updatePersonOrderRequest.getStatusIds() != null)
                sb.append("status_id" + "='" + updatePersonOrderRequest.getStatusIds() + "', ");

            sb.deleteCharAt(sb.lastIndexOf(","));
            sb.append("where ");
            sb.append(findPersonOrderRequest.toSQLStringStatement());
            System.out.println(sb.toString());
            statement = connection.createStatement();
            statement.executeUpdate(sb.toString());
            System.out.println("Data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public PersonOrder updatePersonOrder(Connection connection, PersonOrder personOrder, FindPersonOrderRequest findPersonOrderRequest) {
        UpdatePersonOrderRequest updatePersonOrderRequest = new UpdatePersonOrderRequest();
        updatePersonOrderRequest.setOrderId(personOrder.getOrderId()).setPersonId(personOrder.getPersonId()).setAdress(personOrder.getAdress()).setStatusId(personOrder.getStatusId());
        update(connection, updatePersonOrderRequest, findPersonOrderRequest);
        return personOrder;
    }

    public void delete(Connection connection, FindPersonOrderRequest findPersonOrderRequest) {
        Statement statement;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("delete from person_order where ");
            sb.append(findPersonOrderRequest.toSQLStringStatement());
            System.out.println(sb.toString());
            statement = connection.createStatement();
            statement.executeUpdate(sb.toString());
            System.out.println("Data deleted");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
