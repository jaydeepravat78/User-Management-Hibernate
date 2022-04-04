package controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import models.User;
import services.UserService;
import services.UserServiceImpl;
import utility.KeyGeneration;

/**
 * Servlet implementation class UsersController
 */
@MultipartConfig(maxFileSize = 1216584)
public class UsersController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(UsersController.class.getClass());

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BasicConfigurator.configure();
		Part excelFile = request.getPart("excelFile");
		InputStream out = excelFile.getInputStream();
		XSSFWorkbook wb = new XSSFWorkbook(out);
		XSSFSheet sheet = wb.getSheetAt(0); // creating a Sheet object to retrieve object
		Iterator<Row> itr = sheet.iterator(); // iterating over excel file
		List<User> users = new ArrayList<User>();
		while (itr.hasNext()) {
			Row row = itr.next();
			Iterator<Cell> cellIterator = row.cellIterator(); // iterating over each column
			User user = new User();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				int columnIndex = cell.getColumnIndex();
				switch (columnIndex) {
				case 0:
					user.setName(cell.getStringCellValue());
					break;
				case 1:
					user.setEmail(cell.getStringCellValue());
					break;
				case 2:
					String enrcyptPsw = KeyGeneration.encrypt(cell.getStringCellValue());
					user.setPassword(enrcyptPsw);
					break;
				case 3:
					user.setPhone(String.valueOf((long) cell.getNumericCellValue()));
					break;
				case 4:
					user.setGender(cell.getStringCellValue());
					break;
				case 5:
					user.setLang(cell.getStringCellValue().split(" "));
					break;
				case 6:
					user.setGame(cell.getStringCellValue());
					break;
				case 7:
					user.setSecQues(cell.getStringCellValue());
					break;
				default:
				}
			}
			users.add(user);
		}

		UserService service = new UserServiceImpl();
		if (service.addAllUsers(users)) {
			log.info(users.size() + " users added");
			response.sendRedirect("dashboard.jsp");
		} else {
			log.error("Fail to add users");
		}
	}
}
