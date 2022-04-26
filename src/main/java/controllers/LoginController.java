package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import models.User;
import utility.BeanProvider;
import services.UserService;

/**
 * Servlet implementation class Login
 */
public class LoginController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(RegisterController.class);

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("user_email");
		String password = request.getParameter("user_password");
		if (email.isEmpty()) {
			request.setAttribute("errorMessage", "*Email cannot be empty");
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);
		} else if (password.isEmpty()) {
			request.setAttribute("errorMessage", "*Password cannot be empty");
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);
		} else {
			UserService service = BeanProvider.getUserService();
			User user = service.login(email, password);
			if (user != null && user.getId() != 0) {
				if (user.isAdmin()) {
					log.info(user.getId() + " logged In");
					HttpSession session = request.getSession();
					session.setAttribute("admin", user);
					response.sendRedirect("dashboard.jsp");
				} else {
					log.info(user.getId() + " logged In");
					HttpSession session = request.getSession();
					session.setAttribute("user", user);
					response.sendRedirect("home.jsp");
				}
			} else {
				request.setAttribute("errorMessage", "*Invalid email or password");
				request.setAttribute("errorEmail", email);
				RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
				rd.forward(request, response);
			}
		}
	}

}
