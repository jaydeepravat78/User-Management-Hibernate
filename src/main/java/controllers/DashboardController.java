package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import models.User;
import services.UserServiceImpl;
import services.UserService;

/**
 * Servlet implementation class DashboardController
 */
public class DashboardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserService service = new UserServiceImpl();
		List<User> users = service.getUsers();
		JsonObject jobj = new JsonObject();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		jobj.addProperty("status", "success");
		jobj.add("data", gson.toJsonTree(users));
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(jobj);
		out.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	

}
