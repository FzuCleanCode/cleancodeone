package service;

import java.util.List;

import model.User;
import dao.UserDao;

public class UserService {
	private UserDao userDao=new UserDao();

	public void getAllUser() {
		// TODO Auto-generated method stub
		
	}

	public void regist(User user) {
		// TODO Auto-generated method stub
		userDao.add(user);
//		System.out.println("userservice");
	}
	
	public User Loginin(String id,String password){
		return userDao.Loginin(id, password);
	}

	public boolean hasRegist(String id) {
		// TODO Auto-generated method stub
		return userDao.hasRegist(id);
	}

	public void updata(User user) {
		// TODO Auto-generated method stub
		userDao.update(user);
	}

	public List<User> getFriends(String id) {
		// TODO Auto-generated method stub
		return userDao.getFriends(id);
	}

	public List<User> getOnlineFriends(String id) {
		// TODO Auto-generated method stub
		return userDao.getOnlineFriend(id);
	}

	public List<User> getNonlineFriends(String id) {
		// TODO Auto-generated method stub
		return userDao.getNonlineFriend(id);
	}

	public void addFriend(String id,String friendid)  {
		// TODO Auto-generated method stub
		
		userDao.addFriend(id,friendid);
	}

	public List<User> getMessage(String id) {
		return userDao.GetMessage(id);
		// TODO Auto-generated method stub
		
	}

	public void sureFriend(String id, String friendid) {
		// TODO Auto-generated method stub
		userDao.sureFriend(id,friendid);
	}
}
