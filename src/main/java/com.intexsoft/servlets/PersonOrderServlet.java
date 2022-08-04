package com.intexsoft.servlets;

import com.intexsoft.controler.ConnectToDb;
import com.intexsoft.controler.dao.*;
import com.intexsoft.controler.findRequest.*;
import com.intexsoft.controler.updateRequest.UpdatePersonOrderRequest;
import com.intexsoft.model.*;
import com.intexsoft.model.transfer.*;
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

@WebServlet(name = "PersonOrderServlet", value = "/PersonOrder")
public class PersonOrderServlet extends HttpServlet {

    ConnectToDb connectToDb = new ConnectToDb();
    Connection connection = connectToDb.connect_to_db("bookshop", "postgres", "postgrespw");
    PersonOrderDAO personOrderDAO = new PersonOrderDAO(connection);
    CartDAO cartDAO = new CartDAO(connection);
    PersonOrderHasBookDAO personOrderHasBookDAO = new PersonOrderHasBookDAO(connection);
    CartHasBookDAO cartHasBookDAO = new CartHasBookDAO(connection);
    BookDAO bookDAO = new BookDAO(connection);
    JsonSerializer jsonSerializer = new JsonSerializer();
    Mapper mapper = new Mapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stringUUId = request.getParameter("uuid");
        PrintWriter pw = response.getWriter();
        try {
            UUID uuid = UUID.fromString(stringUUId);
            PersonOrder personOrder = personOrderDAO.findWithBooks(new FindPersonOrderRequest().setOrderId(uuid));
            PersonOrderDTO personOrderDTO = new PersonOrderDTO();
            pw.println("<html>");
            pw.println("<h1> orderId = " + personOrder.getOrderId() + "</h1>");
            personOrderDTO.setOrderId(personOrder.getOrderId().toString());
            pw.println("<h1> personId = " + personOrder.getPersonId() + "</h1>");
            personOrderDTO.setPersonId(personOrder.getPersonId().toString());
            pw.println("<h1> adress = " + personOrder.getAdress() + "</h1>");
            personOrderDTO.setAdress(personOrder.getAdress());
            pw.println("<h1> status = " + personOrder.getStringStatusId() + "</h1>");
            personOrderDTO.setStatusId(personOrder.getStatusId());
            pw.println("<h1> books </h1>");
            List<PersonOrderHasBook> list = personOrderHasBookDAO.find(new FindPersonOrderHasBookRequest().setOrderId(personOrder.getOrderId()));
            PersonOrderHasBookDTO[] bookArr = new PersonOrderHasBookDTO[list.size()];
            int bookCounter = 0;
            for (PersonOrderHasBook personOrderHasBook : list) {
                PersonOrderHasBookDTO personOrderHasBookDTO = new PersonOrderHasBookDTO();
                pw.println("<h1> bookId = " + personOrderHasBook.getBookId() + "</h1>");
                personOrderHasBookDTO.setBookId(personOrderHasBook.getBookId().toString());
                pw.println("<h1> bookCount = " + personOrderHasBook.getBookCount() + "</h1>");
                personOrderHasBookDTO.setBookCount(personOrderHasBook.getBookCount());
                personOrderHasBookDTO.setOrderId(personOrder.getOrderId().toString());
                bookArr[bookCounter] = personOrderHasBookDTO;
                bookCounter++;
                pw.println("<h1> -----------------------</h1>");
            }
            personOrderDTO.setBooks(bookArr);
            pw.println("<h1> As JSON = " + jsonSerializer.serialize(personOrderDTO) + "</h1>");
            pw.println("</html>");
        } catch (IllegalArgumentException e) {
            pw.println("<html>");
            pw.println("<h1> Invalid UUID </h1>");
            pw.println("<h1>" + e + "</h1>");
            pw.println("</html>");
        } catch (IndexOutOfBoundsException e) {
            pw.println("<html>");
            pw.println("<h1> No such cart and book UUIDs </h1>");
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
            CreatePersonOrderDTO createPersonOrderDTO = mapper.map(jsonDeserializer.parseValue(), CreatePersonOrderDTO.class);
            UUID uuid = UUID.randomUUID();
            PersonOrder personOrder = new PersonOrder();
            personOrder.setOrderId(uuid);
            personOrder.setPersonId(UUID.fromString(createPersonOrderDTO.getPersonId()));
            personOrder.setAdress(createPersonOrderDTO.getAdress());
            personOrder.setStatusId(createPersonOrderDTO.getStatusId());
            personOrderDAO.createPersonOrder(personOrder);
            Cart cart = cartDAO.findWithBooks(new FindCartRequest().setCartPersonId(personOrder.getPersonId()));
            List<CartHasBook> listOfBooks = cartHasBookDAO.find(new FindCartHasBookRequest().setCartId(cart.getCartId()));
            for (CartHasBook cartHasBook : listOfBooks) {
                personOrderHasBookDAO.createRow(uuid, cartHasBook.getBookId(), cartHasBook.getBookCount());
                cartHasBookDAO.delete(new FindCartHasBookRequest().setCartId(cart.getCartId()).setBookId(cartHasBook.getBookId()));
                Book book = bookDAO.find(new FindBookRequest().setBookId(cartHasBook.getBookId())).get(0);
                book.setCountInStock(book.getCountInStock() - cartHasBook.getBookCount());
                bookDAO.updateBook(book);
            }
            PersonOrderDTO personOrderDTO = new PersonOrderDTO();
            pw.println("<html>");
            pw.println("<h1> orderId = " + personOrder.getOrderId() + "</h1>");
            personOrderDTO.setOrderId(personOrder.getOrderId().toString());
            pw.println("<h1> personId = " + personOrder.getPersonId() + "</h1>");
            personOrderDTO.setPersonId(personOrder.getPersonId().toString());
            pw.println("<h1> adress = " + personOrder.getAdress() + "</h1>");
            personOrderDTO.setAdress(personOrder.getAdress());
            pw.println("<h1> status = " + personOrder.getStringStatusId() + "</h1>");
            personOrderDTO.setStatusId(personOrder.getStatusId());
            pw.println("<h1> books </h1>");
            PersonOrderHasBookDTO[] bookArr = new PersonOrderHasBookDTO[listOfBooks.size()];
            int bookCounter = 0;
            for (CartHasBook book : listOfBooks) {
                PersonOrderHasBookDTO temp = new PersonOrderHasBookDTO();
                temp.setOrderId(personOrder.getOrderId().toString());
                pw.println("<h1> bookId = " + book.getBookId() + "</h1>");
                temp.setBookId(book.getBookId().toString());
                pw.println("<h1> bookCount = " + book.getBookCount() + "</h1>");
                temp.setBookCount(book.getBookCount());
                bookArr[bookCounter] = temp;
                bookCounter++;
                pw.println("<h1>---------------------------</h1>");
            }
            personOrderDTO.setBooks(bookArr);
            pw.println("<h1> As JSON = " + jsonSerializer.serialize(personOrderDTO) + "</h1>");
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
            UpdateOrderStatusDTO updateOrderStatusDTO = mapper.map(jsonDeserializer.parseValue(), UpdateOrderStatusDTO.class);
            personOrderDAO.update(new UpdatePersonOrderRequest()
                            .setStatusId(updateOrderStatusDTO.getStatusId())
                    , new FindPersonOrderRequest()
                            .setOrderId(UUID.fromString(updateOrderStatusDTO.getOrderId())));
            pw.println("<html>");
            pw.println("<h1> orderId = " + updateOrderStatusDTO.getOrderId() + "</h1>");
            pw.println("<h1> statusId = " + updateOrderStatusDTO.getStatusId() + "</h1>");
            pw.println("<h1> As JSON = " + jsonSerializer.serialize(updateOrderStatusDTO) + "</h1>");
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
            DeleteDTO deleteDTO = mapper.map(jsonDeserializer.parseValue(), DeleteDTO.class);
            PersonOrder personOrder = personOrderDAO.find(new FindPersonOrderRequest().setOrderId(UUID.fromString(deleteDTO.getUuid()))).get(0);
            CreatePersonOrderDTO createPersonOrderDTO = new CreatePersonOrderDTO();
            pw.println("<html>");
            pw.println("<h1> orderId = " + personOrder.getOrderId() + "</h1>");
            pw.println("<h1> personId = " + personOrder.getPersonId() + "</h1>");
            createPersonOrderDTO.setPersonId(personOrder.getPersonId().toString());
            pw.println("<h1> adress = " + personOrder.getAdress() + "</h1>");
            createPersonOrderDTO.setAdress(personOrder.getAdress());
            pw.println("<h1> statusId = " + personOrder.getStatusId() + "</h1>");
            createPersonOrderDTO.setStatusId(personOrder.getStatusId());
            pw.println("<h1> As JSON = " + jsonSerializer.serialize(createPersonOrderDTO) + "</h1>");
            pw.println("</html>");
            personOrderDAO.delete(new FindPersonOrderRequest().setOrderId(personOrder.getOrderId()));
        } catch (
                Exception e) {
            pw.println("<html>");
            pw.println("<h1> Wrong JSON</h1>");
            pw.println("<h1>" + e + "</h1>");
            pw.println("</html>");
        }
    }
}
