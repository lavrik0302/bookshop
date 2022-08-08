package com.intexsoft.servlets;


import com.intexsoft.controler.dao.BookDAO;
import com.intexsoft.controler.findRequest.FindBookRequest;
import com.intexsoft.model.Book;
import com.intexsoft.model.transfer.BookDTO;
import com.intexsoft.model.transfer.CreateBookDTO;
import com.intexsoft.model.transfer.DeleteDTO;
import com.intexsoft.parser.JsonDeserializer;
import com.intexsoft.parser.Mapper;
import com.intexsoft.serializer.JsonSerializer;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

@WebServlet(name = "BookServlet", value = "/bookServlet")
public class BookServlet extends HttpServlet {

    BookDAO bookDAO = new BookDAO();
    JsonSerializer jsonSerializer = new JsonSerializer();
    Mapper mapper = new Mapper();

    public BookServlet() throws SQLException, NamingException {
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stringUUId = request.getParameter("uuid");

        PrintWriter pw = response.getWriter();

        try {
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
        } catch (IllegalArgumentException | NullPointerException e) {
            pw.println("<html>");
            pw.println("<h1> Invalid UUID </h1>");
            pw.println("<h1>" + e + "</h1>");
            response.setStatus(400);
            pw.println("</html>");
        } catch (IndexOutOfBoundsException e) {
            pw.println("<html>");
            pw.println("<h1> No such bookId </h1>");
            pw.println("<h1>" + e + "</h1>");
            e.printStackTrace(pw);
            response.setStatus(404);
            pw.println("</html>");
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
            CreateBookDTO createBookDTO = mapper.map(jsonDeserializer.parseValue(), CreateBookDTO.class);
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
        } catch (Exception e) {
            pw.println("<html>");
            pw.println("<h1> Wrong JSON</h1>");
            pw.println("<h1>" + e + "</h1>");
            response.setStatus(400);
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
            BookDTO bookDTO = mapper.map(jsonDeserializer.parseValue(), BookDTO.class);
            Book book = new Book();
            pw.println("<html>");
            book.setBookId(UUID.fromString(bookDTO.getBookId()));
            pw.println("<h1> bookId = " + book.getBookId() + "</h1>");
            book.setBookname(bookDTO.getBookname());
            pw.println("<h1> bookname = " + book.getBookname() + "</h1>");
            book.setAuthor(bookDTO.getAuthor());
            pw.println("<h1> author = " + book.getAuthor() + "</h1>");
            book.setCostInByn(bookDTO.getCostInByn());
            pw.println("<h1> costInByn = " + book.getCostInByn() + "</h1>");
            book.setCountInStock(bookDTO.getCountInStock());
            pw.println("<h1> countInStock = " + book.getCountInStock() + "</h1>");
            bookDAO.updateBook(book);
            pw.println("<h1> As JSON = " + jsonSerializer.serialize(bookDTO) + "</h1>");
            pw.println("</html>");
        } catch (Exception e) {
            pw.println("<html>");
            pw.println("<h1> Wrong JSON</h1>");
            pw.println("<h1>" + e + "</h1>");
            response.setStatus(400);
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
            DeleteDTO deleteDTO = mapper.map(jsonDeserializer.parseValue(), DeleteDTO.class);
            Book book = bookDAO.find(new FindBookRequest().setBookId(UUID.fromString(deleteDTO.getUuid()))).get(0);
            pw.println("<html>");
            BookDTO bookDTO = new BookDTO();
            bookDTO.setBookId(book.getBookId().toString());
            pw.println("<h1> bookId = " + book.getBookId() + "</h1>");
            bookDTO.setBookname(book.getBookname());
            pw.println("<h1> bookname = " + book.getBookname() + "</h1>");
            bookDTO.setAuthor(book.getAuthor());
            pw.println("<h1> author = " + book.getAuthor() + "</h1>");
            bookDTO.setCostInByn(book.getCostInByn());
            pw.println("<h1> costInByn = " + book.getCostInByn() + "</h1>");
            bookDTO.setCountInStock(book.getCountInStock());
            pw.println("<h1> countInStock = " + book.getCountInStock() + "</h1>");
            bookDAO.delete(new FindBookRequest().setBookId(book.getBookId()));
            pw.println("<h1> As JSON = " + jsonSerializer.serialize(bookDTO) + "</h1>");
            pw.println("</html>");

        } catch (Exception e) {
            pw.println("<html>");
            pw.println("<h1> Wrong JSON</h1>");
            pw.println("<h1>" + e + "</h1>");
            response.setStatus(400);
            pw.println("</html>");
        }

    }
}