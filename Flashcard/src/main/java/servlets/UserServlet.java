package servlets;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.InvalidContentTypeException;
import exceptions.InvalidInputException;
import objects.Card;
import objects.User;
import utils.MockingORM;
import utils.Parse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



public class UserServlet extends HttpServlet {

    // This is a read method - ex. retrieve this user's information
    // Expects: user = # (=user_id) or { "user_id": # }
    // Returns: User object for that ID
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer userId;
        UserId userToGet = new UserId();
        ObjectMapper mapper = new ObjectMapper();
        String contentType = req.getHeader("Content-Type");
        try {
            if (contentType.equals("application/json")) {
                userToGet = mapper.readValue(req.getInputStream(), UserId.class);
            } else {
                // This is some other kind of request - ex. plain/text with key/value pairs in url
                String userIdString = req.getParameter("user_id");
                userId = Parse.getNumberFromString(userIdString);
                userToGet.setId(userId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidInputException("Invalid input received");
        }
        // Call orm and retrieve card
        Card card = MockingORM.getCardFromCardNumber(userToGet.getId());
        // convert object to json
        String json = mapper.writeValueAsString(card);
        // Set response header and return to sender
        resp.setStatus(200);
        resp.getWriter().print(json);
    }

    // This is a write method - ex. add this user's information
    // Expects all the data for a User object (except user_id)
    // Returns: TODO (some sort of confirmation?)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getHeader("Content-Type");
        ObjectMapper mapper = new ObjectMapper();
        User user;
        try {
            if (contentType.equals("application/json")) {
                user = mapper.readValue(req.getInputStream(), User.class);
            }
            else {
                // This is only used if the user submits their information in a form (ex. website).
                if (contentType.equals("application/x-www-form-urlencoded")) {
                    String firstName = req.getParameter("first-name");
                    String lastName = req.getParameter("last-name");
                    String email = req.getParameter("email");

                    user = new User(firstName, lastName, email);
                } else {
                    throw new InvalidContentTypeException("Unsupported content type " + contentType + " received.");
                }
            }
        } catch (Exception e) {
            throw new InvalidInputException("Some invalid input was received.");
        }
        // return updated card to user
        MockingORM.addUser(user);
        String json = mapper.writeValueAsString(user);
        resp.setStatus(203);
        resp.getWriter().write(json);
    }

    // This is an update method - ex. update this user's information
    // Expects some or all of the data in a User object and requires the user_id (user = #)
    // Returns: TODO (some sort of confirmation?)
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getHeader("Content-Type");
        ObjectMapper mapper = new ObjectMapper();
        User user;
        try {
            if (contentType.equals("application/json")) {
                user = mapper.readValue(req.getInputStream(), User.class);
            }
            else {
                // This is only used if the user submits their information in a form (ex. website).
                if (contentType.equals("application/x-www-form-urlencoded")) {
                    String firstName = req.getParameter("first-name");
                    String lastName = req.getParameter("last-name");
                    String email = req.getParameter("email");

                    user = new User(firstName, lastName, email);
                } else {
                    throw new InvalidContentTypeException("Unsupported content type " + contentType + " received.");
                }
            }
        } catch (Exception e) {
            throw new InvalidInputException("Some invalid input was received.");
        }
        // return updated card to user
        MockingORM.addUser(user);
        String json = mapper.writeValueAsString(user);
        resp.setStatus(203);
        resp.getWriter().write(json);
    }

    // This is a delete method - ex. delete this user's information
    // Expects the user_id at least (user = #)
    // Returns: TODO (some sort of confirmation?)
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user;
        try {
            ObjectMapper mapper = new ObjectMapper();
            user = mapper.readValue(req.getInputStream(), User.class);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new InvalidInputException("Bad delete request");
        }

        Boolean result = MockingORM.deleteUser(user.getId());
        resp.setStatus(200);
        String json = "{\"user_id\": " + user.getId() + ", \"deleted\": " + result + "}";
        resp.getWriter().write(json);
    }
}

// Used to parse json requests with Jackson - in general should not be directly persisted or used elsewhere
class UserId {
    Integer id;

    @JsonGetter("user_id")
    public Integer getId() {
        return id;
    }

    @JsonSetter("user_id")
    public void setId(Integer id) {
        this.id = id;
    }
    UserId() {}
}