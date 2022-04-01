package controllers;

import java.io.IOException;
import java.io.InputStream;
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
import javax.servlet.http.Part;

import models.Address;
import models.User;
import utility.Validation;

/**
 * Servlet Filter implementation class ValidationFilter
 */
@MultipartConfig
public class ValidationFilter implements Filter {

    /**
     * Default constructor. 
     */
    public ValidationFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		response.setContentType("text/html");
		User  user = new User();
		user.setName(request.getParameter("user_name"));
		user.setEmail(request.getParameter("user_email"));
		user.setPhone(request.getParameter("user_phone"));
		user.setPassword(request.getParameter("user_password"));
		user.setGender(request.getParameter("gender"));
		user.setLang(request.getParameterValues("lang"));
		user.setGame(request.getParameter("games"));
		user.setSecQues(request.getParameter("secQues"));
		Part filePart = request.getPart("user_photo"); 
		InputStream photo = filePart.getInputStream();
		user.setPhoto(photo);
		List<Address> addresses = new ArrayList<>();
		String[] street = request.getParameterValues("user_street");
		String[] city = request.getParameterValues("user_city");
		String[] state = request.getParameterValues("user_state");
		for(int i = 0; i < street.length; i++) {
			Address address = new Address();
			address.setStreet(street[i]);
			address.setCity(city[i]);
			address.setState(state[i]);
			addresses.add(address);
		}
		if(Validation.error.isEmpty())
			chain.doFilter(request, response);
		else {
			RequestDispatcher rd = request.getRequestDispatcher("registration.jsp");
			request.setAttribute("error", Validation.error);
			rd.forward(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
