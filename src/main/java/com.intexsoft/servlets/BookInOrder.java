package com.intexsoft.servlets;

import com.intexsoft.controler.ConnectToDb;
import com.intexsoft.controler.dao.BookDAO;
import com.intexsoft.controler.dao.CartHasBookDAO;
import com.intexsoft.controler.dao.PersonOrderHasBookDAO;
import com.intexsoft.controler.findRequest.FindBookRequest;
import com.intexsoft.controler.findRequest.FindCartHasBookRequest;
import com.intexsoft.controler.findRequest.FindPersonOrderHasBookRequest;
import com.intexsoft.model.Book;
import com.intexsoft.model.CartHasBook;
import com.intexsoft.model.PersonOrder;
import com.intexsoft.model.PersonOrderHasBook;
import com.intexsoft.model.transfer.CartHasBookDTO;
import com.intexsoft.model.transfer.PersonOrderHasBookDTO;
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

@WebServlet(name = "BookInOrder", value = "/BookInOrder")
public class BookInOrder extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stringUUId = request.getParameter("uuid");
        PrintWriter pw = response.getWriter();
        UUID uuid = UUID.fromString(stringUUId);
        ConnectToDb connectToDb = new ConnectToDb();
        Connection connection = connectToDb.connect_to_db("bookshop", "postgres", "postgrespw");
        PersonOrderHasBookDAO personOrderHasBookDAO = new PersonOrderHasBookDAO(connection);
        List<PersonOrderHasBook> list = personOrderHasBookDAO.find(new FindPersonOrderHasBookRequest().setOrderId(uuid));
        pw.println("<html>");
        BookDAO bookDAO = new BookDAO(connection);
        for (PersonOrderHasBook cur : list) {
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
        PersonOrderHasBookDAO personOrderHasBookDAO = new PersonOrderHasBookDAO(connection);
        JsonDeserializer jsonDeserializer = new JsonDeserializer(sb.toString());
        Mapper mapper = new Mapper();
        BookDAO bookDAO=new BookDAO(connection);
        PersonOrderHasBookDTO personOrderHasBookDTO = mapper.map(jsonDeserializer.parseValue(), PersonOrderHasBookDTO.class);
        personOrderHasBookDAO.createRow(UUID.fromString(personOrderHasBookDTO.getOrderId()), UUID.fromString(personOrderHasBookDTO.getBookId()), personOrderHasBookDTO.getBookCount());
        pw.println("<html>");
        pw.println("<h1> orderId = " +personOrderHasBookDTO.getOrderId() + "</h1>");
        pw.println("<h1> bookId = " + personOrderHasBookDTO.getBookId() + "</h1>");
        Book book = bookDAO.find(new FindBookRequest().setBookId(UUID.fromString(personOrderHasBookDTO.getBookId()))).get(0);
        pw.println("<h1> bookname = " + book.getBookname() + "</h1>");
        pw.println("<h1> author = " + book.getAuthor() + "</h1>");
        pw.println("<h1> costInByn = " + book.getCostInByn() + "</h1>");
        pw.println("<h1> bookCount = " + personOrderHasBookDTO.getBookCount() + "</h1>");
        pw.println("</html>");
    }
}
