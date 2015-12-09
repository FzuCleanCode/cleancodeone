package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import service.UserService;

public class GetMessageServlet extends HttpServlet {
	private UserService userService;
	
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		String id=request.getParameter("id");
		StringBuilder builder=new StringBuilder();
		builder.append('[');
		userService=new UserService();
		List<User> friends=userService.getMessage(id);
		for (User user :friends) {
			
			builder.append('{');
			builder.append("id:\"").append(user.getId()).append("\",");
			builder.append("password:\"").append(user.getPassword()).append("\",");
			builder.append("isonline:\"").append(user.getIsonline()).append("\",");
			builder.append("longitude:\"").append(user.getLongitude()).append("\",");
			builder.append("latitude:\"").append(user.getLatitude()).append("\"},");
		}
		builder.deleteCharAt(builder.length()-1);
		builder.append(']');
		request.setAttribute("json", builder.toString());
		request.getRequestDispatcher("/WEB-INF/page/GetMessage.jsp").forward(request,response);
		
	
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	
	public void init() throws ServletException {
		// Put your code here
	}

}
