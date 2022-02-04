package servlets;

import exceptions.InvalidInputException;
import utils.Log;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorServlet extends HttpServlet {
    Log log = Log.getLogger();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //log.write(new InvalidInputException("Invalid input received, unable to locate the specified resource."));
        resp.setStatus(404);
        String message = "Error: Unable to locate the specified resource.";
        resp.getWriter().print(message);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //log.write(new InvalidInputException("Bad request received, unable to add specified resource."));
        resp.setStatus(400);
        String message = "Error: Bad request or malformed JSON received.  Unable to add the specified resource.";
        resp.getWriter().print(message);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //log.write(new InvalidInputException("Unable to modify the specified resource."));
        resp.setStatus(400);
        String message = "Error: Unable to modify the specified resource.";
        resp.getWriter().print(message);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //log.write(new InvalidInputException("Unable to delete the specified resource."));
        resp.setStatus(400);
        String message = "Error: Unable to delete the specified resource.";
        resp.getWriter().print(message);
    }

}
