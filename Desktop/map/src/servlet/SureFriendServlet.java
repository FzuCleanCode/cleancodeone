package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.UserService;

public class SureFriendServlet extends HttpServlet {
	private UserService userService;
	
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id=request.getParameter("id");
		String friendid=request.getParameter("friendid");
		System.out.println("111111111111");
		userService=new UserService();
		userService.sureFriend(id,friendid);
		
	}

	
	public void init() throws ServletException {
		// Put your code here
	}

}
