package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.InvalidInputException;
import objects.Card;
import objects.User;
import services.BlankRepo;
import utils.Log;
import utils.Parse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ManageCardServlet extends HttpServlet {
    Log log = Log.getLogger();
    BlankRepo repo = new BlankRepo();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer userId;
        UserId userCardsToGet = new UserId();
        ObjectMapper mapper = new ObjectMapper();
        String contentType = req.getHeader("Content-Type");
        try {
            if (contentType.equals("application/json")) {
                userCardsToGet = mapper.readValue(req.getInputStream(), UserId.class);
            } else {
                // This is some other kind of request - ex. plain/text with key/value pairs in url
                String userIdString = req.getParameter("user_id");
                userId = Parse.getNumberFromString(userIdString);
                userCardsToGet.setId(userId);
            }
        } catch (Exception e) {
            log.write(e);
            throw new InvalidInputException("Invalid input received");
        }
        // Determine whether to get the user's info, or the user's cards?
        System.out.println(req.getQueryString());

        // Retrieve all cards from cards where creator_id = user_id
        Card card = new Card();
        User user = new User();
        // use list-specific read method to get all the cards of the user

        List<Card> userCards = new ArrayList<>();
        repo.readAll(user, userCardsToGet.getId(), Collections.singletonList(userCards));

        // Serialize all the cards
        // convert object to json
        String json = mapper.writeValueAsString(userCards);
        // Set response header and return to sender
        resp.setStatus(200);
        resp.getWriter().print(json);
    }

    // Only adding a get method to simplify implementation - modifying or deleting a card is already defined
    // in CardServlet
}
