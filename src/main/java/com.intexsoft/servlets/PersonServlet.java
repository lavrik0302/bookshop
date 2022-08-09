package com.intexsoft.servlets;

import com.intexsoft.controler.dao.CartDAO;
import com.intexsoft.controler.dao.PersonDAO;
import com.intexsoft.controler.dao.PersonOrderHasBookDAO;
import com.intexsoft.controler.findRequest.FindCartRequest;
import com.intexsoft.controler.findRequest.FindPersonOrderHasBookRequest;
import com.intexsoft.controler.findRequest.FindPersonRequest;
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
import java.util.Arrays;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "PersonServlet", value = "/PersonServlet")
public class PersonServlet extends HttpServlet {

    PersonDAO personDAO = new PersonDAO();
    JsonSerializer jsonSerializer = new JsonSerializer();
    Mapper mapper = new Mapper();
    CartDAO cartDAO = new CartDAO();
    PersonOrderHasBookDAO personOrderHasBookDAO = new PersonOrderHasBookDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stringUUId = request.getParameter("uuid");
        PrintWriter pw = response.getWriter();

        try {
            UUID uuid = UUID.fromString(stringUUId);
            Person person = personDAO.findWithPersonOrdersWithBooks(new FindPersonRequest().setPersonId(uuid));
            PersonDTO personDTO = new PersonDTO();
            personDTO.setPersonId(person.getPersonId().toString());
            personDTO.setName(person.getName());
            personDTO.setSurname(person.getSurname());
            personDTO.setMobilenumber(person.getMobilenumber());
            Cart cart = cartDAO.find(new FindCartRequest().setCartPersonId(person.getPersonId()));
            CartDTO cartDTO = new CartDTO();
            cartDTO.setPersonId(cart.getPersonId().toString());
            cartDTO.setCartId(cart.getCartId().toString());
            cartDTO.setCartname(cart.getCartname());
            personDTO.setPersonCart(cartDTO);
            int numberOfOrders = person.getPersonOrders().size();
            PersonOrderDTO[] personOrderDTOList = new PersonOrderDTO[numberOfOrders];
            int orderCounter = 0;
            for (PersonOrder personOrder : person.getPersonOrders()) {
                PersonOrderDTO personOrderDTO = new PersonOrderDTO();
                personOrderDTO.setPersonId(personOrder.getPersonId().toString());
                personOrderDTO.setOrderId(personOrder.getOrderId().toString());
                personOrderDTO.setAdress(personOrder.getAdress());
                personOrderDTO.setStatusId(personOrder.getStatusId());
                int numberOfBooks = person.getPersonOrders().get(orderCounter).getBooks().size();
                PersonOrderHasBookDTO[] bookDTOList = new PersonOrderHasBookDTO[numberOfBooks];
                int bookCounter = 0;
                for (PersonOrderHasBook personOrderHasBook : personOrderHasBookDAO.find(new FindPersonOrderHasBookRequest().setOrderId(personOrder.getOrderId()))) {
                    PersonOrderHasBookDTO personOrderHasBookDTO = new PersonOrderHasBookDTO();
                    personOrderHasBookDTO.setOrderId(personOrderHasBook.getOrderId().toString());
                    personOrderHasBookDTO.setBookId(personOrderHasBook.getBookId().toString());
                    personOrderHasBookDTO.setBookCount(personOrderHasBook.getBookCount());
                    bookDTOList[bookCounter] = personOrderHasBookDTO;
                    bookCounter++;
                }
                personOrderDTO.setBooks(bookDTOList);
                personOrderDTOList[orderCounter] = personOrderDTO;
                orderCounter++;
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
        } catch (IllegalArgumentException | NullPointerException e) {
            pw.println("<html>");
            pw.println("<h1> Invalid UUID </h1>");
            response.setStatus(400);
            pw.println("<h1>" + e + "</h1>");
            pw.println("</html>");
        } catch (IndexOutOfBoundsException e) {
            pw.println("<html>");
            pw.println("<h1> No such personId UUID </h1>");
            pw.println("<h1>" + e + "</h1>");
            response.setStatus(404);
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
            PesronInfoDTO pesronInfoDTO = mapper.map(jsonDeserializer.parseValue(), PesronInfoDTO.class);
            Pattern regexForMobilenumber = Pattern.compile("^(\\+375)(25|29|33|44)\\d{7}$");
            Matcher matcher = regexForMobilenumber.matcher(pesronInfoDTO.getMobilenumber());
            if (matcher.matches()) {
                Person person = personDAO.createRow(pesronInfoDTO.getName(), pesronInfoDTO.getSurname(), pesronInfoDTO.getMobilenumber());
                String cartname = person.getSurname() + " cart";
                cartDAO.createRow(person.getPersonId(), cartname);
                pw.println("<html>");
                pw.println("<h1> personId = " + person.getPersonId() + "</h1>");
                pw.println("<h1> name = " + person.getName() + "</h1>");
                pw.println("<h1> surname = " + person.getSurname() + "</h1>");
                pw.println("<h1> mobileNumber = " + person.getMobilenumber() + "</h1>");
                pw.println("<h1> As JSON = " + jsonSerializer.serialize(pesronInfoDTO) + "</h1>");
                pw.println("</html>");
            }else {
                pw.println("<html>");
                pw.println("<h1> Invalid Mobile Number. Please try again.</h1>");
                response.setStatus(400);
                pw.println("</html>");
            }
        } catch (Exception e) {
            pw.println("<html>");
            pw.println("<h1> Wrong JSON</h1>");
            response.setStatus(400);
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
            PersonDeleteUpdateDTO personUpdateDTO = mapper.map(jsonDeserializer.parseValue(), PersonDeleteUpdateDTO.class);
            Person person = new Person();
            pw.println("<html>");
            person.setPersonId(UUID.fromString(personUpdateDTO.getPersonId()));
            pw.println("<h1> personId = " + person.getPersonId() + "</h1>");
            person.setName(personUpdateDTO.getName());
            pw.println("<h1> name = " + person.getName() + "</h1>");
            person.setSurname(personUpdateDTO.getSurname());
            pw.println("<h1> surname = " + person.getSurname() + "</h1>");
            person.setMobilenumber(personUpdateDTO.getMobilenumber());
            pw.println("<h1> mobilenumber = " + person.getMobilenumber() + "</h1>");
            personDAO.updatePerson(person);
            pw.println("<h1> As JSON = " + jsonSerializer.serialize(personUpdateDTO) + "</h1>");
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
            Person person = personDAO.find(new FindPersonRequest().setPersonId(UUID.fromString(deleteDTO.getUuid()))).get(0);
            pw.println("<html>");
            PersonDeleteUpdateDTO personDeleteUpdateDTO = new PersonDeleteUpdateDTO();
            personDeleteUpdateDTO.setPersonId(person.getPersonId().toString());
            pw.println("<h1> personId = " + person.getPersonId() + "</h1>");
            personDeleteUpdateDTO.setName(person.getName());
            pw.println("<h1> name = " + person.getName() + "</h1>");
            personDeleteUpdateDTO.setSurname(person.getSurname());
            pw.println("<h1> surname = " + person.getSurname() + "</h1>");
            personDeleteUpdateDTO.setMobilenumber(person.getMobilenumber());
            pw.println("<h1> mobilenumber = " + person.getMobilenumber() + "</h1>");
            personDAO.delete(new FindPersonRequest().setPersonId(person.getPersonId()));
            pw.println("<h1> As JSON = " + jsonSerializer.serialize(personDeleteUpdateDTO) + "</h1>");
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