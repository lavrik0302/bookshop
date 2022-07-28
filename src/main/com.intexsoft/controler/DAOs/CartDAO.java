package controler.DAOs;

import controler.FindRequests.FindCartRequest;
import controler.UpdateRequests.UpdateCartRequest;
import model.Cart;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CartDAO {
    public Cart createRow(Connection connection, UUID personId, String cartname) {
        Statement statement;
        Cart cart =new Cart();
        try {
            UUID uuid = UUID.randomUUID();
            cart.setCartId(uuid);
            cart.setPersonId(personId);
            cart.setCartname(cartname);
            String query = "insert into cart values ('" + uuid + "', '" + personId + "', '" + cartname + "');";
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Insert success");
        } catch (Exception e) {
            System.out.println(e);
        }
        return cart;

    }

    public Cart createCart(Connection connection, Cart cart) {
        Statement statement;
        try {
            String query = "insert into cart values ('" + cart.getCartId() + "', '" + cart.getPersonId() + "', '" + cart.getCartname() + "');";
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Insert success");
        } catch (Exception e) {
            System.out.println(e);
        }
        return cart;
    }


    public List<Cart> readAll(Connection connection) {
        List<Cart> list = new ArrayList<>();
        Statement statement;
        ResultSet rs = null;
        try {
            String query = "select * from cart";
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                Cart cart = new Cart();
                cart.setCartId(rs.getObject(1, UUID.class));
                cart.setPersonId(rs.getObject(2, UUID.class));
                cart.setCartname(rs.getString(3));
                list.add(cart);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public Cart find(Connection connection, FindCartRequest findCartRequest) {
        Statement statement;
        Cart cart = new Cart();
        ResultSet rs = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select * from cart where ");
            sb.append(findCartRequest.toSQLStringStatement());

            System.out.println(sb.toString());
            statement = connection.createStatement();
            rs = statement.executeQuery(sb.toString());
            while (rs.next()) {
                cart.setCartId(rs.getObject(1, UUID.class));
                cart.setPersonId(rs.getObject(2, UUID.class));
                cart.setCartname(rs.getString(3));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return cart;
    }

    public void update(Connection connection, UpdateCartRequest updateCartRequest, FindCartRequest findCartRequest) {
        Statement statement;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("update cart set ");

            if (updateCartRequest.getCartId() != null)
                sb.append("cart_id" + "='" + updateCartRequest.getCartId() + "', ");
            if (updateCartRequest.getPersonId() != null)
                sb.append("person_id" + "='" + updateCartRequest.getPersonId() + "', ");
            if (updateCartRequest.getCartname() != null)
                sb.append("cart_name" + "='" + updateCartRequest.getCartname() + "', ");
            sb.deleteCharAt(sb.lastIndexOf(","));
            sb.append("where ");
            sb.append(findCartRequest.toSQLStringStatement());
            System.out.println(sb.toString());
            statement = connection.createStatement();
            statement.executeUpdate(sb.toString());
            System.out.println("Data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Cart updateCart(Connection connection, Cart cart, FindCartRequest findCartRequest) {
        UpdateCartRequest updateCartRequest = new UpdateCartRequest();
        updateCartRequest.setCartCartId(cart.getCartId()).setCartPersonId(cart.getPersonId()).setCartCartname(cart.getCartname());
        update(connection, updateCartRequest, findCartRequest);
        return cart;
    }

    public void delete(Connection connection, FindCartRequest findCartRequest) {
        Statement statement;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("delete from cart where ");
            sb.append(findCartRequest.toSQLStringStatement());
            System.out.println(sb.toString());
            statement = connection.createStatement();
            statement.executeUpdate(sb.toString());
            System.out.println("Data deleted");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
