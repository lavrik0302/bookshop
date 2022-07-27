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
    public void createRow(Connection connection, UUID personId, String cartname) {
        Statement statement;
        try {
            UUID uuid = UUID.randomUUID();
            String query = "insert into cart values ('" + uuid + "', '" + personId + "', '" + cartname + "');";
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Insert success");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void createCart(Connection connection, Cart cart) {
        Statement statement;
        try {
            String query = "insert into cart values ('" + cart.getCart_id() + "', '" + cart.getPerson_id() + "', '" + cart.getCartname() + "');";
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Insert success");
        } catch (Exception e) {
            System.out.println(e);
        }
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
                cart.setCart_id(rs.getObject(1, UUID.class));
                cart.setPerson_id(rs.getObject(2, UUID.class));
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

            if (findCartRequest.getCartId() != null) {
                sb.append("cart_id ");
                sb.append("='" + findCartRequest.getCartId() + "' AND ");
            }
            if (findCartRequest.getPersonId() != null) {
                sb.append("person_id ");
                sb.append("='" + findCartRequest.getPersonId() + "' AND ");
            }
            if (findCartRequest.getCartname() != null) {
                sb.append("cart_name ");
                sb.append("='" + findCartRequest.getCartname() + "' AND ");
            }
            sb.delete(sb.length() - 4, sb.length());
            sb.append(";");
            System.out.println(sb.toString());
            statement = connection.createStatement();
            rs = statement.executeQuery(sb.toString());
            while (rs.next()) {
                cart.setCart_id(rs.getObject(1, UUID.class));
                cart.setPerson_id(rs.getObject(2, UUID.class));
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
            if (findCartRequest.getCartId() != null) {
                sb.append("cart_id ");
                sb.append("='" + findCartRequest.getCartId() + "' AND ");
            }
            if (findCartRequest.getPersonId() != null) {
                sb.append("person_id ");
                sb.append("='" + findCartRequest.getPersonId() + "' AND ");
            }
            if (findCartRequest.getCartname() != null) {
                sb.append("cart_name ");
                sb.append("='" + findCartRequest.getCartname() + "' AND ");
            }
            sb.delete(sb.length() - 4, sb.length());
            sb.append(";");
            System.out.println(sb.toString());
            statement = connection.createStatement();
            statement.executeUpdate(sb.toString());
            System.out.println("Data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateCart(Connection connection, Cart cart, FindCartRequest findCartRequest) {
        UpdateCartRequest updateCartRequest=new UpdateCartRequest();
        updateCartRequest.setCartCartId(cart.getCart_id()).setCartPersonId(cart.getPerson_id()).setCartCartname(cart.getCartname());
        update(connection, updateCartRequest,findCartRequest);
    }

    public void delete(Connection connection, FindCartRequest findCartRequest) {
        Statement statement;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("delete from cart where ");
            if (findCartRequest.getCartId() != null) {
                sb.append("cart_id ");
                sb.append("='" + findCartRequest.getCartId() + "' AND ");
            }
            if (findCartRequest.getPersonId() != null) {
                sb.append("person_id ");
                sb.append("='" + findCartRequest.getPersonId() + "' AND ");
            }
            if (findCartRequest.getCartname() != null) {
                sb.append("cart_name ");
                sb.append("='" + findCartRequest.getCartname() + "' AND ");
            }
            sb.delete(sb.length() - 4, sb.length());
            sb.append(";");
            System.out.println(sb.toString());
            statement = connection.createStatement();
            statement.executeUpdate(sb.toString());
            System.out.println("Data deleted");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
