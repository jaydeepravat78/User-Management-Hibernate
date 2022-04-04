package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import models.User;
import services.UserServiceImpl;
import services.UserService;

/**
 * Servlet implementation class Login
 */
public class LoginController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(RegisterController.class.getClass());

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		BasicConfigurator.configure();
		String email = request.getParameter("user_email");
		String password = request.getParameter("user_password");
		UserService service = new UserServiceImpl();
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
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);
		}
	}

}
