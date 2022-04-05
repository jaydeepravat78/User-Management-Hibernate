package controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import models.Address;
import models.User;


import services.UserServiceImpl;
import services.UserService;

/**
 * Servlet implementation class Register
 */
@MultipartConfig
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	private static final Logger log = Logger.getLogger(RegisterController.class.getClass());


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 *  
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BasicConfigurator.configure();
		
		HttpSession session = request.getSession(false);
		String redirect = "";
		if(session != null)
			redirect += "dashboard.jsp";
		else
			redirect += "index.jsp";
		
		User  user = new User();
		user.setName(request.getParameter("user_name").trim());
		user.setEmail(request.getParameter("user_email").trim());
		user.setPhone(request.getParameter("user_phone").trim());
		user.setPassword(request.getParameter("user_password").trim());
		user.setGender(request.getParameter("gender").trim());
		user.setLang(request.getParameterValues("lang"));
		user.setGame(request.getParameter("games").trim().trim());
		user.setSecQues(request.getParameter("secQues").trim());
		Part filePart = request.getPart("user_photo"); 
		InputStream userPic = filePart.getInputStream();
		
		user.setPhoto(userPic);
		List<Address> addresses = new ArrayList<>();
		String[] street = request.getParameterValues("user_street");
		String[] city = request.getParameterValues("user_city");
		String[] state = request.getParameterValues("user_state");
		for(int i = 0; i < street.length; i++) {
			Address address = new Address();
			address.setStreet(street[i].trim());
			address.setCity(city[i].trim());
			address.setState(state[i].trim());
			addresses.add(address);
		}
		user.setAddresses(addresses);
		UserService service = new UserServiceImpl();
		if(service.signUp(user)) {
			log.info("User signup successfully");
			response.sendRedirect(redirect);
		}
		else {
			log.error("failed to signup");	
			response.sendRedirect("registration.jsp");
		}

	}

}
