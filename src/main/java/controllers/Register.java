package controllers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import org.apache.commons.io.*;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import services.KeyGeneration;

/**
 * Servlet implementation class Register
 */
@MultipartConfig
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	private static final Logger log = Logger.getLogger(Register.class.getClass());

	public Register() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BasicConfigurator.configure();
		String name = request.getParameter("user_name");
		String email = request.getParameter("user_email");
		String phone = request.getParameter("user_phone");
		String password = request.getParameter("user_password");
		String gender = request.getParameter("gender");
		String[] lang = request.getParameterValues("lang");
		String[] street = request.getParameterValues("user_street");
		String[] city = request.getParameterValues("user_city");
		String[] state = request.getParameterValues("user_state");
		String game = request.getParameter("games");
		String profile = request.getParameter("user_photo");

		Part filePart = request.getPart("user_photo"); // Retrieves <input type="file" name="file">
		String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
		InputStream fileContent = filePart.getInputStream();
		String encrptedKey = KeyGeneration.encrypt(password);
//		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
//		if(isMultipart) {
//			try {
//				List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
//				for (FileItem item : multiparts) {
//			        if (!item.isFormField()) {
//			        	String fieldname = item.getFieldName();
//		                String filename = FilenameUtils.getName(item.getName());
//		                System.out.println(fieldname + " " + filename);
//			        } else {
//			            String name = item.getFieldName();
//			            String value = item.getString();
//			            System.out.println(name + " " + value);
//			        }
//			}
//			}catch (FileUploadException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		log.info("In registration");
		response.sendRedirect("index.jsp");
	}

}
