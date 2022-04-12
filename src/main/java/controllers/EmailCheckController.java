package controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.UserServiceImpl;
import services.UserService;

/**
 * Servlet implementation class EmailCheckController
 */
public class EmailCheckController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("user_email");
		PrintWriter out = response.getWriter();
		UserService service = new UserServiceImpl();
		out.print(!service.checkEmail(email));
	}

}
