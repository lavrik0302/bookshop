package com.intexsoft.controler.dao;


import com.intexsoft.controler.ConnectionPool;
import com.intexsoft.controler.findRequest.FindPersonOrderRequest;
import com.intexsoft.controler.updateRequest.UpdatePersonOrderRequest;
import com.intexsoft.model.Book;
import com.intexsoft.model.PersonOrder;
import com.intexsoft.model.PersonOrderHasBook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PersonOrderDAO {
    Connection connection;

    public PersonOrder createRow(UUID personId, String adress, int statusId) {
        PreparedStatement preparedStatement;
        PersonOrder personOrder = new PersonOrder();
        try {
            UUID uuid = UUID.randomUUID();
            personOrder.setOrderId(uuid);
            personOrder.setPersonId(personId);
            personOrder.setAdress(adress);
            personOrder.setStatusId(statusId);
            connection = ConnectionPool.getInstance().getConnection();
            System.out.println("Used connection: " + connection);
            preparedStatement = connection.prepareStatement("insert into person_order values(?, ?, ?, ?)");
            preparedStatement.setObject(1, uuid);
            preparedStatement.setObject(2, personId);
            preparedStatement.setString(3, adress);
            preparedStatement.setInt(4, statusId);
            preparedStatement.executeUpdate();
            ConnectionPool.getInstance().releaseConnection(connection);
            System.out.println("Insert success");
        } catch (Exception e) {
            System.out.println(e);
        }
        return personOrder;
    }

    public PersonOrder createPersonOrder(PersonOrder personOrder) {
        PreparedStatement preparedStatement;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            System.out.println("Used connection: " + connection);
            preparedStatement = connection.prepareStatement("insert into person_order values(?, ?, ?, ?)");
            preparedStatement.setObject(1, personOrder.getOrderId());
            preparedStatement.setObject(2, personOrder.getPersonId());
            preparedStatement.setString(3, personOrder.getAdress());
            preparedStatement.setInt(4, personOrder.getStatusId());
            preparedStatement.executeUpdate();
            ConnectionPool.getInstance().releaseConnection(connection);
            System.out.println("Insert success");
        } catch (Exception e) {
            System.out.println(e);
        }
        return personOrder;
    }

    public List<PersonOrder> findAll() {
        List<PersonOrder> list = new ArrayList<>();
        Statement statement;
        ResultSet rs = null;
        try {
            String query = "select * from person_order";
            connection = ConnectionPool.getInstance().getConnection();
            System.out.println("Used connection: " + connection);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            ConnectionPool.getInstance().releaseConnection(connection);
            while (rs.next()) {
                PersonOrder personOrder = new PersonOrder();
                personOrder.setOrderId(rs.getObject("order_id", UUID.class));
                personOrder.setPersonId(rs.getObject("person_id", UUID.class));
                personOrder.setAdress(rs.getString("adress"));
                personOrder.setStatusId(rs.getInt("status_id"));
                list.add(personOrder);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public List<PersonOrder> find(FindPersonOrderRequest findPersonOrderRequest) {
        Statement statement;
        List<PersonOrder> list = new ArrayList<>();
        PersonOrder personOrder = new PersonOrder();
        ResultSet rs = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select * from person_order where ");
            sb.append(toSQLStringStatement(findPersonOrderRequest));
            System.out.println(sb.toString());
            connection = ConnectionPool.getInstance().getConnection();
            System.out.println("Used connection: " + connection);
            statement = connection.createStatement();
            rs = statement.executeQuery(sb.toString());
            ConnectionPool.getInstance().releaseConnection(connection);
            while (rs.next()) {
                personOrder.setOrderId(rs.getObject("order_id", UUID.class));
                personOrder.setPersonId(rs.getObject("person_id", UUID.class));
                personOrder.setAdress(rs.getString("adress"));
                personOrder.setStatusId(rs.getInt("status_id"));
                list.add(personOrder);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public PersonOrder findWithBooks(FindPersonOrderRequest findPersonOrderRequest) {
        Statement statement;
        PersonOrder personOrder = new PersonOrder();
        ResultSet rs = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select po.*, pohb.*, b.* from person_order AS po left join person_order_has_book AS pohb on po.order_id=pohb.order_id left join book AS b on pohb.book_id=b.book_id where ");
            if (findPersonOrderRequest.getOrderIds() != null) {
                sb.append("po.order_id ");
                sb.append("='").append(findPersonOrderRequest.getOrderIds()).append("' AND ");
            }
            if (findPersonOrderRequest.getPersonIds() != null) {
                sb.append("po.person_id ");
                sb.append("='").append(findPersonOrderRequest.getPersonIds()).append("' AND ");
            }
            if (findPersonOrderRequest.getAdresses() != null) {
                sb.append("po.adress ");
                sb.append("='").append(findPersonOrderRequest.getAdresses()).append("' AND ");
            }
            if (findPersonOrderRequest.getStatusIds() != null) {
                sb.append("po.status_id ");
                sb.append("='").append(findPersonOrderRequest.getStatusIds()).append("' AND ");
            }
            sb.delete(sb.length() - 4, sb.length());
            sb.append(";");
            System.out.println(sb.toString());
            connection = ConnectionPool.getInstance().getConnection();
            System.out.println("Used connection: " + connection);
            statement = connection.createStatement();
            rs = statement.executeQuery(sb.toString());
            Thread.sleep(5000);
            ConnectionPool.getInstance().releaseConnection(connection);
            while (rs.next()) {
                personOrder.setOrderId(rs.getObject("order_id", UUID.class));
                personOrder.setPersonId(rs.getObject("person_id", UUID.class));
                personOrder.setAdress(rs.getString("adress"));
                personOrder.setStatusId(rs.getInt("status_id"));
                PersonOrderHasBook personOrderHasBook = new PersonOrderHasBook();
                personOrderHasBook.setOrderId(rs.getObject("order_id", UUID.class));
                personOrderHasBook.setBookId(rs.getObject("book_id", UUID.class));
                personOrderHasBook.setBookCount(rs.getInt("book_count"));
                Book book = new Book();
                book.setBookId(rs.getObject("book_id", UUID.class));
                book.setBookname(rs.getString("bookname"));
                book.setAuthor(rs.getString("author"));
                book.setCostInByn(rs.getInt("cost_in_byn"));
                book.setCountInStock(rs.getInt("count_in_stock"));
                personOrder.getBooks().add(book);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return personOrder;
    }


    public void update(UpdatePersonOrderRequest updatePersonOrderRequest, FindPersonOrderRequest findPersonOrderRequest) {
        Statement statement;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("update person_order set ");

            if (updatePersonOrderRequest.getOrderIds() != null)
                sb.append("order_id='").append(updatePersonOrderRequest.getPersonIds()).append("', ");
            if (updatePersonOrderRequest.getPersonIds() != null)
                sb.append("person_id='").append(updatePersonOrderRequest.getPersonIds()).append("', ");
            if (updatePersonOrderRequest.getAdresses() != null)
                sb.append("adress='").append(updatePersonOrderRequest.getAdresses()).append("', ");
            if (updatePersonOrderRequest.getStatusIds() != null)
                sb.append("status_id='").append(updatePersonOrderRequest.getStatusIds()).append("', ");

            sb.deleteCharAt(sb.lastIndexOf(","));
            sb.append("where ");
            sb.append(toSQLStringStatement(findPersonOrderRequest));
            System.out.println(sb.toString());
            connection = ConnectionPool.getInstance().getConnection();
            System.out.println("Used connection: " + connection);
            statement = connection.createStatement();
            statement.executeUpdate(sb.toString());
            ConnectionPool.getInstance().releaseConnection(connection);
            System.out.println("Data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public PersonOrder updatePersonOrder(PersonOrder personOrder) {
        FindPersonOrderRequest findPersonOrderRequest = new FindPersonOrderRequest().setOrderId(personOrder.getOrderId());
        UpdatePersonOrderRequest updatePersonOrderRequest = new UpdatePersonOrderRequest();
        updatePersonOrderRequest.setOrderId(personOrder.getOrderId()).setPersonId(personOrder.getPersonId()).setAdress(personOrder.getAdress()).setStatusId(personOrder.getStatusId());
        update(updatePersonOrderRequest, findPersonOrderRequest);
        return personOrder;
    }

    public void delete(FindPersonOrderRequest findPersonOrderRequest) {
        Statement statement;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("delete from person_order where ");
            sb.append(toSQLStringStatement(findPersonOrderRequest));
            System.out.println(sb.toString());
            connection = ConnectionPool.getInstance().getConnection();
            System.out.println("Used connection: " + connection);
            statement = connection.createStatement();
            statement.executeUpdate(sb.toString());
            ConnectionPool.getInstance().releaseConnection(connection);
            System.out.println("Data deleted");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String toSQLStringStatement(FindPersonOrderRequest findPersonOrderRequest) {
        StringBuilder sb = new StringBuilder();
        if (findPersonOrderRequest.getOrderIds() != null) {
            sb.append("order_id ");
            sb.append("='").append(findPersonOrderRequest.getOrderIds()).append("' AND ");
        }
        if (findPersonOrderRequest.getPersonIds() != null) {
            sb.append("person_id ");
            sb.append("='").append(findPersonOrderRequest.getPersonIds()).append("' AND ");
        }
        if (findPersonOrderRequest.getAdresses() != null) {
            sb.append("adress ");
            sb.append("='").append(findPersonOrderRequest.getAdresses()).append("' AND ");
        }
        if (findPersonOrderRequest.getStatusIds() != null) {
            sb.append("status_id ");
            sb.append("='").append(findPersonOrderRequest.getStatusIds()).append("' AND ");
        }
        sb.delete(sb.length() - 4, sb.length());
        sb.append(";");
        return sb.toString();
    }
}
