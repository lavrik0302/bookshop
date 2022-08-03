package com.intexsoft.servlets;

import com.intexsoft.controler.ConnectToDb;
import com.intexsoft.controler.dao.*;
import com.intexsoft.controler.findRequest.*;
import com.intexsoft.model.*;
import com.intexsoft.model.transfer.CreatePersonOrderDTO;
import com.intexsoft.model.transfer.PersonOrderDTO;
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

@WebServlet(name = "PersonOrderServlet", value = "/PersonOrder")
public class PersonOrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stringUUId = request.getParameter("uuid");
        PrintWriter pw = response.getWriter();
        UUID uuid = UUID.fromString(stringUUId);
        ConnectToDb connectToDb = new ConnectToDb();
        Connection connection = connectToDb.connect_to_db("bookshop", "postgres", "postgrespw");
        PersonOrderDAO personOrderDAO = new PersonOrderDAO(connection);
        PersonOrder personOrder = personOrderDAO.findWithBooks(new FindPersonOrderRequest().setOrderId(uuid));
        pw.println("<html>");

        pw.println("<h1> orderId = " + personOrder.getOrderId() + "</h1>");
        pw.println("<h1> personId = " + personOrder.getPersonId() + "</h1>");
        pw.println("<h1> adress = " + personOrder.getAdress() + "</h1>");
        pw.println("<h1> status = " + personOrder.getStringStatusId() + "</h1>");
        pw.println("<h1> books </h1>");
        List<Book> list = personOrder.getBooks();
        for (Book book : list) {
            pw.println("<h1> bookId = " + book.getBookId() + "</h1>");
            pw.println("<h1> bookname = " + book.getBookname() + "</h1>");
            pw.println("<h1> author = " + book.getAuthor() + "</h1>");
            pw.println("<h1> costInByn = " + book.getCostInByn() + "</h1>");
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
        JsonDeserializer jsonDeserializer = new JsonDeserializer(sb.toString());
        Mapper mapper = new Mapper();
        CreatePersonOrderDTO createPersonOrderDTO = mapper.map(jsonDeserializer.parseValue(), CreatePersonOrderDTO.class);
        PersonOrderDAO personOrderDAO = new PersonOrderDAO(connection);
        UUID uuid = UUID.randomUUID();
        PersonOrder personOrder = new PersonOrder();
        personOrder.setOrderId(uuid);
        personOrder.setPersonId(UUID.fromString(createPersonOrderDTO.getPersonId()));
        personOrder.setAdress(createPersonOrderDTO.getAdress());
        personOrder.setStatusId(createPersonOrderDTO.getStatusId());
        personOrderDAO.createPersonOrder(personOrder);
        CartDAO cartDAO = new CartDAO(connection);
        Cart cart = cartDAO.findWithBooks(new FindCartRequest().setCartPersonId(personOrder.getPersonId()));
        PersonOrderHasBookDAO personOrderHasBookDAO = new PersonOrderHasBookDAO(connection);
        CartHasBookDAO cartHasBookDAO = new CartHasBookDAO(connection);
        List<CartHasBook> listOfBooks = cartHasBookDAO.find(new FindCartHasBookRequest().setCartId(cart.getCartId()));
        for (CartHasBook book : listOfBooks) {
            personOrderHasBookDAO.createRow(uuid, book.getBookId(), book.getBookCount());
            cartHasBookDAO.delete(new FindCartHasBookRequest().setCartId(cart.getCartId()).setBookId(book.getBookId()));
        }
        pw.println("<html>");
        pw.println("<h1> orderId = " + personOrder.getOrderId() + "</h1>");
        pw.println("<h1> personId = " + personOrder.getPersonId() + "</h1>");
        pw.println("<h1> adress = "+personOrder.getAdress()+"</h1>");
        pw.println("<h1> status = "+personOrder.getStringStatusId()+"</h1>");
        pw.println("<h1> books </h1>");
        for (CartHasBook book:listOfBooks) {
            pw.println("<h1> bookId = "+book.getBookId()+"</h1>");
            pw.println("<h1> bookCount = "+book.getBookCount()+"</h1>");
            pw.println("<h1>---------------------------</h1>");
        }
        pw.println("</html>");
    }
}