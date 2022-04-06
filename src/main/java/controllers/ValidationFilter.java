package controllers;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.MultipartConfig;

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

		String name = request.getParameter("user_name");
		String email = request.getParameter("user_email");
		String phone = request.getParameter("user_phone");
		String password = request.getParameter("user_password");
		String confimPsw = request.getParameter("confirm_psw");
		String gender = request.getParameter("gender");
		String[] lang = request.getParameterValues("lang");
		String game = request.getParameter("games");
		String secQues = request.getParameter("secQues");
		String[] street = request.getParameterValues("user_street");
		String[] city = request.getParameterValues("user_city");
		String[] state = request.getParameterValues("user_state");
		String error = "";
		error += Validation.checkName(name);
		error += Validation.checkEmail(email);
		error += Validation.checkPhone(phone);
		error += Validation.checkPassword(password);
		error += Validation.confirmPassword(password, confimPsw);
		error += Validation.checkGender(gender);
		error += Validation.checkLang(lang);
		error += Validation.checkGame(game);
		error += Validation.checkSecQues(secQues);
		error += Validation.checkStreet(street);
		error += Validation.checkCity(city);
		error += Validation.checkState(state);
		if (error.isEmpty())
			chain.doFilter(request, response);
		else {
			RequestDispatcher rd = request.getRequestDispatcher("registration.jsp");
			request.setAttribute("error", error);
			rd.forward(request, response);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
