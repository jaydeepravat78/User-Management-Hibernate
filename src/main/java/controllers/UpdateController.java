package controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import models.Address;
import models.User;
import services.UserService;
import services.UserServiceImpl;

/**
 * Servlet implementation class UpdateController
 */

@MultipartConfig
public class UpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(request.getParameter("id"));
		UserService service = new UserServiceImpl();
		User user = service.getUser(id);
		request.setAttribute("userData", user);
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
		UserService service = new UserServiceImpl();
		User newUserData = new User();
		newUserData.setId(Integer.parseInt(request.getParameter("id")));
		newUserData.setName(request.getParameter("user_name").trim());
		newUserData.setPhone(request.getParameter("user_phone").trim());
		newUserData.setPassword(request.getParameter("user_password").trim());
		newUserData.setGender(request.getParameter("gender").trim());
		newUserData.setLang(request.getParameterValues("lang"));
		newUserData.setGame(request.getParameter("games").trim());
		newUserData.setSecQues(request.getParameter("secQues").trim());
		Part filePart = request.getPart("user_photo");
		if (filePart.getSize() > 0) {
			InputStream userPic = filePart.getInputStream();	
			newUserData.setPhoto(userPic);
		}
		else {
			newUserData.setPhoto(service.getUser(newUserData.getId()).getPhoto());
		}
		List<Address> newAddresses = new ArrayList<>();
		String[] street = request.getParameterValues("user_street");
		String[] city = request.getParameterValues("user_city");
		String[] state = request.getParameterValues("user_state");
		String[] address_id = request.getParameterValues("address_id");
		for (int i = 0; i < street.length; i++) {
			Address address = new Address();
			address.setStreet(street[i]);
			address.setCity(city[i]);
			address.setState(state[i]);
			int addressId = 0;
			if (!address_id[i].equals(""))
				addressId = Integer.parseInt(address_id[i]);
			address.setAddress_id(addressId);
			newAddresses.add(address);
		}
		if (service.updateUser(newUserData)) {
			service.updateNewAddress(newAddresses, newUserData.getId());
			if (session != null && session.getAttribute("admin") != null) {
				response.sendRedirect("dashboard.jsp");
			} else if (session != null & session.getAttribute("user") != null) {
				session.setAttribute("user", service.getUser(newUserData.getId()));
				response.sendRedirect("home.jsp");
			}

		} else {
			response.sendRedirect("registration.jsp");
		}
	}
}
