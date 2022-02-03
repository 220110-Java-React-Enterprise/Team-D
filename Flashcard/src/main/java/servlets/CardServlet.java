package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.InvalidContentTypeException;
import exceptions.InvalidInputException;
import objects.Card;
import utils.MockingORM;
import utils.Parse;

public class CardServlet extends HttpServlet {

    // This is a read method - ex. retrieve this card
    // Expects: card = # (=card_id) or json {"card": card_id}
    // Returns: Card object for that ID
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer cardNumber;
        CardId cardToGet = new CardId();
        ObjectMapper mapper = new ObjectMapper();
        String contentType = req.getHeader("Content-Type");
        try {
            if (contentType.equals("application/json")) {
                cardToGet = mapper.readValue(req.getInputStream(), CardId.class);
            } else {
                // This is some other kind of request - ex. plain/text with key/value pairs in url
                String cardNumberString = req.getParameter("card_id");
                cardNumber = Parse.getNumberFromString(cardNumberString);
                cardToGet.setId(cardNumber);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new InvalidInputException("Invalid input received");
        }
        // Call orm and retrieve card
        Card card = MockingORM.getCardFromCardNumber(cardToGet.getId());
        // convert object to json
        String json = mapper.writeValueAsString(card);
        // Set response header and return to sender
        resp.setStatus(200);
        resp.getWriter().print(json);
    }


    // This is a write method - ex. add this card
    // Expects all the data for a Card object (except card_id)
    // Returns: TODO (some sort of confirmation?)
    // TODO: (stretch goal) if requesting a card deletion, the request will have to come through as a post
    // if sent via form because of html restrictions.  So either catch and forward to doDelete() or setup
    // a different endpoint, or something
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getHeader("Content-Type");
        ObjectMapper mapper = new ObjectMapper();
        Card card;
        try {
            if (contentType.equals("application/json")) {
                card = mapper.readValue(req.getInputStream(), Card.class);
            }
            else {
                // This is only used if the user submits their information in a form (ex. website).
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
                } else {
                    throw new InvalidContentTypeException("Unsupported content type " + contentType + " received.");
                }
            }
        } catch (Exception e) {
            throw new InvalidInputException("Some invalid input was received.");
        }
        // return updated card to user
        MockingORM.submitNewCard(card);
        String json = mapper.writeValueAsString(card);
        resp.setStatus(203);
        resp.getWriter().write(json);
    }

    // This is an update method - ex. update this card's information
    // Expects some or all of the data in a Card object and requires the card_id (card = #)
    // Returns: TODO (some sort of confirmation?)
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getHeader("Content-Type");
        ObjectMapper mapper = new ObjectMapper();
        Card card;
        try {
            if (contentType.equals("application/json")) {
                card = mapper.readValue(req.getInputStream(), Card.class);
            }
            else {
                // This is only used if the user submits their information in a form (ex. website).
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
                } else {
                    throw new InvalidContentTypeException("Unsupported content type " + contentType + " received.");
                }
            }
        } catch (Exception e) {
            throw new InvalidInputException("Some invalid input was received.");
        }
        // return updated card to user
        MockingORM.submitNewCard(card);
        String json = mapper.writeValueAsString(card);
        resp.setStatus(203);
        resp.getWriter().write(json);
    }

    // This is a delete method - ex. delete this card
    // Expects the card_id at least (card = #)
    // Returns: TODO (some sort of confirmation?)
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Card card = new Card();
        try {
            ObjectMapper mapper = new ObjectMapper();
            card = mapper.readValue(req.getInputStream(), Card.class);
            System.out.println("card_id: " + card.getId());
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something went wrong in cardservlet.doDelete()");
            System.out.println("card_id: " + card.getId());
            throw new InvalidInputException("Bad delete request");
        }
        // maybe verify that the card # and creator are the same before deleting?

        Boolean result = MockingORM.deleteCard(card.getId());
        resp.setStatus(200);
        String json = "{\"card\": " + card.getId() + ", \"deleted\": " + result + "}";
        resp.getWriter().write(json);
    }
}

// Used to parse json requests with Jackson - in general should not be directly persisted or used elsewhere
class CardId {
    Integer id;
    @JsonGetter("card_id")
    public Integer getId() {
        return id;
    }

    @JsonSetter("card_id")
    public void setId(Integer id) {
        this.id = id;
    }

    CardId() {
    }
}