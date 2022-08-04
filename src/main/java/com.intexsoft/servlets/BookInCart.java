package com.intexsoft.servlets;

import com.intexsoft.controler.ConnectToDb;
import com.intexsoft.controler.dao.BookDAO;
import com.intexsoft.controler.dao.CartHasBookDAO;
import com.intexsoft.controler.findRequest.FindBookRequest;
import com.intexsoft.controler.findRequest.FindCartHasBookRequest;
import com.intexsoft.controler.updateRequest.UpdateCartHasBookRequest;
import com.intexsoft.model.Book;
import com.intexsoft.model.CartHasBook;
import com.intexsoft.model.transfer.CartHasBookDTO;
import com.intexsoft.model.transfer.DeleteBookFromCartDTO;
import com.intexsoft.parser.JsonDeserializer;
import com.intexsoft.parser.Mapper;
import com.intexsoft.serializer.JsonSerializer;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "BookInCart", value = "/bookInCart")
public class BookInCart extends HttpServlet {
    ConnectToDb connectToDb = new ConnectToDb();
    Connection connection = connectToDb.connect_to_db("bookshop", "postgres", "postgrespw");
    CartHasBookDAO cartHasBookDAO = new CartHasBookDAO(connection);
    BookDAO bookDAO = new BookDAO(connection);
    Mapper mapper = new Mapper();
    JsonSerializer jsonSerializer = new JsonSerializer();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stringUUId = request.getParameter("uuid");
        PrintWriter pw = response.getWriter();
        try {
            UUID uuid = UUID.fromString(stringUUId);
            List<CartHasBook> list = cartHasBookDAO.find(new FindCartHasBookRequest().setCartId(uuid));
            CartHasBookDTO[] cartHasBookDTOarr = new CartHasBookDTO[list.size()];
            pw.println("<html>");
            int cartHasBookCounter = 0;
            for (CartHasBook cur : list) {
                CartHasBookDTO cartHasBookDTO = new CartHasBookDTO();
                cartHasBookDTO.setCartId(cur.getCartId().toString());
                cartHasBookDTO.setBookId(cur.getBookId().toString());
                cartHasBookDTO.setBookCount(cur.getBookCount());
                cartHasBookDTOarr[cartHasBookCounter] = cartHasBookDTO;
                pw.println("<h1> bookId = " + cur.getBookId() + "</h1>");
                Book book = bookDAO.find(new FindBookRequest().setBookId(cur.getBookId())).get(0);
                pw.println("<h1> bookname = " + book.getBookname() + "</h1>");
                pw.println("<h1> author = " + book.getAuthor() + "</h1>");
                pw.println("<h1> costInByn = " + book.getCostInByn() + "</h1>");
                pw.println("<h1> bookCount = " + cur.getBookCount() + "</h1>");
                pw.println("<h1> -----------------------</h1>");
                cartHasBookCounter++;
            }
            pw.println("<h1> As JSON= " + jsonSerializer.serialize(cartHasBookDTOarr) + "</h1>");
            pw.println("</html>");
        } catch (IllegalArgumentException e) {
            pw.println("<html>");
            pw.println("<h1> Invalid UUID </h1>");
            pw.println("<h1>" + e + "</h1>");
            pw.println("</html>");
        } catch (IndexOutOfBoundsException e) {
            pw.println("<html>");
            pw.println("<h1> No such cart an book UUIDs </h1>");
            pw.println("<h1>" + e + "</h1>");
            pw.println("</html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletInputStream servletInputStream = request.getInputStream();
        PrintWriter pw = response.getWriter();
        StringBuilder sb = new StringBuilder();
        while (!servletInputStream.isFinished()) {
            sb.append(Character.valueOf((char) servletInputStream.read()));
        }
        try {
            JsonDeserializer jsonDeserializer = new JsonDeserializer(sb.toString());
            CartHasBookDTO cartHasBookDTO = mapper.map(jsonDeserializer.parseValue(), CartHasBookDTO.class);
            cartHasBookDAO.createRow(UUID.fromString(cartHasBookDTO.getCartId()), UUID.fromString(cartHasBookDTO.getBookId()), cartHasBookDTO.getBookCount());
            pw.println("<html>");
            pw.println("<h1> cartId = " + cartHasBookDTO.getCartId() + "</h1>");
            pw.println("<h1> bookId = " + cartHasBookDTO.getBookId() + "</h1>");
            pw.println("<h1> bookCount = " + cartHasBookDTO.getBookCount() + "</h1>");
            pw.println("<h1> As JSON = " + jsonSerializer.serialize(cartHasBookDTO) + "</h1>");
            pw.println("</html>");
        } catch (Exception e) {
            pw.println("<html>");
            pw.println("<h1> Wrong JSON</h1>");
            pw.println("<h1>" + e + "</h1>");
            pw.println("</html>");
        }
    }


    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletInputStream servletInputStream = request.getInputStream();
        PrintWriter pw = response.getWriter();
        StringBuilder sb = new StringBuilder();
        while (!servletInputStream.isFinished()) {
            sb.append(Character.valueOf((char) servletInputStream.read()));
        }
        try {
            JsonDeserializer jsonDeserializer = new JsonDeserializer(sb.toString());
            CartHasBookDTO cartHasBookDTO = mapper.map(jsonDeserializer.parseValue(), CartHasBookDTO.class);
            cartHasBookDAO.update(new UpdateCartHasBookRequest()
                            .setCartId(UUID.fromString(cartHasBookDTO.getCartId()))
                            .setBookId(UUID.fromString(cartHasBookDTO.getBookId()))
                            .setBookCount(cartHasBookDTO.getBookCount())
                    , new FindCartHasBookRequest()
                            .setCartId(UUID.fromString(cartHasBookDTO.getCartId()))
                            .setBookId(UUID.fromString(cartHasBookDTO.getBookId())));
            pw.println("<html>");
            pw.println("<h1> cartId = " + cartHasBookDTO.getCartId() + "</h1>");
            pw.println("<h1> bookId = " + cartHasBookDTO.getBookId() + "</h1>");
            pw.println("<h1> bookCount = " + cartHasBookDTO.getBookCount() + "</h1>");
            pw.println("<h1> As JSON = " + jsonSerializer.serialize(cartHasBookDTO) + "</h1>");
            pw.println("</html>");
        } catch (Exception e) {
            pw.println("<html>");
            pw.println("<h1> Wrong JSON</h1>");
            pw.println("<h1>" + e + "</h1>");
            pw.println("</html>");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletInputStream servletInputStream = request.getInputStream();
        PrintWriter pw = response.getWriter();
        StringBuilder sb = new StringBuilder();
        while (!servletInputStream.isFinished()) {
            sb.append(Character.valueOf((char) servletInputStream.read()));
        }
        try {
            JsonDeserializer jsonDeserializer = new JsonDeserializer(sb.toString());
            DeleteBookFromCartDTO deleteBookFromCartDTO = mapper.map(jsonDeserializer.parseValue(), DeleteBookFromCartDTO.class);
            CartHasBook cartHasBook = cartHasBookDAO
                    .find(new FindCartHasBookRequest()
                            .setCartId(UUID.fromString(deleteBookFromCartDTO.getCartId()))
                            .setBookId(UUID.fromString(deleteBookFromCartDTO.getBookId())))
                    .get(0);
            CartHasBookDTO cartHasBookDTO = new CartHasBookDTO();
            cartHasBookDTO.setCartId(cartHasBook.getCartId().toString());
            cartHasBookDTO.setBookId(cartHasBook.getBookId().toString());
            cartHasBookDTO.setBookCount(cartHasBook.getBookCount());
            pw.println("<html>");
            pw.println("<h1> cartId = " + cartHasBookDTO.getCartId() + "</h1>");
            pw.println("<h1> bookId = " + cartHasBookDTO.getBookId() + "</h1>");
            pw.println("<h1> bookCount = " + cartHasBookDTO.getBookCount() + "</h1>");
            pw.println("<h1> As JSON = " + jsonSerializer.serialize(cartHasBookDTO) + "</h1>");
            pw.println("</html>");
            cartHasBookDAO.delete(new FindCartHasBookRequest().setCartId(cartHasBook.getCartId()).setBookId(cartHasBook.getBookId()));
        } catch (Exception e) {
            pw.println("<html>");
            pw.println("<h1> Wrong JSON</h1>");
            pw.println("<h1>" + e + "</h1>");
            pw.println("</html>");
        }
    }
}
