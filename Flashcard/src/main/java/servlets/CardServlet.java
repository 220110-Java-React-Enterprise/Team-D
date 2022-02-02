package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.InvalidInputException;
import objects.Card;
import utils.MockingORM;
import utils.Parse;

public class CardServlet extends HttpServlet {
    // get request: ex. user wants to view a card
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer cardNumber;
        Card card = null;
        try {
            String cardNumberString = req.getParameter("card");
            cardNumber = Parse.getNumberFromString(cardNumberString);
            // Call orm and retrieve card
            card = MockingORM.getCardFromCardNumber(cardNumber);
        }
        catch (Exception e) {
            throw new InvalidInputException("Invalid input received");
        }

        // package into json and send response
        ObjectMapper mapper = new ObjectMapper();
        // convert object to json
        String json = mapper.writeValueAsString(card);
        System.out.println(card);
        resp.setStatus(200);
        resp.getWriter().print(json);
    }

    // post request: ex. user is submitting a card
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getHeader("Content-Type");
        ObjectMapper mapper = new ObjectMapper();
        Card card = new Card();
        // Note: the Content-Type must be sent on the client side (ex. postman) to application/json or text/plain
        // the code as written will return a server error if no content type is defined
        if (contentType.equals("application/json")) {
            System.out.println("Json detected!");
            card = mapper.readValue(req.getInputStream(), Card.class);
        }
        else {
            // maybe form data? Not sure if this section is needed
            // form data type: application/x-www-form-urlencoded
            System.out.println("Something other than json was received.");
            System.out.println(req.getHeader("Content-Type"));
            if (contentType.equals("application/x-www-form-urlencoded")) {
                try {
                    String question = req.getParameter("question");
                    String answer1 = req.getParameter("answer1");
                    String answer2 = req.getParameter("answer2");
                    String answer3 = req.getParameter("answer3");
                    String answer4 = req.getParameter("answer4");
                    String correctAnswerString = req.getParameter("correct-answer");
                    String creatorIdString = req.getParameter("user-id");

                    // get int form of correctAnswer + creatorId
                    Integer correctAnswer = Parse.getNumberFromString(correctAnswerString);
                    Integer creatorId = Parse.getNumberFromString(creatorIdString);

                    card = new Card(question, answer1, answer2, answer3, answer4, correctAnswer, creatorId);
                }
                catch (NumberFormatException e) {
                    System.out.println("An invalid number was submitted");
                    e.printStackTrace();
                }
                catch (Exception e) {
                    System.out.println("Some error occurred");
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("Something else received!");
                System.out.println(contentType);
                // this is really some error case
                //card = new Card();
            }
        }
        // return updated card to user
        MockingORM.submitNewCard(card);
        String json = mapper.writeValueAsString(card);
        resp.setStatus(203);
        resp.getWriter().write(json);
    }

    // put request: ex. user is editing a card
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getHeader("Content-Type");
        ObjectMapper mapper = new ObjectMapper();
        Card card = new Card();
        // Note: the Content-Type must be sent on the client side (ex. postman) to application/json or text/plain
        // the code as written will return a server error if no content type is defined
        if (contentType.equals("application/json")) {
            System.out.println("Json detected!");
            card = mapper.readValue(req.getInputStream(), Card.class);
        }
        else {
            // maybe form data? Not sure if this section is needed
            // form data type: application/x-www-form-urlencoded
            System.out.println("Something other than json was received.");
            System.out.println(req.getHeader("Content-Type"));
            if (contentType.equals("application/x-www-form-urlencoded")) {
                String question = req.getParameter("question");
                String answer1 = req.getParameter("answer1");
                String answer2 = req.getParameter("answer2");
                String answer3 = req.getParameter("answer3");
                String answer4 = req.getParameter("answer4");
                String correctAnswerString = req.getParameter("correct-answer");
                String creatorIdString = req.getParameter("user-id");

                // get int form of correctAnswer + creatorId
                Integer correctAnswer = Parse.getNumberFromString(correctAnswerString);
                Integer creatorId = Parse.getNumberFromString(creatorIdString);

                card = new Card(question, answer1, answer2, answer3, answer4, correctAnswer, creatorId);
            }
            else {
                System.out.println("Something else received!");
                System.out.println(contentType);
                // this is really some error case
                //card = new Card();
            }
        }

        // return updated card to user
        MockingORM.updateCard(card);
        String json = mapper.writeValueAsString(card);
        resp.setStatus(203);
        resp.getWriter().write(json);
    }

    // delete request: ex. user is deleting a card
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // maybe verify that the card # and creator are the same before deleting?
        // Retrieve the card ID
        //ObjectMapper mapper = new ObjectMapper();
        String cardDeleteString = req.getParameter("card");
        Integer cardNumber = Parse.getNumberFromString(cardDeleteString);
        Boolean result = MockingORM.deleteCard(cardNumber);
        resp.setStatus(200);
        String json = "{\"result\": " + result + ", \"card\": " + cardNumber + "}";
        resp.getWriter().write(json);
    }
}
