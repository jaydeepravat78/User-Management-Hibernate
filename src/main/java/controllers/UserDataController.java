package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.User;
import services.UserService;
import services.UserServiceImpl;

/**
 * Servlet implementation class UserDataController
 */
public class UserDataController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session != null && (session.getAttribute("user") != null || session.getAttribute("admin") != null)) {
			int id = Integer.parseInt(request.getParameter("id"));
			UserService service = new UserServiceImpl();
			User user = service.getUser(id);
			if (user != null) {
				request.setAttribute("id", id);
				request.setAttribute("userData", user);
				RequestDispatcher rd = request.getRequestDispatcher("registration.jsp");
				rd.forward(request, response);
			} else {
				response.sendRedirect("index.jsp");
			}
		} else {
			response.sendRedirect("index.jsp");
		}
	}

}
