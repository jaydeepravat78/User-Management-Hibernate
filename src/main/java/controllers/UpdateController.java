package controllers;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import models.User;
import services.UserService;
import services.UserServiceImpl;

/**
 * Servlet implementation class UpdateController
 */

@MultipartConfig
public class UpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateController() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(request.getParameter("id"));
		UserService service = new UserServiceImpl();
		User u = service.getUser(id);
		u.setId(id);
		request.setAttribute("userData", u);
		RequestDispatcher rd = request.getRequestDispatcher("registration.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		User user = new User();
		user.setId(Integer.parseInt(request.getParameter("id")));
		user.setName(request.getParameter("user_name"));
		user.setPhone(request.getParameter("user_phone"));
		user.setPassword(request.getParameter("user_password"));
		user.setGender(request.getParameter("gender"));
		user.setLang(request.getParameterValues("lang"));
		user.setGame(request.getParameter("games"));
		user.setSecQues(request.getParameter("secQues"));
		Part filePart = request.getPart("user_photo");
		InputStream photo = filePart.getInputStream();
		user.setPhoto(photo);
		UserService service = new UserServiceImpl();
		if (service.updateUser(user)) {
			if (session != null && session.getAttribute("admin") != null) {
				response.sendRedirect("dashboard.jsp");
			} else if (session != null & session.getAttribute("user") != null) {
				session.setAttribute("user", service.getUser(user.getId()));
				response.sendRedirect("home.jsp");
			}

		} else {
			response.sendRedirect("registration.jsp");
		}
	}
}
