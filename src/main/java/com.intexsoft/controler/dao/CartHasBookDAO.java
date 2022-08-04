package com.intexsoft.controler.dao;


import com.intexsoft.controler.findRequest.FindCartHasBookRequest;
import com.intexsoft.controler.updateRequest.UpdateCartHasBookRequest;
import com.intexsoft.model.CartHasBook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CartHasBookDAO {
    Connection connection;

    public CartHasBook createRow(UUID cartId, UUID bookId, int bookCount) {
        PreparedStatement preparedStatement;
        CartHasBook cartHasBook = new CartHasBook();
        try {
            cartHasBook.setCartId(cartId);
            cartHasBook.setBookId(bookId);
            cartHasBook.setBookCount(bookCount);
            preparedStatement = connection.prepareStatement("insert into cart_has_book values (?, ?, ?)");
            preparedStatement.setObject(1, cartId);
            preparedStatement.setObject(2, bookId);
            preparedStatement.setObject(3, bookCount);
            preparedStatement.executeUpdate();
            System.out.println("Insert success");
        } catch (Exception e) {
            System.out.println(e);
        }
        return cartHasBook;
    }

    public CartHasBook createCartHasBook(CartHasBook cartHasBook) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement("insert into cart_has_book values (?, ?, ?)");
            preparedStatement.setObject(1, cartHasBook.getCartId());
            preparedStatement.setObject(2, cartHasBook.getBookId());
            preparedStatement.setObject(3, cartHasBook.getBookCount());
            preparedStatement.executeUpdate();
            System.out.println("Insert success");
        } catch (Exception e) {
            System.out.println(e);
        }
        return cartHasBook;
    }

    public List<CartHasBook> findAll() {
        List<CartHasBook> list = new ArrayList<>();
        Statement statement;
        ResultSet rs = null;
        try {
            String query = "select * from cart_has_book";
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                CartHasBook cartHasBook = new CartHasBook();
                cartHasBook.setCartId(rs.getObject("cart_id", UUID.class));
                cartHasBook.setBookId(rs.getObject("book_id", UUID.class));
                cartHasBook.setBookCount(rs.getInt("book_count"));
                list.add(cartHasBook);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public List<CartHasBook> find(FindCartHasBookRequest findCartHasBookRequest) {
        Statement statement;
        List<CartHasBook> list = new ArrayList<>();
        ResultSet rs = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select * from cart_has_book where ");
            sb.append(toSQLStringStatement(findCartHasBookRequest));
            System.out.println(sb.toString());
            statement = connection.createStatement();
            rs = statement.executeQuery(sb.toString());
            while (rs.next()) {
                CartHasBook cartHasBook = new CartHasBook();
                cartHasBook.setCartId(rs.getObject("cart_id", UUID.class));
                cartHasBook.setBookId(rs.getObject("book_id", UUID.class));
                cartHasBook.setBookCount(rs.getInt("book_count"));
                list.add(cartHasBook);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public void update(UpdateCartHasBookRequest updateCartHasBookRequest, FindCartHasBookRequest findCartHasBookRequest) {
        Statement statement;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("update cart_has_book set ");

            if (updateCartHasBookRequest.getCartIds() != null)
                sb.append("cart_id='").append(updateCartHasBookRequest.getCartIds()).append("', ");
            if (updateCartHasBookRequest.getBookIds() != null)
                sb.append("book_id='").append(updateCartHasBookRequest.getBookIds()).append("', ");
            if (updateCartHasBookRequest.getBookCounts() != null)
                sb.append("book_count='").append(updateCartHasBookRequest.getBookCounts()).append("', ");
            sb.deleteCharAt(sb.lastIndexOf(","));
            sb.append("where ");
            sb.append(toSQLStringStatement(findCartHasBookRequest));
            System.out.println(sb.toString());
            statement = connection.createStatement();
            statement.executeUpdate(sb.toString());
            System.out.println("Data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public CartHasBook updateCartHasBook(CartHasBook cartHasBook) {
        FindCartHasBookRequest findCartHasBookRequest = new FindCartHasBookRequest().setCartId(cartHasBook.getCartId());
        UpdateCartHasBookRequest updateCartHasBookRequest = new UpdateCartHasBookRequest();
        updateCartHasBookRequest.setCartId(cartHasBook.getCartId()).setBookId(cartHasBook.getBookId()).setBookCounts(cartHasBook.getBookCount());
        update(updateCartHasBookRequest, findCartHasBookRequest);
        return cartHasBook;
    }

    public void delete(FindCartHasBookRequest findCartHasBookRequest) {
        Statement statement;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("delete from cart_has_book where ");
            sb.append(toSQLStringStatement(findCartHasBookRequest));
            System.out.println(sb.toString());
            statement = connection.createStatement();
            statement.executeUpdate(sb.toString());
            System.out.println("Data deleted");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String toSQLStringStatement(FindCartHasBookRequest findCartHasBookRequest) {
        StringBuilder sb = new StringBuilder();
        if (!findCartHasBookRequest.getCartIds().isEmpty()) {
            sb.append("cart_id ");
            if (findCartHasBookRequest.getCartIds().size() > 1) {
                sb.append("in (");
                for (UUID cart_id : findCartHasBookRequest.getCartIds()) {
                    sb.append("'").append(cart_id).append("', ");
                }
                sb.deleteCharAt(sb.lastIndexOf(","));
                sb.append(") AND ");
            } else {
                sb.append("='").append(findCartHasBookRequest.getCartIds().get(0)).append("' AND ");
            }
        }
        if (!findCartHasBookRequest.getBookIds().isEmpty()) {
            sb.append("book_id ");
            if (findCartHasBookRequest.getBookIds().size() > 1) {
                sb.append("in (");
                for (UUID bookId : findCartHasBookRequest.getBookIds()) {
                    sb.append("'").append(bookId).append("', ");
                }
                sb.deleteCharAt(sb.lastIndexOf(","));
                sb.append(") AND ");
            } else {
                sb.append("='").append(findCartHasBookRequest.getBookIds().get(0)).append("' AND ");
            }
        }
        if (!findCartHasBookRequest.getBookCounts().isEmpty()) {
            sb.append("book_count ");
            if (findCartHasBookRequest.getBookCounts().size() > 1) {
                sb.append("in (");
                for (Integer bookCount : findCartHasBookRequest.getBookCounts()) {
                    sb.append("'").append(bookCount).append("', ");
                }
                sb.deleteCharAt(sb.lastIndexOf(","));
                sb.append(") AND ");
            } else {
                sb.append("='").append(findCartHasBookRequest.getBookCounts().get(0)).append("' AND ");
            }
        }
        sb.delete(sb.length() - 4, sb.length());
        sb.append(";");
        return sb.toString();
    }

    public CartHasBookDAO(Connection connection) {
        this.connection = connection;
    }
}
