package com.intexsoft.servlets;

import com.intexsoft.controler.ConnectToDb;
import com.intexsoft.controler.dao.PersonDAO;
import com.intexsoft.controler.findRequest.FindPersonRequest;
import com.intexsoft.model.Book;
import com.intexsoft.model.Person;
import com.intexsoft.model.PersonOrder;
import com.intexsoft.model.transfer.BookDTO;
import com.intexsoft.model.transfer.PersonDTO;
import com.intexsoft.model.transfer.PersonOrderDTO;
import com.intexsoft.model.transfer.PesronInfoDTO;
import com.intexsoft.parser.JsonDeserializer;
import com.intexsoft.parser.Mapper;
import com.intexsoft.serializer.JsonSerializer;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Arrays;
import java.util.UUID;

@WebServlet(name = "PersonServlet", value = "/PersonServlet")
public class PersonServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stringUUId = request.getParameter("uuid");
        PrintWriter pw = response.getWriter();
        UUID uuid = UUID.fromString(stringUUId);
        ConnectToDb connectToDb = new ConnectToDb();
        Connection connection = connectToDb.connect_to_db("bookshop", "postgres", "postgrespw");
        PersonDAO personDAO = new PersonDAO(connection);
        Person person = personDAO.findWithPersonOrdersWithBooks(new FindPersonRequest().setPersonId(uuid));
        JsonSerializer jsonSerializer = new JsonSerializer();
        PersonDTO personDTO = new PersonDTO();
        personDTO.setPersonId(person.getPersonId().toString());
        personDTO.setName(person.getName());
        personDTO.setSurname(person.getSurname());
        personDTO.setMobilenumber(person.getMobilenumber());
        personDTO.setPersonCart(person.getPersonCart());
        int q = person.getPersonOrders().size();
        PersonOrderDTO[] personOrderDTOList = new PersonOrderDTO[q];
        int i = 0;
        for (PersonOrder personOrder : person.getPersonOrders()) {
            PersonOrderDTO personOrderDTO = new PersonOrderDTO();
            personOrderDTO.setPersonId(personOrder.getPersonId().toString());
            personOrderDTO.setOrderId(personOrder.getOrderId().toString());
            personOrderDTO.setAdress(personOrder.getAdress());
            personOrderDTO.setStatusId(personOrder.getStatusId());
            int a = person.getPersonOrders().get(i).getBooks().size();
            BookDTO[] bookDTOList = new BookDTO[a];
            int j = 0;
            for (Book book : person.getPersonOrders().get(i).getBooks()) {
                BookDTO bookDTO = new BookDTO();
                bookDTO.setBookId(book.getBookId().toString());
                bookDTO.setBookname(book.getBookname());
                bookDTO.setAuthor(book.getAuthor());
                bookDTO.setCostInByn(book.getCostInByn());
                bookDTO.setCountInStock(book.getCountInStock());
                bookDTOList[j] = bookDTO;
                j++;
            }

            personOrderDTO.setBooks(bookDTOList);
            personOrderDTOList[i] = personOrderDTO;
            i++;
        }
        personDTO.setPersonOrders(personOrderDTOList);
        String json = jsonSerializer.serialize(personDTO);
        personDTO.setPersonOrders(personOrderDTOList);
        pw.println("<html>");
        pw.println("<h1> person_id = " + personDTO.getPersonId() + "</h1>");
        pw.println("<h1> name = " + personDTO.getName() + "</h1>");
        pw.println("<h1> surname = " + personDTO.getSurname() + "</h1>");
        pw.println("<h1> mobilenumber = " + personDTO.getMobilenumber() + "</h1>");
        pw.println("<h1> cart = " + personDTO.getPersonCart() + "</h1>");
        pw.println("<h1> personorders = " + Arrays.toString(personDTO.getPersonOrders()) + "</h1>");
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
        ConnectToDb connectToDb = new ConnectToDb();
        Connection connection = connectToDb.connect_to_db("bookshop", "postgres", "postgrespw");
        PersonDAO personDAO = new PersonDAO(connection);
        JsonDeserializer jsonDeserializer = new JsonDeserializer(sb.toString());
        Mapper mapper = new Mapper();
        PesronInfoDTO pesronInfoDTO = new PesronInfoDTO();
        pesronInfoDTO = mapper.map(jsonDeserializer.parseValue(), PesronInfoDTO.class);
        Person person = personDAO.createRow(pesronInfoDTO.getName(), pesronInfoDTO.getSurname(), pesronInfoDTO.getMobilenumber());
        pw.println("<html>");
        pw.println("<h1> personId = " + person.getPersonId() + "</h1>");
        pw.println("<h1> name = " + person.getName() + "</h1>");
        pw.println("<h1> surname = " + person.getSurname() + "</h1>");
        pw.println("<h1> mobileNumber = " + person.getMobilenumber() + "</h1>");

        pw.println("</html>");


    }
}
