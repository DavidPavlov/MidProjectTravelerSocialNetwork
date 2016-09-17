package controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.User;

/**
 * Servlet implementation class PictureServlet
 */
@WebServlet("/PictureServlet")
public class PictureServlet extends HttpServlet {

	public static void returnProfilePic(User u, HttpServletResponse response) throws IOException {
		System.out.println("getting picture");
		File profilePicFile = new File("userPics", u.getProfilePic());
		response.setContentLength((int) profilePicFile.length());
		String contentType = "image/"
				+ profilePicFile.getName().split("[.]")[profilePicFile.getName().split("[.]").length - 1];
		response.setContentType(contentType);
		OutputStream out = response.getOutputStream();
		Files.copy(profilePicFile.toPath(), out);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			response.sendRedirect("index.jsp");
		} else {
			returnProfilePic(user, response);
		}
	}

}
