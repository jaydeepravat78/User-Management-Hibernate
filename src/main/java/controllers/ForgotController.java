package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import models.User;
import services.UserServiceImpl;
import utility.Validation;
import services.UserService;

/**
 * Servlet implementation class Forgot
 */
public class ForgotController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(ForgotController.class.getClass());
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = new User();
		user.setEmail(request.getParameter("user_email"));
		user.setSecQues(request.getParameter("secQues"));
		user.setGame(request.getParameter("games"));
		user.setPassword(request.getParameter("user_password"));
		String psw = request.getParameter("confirm_psw");
		String error = "";
		if(user.getEmail().isEmpty()) {
			error += "Email cannot be empty";
		}
		error += Validation.checkSecQues(user.getSecQues());
		error += Validation.checkGame(user.getGame());
		error += Validation.checkPassword(user.getPassword());
		error += Validation.confirmPassword(user.getPassword(), psw);
		if(error.isEmpty()) {
		UserService service = new UserServiceImpl();
			if(service.forgotPsw(user)) {
				log.info(user.getEmail() +  " Changed password");
				response.sendRedirect("index.jsp");
			}
			else {
				request.setAttribute("errorEmail", user.getEmail());
				request.setAttribute("errorMessage", "Unable to change password! Please check your input!!");
	            RequestDispatcher rd = request.getRequestDispatcher("forgot.jsp");
	            rd.forward(request, response);
			}
		}
		else {
			request.setAttribute("errorMessage", error);
            RequestDispatcher rd = request.getRequestDispatcher("forgot.jsp");
            rd.forward(request, response); 
		}
	}

}
