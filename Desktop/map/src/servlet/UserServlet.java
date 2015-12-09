package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import service.UserService;

public class UserServlet extends HttpServlet {

	private UserService userService=null;
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("hhhhhhhhhh");
		String method=request.getParameter("method");
		if (method.equals("hasRegist")) {
			hasRegist(request, response);
		}
//		String format=request.getParameter("format");
//		if (format.equals("user")) {
//			
//			getAllUser();
//		}
		
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user=new User();
		user.setId(request.getParameter("id"));
		user.setPassword(request.getParameter("password"));
		user.setIsonline("0");
//		System.out.println(user);
		userService=new UserService();
		userService.regist(user);
		
		
	}


	private void getAllUser() {
		// TODO Auto-generated method stub
		userService=new UserService();
		userService.getAllUser();
	}


	public void init() throws ServletException {
		// Put your code here
	}

	
	public static void hasRegist(HttpServletRequest request,HttpServletResponse response){
		UserService userService =new UserService();
		String id=request.getParameter("id");
		StringBuilder builder=new StringBuilder();
		builder.append("[{hasRegist:");
		builder.append('"');
		if(userService.hasRegist(id)){	
			
			builder.append("true");
			
		}else {		
			builder.append("false");
		

		}
		try {
			builder.append('"');
			builder.append("}]");
			request.setAttribute("json", builder.toString());
			request.getRequestDispatcher("/WEB-INF/page/hasRegist.jsp").forward(request,response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}
}
