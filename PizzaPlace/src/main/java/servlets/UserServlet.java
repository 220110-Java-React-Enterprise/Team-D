package servlets;
import com.fasterxml.jackson.databind.ObjectMapper;
import objects.User;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserServlet extends HttpServlet {

    // This is a read method - ex. user wants to bring up their own information
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("get response successful?");
        // query string is the info sent in the URL (ex. user = 1)
        System.out.println(req.getQueryString());

        // Test data
        User user = new User();
        user.setUserId(3);
        user.setEmail("my.mail@mail.me");
        user.setFirstName("Bilbo");
        user.setLastName("Baggins");
        String json = new ObjectMapper().writeValueAsString(user);

        // Debug stuff
        resp.setStatus(200);
        resp.getWriter().print(json);

        // Retrieve data from the orm for this user
        //super.doGet(req, resp);
    }

    // This is a create method - ex. user is registering by sending data to the server
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPost(req, resp);
        System.out.println("get response successful?");
        resp.setStatus(200);
        resp.getWriter().print("Pong!");
    }

    // This is an update method - ex. user is modifying their own information
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPut(req, resp);
    }

    // This is a delete method - ex. user wants to delete their account
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doDelete(req, resp);
    }
}
