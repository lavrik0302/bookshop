package com.intexsoft.servlets;

import com.intexsoft.controler.ConnectToDb;
import com.intexsoft.controler.dao.BookDAO;
import com.intexsoft.controler.dao.PersonDAO;
import com.intexsoft.controler.findRequest.FindBookRequest;
import com.intexsoft.controler.findRequest.FindPersonRequest;
import com.intexsoft.model.Book;
import com.intexsoft.model.transfer.BookDTO;
import com.intexsoft.model.transfer.CreateBookDTO;
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
    ConnectToDb connectToDb = new ConnectToDb();
    Connection connection = connectToDb.connect_to_db("bookshop", "postgres", "postgrespw");
    BookDAO bookDAO = new BookDAO(connection);

    JsonSerializer jsonSerializer = new JsonSerializer();
    Mapper mapper = new Mapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stringUUId = request.getParameter("uuid");
        PrintWriter pw = response.getWriter();
        UUID uuid = UUID.fromString(stringUUId);
        Book book = bookDAO.find(new FindBookRequest().setBookId(uuid)).get(0);
        BookDTO bookDTO = new BookDTO();
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
        pw.println("<h1> As JSON = " + json + "</h1>");
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
        JsonDeserializer jsonDeserializers = new JsonDeserializer(sb.toString());
        CreateBookDTO createBookDTO = mapper.map(jsonDeserializers.parseValue(), CreateBookDTO.class);
        pw.println("<html>");
        Book book = bookDAO.createRow(createBookDTO.getBookname(), createBookDTO.getAuthor(), createBookDTO.getCostInByn(), createBookDTO.getCountInStock());
        pw.println("<h1> bookid = " + book.getBookId() + "</h1>");
        pw.println("<h1> bookname = " + book.getBookname() + "</h1>");
        pw.println("<h1> author = " + book.getAuthor() + "</h1>");
        pw.println("<h1> costInByn = " + book.getCostInByn() + "</h1>");
        pw.println("<h1> countInStock = " + book.getCountInStock() + "</h1>");
        BookDTO bookDTO = new BookDTO();
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setBookname(book.getBookname());
        bookDTO.setCountInStock(book.getCountInStock());
        bookDTO.setCostInByn(book.getCostInByn());
        bookDTO.setBookId(book.getBookId().toString());
        pw.println("<h1> As JSON = " + jsonSerializer.serialize(bookDTO) + "</h1>");
        pw.println("</html>");

    }
}
