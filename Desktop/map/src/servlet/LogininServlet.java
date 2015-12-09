package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import service.UserService;

public class LogininServlet extends HttpServlet {

	private UserService userService;
	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user=new User();
		userService=new UserService();
		StringBuilder builder=new StringBuilder();
		builder.append("[{login:");
		builder.append('"');
		user.setId(request.getParameter("id"));
		user.setPassword(request.getParameter("password"));
		user=userService.Loginin(user.getId(),user.getPassword());
		if (user==null) {
			builder.append("false");
			
		}else{
			builder.append("true");
		}
		builder.append('"');
		builder.append("}]");
		request.setAttribute("json", builder.toString());
		request.getRequestDispatcher("/WEB-INF/page/login.jsp").forward(request,response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user=new User();
		user.setId(request.getParameter("id"));
		user.setPassword(request.getParameter("password"));
		user.setLatitude(request.getParameter("latitude"));
		user.setLongitude(request.getParameter("longitude"));
		user.setIsonline(request.getParameter("isonline"));
		userService=new UserService();
		
		userService.updata(user);
		
	}

	
	public void init() throws ServletException {
		// Put your code here
	}

}
