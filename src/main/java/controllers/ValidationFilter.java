package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.MultipartConfig;

import models.Address;
import models.User;
import services.UserService;
import services.UserServiceImpl;
import utility.BeanProvider;
import utility.Validation;

/**
 * Servlet Filter implementation class ValidationFilter
 */
@MultipartConfig
public class ValidationFilter implements Filter {

	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String id = request.getParameter("id");
		User userData = BeanProvider.getUserBean();
		
		userData.setName(request.getParameter("user_name"));
		userData.setPhone(request.getParameter("user_phone"));
		userData.setPassword(request.getParameter("user_password"));
		String confimPsw = request.getParameter("confirm_psw");
		userData.setGender(request.getParameter("gender"));
		userData.setLang(request.getParameterValues("lang"));
		userData.setGame(request.getParameter("games"));
		userData.setSecQues(request.getParameter("secQues"));
		List<Address> userAddresses = new ArrayList<>();
		String[] street = request.getParameterValues("user_street");
		String[] city = request.getParameterValues("user_city");
		String[] state = request.getParameterValues("user_state");
		for (int i = 0; i < street.length; i++) {
			Address userAddress = new Address();
			userAddress.setStreet(street[i].trim());
			userAddress.setCity(city[i].trim());
			userAddress.setState(state[i].trim());
			userAddresses.add(userAddress);
		}
		userData.setAddresses(userAddresses);
		String error = "";
		if (id == null) {
			userData.setEmail(request.getParameter("user_email"));
			error += Validation.checkEmail(userData.getEmail());
		}
		error += Validation.checkName(userData.getName());
		error += Validation.checkPhone(userData.getPhone());
		error += Validation.checkPassword(userData.getPassword());
		error += Validation.confirmPassword(userData.getPassword(), confimPsw);
		error += Validation.checkGender(userData.getGender());
		error += Validation.checkLang(userData.getLang());
		error += Validation.checkGame(userData.getGame());
		error += Validation.checkSecQues(userData.getSecQues());
		error += Validation.checkStreet(street);
		error += Validation.checkCity(city);
		error += Validation.checkState(state);
		if (error.isEmpty())
			chain.doFilter(request, response);
		else {
			if (id == null) {
				RequestDispatcher rd = request.getRequestDispatcher("registration.jsp");
				request.setAttribute("error", error);
				request.setAttribute("userError", userData);
				rd.forward(request, response);
			} else {
				RequestDispatcher rd = request.getRequestDispatcher("registration.jsp");
				UserService service = new UserServiceImpl();
				userData.setEmail(service.getUser(Integer.parseInt(id)).getEmail());
				request.setAttribute("id", id);
				request.setAttribute("error", error);
				request.setAttribute("userData", userData);
				rd.forward(request, response);
			}
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
