package servlets;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.InvalidInputException;
import objects.User;
import services.BlankRepo;
import utils.Log;
import utils.Parse;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Handles requests for user-related data manipulation.
 */
public class UserServlet extends HttpServlet {
    Log log = Log.getLogger();
    BlankRepo repo = new BlankRepo();

    /**
     * Returns a User object in JSON format for the specified ID.
     * Input can either be in JSON format or in the url as a key/value pair.
     * The JSON format requires the user_id as an integer.
     * The key must be "user_id" with an integer value.
     *
     * @param - user_id  an integer uniquely identifying the user.
     * @return      a JSON representation of the requested user.
     *
     * <p>
     *     <b>user_id:</b> (Integer) the user's identification number.
     *     <b>first_name:</b> (String) the first name of the user.
     *     <b>last_name:</b> (String) the last name of the user.
     *     <b>email:</b> (String) the user's email address.
     * </p>
     */
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
            log.write(e);
            throw new InvalidInputException("Invalid input received");
        }

        // Call orm and retrieve card
        User user = new User();
        user = (User) repo.read(user, userToGet.getId());

        // convert object to json
        String json = mapper.writeValueAsString(user);
        // Set response header and return to sender
        resp.setStatus(200);
        resp.getWriter().print(json);
    }


    /**
     * Returns a User object in JSON format after successful persistence of the data.
     * Input can either be in JSON format or in the url as a key/value pair.
     * The JSON format requires the first_name, last_name, and email.
     * The keys must be in key-value pairs.
     *
     * @param - first_name  a String of the user's first name.
     * @param - last_name   a String of the user's last name.
     * @param - email       a String of the user's email.
     * @return      a JSON representation of the requested user.
     *
     * <p>
     *     <b>user_id:</b>  (Integer) the user's identification number.
     *     <b>first_name:</b> (String) the first name of the user.
     *     <b>last_name:</b> (String) the last name of the user.
     *     <b>email:</b> (String) the user's email address.
     * </p>
     */
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
                    String firstName = req.getParameter("first_name");
                    String lastName = req.getParameter("last_name");
                    String email = req.getParameter("email");

                    user = new User(firstName, lastName, email);
                } else {
                    log.write(new InvalidInputException("Unsupported content type " + contentType + " received."));
                    throw new InvalidInputException("Unsupported content type " + contentType + " received.");
                }
            }
        } catch (Exception e) {
            log.write(e);
            throw new InvalidInputException("Some invalid input was received.");
        }
        // return updated card to user
        user = (User) repo.create(user);

        String json = mapper.writeValueAsString(user);
        resp.setStatus(203);
        resp.getWriter().write(json);
    }


    /**
     * Returns an updated User object in JSON format for the specified ID.
     * Input must be in JSON format with all the required fields for a User object.
     *
     * @param - user_id  an integer uniquely identifying the user.
     * @param - first_name   a string of the user's first name.
     * @param - last_name    a string of the user's last name.
     * @param - email    the user's email address.
     * @return      a JSON representation of the requested user.
     *
     */
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
                    log.write(new InvalidInputException("Unsupported content type " + contentType + " received."));
                    throw new InvalidInputException("Unsupported content type " + contentType + " received.");
                }
            }
        } catch (Exception e) {
            log.write(e);
            throw new InvalidInputException("Some invalid input was received.");
        }
        // return updated card to user
        user = (User) repo.update(user);
        String json = mapper.writeValueAsString(user);
        resp.setStatus(203);
        resp.getWriter().write(json);
    }


    /**
     * Returns a JSON object indicating if the deletion was successful.
     * Input should be in JSON format with the user_id.
     * User will be deleted in the database.
     *
     * @param - user_id   (Integer) the identification number of the user to remove.
     * @return      a JSON object of the user's id number, name, and result of the deletion.
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user;
        try {
            ObjectMapper mapper = new ObjectMapper();
            user = mapper.readValue(req.getInputStream(), User.class);
        }
        catch (Exception e) {
            log.write(e);
            throw new InvalidInputException("Bad delete request");
        }

        // Creating user object so ORM can understand request
        User userToDelete = new User();
        repo.delete(userToDelete, user.getId());
        resp.setStatus(200);
        String json = "{\"user_id\": " + user.getId() + ", \"name\": " + user.getFirstName() + " " + user.getLastName() + ", \"deleted\": " + "true" + "}";
        resp.getWriter().write(json);
    }
}


/**
 * Returns a UserID object which only has one property, id.
 * This is to facilitate parsing JSON requests with Jackson and shouldn't be persisted/used elsewhere.
 */
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