package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.InvalidInputException;
import objects.Card;
import utils.Log;
import utils.MockingORM;
import utils.Parse;
import services.BlankRepo;


/**
 * Handles requests for card-related data manipulation.
 */
public class CardServlet extends HttpServlet {
    Log log = Log.getLogger();
    BlankRepo repo = new BlankRepo();

    /**
     * Returns a Card object in JSON format for the specified ID.
     * Input can either be in JSON format or in the url as a key/value pair.
     * The JSON format requires the card_id as an integer.
     * The key must be "card_id" with an integer.
     *
     * @param - card_id  an integer uniquely identifying the card.
     * @return      a JSON representation of the requested card.
     *
     * <p>
     *     <b>question:</b> (String) the question asked on the card.
     *     <b>answer1:</b> (String) one of the potential answers to the question.
     *     <b>answer2:</b> (String) one of the potential answers to the question.
     *     <b>answer3:</b> (String) one of the potential answers to the question.
     *     <b>answer4:</b> (String) one of the potential answers to the question.
     *     <b>correct_answer:</b> (Integer) the number identifying the correct answer.
     *     <b>creator_id:</b> (Integer) the user id of the person who created the card.
     * </p>
     */
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
            log.write(e);
            throw new InvalidInputException("Invalid input received");
        }
        // Call orm and retrieve card
        //Card card = MockingORM.getCardFromCardNumber(cardToGet.getId());
        Card card = new Card();


        card = (Card) repo.read(card, cardToGet.getId());
        // convert object to json
        String json = mapper.writeValueAsString(card);
        // Set response header and return to sender
        resp.setStatus(200);
        resp.getWriter().print(json);
    }


    /**
     * Returns the id number of a Card object in JSON format for the submitted card.
     * Input can either be in JSON format or submitted as text/plain as part of a form.
     * Requires several parameters to compose a complete card.
     * Card will be created and persisted in the database.
     *
     * @param - question  (String) the question asked on the card.
     * @param - answer1   (String) one potential answer to the question.
     * @param - answer2   (String) one potential answer to the question.
     * @param - answer3   (String) one potential answer to the question.
     * @param - answer4   (String) one potential answer to the question.
     * @param - correct_answer   (Integer) the number identifying the correct answer.
     * @return      a JSON object of the card with the card_id number.
     */
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
                    String correctAnswerString = req.getParameter("correct_answer");
                    String creatorIdString = req.getParameter("creator_id");

                    // get int form of correctAnswer + creatorId
                    Integer correctAnswer = Parse.getNumberFromString(correctAnswerString);
                    Integer creatorId = Parse.getNumberFromString(creatorIdString);

                    card = new Card(question, answer1, answer2, answer3, answer4, correctAnswer, creatorId);
                } else {
                    throw new InvalidInputException("Unsupported content type " + contentType + " received.");
                }
            }
        } catch (Exception e) {
            log.write(e);
            throw new InvalidInputException("Some invalid input was received.");
        }
        // return updated card to user
        MockingORM.submitNewCard(card);
        String json = mapper.writeValueAsString(card);
        resp.setStatus(203);
        resp.getWriter().write(json);
    }


    /**
     * Returns an updated Card object in JSON format for the specified ID.
     * Input should be in JSON format with all the parameters present.
     * Card will be updated in the database.
     *
     * @param - card_id   (Integer) the identification number of the card to modify.
     * @param - question  (String) the question asked on the card.
     * @param - answer1   (String) one potential answer to the question.
     * @param - answer2   (String) one potential answer to the question.
     * @param - answer3   (String) one potential answer to the question.
     * @param - answer4   (String) one potential answer to the question.
     * @param - correct_answer   (Integer) the number identifying the correct answer.
     * @return      a JSON representation of the updated card.
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getHeader("Content-Type");
        ObjectMapper mapper = new ObjectMapper();
        Card card = null;
        try {
            if (contentType.equals("application/json")) {
                card = mapper.readValue(req.getInputStream(), Card.class);
            }
            else {
                log.write(new InvalidInputException("Unsupported content type " + contentType + " received."));
                throw new InvalidInputException("Unsupported content type " + contentType + " received.");
            }
        } catch (Exception e) {
            log.write(e);
            throw new InvalidInputException("Some invalid input was received.");
        }
        // return updated card to user
        MockingORM.submitNewCard(card);
        String json = mapper.writeValueAsString(card);
        resp.setStatus(203);
        resp.getWriter().write(json);
    }


    /**
     * Returns a JSON object indicating if the deletion was successful.
     * Input should be in JSON format with the card_id.
     * Card will be deleted in the database.
     *
     * @param - card_id   (Integer) the identification number of the card to modify.
     * @return      a JSON object of the card's id number and result of the deletion.
     * <p>
     *     <b>card_id:</b> (Integer) the number identifying the card to be deleted.
     *     <b>deleted:</b> (Boolean) Indicates whether or not the delete request was successful.
     * </p>
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CardId card;
        try {
            ObjectMapper mapper = new ObjectMapper();
            card = mapper.readValue(req.getInputStream(), CardId.class);
        }
        catch (Exception e) {
            log.write(e);
            throw new InvalidInputException("Bad delete request");
        }
        Boolean result = MockingORM.deleteCard(card.getId());
        resp.setStatus(200);
        String json = "{\"card_id\": " + card.getId() + ", \"deleted\": " + result + "}";
        resp.getWriter().write(json);
    }

}


/**
 * Returns an object which only has one property (id).
 * This is used to facilitate parsing json requests with Jackson in which only the
 * card_id is sent, not the full card information.
 * In general, this should not be directly persisted or used elsewhere.
 */
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