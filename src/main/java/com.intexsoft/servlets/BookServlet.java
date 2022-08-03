package com.intexsoft.servlets;

import com.intexsoft.controler.ConnectToDb;
import com.intexsoft.controler.dao.BookDAO;
import com.intexsoft.controler.dao.PersonDAO;
import com.intexsoft.controler.findRequest.FindBookRequest;
import com.intexsoft.controler.findRequest.FindPersonRequest;
import com.intexsoft.model.Book;
import com.intexsoft.model.transfer.BookDTO;
import com.intexsoft.model.transfer.PersonDTO;
import com.intexsoft.parser.JsonDeserializer;
import com.intexsoft.parser.Mapper;
import com.intexsoft.serializer.JsonSerializer;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.UUID;

@WebServlet(name = "BookServlet", value = "/bookServlet")
public class BookServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stringUUId = request.getParameter("uuid");

        PrintWriter pw = response.getWriter();
        UUID uuid = UUID.fromString(stringUUId);
        ConnectToDb connectToDb = new ConnectToDb();
        Connection connection = connectToDb.connect_to_db("bookshop", "postgres", "postgrespw");
        BookDAO bookDAO = new BookDAO(connection);
        Book book = bookDAO.find(new FindBookRequest().setBookId(uuid)).get(0);
        BookDTO bookDTO = new BookDTO();
        JsonSerializer jsonSerializer = new JsonSerializer();

        bookDTO.setBookId(book.getBookId().toString());
        bookDTO.setBookname(book.getBookname());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setCostInByn(book.getCostInByn());
        bookDTO.setCountInStock(book.getCountInStock());

        String json = jsonSerializer.serialize(bookDTO);
        pw.println("<html>");
        pw.println("<h1> book_id = " + book.getBookId() + "</h1>");
        pw.println("<h1> bookname = " + book.getBookname() + "</h1>");
        pw.println("<h1> author = " + book.getAuthor() + "</h1>");
        pw.println("<h1> cost_in_byn = " + book.getCostInByn() + "</h1>");
        pw.println("<h1> count_in_stock = " + book.getCountInStock() + "</h1>");
        pw.println("<h1> as JSON = " + json + "</h1>");
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
        BookDAO bookDAO = new BookDAO(connection);
        JsonDeserializer jsonDeserializers = new JsonDeserializer(sb.toString());
        Mapper mapper = new Mapper();
        BookDTO bookDTO = mapper.map(jsonDeserializers.parseValue(), BookDTO.class);
        pw.println("<html>");
        bookDAO.createRow(bookDTO.getBookname(), bookDTO.getAuthor(), bookDTO.getCostInByn(), bookDTO.getCountInStock());
        pw.println("<h1> bookid = " + bookDTO.getBookId() + "</h1>");
        pw.println("<h1> bookname = " + bookDTO.getBookname() + "</h1>");
        pw.println("<h1> author = " + bookDTO.getAuthor() + "</h1>");
        pw.println("<h1> costInByn = " + bookDTO.getCostInByn() + "</h1>");
        pw.println("<h1> countInStock = " + bookDTO.getCountInStock() + "</h1>");
        pw.println("</html>");

    }
}
