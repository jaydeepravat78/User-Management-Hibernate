package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.User;
import services.UserServiceImpl;
import services.UserService;

/**
 * Servlet implementation class Forgot
 */
public class ForgotController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ForgotController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = new User();
		user.setEmail(request.getParameter("user_email"));
		user.setSecQues(request.getParameter("secQues"));
		user.setGame(request.getParameter("games"));
		user.setPassword(request.getParameter("user_password"));
		UserService service = new UserServiceImpl();
		if(service.forgotPsw(user)) {
			response.sendRedirect("index.jsp");
		}
		else {
			request.setAttribute("errorMessage", "*Something went wrong unable to change password");
            RequestDispatcher rd = request.getRequestDispatcher("forgot.jsp");
            rd.forward(request, response); 
		}
	}

}
