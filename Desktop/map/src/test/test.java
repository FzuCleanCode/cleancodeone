package test;

import dao.UserDao;

public class test {
	
	public static void main(String[] args) {
		add();
	}
	public static void add(){
		UserDao userDao=new UserDao();
		userDao.addFriend("11","123");
		
	}
}
