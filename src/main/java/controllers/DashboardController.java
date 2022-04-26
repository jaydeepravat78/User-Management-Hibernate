package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import models.User;
import utility.BeanProvider;
import services.UserService;

/**
 * Servlet implementation class DashboardController
 */
public class DashboardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserService service = BeanProvider.getUserService();
		List<User> users = service.getUsers();
		List<User> usersdata = new ArrayList<>();
		for(User user: users) {
			User userdata = new User();
			userdata.setId(user.getId());
			userdata.setName(user.getName());
			userdata.setEmail(user.getEmail());
			userdata.setPhone(user.getPhone());
			userdata.setGame(user.getGame());
			userdata.setGender(user.getGender());
			usersdata.add(userdata);
		}
		JsonObject jobj = new JsonObject();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		jobj.addProperty("status", "success");
		jobj.add("data", gson.toJsonTree(usersdata));
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(jobj);
		out.flush();
	}

}
