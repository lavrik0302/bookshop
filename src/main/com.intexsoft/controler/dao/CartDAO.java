package controler.dao;

import controler.findRequest.FindCartRequest;
import controler.updateRequest.UpdateCartRequest;
import model.Book;
import model.Cart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CartDAO {
    Connection connection;

    public Cart createRow(UUID personId, String cartname) {
        PreparedStatement preparedStatement;
        Cart cart = new Cart();
        try {
            UUID uuid = UUID.randomUUID();
            cart.setCartId(uuid);
            cart.setPersonId(personId);
            cart.setCartname(cartname);
            preparedStatement = connection.prepareStatement("insert into cart values (?, ?, ?)");
            preparedStatement.setObject(1, uuid);
            preparedStatement.setObject(2, personId);
            preparedStatement.setString(3, cartname);
            preparedStatement.executeUpdate();
            System.out.println("Insert success");
        } catch (Exception e) {
            System.out.println(e);
        }
        return cart;

    }

    public Cart createCart(Cart cart) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement("insert into cart values (?, ?, ?)");
            preparedStatement.setObject(1, cart.getCartId());
            preparedStatement.setObject(2, cart.getPersonId());
            preparedStatement.setString(3, cart.getCartname());
            preparedStatement.executeUpdate();
            System.out.println("Insert success");
        } catch (Exception e) {
            System.out.println(e);
        }
        return cart;
    }


    public List<Cart> findAll() {
        List<Cart> list = new ArrayList<>();
        Statement statement;
        ResultSet rs = null;
        try {
            String query = "select * from cart";
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                Cart cart = new Cart();
                cart.setCartId(rs.getObject("cart_id", UUID.class));
                cart.setPersonId(rs.getObject("person_id", UUID.class));
                cart.setCartname(rs.getString("cart_name"));
                list.add(cart);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public Cart find(FindCartRequest findCartRequest) {
        Statement statement;
        Cart cart = new Cart();
        ResultSet rs = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select * from cart where ");

            sb.append(toSQLStringStatement(findCartRequest));

            System.out.println(sb.toString());
            statement = connection.createStatement();
            rs = statement.executeQuery(sb.toString());
            while (rs.next()) {
                cart.setCartId(rs.getObject("cart_id", UUID.class));
                cart.setPersonId(rs.getObject("person_id", UUID.class));
                cart.setCartname(rs.getString("cart_name"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return cart;
    }

    public Cart findWithBooks(FindCartRequest findCartRequest) {
        Statement statement;
        Cart cart = new Cart();
        ResultSet rs = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select c.*, chs.book_id, b.* from cart AS c left join cart_has_book AS chs on c.cart_id=chs.cart_id left join book as b on chs.book_id=b.book_id where ");
            if (findCartRequest.getCartId() != null) {
                sb.append("c.cart_id ");
                sb.append("='").append(findCartRequest.getCartId()).append("' AND ");
            }
            if (findCartRequest.getPersonId() != null) {
                sb.append("c.person_id ");
                sb.append("='").append(findCartRequest.getPersonId()).append("' AND ");
            }
            if (findCartRequest.getCartname() != null) {
                sb.append("c.cart_name ");
                sb.append("='").append(findCartRequest.getCartname()).append("' AND ");
            }
            sb.delete(sb.length() - 4, sb.length());
            sb.append(";");
            System.out.println(sb.toString());
            statement = connection.createStatement();
            rs = statement.executeQuery(sb.toString());

            while (rs.next()) {
                cart.setCartId(rs.getObject("cart_id", UUID.class));
                cart.setPersonId(rs.getObject("person_id", UUID.class));
                cart.setCartname(rs.getString("cart_name"));
                Book book = new Book();
                book.setBookId(rs.getObject("book_id", UUID.class));
                book.setBookname(rs.getString("bookname"));
                book.setAuthor(rs.getString("author"));
                book.setCostInByn(rs.getInt("cost_in_byn"));
                book.setCountInStock(rs.getInt("count_in_stock"));
                cart.getBooks().add(book);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return cart;
    }

    public void update(UpdateCartRequest updateCartRequest, FindCartRequest findCartRequest) {
        Statement statement;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("update cart set ");

            if (updateCartRequest.getCartId() != null)
                sb.append("cart_id='").append(updateCartRequest.getCartId()).append("', ");
            if (updateCartRequest.getPersonId() != null)
                sb.append("person_id='").append(updateCartRequest.getPersonId()).append("', ");
            if (updateCartRequest.getCartname() != null)
                sb.append("cart_name='").append(updateCartRequest.getCartname()).append("', ");
            sb.deleteCharAt(sb.lastIndexOf(","));
            sb.append("where ");
            sb.append(toSQLStringStatement(findCartRequest));
            System.out.println(sb.toString());
            statement = connection.createStatement();
            statement.executeUpdate(sb.toString());
            System.out.println("Data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Cart updateCart(Cart cart) {
        FindCartRequest findCartRequest = new FindCartRequest().setCartCartId(cart.getCartId());
        UpdateCartRequest updateCartRequest = new UpdateCartRequest();
        updateCartRequest.setCartCartId(cart.getCartId()).setCartPersonId(cart.getPersonId()).setCartCartname(cart.getCartname());
        update(updateCartRequest, findCartRequest);
        return cart;
    }

    public void delete(FindCartRequest findCartRequest) {
        Statement statement;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("delete from cart where ");

            sb.append(toSQLStringStatement(findCartRequest));
            System.out.println(sb.toString());
            statement = connection.createStatement();
            statement.executeUpdate(sb.toString());
            System.out.println("Data deleted");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public String toSQLStringStatement(FindCartRequest findCartRequest){
        StringBuilder sb =new StringBuilder();
        if (findCartRequest.getCartId() != null) {
            sb.append("cart_id ");
            sb.append("='").append(findCartRequest.getCartId()).append("' AND ");
        }
        if (findCartRequest.getPersonId() != null) {
            sb.append("person_id ");
            sb.append("='").append(findCartRequest.getPersonId()).append("' AND ");
        }
        if (findCartRequest.getCartname() != null) {
            sb.append("cart_name ");
            sb.append("='").append(findCartRequest.getCartname()).append("' AND ");
        }
        sb.delete(sb.length() - 4, sb.length());
        sb.append(";");
        return sb.toString();
    }

    public CartDAO(Connection connection) {
        this.connection = connection;
    }
}
