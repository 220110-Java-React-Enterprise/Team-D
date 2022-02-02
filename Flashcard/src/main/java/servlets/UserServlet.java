package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserServlet extends HttpServlet {

    // This is a read method - ex. retrieve this user's information
    // Expects: user = # (=user_id)
    // Returns: User object for that ID
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    // This is a write method - ex. add this user's information
    // Expects all the data for a User object (except user_id)
    // Returns: TODO (some sort of confirmation?)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    // This is an update method - ex. update this user's information
    // Expects some or all of the data in a User object and requires the user_id (user = #)
    // Returns: TODO (some sort of confirmation?)
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    // This is a delete method - ex. delete this user's information
    // Expects the user_id at least (user = #)
    // Returns: TODO (some sort of confirmation?)
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}