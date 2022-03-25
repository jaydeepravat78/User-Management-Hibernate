package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * Servlet implementation class Register
 */
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	private static final Logger log = Logger.getLogger(Register.class.getClass());

	public Register() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		BasicConfigurator.configure(); 
		String name = request.getParameter("user_name");
		String email = request.getParameter("user_email");
		String phone = request.getParameter("user_phone");
		String password = request.getParameter("user_password");
		String gender = request.getParameter("gender");
		String[] lang = request.getParameterValues("lang");
		String[] street = request.getParameterValues("user_city");
		String game = request.getParameter("games");
		String profile = request.getParameter("user_photo");
		System.out.println(name + " " + email + " " + phone + " " + password + " " + gender + " " + game + " " + profile);
		for(String l: lang)
			System.out.println("Language " + l);
		for(String s: street)
			System.out.println("Street " + s);
		log.info("In registration");
		response.sendRedirect("index.jsp");
	}

}
