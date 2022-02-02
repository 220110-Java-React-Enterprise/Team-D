package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// The goal behind this was to provide more specific error messages - ex. unsupported Content-Type
public class ErrorUsageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(404);
        String message = "Error: Unable to locate the specified resource.";
        resp.getWriter().print(message);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(400);
        String message = "Error: This content type is not supported.  Unable to add the specified resource.";
        resp.getWriter().print(message);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(400);
        String message = "Error: This content type is not supported.  Unable to modify the specified resource.";
        resp.getWriter().print(message);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(400);
        String message = "Error: Bad request - unable to delete the specified resource.";
        resp.getWriter().print(message);
    }
}
