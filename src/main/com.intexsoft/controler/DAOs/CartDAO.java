package controler.DAOs;

import controler.FindRequests.FindCartRequest;
import controler.FindRequests.FindPersonRequest;
import controler.UpdateRequests.UpdateCartRequest;
import model.Cart;
import model.CartHasBook;
import model.Person;

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
    public Cart findWithBooks(Connection connection, FindCartRequest findCartRequest) {
        Statement statement;
        Cart cart=new Cart();
        ResultSet rs = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select c.cart_id, c.person_id, c.cart_name, chs.book_id, chs.book_count from cart AS c left join cart_has_book AS chs on c.cart_id=chs.cart_id where ");
            if (findCartRequest.getCartId() != null) {
                sb.append("c.cart_id ");
                sb.append("='" + findCartRequest.getCartId() + "' AND ");
            }
            if (findCartRequest.getPersonId() != null) {
                sb.append("c.person_id ");
                sb.append("='" + findCartRequest.getPersonId() + "' AND ");
            }
            if (findCartRequest.getCartname() != null) {
                sb.append("c.cart_name ");
                sb.append("='" + findCartRequest.getCartname() + "' AND ");
            }
            sb.delete(sb.length() - 4, sb.length());
            sb.append(";");
            System.out.println(sb.toString());
            statement = connection.createStatement();
            rs = statement.executeQuery(sb.toString());

            while (rs.next()) {
                cart.setCartId(rs.getObject(1, UUID.class));
                cart.setPersonId(rs.getObject(2,UUID.class));
                cart.setCartname(rs.getString(3));
                CartHasBook cartHasBook=new CartHasBook();
                cartHasBook.setCartId(rs.getObject(1,UUID.class));
                cartHasBook.setBookId(rs.getObject(4, UUID.class));
                cartHasBook.setBookCount(rs.getInt(5));
                cart.getCartHasBooks().add(cartHasBook);
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
