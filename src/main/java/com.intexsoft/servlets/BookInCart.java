package com.intexsoft.servlets;

import com.intexsoft.controler.ConnectToDb;
import com.intexsoft.controler.dao.BookDAO;
import com.intexsoft.controler.dao.CartHasBookDAO;
import com.intexsoft.controler.findRequest.FindBookRequest;
import com.intexsoft.controler.findRequest.FindCartHasBookRequest;
import com.intexsoft.model.Book;
import com.intexsoft.model.CartHasBook;
import com.intexsoft.model.transfer.CartHasBookDTO;
import com.intexsoft.parser.JsonDeserializer;
import com.intexsoft.parser.Mapper;

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
    private CartHasBook cartHasBook = new CartHasBook();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stringUUId = request.getParameter("uuid");
        PrintWriter pw = response.getWriter();
        UUID uuid = UUID.fromString(stringUUId);
        ConnectToDb connectToDb = new ConnectToDb();
        Connection connection = connectToDb.connect_to_db("bookshop", "postgres", "postgrespw");
        CartHasBookDAO cartHasBookDAO = new CartHasBookDAO(connection);
        List<CartHasBook> list = cartHasBookDAO.find(new FindCartHasBookRequest().setCartId(uuid));
        pw.println("<html>");
        BookDAO bookDAO = new BookDAO(connection);
        for (CartHasBook cur : list) {
            pw.println("<h1> bookId = " + cur.getBookId() + "</h1>");

            Book book = bookDAO.find(new FindBookRequest().setBookId(cur.getBookId())).get(0);
            pw.println("<h1> bookname = " + book.getBookname() + "</h1>");
            pw.println("<h1> author = " + book.getAuthor() + "</h1>");
            pw.println("<h1> costInByn = " + book.getCostInByn() + "</h1>");
            pw.println("<h1> bookCount = " + cur.getBookCount() + "</h1>");
            pw.println("<h1> -----------------------</h1>");
        }
        pw.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletInputStream servletInputStream = request.getInputStream();
        PrintWriter pw = response.getWriter();
        StringBuilder sb = new StringBuilder();
        while (!servletInputStream.isFinished()) {
            sb.append(Character.valueOf((char) servletInputStream.read()));
        }
        ConnectToDb connectToDb = new ConnectToDb();
        Connection connection = connectToDb.connect_to_db("bookshop", "postgres", "postgrespw");
        CartHasBookDAO cartHasBookDAO = new CartHasBookDAO(connection);
        JsonDeserializer jsonDeserializer = new JsonDeserializer(sb.toString());
        Mapper mapper = new Mapper();
        CartHasBookDTO cartHasBookDTO = mapper.map(jsonDeserializer.parseValue(), CartHasBookDTO.class);
        cartHasBookDAO.createRow(UUID.fromString(cartHasBookDTO.getCartId()), UUID.fromString(cartHasBookDTO.getBookId()), cartHasBookDTO.getBookCount());
        pw.println("<html>");
        pw.println("<h1> cartId = " + cartHasBookDTO.getCartId() + "</h1>");
        pw.println("<h1> bookId = " + cartHasBookDTO.getBookId() + "</h1>");
        pw.println("<h1> bookCount = " + cartHasBookDTO.getBookCount() + "</h1>");
        pw.println("</html>");
    }
}
