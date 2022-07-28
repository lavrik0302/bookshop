package controler.DAOs;

import controler.FindRequests.FindCartHasBookRequest;
import controler.UpdateRequests.UpdateCartHasBookRequest;
import model.CartHasBook;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CartHasBookDAO {
    public CartHasBook createRow(Connection connection, UUID cartId, UUID bookId, int bookCount) {
        Statement statement;
        CartHasBook cartHasBook = new CartHasBook();
        try {
            cartHasBook.setCartId(cartId);
            cartHasBook.setBookId(bookId);
            cartHasBook.setBookCount(bookCount);
            String query = "insert into cart_has_book values ('" + cartId + "', '" + bookId + "', '" + bookCount + "');";
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Insert success");
        } catch (Exception e) {
            System.out.println(e);
        }
        return cartHasBook;
    }

    public CartHasBook createCartHasBook(Connection connection, CartHasBook cartHasBook) {
        Statement statement;
        try {
            String query = "insert into cart_has_book values ('" + cartHasBook.getCartId() + "', '" + cartHasBook.getBookId() + "', '" + cartHasBook.getBookCount() + "');";
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Insert success");
        } catch (Exception e) {
            System.out.println(e);
        }
        return cartHasBook;
    }

    public List<CartHasBook> readAll(Connection connection) {
        List<CartHasBook> list = new ArrayList<>();
        Statement statement;
        ResultSet rs = null;
        try {
            String query = "select * from cart_has_book";
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                CartHasBook cartHasBook = new CartHasBook();
                cartHasBook.setCartId(rs.getObject(1, UUID.class));
                cartHasBook.setBookId(rs.getObject(2, UUID.class));
                cartHasBook.setBookCount(rs.getInt(3));
                list.add(cartHasBook);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }
    public List<CartHasBook> find(Connection connection, FindCartHasBookRequest findCartHasBookRequest) {
        Statement statement;
        List<CartHasBook> list=new ArrayList<>();
        ResultSet rs = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select * from cart_has_book where ");
            sb.append(findCartHasBookRequest.toSQLStringStatement());
            System.out.println(sb.toString());
            statement = connection.createStatement();
            rs = statement.executeQuery(sb.toString());
            while (rs.next()) {
                CartHasBook cartHasBook = new CartHasBook();
                cartHasBook.setCartId(rs.getObject(1, UUID.class));
                cartHasBook.setBookId(rs.getObject(2, UUID.class));
                cartHasBook.setBookCount(rs.getInt(3));
                list.add(cartHasBook);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }
    public void update(Connection connection, UpdateCartHasBookRequest updateCartHasBookRequest, FindCartHasBookRequest findCartHasBookRequest) {
        Statement statement;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("update cart_has_book set ");

            if (updateCartHasBookRequest.getCartIds() != null)
                sb.append("cart_id" + "='" + updateCartHasBookRequest.getCartIds() + "', ");
            if (updateCartHasBookRequest.getBookIds() != null)
                sb.append("book_id" + "='" + updateCartHasBookRequest.getBookIds() + "', ");
            if (updateCartHasBookRequest.getBookCounts() != null)
                sb.append("book_count" + "='" + updateCartHasBookRequest.getBookCounts() + "', ");
            sb.deleteCharAt(sb.lastIndexOf(","));
            sb.append("where ");
            sb.append(findCartHasBookRequest.toSQLStringStatement());
            System.out.println(sb.toString());
            statement = connection.createStatement();
            statement.executeUpdate(sb.toString());
            System.out.println("Data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public CartHasBook updateCartHasBook(Connection connection, CartHasBook cartHasBook, FindCartHasBookRequest findCartHasBookRequest) {
        UpdateCartHasBookRequest updateCartHasBookRequest = new UpdateCartHasBookRequest();
        updateCartHasBookRequest.setCartId(cartHasBook.getCartId()).setBookId(cartHasBook.getBookId()).setBookCounts(cartHasBook.getBookCount());
        update(connection, updateCartHasBookRequest, findCartHasBookRequest);
        return cartHasBook;
    }
    public void delete(Connection connection, FindCartHasBookRequest findCartHasBookRequest) {
        Statement statement;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("delete from cart_has_book where ");
            sb.append(findCartHasBookRequest.toSQLStringStatement());
            System.out.println(sb.toString());
            statement = connection.createStatement();
            statement.executeUpdate(sb.toString());
            System.out.println("Data deleted");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
